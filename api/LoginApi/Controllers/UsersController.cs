using System;
using System.Collections.Generic;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using System.Threading.Tasks;
using LoginApi.Configuration;
using LoginApi.Models;
using LoginApi.Models.Responces;
using LoginApi.Services;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Options;
using Microsoft.IdentityModel.Tokens;
using MongoDB.Driver;

namespace LoginApi.Controllers
{
    [Route("users")]
    [ApiController]
    public class UsersController : ControllerBase
    {
        private readonly UserService _userService;
        private readonly JwtConfig _jwtConfig;
        public UsersController(UserService userService, IOptionsMonitor<JwtConfig> optionsMonitor)
        {
            _userService = userService;
            _jwtConfig = optionsMonitor.CurrentValue;
        }
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)]
        [HttpGet]
        public async Task<ActionResult<List<User>>> GetAsync() =>
            await _userService.GetAsync();

        [HttpGet("{Email:length(24)}", Name = "GetBook")]
        public async Task<ActionResult<User>> GetByEmailAsync(string email)
        {
            var user = await _userService.GetByEmailAsync(email);
            if(user == null)
                return NotFound();
            return user;
        }

        [HttpPost]
        public async Task<ActionResult<User>> CreateAsync(User user){
            if(!ModelState.IsValid)
                {
                    return BadRequest(new AuthResult{
                    Errors = new List<string>(){
                        "Invalid payload"
                    },
                    Success = false
                    });
                }
            User newUser = new(){
                Id = Guid.NewGuid().ToString(),
                UserName = user.UserName,
                Email = user.Email.ToLower(),
                FirstName = user.FirstName,
                LastName = user.LastName,
                Password = hashPassword(user.Password),
                Scopes = user.Scopes
            };
            
            if(newUser.loginDates == null)
            {
                newUser.loginDates = new();
            }          
            newUser.loginDates.Add(DateTime.UtcNow.ToString());
            await _userService.CreateAsync(newUser);
            var jwtToken = GenerateJwtToken(newUser);
            return Ok(new AuthResult(){
                Id = newUser.Id,
                UserName = newUser.UserName,
                Email = newUser.Email,
                FirstName = newUser.FirstName,
                LastName = newUser.LastName,
                loginDates = newUser.loginDates,
                Success = true,
                Scopes = newUser.Scopes,
                Token = jwtToken
                });
        }

        [HttpPut("{id:length(36)}")]
        public async Task<IActionResult> UpdateAsync(string id, User userIn)
        {
            var user = await _userService.GetAsync(id);
            if(user == null){
                return NotFound();
            }
                
            
            await _userService.UpdateAsync(id, userIn);
            return NoContent();
        }

        [HttpPut("changepass/{id:length(36)}")]
        public async Task<IActionResult> ChangePass(string id, User userIn)
        {
            var user = await _userService.GetAsync(id);
            if(user == null){
                return NotFound();
            }
                
            if(!BCrypt.Net.BCrypt.Verify(userIn.oldPassword, user.Password))
            {
                return BadRequest(new AuthResult{
                        Errors = new List<string>(){
                            "Invalid change password request"
                        },
                        Success = false
                    });
            }
            userIn.Password = hashPassword(userIn.Password);
            userIn.oldPassword = null;
            
            await _userService.UpdateAsync(id, userIn);
            return NoContent();
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteAsync(string id)
        {
            var user = _userService.GetAsync(id);
            if(user == null){
                return NotFound();
            }
            await _userService.RemoveAsync(id);
            return NoContent();
        }
        private string hashPassword(string password)
        {
            const int workFactor = 13;
            return BCrypt.Net.BCrypt.HashPassword(password, workFactor);
        }   

        private string GenerateJwtToken(User user)
        {
            var jwtTokenHandler = new JwtSecurityTokenHandler();

            var key = Encoding.ASCII.GetBytes(_jwtConfig.Secret);
            
            var tokenDescriptor = new SecurityTokenDescriptor
            {
                Subject = new ClaimsIdentity(new []
                {
                    new Claim("Id", user.Id),
                    new Claim(JwtRegisteredClaimNames.Email, user.Email),
                    new Claim(JwtRegisteredClaimNames.Sub, user.Email),
                    new Claim(JwtRegisteredClaimNames.Jti, Guid.NewGuid().ToString())
                }),
                Expires = DateTime.UtcNow.AddHours(6),
                SigningCredentials = new SigningCredentials(new SymmetricSecurityKey(key), SecurityAlgorithms.HmacSha256Signature)
            };

            var token = jwtTokenHandler.CreateToken(tokenDescriptor);
            var jwtToken = jwtTokenHandler.WriteToken(token);

            return jwtToken;
        }


    }
}