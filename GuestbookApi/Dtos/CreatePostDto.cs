using System.ComponentModel.DataAnnotations;

namespace GuestbookApi.Dtos
{
    public record CreatePostDto
    {
        [Required]
        public string Header {get; init;}
        [Required]
        public string Text {get; init;}
        [Required]
        public string Category {get; init;}
        [Required]
        public string UserId {get; set;}
    }
}