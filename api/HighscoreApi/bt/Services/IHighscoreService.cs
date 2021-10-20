using System.Threading.Tasks;
using HighscoreApi.bt.DTOs;

namespace HighscoreApi.bt.Services
{
    public interface IHighscoreService
    {
         Task createAsync(CreateHighscoreDTO createHighscoreDTO);
    }
}