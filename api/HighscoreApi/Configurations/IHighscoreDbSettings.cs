namespace HighscoreApi.Configurations
{
    public interface IHighscoreDbSettings
    {
        public string CollectionName{get; set;}
        public string ConnectionString{get; set;}
        public string DatabaseName{get; set;}
    }
}