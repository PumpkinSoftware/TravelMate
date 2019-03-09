var express = require('express');
var mongoose = require('mongoose');
var UserSchema = require('./Schema_mongoose/user_schema');
var TripSchema = require('./Schema_mongoose/trip_schema');
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
    res.send("The user router works completely");
});

/****************************************/
//Api per inserire un nuovo utente
//Api verificata con lo stress test

router.post('/newUser', function(req, res){

    var clientInput = req.body;

    var conditions = {							
		email: clientInput.email	
    };
    
    UserSchema.findOne(conditions, function(err, user){
        if (err){
            console.log(JSON.stringify({ status: "error", type: "-1" }));
            res.send(JSON.stringify({ status: "error", type: "-1" }));
            console.log(err);
        
        }
        else if (user){
            
            res.send(JSON.stringify({ status: "error", type: "-5" }));
            console.log(JSON.stringify({ status: "error", type: "-5" }));
        
        }
        else{

        	var toInsert = null;

        	if(clientInput.id != undefined){
        		toInsert = new UserSchema({
        		_id: clientInput.id,
                name: clientInput.name.toLowerCase(),
                surname: clientInput.surname.toLowerCase(),
                birthday: clientInput.birthday,
                gender: clientInput.gender.toLowerCase(),
                relationship: clientInput.relationship.toLowerCase(),
                email: clientInput.email,
                uid: clientInput.uid,
                description: clientInput.description,
                avatar: clientInput.avatar,
                cover: clientInput.cover,
                sumReview: 0,
                numReview: 0,
                trips: [],
                favouriteTrips: [],
                comments: []
            	});
        	}
        	else{
        		toInsert = new UserSchema({
                name: clientInput.name.toLowerCase(),
                surname: clientInput.surname.toLowerCase(),
                birthday: clientInput.birthday,
                gender: clientInput.gender.toLowerCase(),
                relationship: clientInput.relationship.toLowerCase(),
                email: clientInput.email,
                uid: clientInput.uid,
                description: clientInput.description,
                avatar: clientInput.avatar,
                cover: clientInput.cover,
                sumReview: 0,
                numReview: 0,
                trips: [],
                favouriteTrips: [],
                comments: []
            	});
        	}
            
            
            toInsert.save(function (err) {
                if (err){ 
                    console.log(err);
                    res.send(JSON.stringify({ status: "error", type: "-1" }));
                    console.log(JSON.stringify({ status: "error", type: "-1" }));
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
//Api verificata con lo stress test

router.get('/allUsers', function(req, res){
    
    UserSchema.find({}).exec( function(err, users){
		if(err){
			console.log(err);
			console.log(JSON.stringify({ status: "error", type: "-1" }));
			res.send(JSON.stringify({ status: "error", type: "-1" }));
		}else{
			res.send(users);
			console.log(users);
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
			res.send(JSON.stringify({ status: "error", type: "-1" }));
			console.log(err);
			console.log(JSON.stringify({ status: "error", type: "-1" }));
		}
		else if(user != null){
			res.send(user);
			console.log(user);
        }
        else{
            res.send(JSON.stringify({ status: "error", type: "-2" }));
            console.log(JSON.stringify({ status: "error", type: "-2" }));
        }
    });
});

//Api per ottenere un utente tramite email => /getUserByUid?uid=prova@gmail.com

router.get('/getUserByUid', function(req, res){

    var conditions = {
        uid: req.query.uid
    };

    UserSchema.findOne(conditions, function(err, user){
        
        if (err){
			res.send(JSON.stringify({ status: "error", type: "-1" }));
			console.log(err);
			console.log(JSON.stringify({ status: "error", type: "-1" }));
		}
		else if(user != null){
			res.send(user);
			console.log(user);
        }
        else{
            res.send(JSON.stringify({ status: "error", type: "-2" }));
            console.log(JSON.stringify({ status: "error", type: "-2" }));
        }
    });
});

/****************************************/
//Api per ottenere un utente tramite id => /getUserById?userId=...

router.get('/getUserById', function(req, res){

	var conditions = {
		_id: req.query.userId
	};
	
	UserSchema.findOne(conditions, function(err, user){
        
        if (err){
			res.send(JSON.stringify({ status: "error", type: "-1" }));
			console.log(err);
			console.log(JSON.stringify({ status: "error", type: "-1" }));
		}
		else if(user != null){
			res.send(user);
			console.log(user);
        }
        else{
            res.send(JSON.stringify({ status: "error", type: "-2" }));
            console.log(JSON.stringify({ status: "error", type: "-2" }));
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
    if(JsonObject.uid != undefined)
        query.uid = JsonObject.uid;
    if(JsonObject.description != undefined)
        query.description = JsonObject.description.toLowerCase();      
    if(JsonObject.avatar != undefined)
        query.avatar = JsonObject.avatar;
    if(JsonObject.cover != undefined)
        query.cover = JsonObject.cover;         
    if(JsonObject.sumReview != undefined)
        query.sumReview = JsonObject.sumReview;
    if(JsonObject.numReview != undefined)
        query.numReview = JsonObject.numReview;
    
    
	UserSchema.findById(JsonObject.userId).exec(function(err, user){
		
		if (err){
			res.send(JSON.stringify({ status: "error", type: "-1" }));
			console.log(err);
			console.log(JSON.stringify({ status: "error", type: "-1" }));
        }
        
        if (user != null){

		    user.set(query);
		
		    user.save(function(err, updateuser){
			    if (err){
				    res.send(JSON.stringify({ status: "error", type: "-1" }));
				    console.log(err);
				    console.log(JSON.stringify({ status: "error", type: "-1" }));

			    } 
			    res.send(updateuser);
			    console.log(updateuser);
            });
        }
        else{
            res.send(JSON.stringify({ status: "error", type: "-2" }));
            console.log(JSON.stringify({ status: "error", type: "-2" }));
        }
	}); 
});

/******************************************/
//Api per inserire l'id di un viaggio all'interno dell'array favouriteTrips => /addFavourTrip?tripId=....&usersId=....

router.get('/addFavouriteTrip', function(req, res){

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
            res.send(JSON.stringify({ status: "error", type: "-1" }));
            console.log(err);
            console.log(JSON.stringify({ status: "error", type: "-1" }));
        }

        if (user != null){
            user.updateOne(update, function(err, userupdate){
				if (err){
					res.send(JSON.stringify({ status: "error", type: "-6" }));
					console.log(err);
					console.log(JSON.stringify({ status: "error", type: "-6" }));
				}
				else{
					res.send(JSON.stringify({ status: "ok", message: "Trip: " + req.query.tripId + " added to user: " + user._id }));
					console.log(JSON.stringify({ status: "ok", message: "Trip: " + req.query.tripId + " added to user: " + user._id }));
				}
			});
        }
        else{
            res.send(JSON.stringify({ status: "error", type: "-2" }));
            console.log(JSON.stringify({ status: "error", type: "-2" }));
        }
    });
});

/******************************************/
//Api per ottenere tutti gli utenti che abbiano tripId all'interno di trips => /GetUsersByTrip?tripId=...

router.get('/getUsersByTrip', function(req, res){

    var conditions = {
        "trips.tripId": { $eq: req.query.tripId }
    }

    UserSchema.find(conditions, function(err, users){
        
        if (err){
            res.send(JSON.stringify({ status: "error", type: "-1" }));
            console.log(err);
            console.log(JSON.stringify({ status: "error", type: "-1" }));
        }
        
        if (users.length > 0){
            res.send(users);
            console.log(users);
        }
        else{
            res.send(JSON.stringify({ status: "error", type: "-2" }));
            console.log(JSON.stringify({ status: "error", type: "-2" }));
        }

    });
});

/******************************************/
//Api per inserire l'id di un viaggio all'interno dell'array Trips => /addTrip?tripId=....&userId=....

router.post('/addTrip', function(req,res){
	
	var JsonObject = req.body;

	
	var trip = {
		"tripId": JsonObject.tripId
	};
		
	var conditions_A = {							
		_id: JsonObject.tripId,
		$where:'this.partecipants < this.maxPartecipant'	
	};
	
	var conditions_B = {							
		_id: JsonObject.userId,
		'trips.tripId': { $ne: JsonObject.tripId }	
	};
	
	var update_A = {
		$addToSet: {trips: trip}
	};
	
	var update_B = {
		$inc: {partecipants: +1}
	};
	
	TripSchema.findOne(conditions_A,function(err,trip) {
		if (err){
			res.send(JSON.stringify({ status: "error", type: "-1" }));
			console.log(err);
			console.log(JSON.stringify({ status: "error", type: "-1" }));
		}
		else if (trip == null){
			res.send(JSON.stringify({ status: "error", type: "-7" }));
			console.log(JSON.stringify({ status: "error", type: "-7" }));
		}
		else{
			UserSchema.findOne(conditions_B, function (err, user) {
				if (err){
					res.send(JSON.stringify({ status: "error", type: "-1" }));
					console.log(err);
					console.log(JSON.stringify({ status: "error", type: "-1" }));
				}
				else if (user == null){
					res.send(JSON.stringify({ status: "error", type: "-8" }));
					console.log(JSON.stringify({ status: "error", type: "-8" }));
				}			
				else{
					user.updateOne(update_A, function(err, userupdate){
						if (err){
							res.send(JSON.stringify({ status: "error", type: "-1" }));
							console.log(err);
							console.log(JSON.stringify({ status: "error", type: "-1" }));
						}
						else{
							trip.updateOne(update_B, function(err, tripupdate) {
								if (err){
									res.send(JSON.stringify({ status: "error", type: "-1" }));
									console.log(err);
									console.log(JSON.stringify({ status: "error", type: "-1" }));
								}
								else {
									res.send(JSON.stringify({ status: "ok", message: "Trip: " + JsonObject.tripId + " added to user: " + user._id }));
									console.log(JSON.stringify({ status: "ok", message: "Trip: " + JsonObject.tripId + " added to user: " + user._id }));
								}
							});
						};
					});
				};
			});
		}
	});
});

/******************************************/
//Api per rimuovere l'id di un viaggio all'interno dell'array Trips => /removeTrip?tripId=....&userId=....

router.post('/removeTrip', function(req,res){
	
	var JsonObject = req.body;

	var trip = {
		"tripId": JsonObject.tripId
	};	
	var conditions_A = {							
		_id: JsonObject.userId,
		'trips.tripId': { $eq: JsonObject.tripId }		
	};
	
	var conditions_B = {
		_id: JsonObject.tripId
	}
	
	var update_A = {
		$pull: {trips: trip}
	};
	
	var update_B = {
		$inc: {partecipants: -1}
	};
	
	UserSchema.findOne(conditions_A, function (err, user) {
		if (err){
			res.send(JSON.stringify({ status: "error", type: "-1" }));
			console.log(err);
			console.log(JSON.stringify({ status: "error", type: "-1" }));
		}
		else if (user == null){
			res.send(JSON.stringify({ status: "error", type: "-3" }));
			console.log(JSON.stringify({ status: "error", type: "-3" }));
		}			
		else{
			user.updateOne(update_A, function(err, userupdate){
				if (err){
					res.send(JSON.stringify({ status: "error", type: "-1" }));
					console.log(err);
					console.log(JSON.stringify({ status: "error", type: "-1" }));
				}
				else{
					TripSchema.findOne(conditions_B, function(err, trip){
						if (err){
							res.send(JSON.stringify({ status: "error", type: "-1" }));
							console.log(err);
							console.log(JSON.stringify({ status: "error", type: "-1" }));
						}
						else if (trip == null){
							res.send(JSON.stringify({ status: "error", type: "-3" }));
							console.log(JSON.stringify({ status: "error", type: "-3" }));
						}
						else {
							trip.updateOne(update_B, function(err, tripupdate){
								if(err){
									res.send(JSON.stringify({ status: "error", type: "-1" }));
									console.log(err);	
									console.log(JSON.stringify({ status: "error", type: "-1" }));
								}
								else {
									res.send(JSON.stringify({ status: "ok", message: "Trip: " + JsonObject.tripId + " removed from user: " + user._id }));
									console.log(JSON.stringify({ status: "ok", message: "Trip: " + JsonObject.tripId + " removed from user: " + user._id }));
								}
							});
						}
					});
				};
			});
		};
	});
});

/******************************************/
//Api per rimuovere un utente. e.g. /deleteUser?userId=...

router.get('/deleteUser', function(req, res){

	var id = req.query.userId;    

	UserSchema.remove({_id : id }, function(err){
		if(err){
			res.send(JSON.stringify({ status: "error", type: "-1" }));
			console.log(err);
			console.log(JSON.stringify({ status: "error", type: "-1" }));
		}
		else{
			res.send(JSON.stringify({ status: "ok", message: "User is deleted" }));
			console.log(JSON.stringify({ status: "ok", message: "User deleted" }));
								
		}
	});
});

/******************************************/
//Api che dato un utente restituisce i suoi viaggi con informazioni annesse e.g. /getTripByUser?userId=...

router.get('/getTripsByUser', function(req, res){

	var id = req.query.userId;

	var conditions1 = {
		_id: id
	};

	UserSchema.findOne(conditions1, function(err, user){
        
        if (err){
            res.send(JSON.stringify({ status: "error", type: "-1" }));
            console.log(err);
            console.log(JSON.stringify({ status: "error", type: "-1" }));
        }
        else if (user){

        	var list_trips = user.trips.map(function(trip){
        		return trip.tripId;
        	});

        	var conditions2 = {
        		_id: { $in:  list_trips } 
        	};

			TripSchema.find(conditions2,function(err,trips){
				if(err){
					res.send(JSON.stringify({ status: "error", type: "-1" }));
					console.log(err);
					console.log(JSON.stringify({ status: "error", type: "-1" }));
				}
				else{
					res.send(trips);
					console.log(trips);
				}
			});

		}
		else{
            res.send(JSON.stringify({ status: "error", type: "-2" }));
            console.log(JSON.stringify({ status: "error", type: "-2" }));
        }

    });
});

/******************************************/
//Api che dato un utente restituisce i suoi viaggi con informazioni annesse,divisi 
// in in corso e passati e.g. /getTripByUserSplit?userId=...

router.get('/getTripsByUserSplit',function(req,res){
	var id = req.query.userId;

	UserSchema.findOne({_id : id}, function(err, user){
        
        if (err){
            res.send(JSON.stringify({ status: "error", type: "-1" }));
            console.log(err);
            console.log(JSON.stringify({ status: "error", type: "-1" }));
        }
        else if (user){

        	var list_trips = user.trips.map(function(trip){
        		return trip.tripId;
        	});

        	var conditions2 = {
        		_id: { $in:  list_trips } 
        	};

			TripSchema.find(conditions2).where('startDate').gte(new Date()).sort({"startDate": 'desc'}).exec(function(err,progress){
				if(err){
					console.log(err);
					console.log(JSON.stringify({ status: "error", type: "-1" }));
					res.send(JSON.stringify({ status: "error", type: "-1" }));
				}
				else{
					TripSchema.find(conditions2).where('startDate').lt(new Date()).sort({"startDate": 'desc'}).exec(function(err,done){
						if(err){
							console.log(err);
							console.log(JSON.stringify({ status: "error", type: "-1" }));
							res.send(JSON.stringify({ status: "error", type: "-1" }));
						}
						else{
							res.send([[progress],[done]]);
							console.log([[progress],[done]]);
						}
					});
				}
			});

		}
		else{
            res.send(JSON.stringify({ status: "error", type: "-2" }));
            console.log(JSON.stringify({ status: "error", type: "-2" }));
        }

    });


});

/******************************************/
//Api che dato un utente restituisce i suoi viaggi passati con informazioni annesse,divisi 
// in in corso e passati e.g. /getPassedTripsByUser?userId=...

router.get('/getPassedTripsByUser',function(req,res){
	var id = req.query.userId;

	UserSchema.findOne({_id : id}, function(err, user){
        
        if (err){
            res.send(JSON.stringify({ status: "error", typw: "-1" }));
            console.log(err);
            console.log(JSON.stringify({ status: "error", typw: "-1" }));
        }
        else if (user){

        	var list_trips = user.trips.map(function(trip){
        		return trip.tripId;
        	});

        	var conditions2 = {
        		_id: { $in:  list_trips } 
        	};

			TripSchema.find(conditions2).where('startDate').lt(new Date()).sort({"startDate": 'desc'}).exec(function(err,passed){
				if(err){
					console.log(err);
					console.log(JSON.stringify({ status: "error", type: "-1" }));
					res.send(JSON.stringify({ status: "error", type: "-1" }));
				}
				else{
					res.send(passed);
					console.log(passed);
				}
			});

		}
		else{
            res.send(JSON.stringify({ status: "error", type: "-2" }));
            console.log(JSON.stringify({ status: "error", type: "-2" }));
        }

    });


});

/******************************************/
//Api che dato un utente restituisce i suoi viaggi con informazioni annesse,divisi 
// in in corso e passati e.g. /getProgressByUserSplit?userId=...

router.get('/getProgressTripByUser',function(req,res){
	var id = req.query.userId;

	UserSchema.findOne({_id : id}, function(err, user){
        
        if (err){
            res.send(JSON.stringify({ status: "error", type: "-1" }));
            console.log(err);
            console.log(JSON.stringify({ status: "error", type: "-1" }));
        }
        else if (user){

        	var list_trips = user.trips.map(function(trip){
        		return trip.tripId;
        	});

        	var conditions2 = {
        		_id: { $in:  list_trips } 
        	};

			TripSchema.find(conditions2).where('startDate').gte(new Date()).sort({"startDate": 'desc'}).exec(function(err,progress){
				if(err){
					console.log(err);
					console.log(JSON.stringify({ status: "error", type: "-1" }));
					res.send(JSON.stringify({ status: "error", type: "-1" }));
				}
				else{
					res.send(progress);
					console.log(progress);
				}
			});

		}
		else{
            res.send(JSON.stringify({ status: "error", type: "-2" }));
            console.log(JSON.stringify({ status: "error", type: "-2" }));
        }

    });


});


module.exports = router;
