namespace LoginApi.Configuration
{
    public interface IUserDbSettings
    {
        public string UsersCollectionName{get; set;}
        public string ConnectionString{get; set;}
        public string DatabaseName{get; set;}
    }
}