using System;

namespace GuestbookApi.Dtos
{
        public record PostDto
    {
        public string id {get; init;}
        public string header {get; init;}
        public string text {get; init;}
        public DateTimeOffset createdDate {get; init;}
        public string category {get; init;}
        public string name {get; set;}
    }
}