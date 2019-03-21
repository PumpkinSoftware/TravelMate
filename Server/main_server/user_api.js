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
//Api verificata

router.get('/', function (req, res) {
    res.send("The user router works completely");
});

/****************************************/
//Api per inserire un nuovo utente
//Api verificata

router.post('/newUser', function(req, res){

    var clientInput = req.body;

    var conditions = {							
		email: clientInput.email	
    };
    
    UserSchema.findOne(conditions).exec(function(err, user){
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
                name: clientInput.name,
                surname: clientInput.surname,
                birthday: clientInput.birthday,
                gender: clientInput.gender,
                relationship: clientInput.relationship,
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
                name: clientInput.name,
                surname: clientInput.surname,
                birthday: clientInput.birthday,
                gender: clientInput.gender,
                relationship: clientInput.relationship,
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
                    res.send(JSON.stringify({ status: "ok", message: "User " + toInsert.name + " created!" }));
                }
            });
        }
    });
});

/****************************************/
//Api per testing che stampa tutti gli utenti registrati
//Api verificata

router.get('/allUsers', function(req, res){
    
    UserSchema.find({}).exec( function(err, users){
		if(err){
			console.log(err);
			console.log(JSON.stringify({ status: "error", type: "-1" }));
			res.send(JSON.stringify({ status: "error", type: "-1" }));
		}else{
			res.send(users);
		}
	});
});

/****************************************/
//Api per ottenere un utente tramite email
//Api verificata

//Example to use getUserByEmail?email=prova@gmail.com

router.get('/getUserByEmail', function(req, res){

    var conditions = {
        email: req.query.email
    };

    UserSchema.findOne(conditions).exec(function(err, user){
        
        if (err){
			res.send(JSON.stringify({ status: "error", type: "-1" }));
			console.log(err);
			console.log(JSON.stringify({ status: "error", type: "-1" }));
		}
		else if(user != null){
			res.send(user);
        }
        else{
            res.send(JSON.stringify({ status: "error", type: "-2" }));
            console.log(JSON.stringify({ status: "error", type: "-2" }));
        }
    });
});

/****************************************/
//Api per ottenere un utente tramite uid
//Api verificata

//Example to use getUserByUid?userUid=ddeun534FR32ew

router.get('/getUserByUid', function(req, res){

    var conditions = {
        uid: req.query.userUid
    };

    UserSchema.findOne(conditions).exec(function(err, user){
        
        if (err){
			res.send(JSON.stringify({ status: "error", type: "-1" }));
			console.log(err);
			console.log(JSON.stringify({ status: "error", type: "-1" }));
		}
		else if(user != null){
			res.send(user);
        }
        else{
            res.send(JSON.stringify({ status: "error", type: "-2" }));
            console.log(JSON.stringify({ status: "error", type: "-2" }));
        }
    });
});

/****************************************/
//Api per ottenere un utente tramite uid
//Api verificata

//Example to use /getUserById?userId=...

router.get('/getUserById', function(req, res){

	var conditions = {
		_id: req.query.userId
	};
	
	UserSchema.findOne(conditions).exec( function(err, user){
        
        if (err){
			res.send(JSON.stringify({ status: "error", type: "-1" }));
			console.log(err);
			console.log(JSON.stringify({ status: "error", type: "-1" }));
		}
		else if(user != null){
			res.send(user);
        }
        else{
            res.send(JSON.stringify({ status: "error", type: "-2" }));
            console.log(JSON.stringify({ status: "error", type: "-2" }));
        }
    });
	
});

/******************************************/
//Api per aggiornare un utente
//Api verificata

router.post('/updateUser', function(req, res){
    
    var query = {};

    if(req.body.name != undefined)
        query.name = req.body.name;
    if(req.body.surname != undefined)
        query.surname = req.body.surname;
    if(req.body.age != undefined)
        query.age = req.body.age;
    if(req.body.gender != undefined)
        query.gender = req.body.gender;
    if(req.body.relationship != undefined)
        query.relationship = req.body.relationship;
    if(req.body.email != undefined)
        query.email = req.body.email;
    if(req.body.uid != undefined)
        query.uid = req.body.uid;
    if(req.body.description != undefined)
        query.description = req.body.description;      
    if(req.body.avatar != undefined)
        query.avatar = req.body.avatar;
    if(req.body.cover != undefined)
        query.cover = req.body.cover;         
    if(req.body.sumReview != undefined)
        query.sumReview = req.body.sumReview;
    if(req.body.numReview != undefined)
        query.numReview = req.body.numReview;

    var conditions = {
        uid: query.uid
    };

    console.log(query);
    
	UserSchema.findOne(conditions).exec(function(err, user){
		
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
			    res.send({ status: "ok", message: "Your user is updated" });
            });
        }
        else{
            res.send(JSON.stringify({ status: "error", type: "-2" }));
            console.log(JSON.stringify({ status: "error", type: "-2" }));
        }
	}); 
});

/******************************************/
//Api per inserire l'id di un viaggio all'interno dell'array favouriteTrips
//Api verificata

//Example to use addFavourTrip?tripId=....&usersUid=....

router.get('/addFavouriteTrip', function(req, res){

    var trip = {
        "tripId": req.query.tripId
    };
   
    var conditions = {
        uid: req.query.userUid,
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
					res.send(JSON.stringify({ status: "ok", message: "FavouriteTrip: " + req.query.tripId + " added to user: " + user.uid }));
				}
			});
        }
        else{
            res.send(JSON.stringify({ status: "error", type: "-9" }));
            console.log(JSON.stringify({ status: "error", type: "-9" }));
        }
    });
});

/******************************************/
//Api per rimuovere l'id di un viaggio all'interno dell'array favouriteTrips
//Api verificata

//Example to use removeFavourTrip?tripId=....&usersUid=....

router.get('/removeFavouriteTrip', function(req, res){

    var trip = {
        "tripId": req.query.tripId
    };
   
    var conditions = {
        uid: req.query.userUid,
        "favouriteTrips.tripId": { $eq: req.query.tripId }
    };

    var update = {
		$pull: {favouriteTrips: trip}
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
					res.send(JSON.stringify({ status: "ok", message: "FavouriteTrip: " + req.query.tripId + " removed to user: " + user.uid }));
				}
			});
        }
        else{
            res.send(JSON.stringify({ status: "error", type: "-10" }));
            console.log(JSON.stringify({ status: "error", type: "-10" }));
        }
    });
});

/******************************************/
//Api per ottenere tutti gli utenti che abbiano tripId all'interno di trips
//Api verificata

//Example to use /GetUsersByTrip?tripId=...

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
        }
        else{
            res.send(JSON.stringify({ status: "error", type: "-2" }));
            console.log(JSON.stringify({ status: "error", type: "-2" }));
        }

    });
});

/******************************************/
//Api per inserire l'id di un viaggio all'interno dell'array Trips
//Api verificata

//Example to use Post /addTrip?tripId=....&userUid=....

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
		uid: JsonObject.userUid,
		'trips.tripId': { $ne: JsonObject.tripId }	
	};
	
	var update_A = {
		$addToSet: {trips: trip}
	};
	
	var update_B = {
		$inc: {partecipants: +1}
	};
	
	TripSchema.findOne(conditions_A).exec(function(err,trip) {
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
			UserSchema.findOne(conditions_B).exec(function (err, user) {
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
					user.updateOne(update_A).exec( function(err, userupdate){
						if (err){
							res.send(JSON.stringify({ status: "error", type: "-1" }));
							console.log(err);
							console.log(JSON.stringify({ status: "error", type: "-1" }));
						}
						else{
							trip.updateOne(update_B).exec(function(err, tripupdate) {
								if (err){
									res.send(JSON.stringify({ status: "error", type: "-1" }));
									console.log(err);
									console.log(JSON.stringify({ status: "error", type: "-1" }));
								}
								else {
									res.send(JSON.stringify({ status: "ok", message: "Trip: " + JsonObject.tripId + " added to user: " + user._id }));
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
//Api per rimuovere l'id di un viaggio all'interno dell'array Trips
//Api verificata

//Example to use Post /removeTrip?tripId=....&userUid=....

router.post('/removeTrip', function(req,res){
	
	var JsonObject = req.body;

	var trip = {
		"tripId": JsonObject.tripId
	};	
	var conditions_A = {							
		uid: JsonObject.userUid,
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
	
	UserSchema.findOne(conditions_A).exec( function (err, user) {
		if (err){
			res.send(JSON.stringify({ status: "error", type: "-1" }));
			console.log(err);
			console.log(JSON.stringify({ status: "error", type: "-1" }));
		}
		else if (user == null){
			res.send(JSON.stringify({ status: "error", type: "-2" }));
			console.log(JSON.stringify({ status: "error", type: "-2" }));
		}			
		else{
			user.updateOne(update_A).exec( function(err, userupdate){
				if (err){
					res.send(JSON.stringify({ status: "error", type: "-1" }));
					console.log(err);
					console.log(JSON.stringify({ status: "error", type: "-1" }));
				}
				else{
					TripSchema.findOne(conditions_B).exec( function(err, trip){
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
							trip.updateOne(update_B).exec( function(err, tripupdate){
								if(err){
									res.send(JSON.stringify({ status: "error", type: "-1" }));
									console.log(err);	
									console.log(JSON.stringify({ status: "error", type: "-1" }));
								}
								else {
									res.send(JSON.stringify({ status: "ok", message: "Trip: " + JsonObject.tripId + " removed from user: " + user._id }));
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
//Api per rimuovere un utente.
//Api verificata

 //Example to use /deleteUser?userUid=...

router.get('/deleteUser', function(req, res){

	var uid = req.query.userUid;    

	UserSchema.remove({uid : uid }).exec( function(err){
		if(err){
			res.send(JSON.stringify({ status: "error", type: "-1" }));
			console.log(err);
			console.log(JSON.stringify({ status: "error", type: "-1" }));
		}
		else{
			res.send(JSON.stringify({ status: "ok", message: "User is deleted" }));
								
		}
	});
});

/******************************************/
//Api che dato un utente restituisce i suoi viaggi con informazioni annesse
//Api verificata
 
//Example to use /getTripByUser?userUid=...

router.get('/getTripsByUser', function(req, res){

	var uid = req.query.userUid;

	var conditions1 = {
		uid: uid
	};

	UserSchema.findOne(conditions1).exec( function(err, user){
        
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

			TripSchema.find(conditions2).exec(function(err,trips){
				if(err){
					res.send(JSON.stringify({ status: "error", type: "-1" }));
					console.log(err);
					console.log(JSON.stringify({ status: "error", type: "-1" }));
				}
				else{
					res.send(trips);
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
// in in corso e passati
//Api verificata

//Example to use /getTripByUserSplit?userUid=...

router.get('/getTripsByUserSplit',function(req,res){
	var uid = req.query.userUid;

	UserSchema.findOne({uid : uid}).exec( function(err, user){
        
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

			TripSchema.find(conditions2).where('startDate').gte(new Date()).sort('startDate').exec(function(err,progress){
				if(err){
					console.log(err);
					console.log(JSON.stringify({ status: "error", type: "-1" }));
					res.send(JSON.stringify({ status: "error", type: "-1" }));
				}
				else{
					TripSchema.find(conditions2).where('startDate').lt(new Date()).sort('startDate').exec(function(err,done){
						if(err){
							console.log(err);
							console.log(JSON.stringify({ status: "error", type: "-1" }));
							res.send(JSON.stringify({ status: "error", type: "-1" }));
						}
						else{
							res.send([[progress],[done]]);
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
//Api che dato un utente restituisce i suoi viaggi passati con informazioni annesse 
//Api verificata

// Example to use /getPassedTripsByUser?userUid=...

router.get('/getPassedTripsByUser',function(req,res){
	var uid = req.query.userUid;

	UserSchema.findOne({uid : uid}).exec( function(err, user){
        
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

			TripSchema.find(conditions2).where('endDate').lte(new Date()).sort({'startDate':'desc'}).exec(function(err,passed){
				if(err){
					console.log(err);
					console.log(JSON.stringify({ status: "error", type: "-1" }));
					res.send(JSON.stringify({ status: "error", type: "-1" }));
				}
				else{
					res.send(passed);
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
//Api che dato un utente restituisce i suoi viaggi in corso con informazioni annesse 
//Api verificata

// Example to use /getPassedTripsByUser?userUid=...

router.get('/getProgressTripsByUser',function(req,res){
	var uid = req.query.userUid;

	UserSchema.findOne({uid : uid}).exec( function(err, user){
        
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

			TripSchema.find(conditions2).where('endDate').gt(new Date()).sort('startDate').exec(function(err,progress){
				if(err){
					console.log(err);
					console.log(JSON.stringify({ status: "error", type: "-1" }));
					res.send(JSON.stringify({ status: "error", type: "-1" }));
				}
				else{
					res.send(progress);
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
//Api che dato un viaggio e un utente, cambia l'owner del viaggio nell'utente dato e rimuove il precedente owner dai partecipanti del viaggio 

// Example to use /changeOwnerAndRemoveLast?tripId=...&userUid=...

router.post('/changeOwnerAndRemoveLast',function(req,res){
	
	var JsonObject = req.body;
	
	var trip = {
		"tripId":JsonObject.tripId
	};
	
	var conditions_A = {
		_id: JsonObject.tripId
	}
	
	var update_A = {
		$set: {owner: JsonObject.userUid},
		$inc: {partecipants: -1}
	};
	
	var update_B = {
		$pull: {trips: trip}
	}
	
	TripSchema.findOne(conditions_A).exec(function(err,trip) {
		if (err){
			res.send(JSON.stringify({ status: "error", type: "-1" }));
			console.log(err);
			console.log(JSON.stringify({ status: "error", type: "-1" }));
		}
		else if (trip == null){
			res.send(JSON.stringify({ status: "error", type: "-3" }));
			console.log(JSON.stringify({ status: "error", type: "-3" }));
		}
		else{
			
			var conditions_B = {
				uid: trip.owner
			}
			
			UserSchema.findOne(conditions_B).exec(function(err, user) {
				if (err){
					res.send(JSON.stringify({ status: "error", type: "-1" }));
					console.log(err);
					console.log(JSON.stringify({ status: "error", type: "-1" }));
				}
				else if (user == null){
					res.send(JSON.stringify({ status: "error", type: "-2" }));
					console.log(JSON.stringify({ status: "error", type: "-2" }));
				}			
				else{					
					user.updateOne(update_B).exec(function(err, userupdate){
						if (err){
							res.send(JSON.stringify({ status: "error", type: "-1" }));
							console.log(err);
							console.log(JSON.stringify({ status: "error", type: "-1" }));
						}
						else{
							trip.updateOne(update_A).exec(function(err, tripupdate) {
								if (err){
									res.send(JSON.stringify({ status: "error", type: "-1" }));
									console.log(err);
									console.log(JSON.stringify({ status: "error", type: "-1" }));
								}
								else{
									res.send(JSON.stringify({ status: "ok", message: "Trip: " + JsonObject.tripId + " has now the new owner: " + JsonObject.userUid }));
								}
							});
						}
					});
				}
			});	
		}
	});

});

/****************************************/
//Api per inserire pulire tutta la collezione trips
//Api verificata

router.get('/deleteAll', function(req, res){

	UserSchema.remove({}).exec(function(err, trip){
		
		if (err){
			res.send(JSON.stringify({ status: "error", type: "-1" }));
			console.log(err);
			console.log(JSON.stringify({ status: "error", type: "-1" }));
		}
		else{
			res.send(JSON.stringify({ status: "ok", type: "all users deleted" }));
		}

	});
});


module.exports = router;
