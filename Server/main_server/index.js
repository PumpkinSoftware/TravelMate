var app = require('./app_router');
var http = require('http');
var https = require('https');
var fs = require('fs');
var database = require('./database');
var admin = require('./firebase');

var url = process.env.DATA || "mongodb://127.0.0.1:27017/TravelMate";
const PORT = process.env.PORT || 8095;

var privateKey  = fs.readFileSync('sslcert/key.pem', 'utf8');
var certificate = fs.readFileSync('sslcert/certificate.pem', 'utf8');

var credentials = {key: privateKey, cert: certificate};

app.set('port', PORT);

console.log("Server Start");

var server = http.createServer(app);
var httpsServer = https.createServer(credentials, app);

//Only for firebase 
//admin.initializeFirebase();

database.connect(url);

server.listen(PORT,function(err){
	var address = server.address();
	console.log('server listening on http://%s:%d',address.address,address.port);
});

httpsServer.listen(8442,function(err){
	var address = httpsServer.address();
	console.log('server https listening on http://%s:%d',address.address,address.port);
	console.log('press CTRL+C to exit');
});

//Only for Heroku
/*
setInterval(function() {
    https.get("https://debugtm.herokuapp.com");
}, 300000); // every 5 minutes (300000)
*/

process.on('SIGINT',function(){
	server.close();
	httpsServer.close();
	database.close();
});

       


