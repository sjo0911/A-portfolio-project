using System;
using System.Collections.Generic;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using System.Threading.Tasks;
using LoginApi.Configuration;
using LoginApi.Models;
using LoginApi.Models.Requests;
using LoginApi.Models.Responces;
using LoginApi.Services;
using Microsoft.AspNetCore.Components.Web;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Options;
using Microsoft.IdentityModel.Tokens;

namespace LoginApi.Controllers
{
    [Route("login")]
    [ApiController]
    public class LoginController : ControllerBase
    {
        private readonly UserService _userService;
        private readonly JwtConfig _jwtConfig;
        public LoginController(UserService userService, IOptionsMonitor<JwtConfig> optionsMonitor)
        {
            _userService = userService;
            _jwtConfig = optionsMonitor.CurrentValue;
        }

        [HttpPost]
        public async Task<IActionResult> Login([FromBody] UserLoginRequest req)
        {
            if(ModelState.IsValid)
            {
                var existingUser = await _userService.GetByEmailAsync(req.Email);

                if(existingUser == null){
                    return BadRequest(new AuthResult{
                        Errors = new List<string>(){
                            "Invalid login request"
                        },
                        Success = false
                    });
                }
                var isCorrect = BCrypt.Net.BCrypt.Verify(req.Password, existingUser.Password);
                
                if(!isCorrect){
                      return BadRequest(new AuthResult{
                        Errors = new List<string>(){
                            "Invalid login request"
                        },
                        Success = false
                    });
                }

                var jwtToken = GenerateJwtToken(existingUser);
                if(existingUser.loginDates == null)
                    existingUser.loginDates = new();
                existingUser.loginDates.Add(DateTime.UtcNow.ToString());
                await _userService.UpdateAsync(existingUser.Id, existingUser);
                return Ok(new AuthResult(){
                    Success = true,
                    Token = jwtToken
                });

            }
            return BadRequest(new AuthResult{
                Errors = new List<string>(){
                    "Invalid payload"
                },
                Success = false
            });
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