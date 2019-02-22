var app = require('./app_router');
var http = require('http');
var database = require('./database');

var url = process.env.DATA || "mongodb://127.0.0.1:27017/TravelMate";
const PORT = process.env.PORT || 8095;

app.set('port', PORT);

console.log("Server Start");

var server = http.createServer(app);

database.connect(url);

server.listen(PORT,function(err){
	var address = server.address();
	console.log('server listening on http://%s:%d',address.address,address.port);
	console.log('press CTRL+C to exit');
});

process.on('SIGINT',function(){
	server.close();
	database.close();
});

       


