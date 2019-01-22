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


/*
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

*/

router.post('/new_trip', function(req,res){

	var clientInput = req.body;

	var toInsert = new TripSchema({
		name : clientInput.name,
        description: clientInput.description,
        departure: clientInput.departure,
        destination: clientInput.destination,
        budget: clientInput.budget,
        startDate: clientInput.startDate,
        endDate: clientInput.endDate,
		partecipant: []
	});

	toInsert.save(function (err) {
        if (err){ 
            console.log(err);
        	res.send("Error to add "+toInsert.name+" on the database.");
        	console.log("Error to add "+toInsert.name+" on the database.");
        }
        else {
        	res.send("Trip " + toInsert.name + " created!");
        	console.log("Trip " + toInsert.name + " created!");
        }
    });

});

/*****************************************/
//Api per ottenere tutti i viaggi

router.get('/all_trips', function(req, res){
	TripSchema.find({}, function(err, trips){
		if(err){
			console.log(err);
		}else{
			res.send(trips);
			console.log('retrieved list of trips', trips.length);
		}
	});
});

/*****************************************/
//Api per ottenere i viaggi con filtri

// example use /getTrips?destination=rome&budget=300

router.get('/getTrips', function(req, res){
	var destination = req.query.destination;
	var budget = req.query.budget;
	
	TripSchema.find({destination:destination,budget:budget }, function(err, trips){
		if(err){
			console.log(err);
		}else{
			res.send(trips);
			console.log(destination + budget);
			console.log('retrieved list of trips', trips.length);
		}
	});
});

module.exports = router;
