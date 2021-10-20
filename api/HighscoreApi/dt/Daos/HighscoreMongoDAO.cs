using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using HighscoreApi.Configurations;
using HighscoreApi.dt.Enteties;
using MongoDB.Driver;

namespace HighscoreApi.Daos
{
    public class HighscoreMongoDAO : IHighscoreMongoDAO
    {
        private readonly IMongoCollection<Highscore> _highscores;

        public HighscoreMongoDAO(IHighscoreDbSettings settings) {
            var client = new MongoClient(settings.ConnectionString);
            var database = client.GetDatabase(settings.DatabaseName);
            _highscores = database.GetCollection<Highscore>(settings.CollectionName);
        }

        public async Task<List<Highscore>> getAsync() =>
            await _highscores.Find(hs => true).ToListAsync();

        public async Task<Highscore>  getByName(string name) =>
            await _highscores.Find(hs => hs.name == name).FirstOrDefaultAsync();
            
        public async Task createAsync(Highscore highscore) =>
            await _highscores.InsertOneAsync(highscore);
        

        public async Task UpdateAsync(string id, Highscore highscore) =>
            await _highscores.ReplaceOneAsync(hs => hs.id == id, highscore);
        
        public async Task RemoveAsync(string id) =>
            await _highscores.DeleteOneAsync(hs => hs.id == id);
    }
}