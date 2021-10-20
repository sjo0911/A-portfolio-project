using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using HighscoreApi.bt.DTOs;
using HighscoreApi.Daos;
using HighscoreApi.dt.Enteties;

namespace HighscoreApi.bt.Services
{
    public class HighscoreService : IHighscoreService
    {
        private readonly IHighscoreMongoDAO _highscoreMongoDao;
        public HighscoreService(IHighscoreMongoDAO highscoreMongoDAO){
            _highscoreMongoDao = highscoreMongoDAO;
        }

        public Task createAsync(CreateHighscoreDTO createHighscoreDTO){
            Highscore highscore = new Highscore() {
                id = Guid.NewGuid().ToString(),
                name = createHighscoreDTO.name,
                highscoreEntries = new List<HighscoreEntry>(),

            };
            return _highscoreMongoDao.createAsync(highscore);
        }

        public Task<Highscore>  getByName(string name){
            return _highscoreMongoDao.getByName(name);
        }

        public Boolean addEntryToHighscore(AddEntryDTO addEntryDTO){
            string highscoreName = addEntryDTO.highscoreName;
            Highscore highscoreToUpdate = _highscoreMongoDao.getByName(highscoreName).Result;
            highscoreToUpdate.highscoreEntries.Sort((x, y) => y.score.CompareTo(x.score));
            if(highscoreToUpdate.highscoreEntries.Count < 10 || highscoreToUpdate.highscoreEntries.Last().score < addEntryDTO.score ){
                HighscoreEntry newHighscoreEntry = new HighscoreEntry(){
                    userFirstName = addEntryDTO.userFirstName,
                    userLastName = addEntryDTO.userLastName,
                    email = addEntryDTO.email,
                    score = addEntryDTO.score
                };
                if(highscoreToUpdate.highscoreEntries.Last().score < addEntryDTO.score){
                    highscoreToUpdate.highscoreEntries.Remove(highscoreToUpdate.highscoreEntries.Last());
                }
                highscoreToUpdate.highscoreEntries.Add(newHighscoreEntry);
                highscoreToUpdate.highscoreEntries.Sort((x, y) => y.score.CompareTo(x.score));
                return _highscoreMongoDao.UpdateAsync(highscoreToUpdate.id, highscoreToUpdate).IsCompletedSuccessfully;
            } else {
                return false;
            }

            
        }
    }
}