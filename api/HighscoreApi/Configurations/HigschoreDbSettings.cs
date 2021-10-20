namespace HighscoreApi.Configurations
{
    public class HigschoreDbSettings : IHighscoreDbSettings
    {
        public string CollectionName{get; set;}
        public string ConnectionString{get; set;}
        public string DatabaseName{get; set;}
    }
}