var express = require('express');
var bodyParser = require('body-parser');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var helmet = require('helmet');

var mail_api = require('./mail_api');

var app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(helmet());



//Logger
app.use(logger('dev'));

app.use('/mail', mail_api);

app.get('/',function(req,res){
	res.send('The main router works completely - Report System server');
});

module.exports = app;
