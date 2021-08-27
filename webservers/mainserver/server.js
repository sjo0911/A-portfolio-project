const express = require('express')
const app = express();

app.use(express.static('./dist/angularMain'));
app.get('/*', function (req, res) {
    res.sendFile('index.html', {root: 'dist/angularMain'});
});

app.listen(process.env.PORT || 8080);