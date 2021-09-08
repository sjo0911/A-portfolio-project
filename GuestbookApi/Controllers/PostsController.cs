using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using DnsClient.Internal;
using GuestbookApi.Dtos;
using GuestbookApi.Entities;
using GuestbookApi.Repositories;

using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;


namespace GuestbookApi.Controllers
{
    [ApiController]
    [Route("posts")]
    public class PostsController : ControllerBase
    {
        private readonly IItemsRepository repository;
        private readonly ILogger<PostsController> logger;
        public PostsController(IItemsRepository repository, ILogger<PostsController> logger)
        {
           this.repository = repository;
           this.logger = logger;
        }

        [HttpGet]
        public async Task<IEnumerable<PostDto>> GetPostsAsync()
        {
            var posts = (await repository.GetPostsAsync())
                .Select(post => post.AsDto());

            logger.LogInformation($"{DateTime.UtcNow.ToString("hh:mm:ss")}: Retrieved {posts.Count()} items");
            return posts;
        }
        // Get /items/{id}
        [HttpGet("{id}")]
        public async Task<ActionResult<PostDto>> GetPostAsync(string id){
            var post = await repository.GetPostAsync(id);
            if (post is null){
                return NotFound();
            } else {
                return post.AsDto();
            }
             
        }
        [HttpGet("{category}/category")]
        public async Task<IEnumerable<PostDto>> GetPostByCategoryAsync(string category){
            var posts = (await repository.GetPostsByCategoryAsync(category))
            .Select(post => post.AsDto());
            return posts; 
        }
        [HttpPost]
        public async Task<ActionResult<PostDto>> CreateItemAsync(CreatePostDto postDto)
        {
           Post post = new(){
               Id = Guid.NewGuid().ToString(),
               Header = postDto.Header,
               Text = postDto.Text,
               CreatedDate = DateTimeOffset.UtcNow,
               Category = postDto.Category,
               Name = postDto.Name
           };
           await repository.CreatePostAsync(post);
           return CreatedAtAction("CreateItemAsync", new { id = post.Id}, post.AsDto());
        }
        [HttpPut("{id}")]
        public async Task<ActionResult> UpdateItemAsync(string id, UpdatePostDto postDto) 
        {
            var existingItem = await repository.GetPostAsync(id);
            if ( existingItem is null)
            {
                return NotFound();
            } 
            Post updatedPost = existingItem with
            {
                Header = postDto.Header,
                Text = postDto.Text
            };
            await repository.UpdatePostAsync(updatedPost);
            return NoContent();
        }
        [HttpDelete("{id}")]
        public async Task<ActionResult> DeleteItemAsync(string id){
            var existingItem = await repository.GetPostAsync(id);
            if ( existingItem is null)
            {
                return NotFound();
            } 
            await repository.DeletePostAsync(id);
            return NoContent();
        }
    }
}