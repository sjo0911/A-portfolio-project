using System.ComponentModel.DataAnnotations;

namespace GuestbookApi.Dtos
{
    public record UpdatePostDto
    {
        [Required]
        public string header {get; init;}
        [Required]
        public string text {get; init;}
    }
}