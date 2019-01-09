var app = require('./app_router');
var http = require('http');

const PORT = process.env.PORT || 8095;

app.set('port', PORT);

var server = http.createServer(app);

server.listen(PORT,function(){
	console.log('Server listen on port '+PORT);
});
       


