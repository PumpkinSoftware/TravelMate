var app = require('./app_router');
var http = require('http');
var database = require('./database');

var url = process.env.DATA || "mongodb://127.0.0.1:27017/TravelMate";
const PORT = process.env.PORT || 8095;

app.set('port', PORT);

console.log("Server Start");

var server = http.createServer(app);


database.connect(url);

server.listen(PORT,function(){
	console.log('Server listen on port '+PORT);
});

       


