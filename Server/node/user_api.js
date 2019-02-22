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

router.post('/new_user', function(req, res){

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
                nickname: clientInput.nickname.toLowerCase(),
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

router.get('/all_users', function(req, res){
    
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
		_id: req.body.userId
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

module.exports = router;
