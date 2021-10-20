using System.ComponentModel.DataAnnotations;

namespace HighscoreApi.bt.DTOs
{
    public class CreateHighscoreDTO
    {
        [Required]
        public string name{get;init;}
    }
}