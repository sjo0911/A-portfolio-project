const express = require('express')
const app = express();

app.use(express.static('./dist/angularMain'));
app.get('/', function (req, res) {
    res.sendFile('index.html', {root: 'dist/angularMain'});
});

app.get('/javagames/memorygame/', function (req, res) {
    res.sendFile('index.html', {root: 'dist/javagames/memorygame'});
});

app.use('/javagames/memorygame/public', express.static('./dist/javagames/memorygame/public'));

app.listen(process.env.PORT || 8080);