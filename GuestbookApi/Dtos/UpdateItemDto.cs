using System.ComponentModel.DataAnnotations;

namespace GuestbookApi.Dtos
{
    public record UpdatePostDto
    {
        [Required]
        public string Header {get; init;}
        [Required]
        public string Text {get; init;}
    }
}