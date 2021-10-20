namespace HighscoreApi.bt.DTOs
{
    public class AddEntryDTO
    {
        public string highscoreName {get;init;}
        public int score{get; init;}
        public string userFirstName{get;init;}
        public string userLastName{get;init;}
        public string email{get;init;}
    }
}