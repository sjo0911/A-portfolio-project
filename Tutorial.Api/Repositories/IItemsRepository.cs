using System;
using System.Collections;
using System.Collections.Generic;
using System.Threading.Tasks;
using Tutorial.Api.Entities;

namespace Tutorial.Api.Repositories
{
        public interface IItemsRepository
    {
        Task<Item> GetItemAsync(Guid id);
        Task<IEnumerable<Item>> GetItemsAsync();
        Task CreateItemAsync(Item item);
        Task UpdateItemAsync(Item item);
        Task DeleteItemAsync(Guid id);
    }
}