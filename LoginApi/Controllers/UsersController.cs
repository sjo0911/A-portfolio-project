using System;
using System.Collections.Generic;
using System.IdentityModel.Tokens.Jwt;
using System.Text;
using System.Threading.Tasks;
using LoginApi.Models;
using LoginApi.Models.Responces;
using LoginApi.Services;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using MongoDB.Driver;

namespace LoginApi.Controllers
{
    [Route("users")]
    [ApiController]
    public class UsersController : ControllerBase
    {
        private readonly UserService _userService;
        public UsersController(UserService userService)
        {
            _userService = userService;
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
                Password = hashPassword(user.Password)
            };
            await _userService.CreateAsync(newUser);
            return CreatedAtAction("GetAsync", new { id = newUser.Id}, newUser );
        }

        [HttpPut("{id:length(36)}")]
        public async Task<IActionResult> UpdateAsync(string id, User userIn)
        {
            var user = await _userService.GetAsync(id);
            if(user == null){
                Console.WriteLine("hej");
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
                 Console.WriteLine("hejsan");
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
                Console.WriteLine("hej");
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


    }
}