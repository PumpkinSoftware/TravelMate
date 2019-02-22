var app = require('./app_router');
var http = require('http');
var https = require('https');
var fs = require('fs');
var database = require('./database');

var url = process.env.DATA || "mongodb://127.0.0.1:27017/TravelMate";
const PORT = process.env.PORT || 8095;

var privateKey  = fs.readFileSync('sslcert/key.pem', 'utf8');
var certificate = fs.readFileSync('sslcert/certificate.pem', 'utf8');

var credentials = {key: privateKey, cert: certificate};

app.set('port', PORT);

console.log("Server Start");

var server = http.createServer(app);
var httpsServer = https.createServer(credentials, app);

database.connect(url);

server.listen(PORT,function(err){
	var address = server.address();
	console.log('server listening on http://%s:%d',address.address,address.port);
	console.log('press CTRL+C to exit');
});

httpsServer.listen(8443,function(err){
	var address = server.address();
	console.log('server https listening on http://%s:8443',address.address);
});

process.on('SIGINT',function(){
	server.close();
	database.close();
});

       


