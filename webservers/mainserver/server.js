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

app.get('/javagames/whackamole/', function (req, res) {
    res.sendFile('index.html', {root: 'dist/javagames/whackamole'});
});

app.use('/javagames/whackamole/public', express.static('./dist/javagames/whackamole/public'));

app.get('/javagames/connectfour/', function (req, res) {
    res.sendFile('index.html', {root: 'dist/javagames/connectfour'});
});

app.use('/javagames/connectfour/public', express.static('./dist/javagames/connectfour/public'));

app.get('/javagames/snake/', function (req, res) {
    res.sendFile('index.html', {root: 'dist/javagames/snake'});
});

app.use('/javagames/snake/public', express.static('./dist/javagames/snake/public'));

app.get('/javagames/spaceinvader/', function (req, res) {
    res.sendFile('index.html', {root: 'dist/javagames/spaceinvader'});
});

app.use('/javagames/spaceinvader/public', express.static('./dist/javagames/spaceinvader/public'));

app.listen(process.env.PORT || 8080);