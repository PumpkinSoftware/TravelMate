var express = require('express');
var mongoose = require('mongoose');
var TripSchema = require('./Schema_mongoose/trip_schema');
//var database = require('./database');
var url = process.env.DATA || "mongodb://127.0.0.1:27017/TravelMate";


//Faccio collegare mongoose al database
mongoose.connect(url);

//Creo il router
var router = express.Router(); 


/******************************************
			  Inizio API
******************************************/

/*****************************************/
//Api per testare il funzionamento di trip_api.js

router.get('/', function (req, res) {
    res.send("Mi trovo in trip_api.js");
});


/*****************************************/
//Api per inserire un nuovo viaggio

router.get('/new_trip', function(req,res){

	var toInsert = new TripSchema({
		'name' : 'Road to Rome',
        'description': 'Best travel to Rome',
		'partecipant': [{'email':'roma@rome.it'},{'email':'nino@nino.it'}]
	});

	toInsert.save(function (err) {
        if (err){ 
        	res.send("Error to add"+toInsert.name+"on the database.");
        	console.log("Error to add"+toInsert.name+"on the database.");
        }
        else {
        	res.send("Trip " + toInsert.name + " created!");
        	console.log("Trip " + toInsert.name + " created!");
        }
    });

});

module.exports = router;
