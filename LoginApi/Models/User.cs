using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using MongoDB.Bson.Serialization.Attributes;

namespace LoginApi.Models
{
    public class User
    {
        [BsonId]
        public string Id{get; set;}
        [BsonRequired]
        public string UserName{get; set;}
        [BsonRequired]
        [EmailAddress]
        public string Email {get; set;}
        public string Password {get; set;}
        public string oldPassword{get; set;}
        public string FirstName{get; set;}
        public string LastName{get; set;}
        public List<string> loginDates{get; set;}
        public List<string> Scopes {get;set;}
    }
}