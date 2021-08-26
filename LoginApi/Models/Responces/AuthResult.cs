using System.Collections.Generic;

namespace LoginApi.Models.Responces
{
    public class AuthResult
    {
        public string Id{get; set;}
        public string UserName{get; set;}
        public string Token {get; set;}
        public string Email {get; set;}
        public string FirstName{get; set;}
        public string LastName{get; set;}
        public List<string> loginDates{get; set;}
        public bool Success {get; set;}
        public List<string> Errors {get; set;}
    }
}