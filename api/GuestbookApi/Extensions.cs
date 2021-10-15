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
                id = post.id,
                header = post.header,
                text = post.text,
                createdDate = post.createdDate,
                category = post.category,
                name = post.name
            };
        }
    }
}