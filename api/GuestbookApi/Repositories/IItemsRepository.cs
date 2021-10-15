using System;
using System.Collections;
using System.Collections.Generic;
using System.Threading.Tasks;
using GuestbookApi.Entities;

namespace GuestbookApi.Repositories
{
        public interface IItemsRepository
    {
        Task<Post> GetPostAsync(string id);
        Task<IEnumerable<Post>> GetPostsByCategoryAsync(string category);
        Task<IEnumerable<Post>> GetPostsAsync();
        Task CreatePostAsync(Post post);
        Task UpdatePostAsync(Post post);
        Task DeletePostAsync(string id);
    }
}