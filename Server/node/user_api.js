var express = require('express');
var mongoose = require('mongoose');
var UserSchema = require('./Schema_mongoose/user_schema');
var url = process.env.DATA || "mongodb://127.0.0.1:27017/TravelMate";

//Faccio collegare mongoose al database
mongoose.connect(url,{ useNewUrlParser: true });

var router = express.Router(); 

/******************************************
			  Inizio API
******************************************/


/*****************************************/


module.exports = router;
