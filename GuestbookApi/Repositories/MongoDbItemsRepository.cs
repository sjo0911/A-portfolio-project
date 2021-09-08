using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using GuestbookApi.Entities;
using MongoDB.Bson;
using MongoDB.Driver;

namespace GuestbookApi.Repositories
{
    public class MongoDbItemsRepository : IItemsRepository
    {
        private const string databaseName = "postsDatabase";
        private const string collectionName = "posts";
        private readonly IMongoCollection<Post> postsCollection;
        private readonly FilterDefinitionBuilder<Post> filterBuilder = Builders<Post>.Filter;
        public MongoDbItemsRepository(IMongoClient mongoClient)
        {
            var client = new MongoClient("mongodb+srv://sven:eriksson@cluster0.2si5o.mongodb.net/postsDatabase?retryWrites=true&w=majority");
            IMongoDatabase database = client.GetDatabase(databaseName);
            postsCollection = database.GetCollection<Post>(collectionName);
        }
        public async Task CreatePostAsync(Post post)
        {
            await postsCollection.InsertOneAsync(post);
        }
        

        public async Task DeletePostAsync(string id)
        {
            var filter = filterBuilder.Eq(item => item.id, id);
            await postsCollection.DeleteOneAsync(filter);
        }

        public async Task<Post> GetPostAsync(string id)
        {
            var filter = filterBuilder.Eq(item => item.id, id);
            return await postsCollection.Find(filter).SingleOrDefaultAsync();
        }

        public async Task<IEnumerable<Post>> GetPostsAsync()
        {
            return await postsCollection.Find(new BsonDocument()).ToListAsync();
        }
        public async Task<IEnumerable<Post>> GetPostsByCategoryAsync(string category)
        {
            var filter = filterBuilder.Eq(post => post.category, category);
            return await postsCollection.Find(filter).ToListAsync();
        }

        public async Task UpdatePostAsync(Post post)
        {
            var filter = filterBuilder.Eq(excistingItem => excistingItem.id, post.id);
            await postsCollection.ReplaceOneAsync(filter, post);
        }
    }
}