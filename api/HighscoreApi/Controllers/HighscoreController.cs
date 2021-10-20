using System.Threading.Tasks;
using HighscoreApi.bt.DTOs;
using HighscoreApi.bt.Services;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.ModelBinding;

namespace HighscoreApi.Controllers
{
    [Route("highscore")]
    [ApiController]
    public class HighscoreController : ControllerBase
    {
        private readonly IHighscoreService _highscoreService;

        public HighscoreController(IHighscoreService highscoreService){
            _highscoreService = highscoreService;
        }

        [HttpPost]
        public async Task<IActionResult> createHighscore([FromBody] CreateHighscoreDTO createHighscoreDTO){
            if(!ModelState.IsValid){
                return BadRequest();
            }
            await _highscoreService.createAsync(createHighscoreDTO);
            return Ok();
        }
    }
}