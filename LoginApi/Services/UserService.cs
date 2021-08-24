using System.Collections.Generic;
using System.Runtime.CompilerServices;
using System.Threading.Tasks;
using LoginApi.Configuration;
using LoginApi.Models;
using MongoDB.Driver;

namespace LoginApi.Services
{
    public class UserService
    {
        private readonly IMongoCollection<User> _users;
        public UserService(IUserDbSettings settings)
        {
            var client = new MongoClient(settings.ConnectionString);
            var database = client.GetDatabase(settings.DatabaseName);
            _users = database.GetCollection<User>(settings.UsersCollectionName);
        }

        public Task<List<User>> GetAsync() =>
            _users.Find(user => true).ToListAsync();

        public async Task<User> GetByEmailAsync(string email) =>
            await _users.Find<User>(user => user.Email == email).FirstOrDefaultAsync();

        public async Task<User> GetAsync(string id) =>
            await _users.Find<User>(user => user.Id == id).FirstOrDefaultAsync();

        public async Task CreateAsync(User user)
        {
            await _users.InsertOneAsync(user);
        }

        public async Task UpdateAsync(string id, User user) =>
            await _users.ReplaceOneAsync(user => user.Id == id, user);
        
        public async Task RemoveAsync(string id) =>
            await _users.DeleteOneAsync(user => user.Id == id);
    }
}