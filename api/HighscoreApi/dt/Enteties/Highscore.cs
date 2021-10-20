using System;
using System.Collections.Generic;

namespace HighscoreApi.dt.Enteties
{
    public class Highscore
    {
        public string id {get;init;}
        public List<HighscoreEntry> highscoreEntries {get; init;}
        public string name {get; init;}
    }
}