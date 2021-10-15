using System.ComponentModel.DataAnnotations;

namespace GuestbookApi.Dtos
{
    public record CreatePostDto
    {
        [Required]
        public string header {get; init;}
        [Required]
        public string text {get; init;}
        [Required]
        public string category {get; init;}
        [Required]
        public string name {get; set;}
    }
}