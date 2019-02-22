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
//Api per testare il funzionamento di trip_api.js

router.get('/', function (req, res) {
    res.send("Mi trovo in user_api.js");
});

/****************************************/
//Api per inserire un nuovo utente

router.post('/newUser', function(req, res){

    var clientInput = req.body;

    var conditions = {							
		email: clientInput.email	
    };
    
    UserSchema.findOne(conditions, function(err, user){
        if (err){
            
            res.send(JSON.stringify({ status: "error", message: "Error with email" }));
            console.log(err);
        
        }
        else if (user){
            
            res.send(JSON.stringify({ status: "error", message: "User with this email is registered" }));
            console.log(JSON.stringify({ status: "error", message: "User with this email is registered" }));
        
        }
        else{
            
            var toInsert = new UserSchema({
                name: clientInput.name.toLowerCase(),
                surname: clientInput.surname.toLowerCase(),
                age: clientInput.age,
                gender: clientInput.gender.toLowerCase(),
                relationship: clientInput.relationship.toLowerCase(),
                email: clientInput.email,
                description: clientInput.description.toLowerCase(),
                sumReview: 0,
                numReview: 0,
                trips: [],
                favouriteTrips: [],
                comments: []
            });
            
            toInsert.save(function (err) {
                if (err){ 
                    console.log(err);
                    res.send(JSON.stringify({ status: "error", message: "Error to add "+toInsert.name+" on database." }));
                    console.log(JSON.stringify({ status: "error", message: "Error to add "+toInsert.name+" on database." }));
                }
                else {
                    res.send(JSON.stringify({ status: "success", message: "User " + toInsert.name + " created!" }));
                    console.log(JSON.stringify({ status: "success", message: "User " + toInsert.name + " created!" }));
                }
            });
        }
    });
});

/****************************************/
//Api per testing che stampa tutti gli utenti registrati

router.get('/allUsers', function(req, res){
    
    UserSchema.find({}, function(err, users){
		if(err){
			console.log(err);
			res.send(JSON.stringify({ status: "error", message: "Error to send all users." }));
		}else{
			res.send(users);
			console.log('retrieved list of users', users.length);
		}
	});
});

/****************************************/
//Api per ottenere un utente tramite email => /getUserByEmail?email=prova@gmail.com

router.get('/getUserByEmail', function(req, res){

    var conditions = {
        email: req.query.email
    };

    UserSchema.findOne(conditions, function(err, user){
        
        if (err){
			res.send(JSON.stringify({ status: "error", message: "Error get with email" }));
			console.log(err);
		}
		else if(user != null){
			res.send(user);
			console.log(user);
        }
        else{
            res.send(JSON.stringify({ status: "error", message: "User not found" }));
            console.log(JSON.stringify({ status: "error", message: "User not found" }));
        }
    });
});

/****************************************/
//Api per ottenere un utente tramite id => /getUserById?userId=5c702ddca2c6514f9b18c4ad

router.get('/getUserById', function(req, res){

	var conditions = {
		_id: req.query.userId
	};
	
	UserSchema.findOne(conditions, function(err, user){
        
        if (err){
			res.send(JSON.stringify({ status: "error", message: "Error get with Id" }));
			console.log(err);
		}
		else if(user != null){
			res.send(user);
			console.log(user);
        }
        else{
            res.send(JSON.stringify({ status: "error", message: "User not found" }));
            console.log(JSON.stringify({ status: "error", message: "User not found" }));
        }
    });
	
});

/******************************************/
//Api per aggiornare un utente

router.post('/updateUser', function(req, res){
    
    var JsonObject = req.body;
    var query = {};

    if(JsonObject.name != undefined)
        query.name = JsonObject.name.toLowerCase();
    if(JsonObject.surname != undefined)
        query.surname = JsonObject.surname.toLowerCase();
    if(JsonObject.age != undefined)
        query.age = JsonObject.age;
    if(JsonObject.gender != undefined)
        query.gender = JsonObject.gender.toLowerCase();
    if(JsonObject.relationship != undefined)
        query.relationship = JsonObject.relationship.toLowerCase();
    if(JsonObject.email != undefined)
        query.email = JsonObject.email;
    if(JsonObject.description != undefined)
        query.description = JsonObject.description.toLowerCase();
    if(JsonObject.sumReview != undefined)
        query.sumReview = JsonObject.sumReview;
    if(JsonObject.numReview != undefined)
        query.numReview = JsonObject.numReview;
    
    
	UserSchema.findById(JsonObject.userId).exec(function(err, user){
		
		if (err){
			res.send(JSON.stringify({ status: "error", message: "Cannot find user by _id" }));
			console.log(err);
        }
        
        if (user != null){

		    user.set(query);
		
		    user.save(function(err, updateuser){
			    if (err){
				    res.send(JSON.stringify({ status: "error", message: "Error on update your user" }));
				    console.log(err);
			    } 
			    res.send(updateuser);
            });
        }
        else{
            res.send(JSON.stringify({ status: "error", message: "Cannot find user" }));
            console.log(JSON.stringify({ status: "error", message: "Cannot find user" }));
        }
	}); 
});

/******************************************/
//Api per inserire l'id di un viaggio all'interno dell'array favouriteTrips => /addFavourTrip?tripId=....

router.get('/addFavourTrip', function(req, res){

    var trip = {
        "tripId": req.query.tripId
    };
   
    var conditions = {
        _id: req.query.userId,
        "favouriteTrips.tripId": { $ne: req.query.tripId }
    };

    var update = {
		$addToSet: {favouriteTrips: trip}
	};

    UserSchema.findOne(conditions, function(err, user){
        
        if (err){
            res.send(JSON.stringify({ status: "error", message: "Error in adding trip" }));
            console.log(err);
        }

        if (user != null){
            user.updateOne(update, function(err, userupdate){
				if (err){
					res.send(JSON.stringify({ status: "error", message: "Error in adding trip" }));
					console.log(err);
				}
				else{
					res.send(JSON.stringify({ status: "ok", message: "Trip: " + req.query.tripId + " added to user: " + user._id }));
					console.log(JSON.stringify({ status: "ok", message: "Trip: " + req.query.tripId + " added to user: " + user._id }));
				}
			});
        }
        else{
            res.send(JSON.stringify({ status: "error", message: "Trip already added" }));
            console.log(JSON.stringify({ status: "error", message: "Trip already added" }));
        }
    });
});

module.exports = router;
