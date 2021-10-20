using System.Collections.Generic;
using System.Threading.Tasks;
using HighscoreApi.dt.Enteties;

namespace HighscoreApi.Daos
{
    public interface IHighscoreMongoDAO
    {
          Task<List<Highscore>> getAsync();
          Task<Highscore>  getByName(string name);
          Task createAsync(Highscore highscore) ;
          Task UpdateAsync(string id, Highscore highscore);
          Task RemoveAsync(string id);

    }
}