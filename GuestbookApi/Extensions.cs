using System.ComponentModel;
using GuestbookApi.Dtos;
using GuestbookApi.Entities;
using Microsoft.Extensions.Configuration.UserSecrets;

namespace GuestbookApi
{
    public static class Extensions{
        public static PostDto AsDto(this Post post)
        {
            return new PostDto
            {   
                Id = post.Id,
                Header = post.Header,
                Text = post.Text,
                CreatedDate = post.CreatedDate,
                Category = post.Category,
                UserId = post.UserId
            };
        }
    }
}