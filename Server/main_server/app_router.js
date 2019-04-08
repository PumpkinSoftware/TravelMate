var express = require('express');
var bodyParser = require('body-parser');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var helmet = require('helmet');
var compression = require('compression');


var user_api = require('./user_api');
var trip_api = require('./trip_api');

var app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(helmet());
app.use(compression());


//Logger
app.use(logger('dev'));

app.use('/user', user_api);

app.use('/trip', trip_api);

app.get('/',function(req,res){
	res.send('The main router works completely');
});

module.exports = app;
