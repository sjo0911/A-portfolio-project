using System;

namespace GuestbookApi.Entities
{
    public record Post
    {
        public string Id {get; init;}
        public string Header {get; init;}
        public string Text {get; init;}
        public DateTimeOffset CreatedDate {get; init;}
        public string Category {get; init;}
        public string Name {get; set;}
    }
}