var express = require('express');
var mongoose = require('mongoose');
var TripSchema = require('./Schema_mongoose/trip_schema');
var UserSchema = require('./Schema_mongoose/user_schema');
//var database = require('./database');
var firebase = require("./firebase");

var url = process.env.DATA || "mongodb://127.0.0.1:27017/TravelMate";


//Faccio collegare mongoose al database
mongoose.connect(url,{ useNewUrlParser: true });

//Firebase
//var admin = firebase.getAdmin();

//Creo il router
var router = express.Router(); 


/******************************************
			  Inizio API
******************************************/

/*****************************************/
//Api per testare il funzionamento di trip_api.js
//Api verificata

router.get('/', function (req, res) {
	/*var token = req.headers.authorization;
	if(token == undefined){
		res.send(JSON.stringify({"status":"error","type":"-12"}));
	}
	else{
		admin.auth().verifyIdToken(token).then(function(decodedToken) {
    		//var userUid = decodedToken.uid;*/
    		res.send("The trip router works completely");
    		/*}).catch(function(error) {
    			res.send(JSON.stringify({"status":"error","type":"-12"}));
  		});
  	}*/
});


/*****************************************/
//Api per inserire un nuovo viaggio
//Api verificata

router.post('/newTrip', function(req,res){
	/*var token = req.headers.authorization;
	if(token == undefined){
		res.send(JSON.stringify({"status":"error","type":"-12"}));
	}
	else{
		admin.auth().verifyIdToken(token).then(function(decodedToken) {
    		var userUid = decodedToken.uid;
			*/
			var clientInput = req.body;

			var toInsert = null;

			if(req.body.id != undefined){
				toInsert = new TripSchema({
					_id : clientInput.id,
					name : clientInput.name,
        			description: clientInput.description,
        			departure: clientInput.departure,
        			destination: clientInput.destination,
        			budget: clientInput.budget,
        			image: clientInput.image,
        			owner: clientInput.owner/*userUid*/,
        			startDate: clientInput.startDate,
        			endDate: clientInput.endDate,
        			createDate: new Date(),
        			vehicle: clientInput.vehicle,
        			tag: clientInput.tag,
        			maxPartecipant: clientInput.maxPartecipant,
					partecipants: 1
				});
			}
			else{
				toInsert = new TripSchema({
					name : clientInput.name,
        			description: clientInput.description,
        			departure: clientInput.departure,
        			destination: clientInput.destination,
        			budget: clientInput.budget,
        			image: clientInput.image,
        			owner: clientInput.owner/*userUid*/,
        			startDate: clientInput.startDate,
        			endDate: clientInput.endDate,
        			createDate: new Date(),
        			vehicle: clientInput.vehicle,
        			tag: clientInput.tag,
        			maxPartecipant: clientInput.maxPartecipant,
					partecipants: 1
				});
			}

			var conditions = {							
				uid: clientInput.owner/*userUid*/,
			};

			var trip = {
				"tripId": toInsert._id
			};	
	
			var update = {
				$addToSet: {trips: trip}
			};

			UserSchema.findOne(conditions, function (err, user) {
				if (err){
					console.log(err);
					console.log(JSON.stringify({ status: "error", type: "-1" }));
					res.send(JSON.stringify({ status: "error", type: "-1" }));
				}
				else if (user == null){
					console.log(JSON.stringify({ status: "error", type: "-2" }));
					res.send(JSON.stringify({ status: "error", type: "-2" }));
				}			
				else{
					user.updateOne(update, function(err, tripupdate){
						if (err){
							console.log(err);
							console.log(JSON.stringify({ status: "error", type: "-1" }));
							res.send(JSON.stringify({ status: "error", type: "-1" }));
						}
						else{
							console.log(JSON.stringify({ status: "success", message: "Trip added to user"}));
							toInsert.save(function (err) {
							if (err){ 
								console.log(err);
								console.log(JSON.stringify({ status: "error", type: "-1" }));
								res.send(JSON.stringify({ status: "error", type: "-1" }));
							}
							else {
								res.send(JSON.stringify({ status: "success", message: "Trip " + toInsert.name + " created!" }));
							}
						});
					};
				});
			};
		});
		/*
		}).catch(function(error) {
    			res.send(JSON.stringify({"status":"error","type":"-12"}));
  		});
  	}*/
});
/*****************************************/
//Api per ottenere tutti i viaggi
//Api verificata

//example use /allTrips

router.get('/allTrips', function(req, res){
	/*var token = req.headers.authorization;
	if(token == undefined){
		res.send(JSON.stringify({"status":"error","type":"-12"}));
	}
	else{
		admin.auth().verifyIdToken(token).then(function(decodedToken) {
    		//var userUid = decodedToken.uid;
			*/
			TripSchema.find({}).exec( function(err, trips){
				if(err){
					console.log(err);
					console.log(JSON.stringify({ status: "error", type: "-1" }));
					res.send(JSON.stringify({ status: "error", type: "-1" }));
				}else{
					res.send(trips);
				}
			});
			/*
			}).catch(function(error) {
    			res.send(JSON.stringify({"status":"error","type":"-12"}));
  		});
  	}*/
});

/*****************************************/
//Api per ottenere gli ultimi limit viaggi inseriti
//Api verificata

//example use /lastTripsCreated?limit=200

router.get('/lastTripsCreated', function(req, res){
	/*var token = req.headers.authorization;
	if(token == undefined){
		res.send(JSON.stringify({"status":"error","type":"-12"}));
	}
	else{
		admin.auth().verifyIdToken(token).then(function(decodedToken) {
    		//var userUid = decodedToken.uid;
			*/
			var limit = 200;

			if(req.query.limit != undefined) 
				limit = parseInt(req.query.limit);

			TripSchema.find().where('partecipants<maxPartecipant').where('startDate').gte(new Date()).sort({"createDate": 'desc'}).limit(limit).exec(function(err, trips){
				if(err){
					console.log(err);
					console.log(JSON.stringify({ status: "error", type: "-1" }));
					res.send(JSON.stringify({ status: "error", type: "-1" }));
				}else{
					res.send(trips);
				}
			});
		/*
		}).catch(function(error) {
    		res.send(JSON.stringify({"status":"error","type":"-12"}));
  		});
  	}*/
});

/*****************************************/
//Api per ottenere gli ultimi limit viaggi inseriti senza quelli dell'utente
//Api verificata

//example use /lastTripsCreatedWithUser?limit=200&userUid=

router.get('/lastTripsCreatedWithUser', function(req, res){
	/*var token = req.headers.authorization;
	if(token == undefined){
		res.send(JSON.stringify({"status":"error","type":"-12"}));
	}
	else{
		admin.auth().verifyIdToken(token).then(function(decodedToken) {
    		var userUid = decodedToken.uid;
			*/
			var limit = 200;

			if(req.query.limit != undefined) 
				limit = parseInt(req.query.limit);

			UserSchema.findOne({uid:req.query.userUid/*userUid*/}).exec(function (err, user) {
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

					var list_trips = user.trips.map(function(trip){
        			return trip.tripId;
        		});

				TripSchema.find({_id: { $nin: list_trips }}).where('partecipants<maxPartecipant').where('startDate').gte(new Date()).sort('startDate').limit(limit).exec(function(err, trips){
					if(err){
						console.log(err);
						console.log(JSON.stringify({ status: "error", type: "-1" }));
						res.send(JSON.stringify({ status: "error", type: "-1" }));
					}else{
						res.send(trips);
					}
				});
			}
		});
		/*
		}).catch(function(error) {
    		res.send(JSON.stringify({"status":"error","type":"-12"}));
  		});
  	}*/
});

/*****************************************/
//Api per ottenere i viaggi con filtri
//Api verificata

// example use /getTripsWithFilter?name=casa&destination=rome&departure=milan&minBudget=430&maxBudget=730&startDate=12/31/2018&endDate=01/02/2019&maxPartecipant=12
// &vehicle=auto&minPartecipant=1&tag="intrattenimento"

router.get('/getTripsWithFilter', function(req, res){
	/*var token = req.headers.authorization;
	if(token == undefined){
		res.send(JSON.stringify({"status":"error","type":"-12"}));
	}
	else{
		admin.auth().verifyIdToken(token).then(function(decodedToken) {
    		//var userUid = decodedToken.uid;
			*/
			var query = {};
			var name = req.query.name;
			var departure = req.query.departure;
			var destination = req.query.destination;
			var tag = req.query.tag;
			var vehicle = req.query.vehicle;
			var minBudget = 0;
			var maxBudget = 100000;
			var startDate = new Date();
			var endDate = new Date("1/1/4000");
			var maxPartecipant = 1000000;
			var minPartecipant = 1;

			if(name != undefined)
				query.name = name.toLowerCase();
			if(departure != undefined)
				query.departure = departure.toLowerCase();
			if(destination != undefined)
				query.destination = destination.toLowerCase();
			if(tag != undefined)
				query.tag = tag;
			if(vehicle != undefined)
				query.vehicle = vehicle;
			if(req.query.minBudget != undefined)
				minBudget = req.query.minBudget;
			if(req.query.maxBudget != undefined)
				maxBudget = req.query.maxBudget;
			if(req.query.startDate != undefined)
				startDate = new Date(req.query.startDate);
			if(req.query.endDate != undefined)
				endDate = new Date(req.query.endDate);
			if(req.query.maxPartecipant != undefined)
				maxPartecipant = req.query.maxPartecipant;
			if(req.query.minPartecipant != undefined)
				minPartecipant = req.query.minPartecipant;

			TripSchema.find(query).where('partecipants<maxPartecipant').where('startDate').gte(new Date()).where('budget').gte(minBudget).lte(maxBudget).where('startDate').gte(startDate).where('endDate').lte(endDate).where('maxPartecipant').lte(maxPartecipant).gte(minPartecipant).exec( function(err, trips){
				if(err){
					res.send(JSON.stringify({ status: "error", type: "-1" }));
					console.log(err);
					console.log(JSON.stringify({ status: "error", type: "-1" }));
				}else{
					res.send(trips);
				}
			});
		/*
		}).catch(function(error) {
    		res.send(JSON.stringify({"status":"error","type":"-12"}));
  		});
  	}*/
});

/******************************************/
//Api per aggiornare un viaggio
//Api verificata

router.post('/updateTrip', function(req, res){
	/*var token = req.headers.authorization;
	if(token == undefined){
		res.send(JSON.stringify({"status":"error","type":"-12"}));
	}
	else{
		admin.auth().verifyIdToken(token).then(function(decodedToken) {
    		//var userUid = decodedToken.uid;
			*/
			var input = req.body;

			var query = {};
	
			if (input.name != undefined)
				query.name = input.name;
			if(input.description != undefined)
				query.description = input.description;
			if(input.departure != undefined)
				query.departure = input.departure;
			if(input.destination != undefined)
				query.destination = input.destination;
			if(input.budget != undefined)
				query.budget = input.budget;
			if(input.image != undefined)
				query.image = input.image;
			if(input.owner != undefined)
				query.owner = input.owner;
			if(input.startDate != undefined)
				query.startDate = input.startDate;
			if(input.endDate != undefined)
				query.endDate = input.endDate;
			if(input.vehicle != undefined)
				query.vehicle = input.vehicle;
			if(input.tag != undefined)
				query.tag = input.tag;
			if(input.maxPartecipant != undefined)
				query.maxPartecipant = input.maxPartecipant;
	
		TripSchema.findById(input._id).exec(function(err, trip){
			if (err){
				res.send(JSON.stringify({ status: "error", type: "-3" }));
				console.log(err);
				console.log(JSON.stringify({ status: "error", type: "-3" }));
			}
			else{
				trip.set(query);
				trip.save(function(err, updatetrip){
					if (err){
						res.send(JSON.stringify({ status: "error", type: "-1" }));
						console.log(err);
						console.log(JSON.stringify({ status: "error", type: "-1" }));
					} 
					res.send({ status: "success", message: "Your trip is updated" });
				});
			}
		});
		/*
		}).catch(function(error) {
    		res.send(JSON.stringify({"status":"error","type":"-12"}));
  		});
  	}*/
});

/******************************************/
//Api per cancellare un viaggio
//Api verificata

//example use: /deleteTrip?tripId=5c537f4bbd73113cd71d1384

router.get('/deleteTrip', function(req, res){
	/*var token = req.headers.authorization;
	if(token == undefined){
		res.send(JSON.stringify({"status":"error","type":"-12"}));
	}
	else{
		admin.auth().verifyIdToken(token).then(function(decodedToken) {
    		var userUid = decodedToken.uid;
			*/
			TripSchema.findById(req.query.tripId, function(err, resp){
		
				if(err){
					res.send(JSON.stringify({ status: "error", type: "-1" }));
					console.log(err);
					console.log(JSON.stringify({ status: "error", type: "-1" }));
				}
				else{
			
					if(resp == null || resp.partecipants != 1){
						res.send(JSON.stringify({ status: "error", type: "-12" }));
						console.log(JSON.stringify({ status: "error", type: "-12" }));
						return;
					}
		
				var trip = {
					"tripId": req.query.tripId
				};
		
				var condition = {
					uid : resp.owner
				};
		
				var update = {
					$pull: {trips: trip},
				};

				UserSchema.findOne(condition).exec(function(err, user){
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
						user.updateOne(update).exec(function(err, update) {
							if (err){
								res.send(JSON.stringify({ status: "error", type: "-1" }));
								console.log(err);
								console.log(JSON.stringify({ status: "error", type: "-1" }));
							}
							else{
								TripSchema.remove({_id : req.query.tripId}, function(err){
									if(err){
										res.send(JSON.stringify({ status: "error", type: "-1" }));
										console.log(err);
										console.log(JSON.stringify({ status: "error", type: "-1" }));
									}
									else{
										res.send(JSON.stringify({ status: "success", message: "The removal is a success"}));
									}
								});
							}
						});
					}
				});
			}

	

	/*
	TripSchema.remove({_id : id }, function(err){
		if(err){
			res.send(JSON.stringify({ status: "error", type: "-1" }));
			console.log(err);
			console.log(JSON.stringify({ status: "error", type: "-1" }));
		}
		else{
			
			console.log(JSON.stringify({ status: "success", message: "Your trip is deleted" }));
			
			var trip = {
				"tripId": req.query.tripId
			};
		   
			var condition1 = {
				"trips.tripId" : { $eq: req.query.tripId }
			};

			var condition2 = {
				"favouriteTrips.tripId" : { $eq: req.query.tripId }
			};
		
			var update = {
				$pull: {trips: trip, favouriteTrips: trip},
			};

			UserSchema.updateMany(condition1 || condition2, update, { multi: true }, function (err, raw) {
				if (err){
					console.log(err);
					res.send(JSON.stringify({ status: "error", type: "-1" }));
				}
				else{
					res.send(JSON.stringify({ status: "success", message: "the removal is a success" }));
				} 
			});
		}
	});
	*/
	});
	/*
	}).catch(function(error) {
    		res.send(JSON.stringify({"status":"error","type":"-12"}));
  		});
  	}*/
});

/******************************************/
//Api per ottenere un viaggio da Id
//Api verificata

// example use getTripByid?id=483249832948932ab43c443b

router.get('/getTripById', function(req, res){
	/*var token = req.headers.authorization;
	if(token == undefined){
		res.send(JSON.stringify({"status":"error","type":"-12"}));
	}
	else{
		admin.auth().verifyIdToken(token).then(function(decodedToken) {
    		//var userUid = decodedToken.uid;
			*/
			var id = req.query.id;

			if(id == undefined){
				res.send(JSON.stringify({ status: "error", type: "-4" }));
				return;
			}

			TripSchema.findById(id).exec(function(err, trip){
		
				if (err){
					res.send(JSON.stringify({ status: "error", type: "-3" }));
					console.log(err);
					console.log(JSON.stringify({ status: "error", type: "-3" }));
				}
				else{
					res.send(trip);
				}

		});
		/*
		}).catch(function(error) {
    		res.send(JSON.stringify({"status":"error","type":"-12"}));
  		});
  	}*/
});

/******************************************/
//Api per ottenere un viaggio da Id
//Api verificata

// example use getTripByidWithUsers?id=483249832948932ab43c443b

router.get('/getTripByIdWithUsers', function(req, res){
	/*var token = req.headers.authorization;
	if(token == undefined){
		res.send(JSON.stringify({"status":"error","type":"-12"}));
	}
	else{
		admin.auth().verifyIdToken(token).then(function(decodedToken) {
    		//var userUid = decodedToken.uid;
			*/
			var id = req.query.id;

			if(id == undefined)
				res.send(JSON.stringify({ status: "error", type: "-4" }));

			TripSchema.findById(id).exec(function(err, trip){
		
			if (err){
				res.send(JSON.stringify({ status: "error", type: "-3" }));
				console.log(err);
				console.log(JSON.stringify({ status: "error", type: "-3" }));
			}
			else{

				var conditions = {
        			"trips.tripId": { $eq: trip._id }
    			}

    			UserSchema.find(conditions,'uid name surname avatar').exec(function(err, users){
        
        		if (err){
            		res.send(JSON.stringify({ status: "error", type: "-1" }));
            		console.log(err);
            		console.log(JSON.stringify({ status: "error", type: "-1" }));
        		}
        
        		if (users.length > 0){
            		res.send([trip,users]);
        		}
        		else{
            		res.send(JSON.stringify({ status: "error", type: "-2" }));
            		console.log(JSON.stringify({ status: "error", type: "-2" }));
        		}

    		});

		}

	});
	/*
	}).catch(function(error) {
    		res.send(JSON.stringify({"status":"error","type":"-12"}));
  		});
  	}*/
});

/****************************************/
//Api per inserire pulire tutta la collezione trips
//Api verificata

router.get('/deleteAll', function(req, res){
	/*var token = req.headers.authorization;
	if(token == undefined){
		res.send(JSON.stringify({"status":"error","type":"-12"}));
	}
	else{
		admin.auth().verifyIdToken(token).then(function(decodedToken) {
    		//var userUid = decodedToken.uid;
			*/
			TripSchema.remove({}).exec(function(err, trip){
		
			if (err){
				res.send(JSON.stringify({ status: "error", type: "-1" }));
				console.log(err);
				console.log(JSON.stringify({ status: "error", type: "-1" }));
			}
			else{
				res.send(JSON.stringify({ status: "success", type: "all trips deleted" }));
			}

		});
		/*
		}).catch(function(error) {
    		res.send(JSON.stringify({"status":"error","type":"-12"}));
  		});
  	}*/
});



/****************************************/
//Api per inserire un nuovo partecipante in un viaggio

/*
router.post('/addParticipant', function(req,res){
	
	var JsonObject = req.body;

	var buddy = {
		"userId": JsonObject.userId
	};	
	var conditions = {							
		_id: JsonObject.tripId,
		'partecipant.userId': { $ne: JsonObject.userId }		
	};
	var update = {
		$addToSet: {partecipant: buddy}
	};
	
	TripSchema.findOne(conditions, function (err, trip) {
		if (err){
			res.send(JSON.stringify({ status: "error", message: "Error with ObjectId" }));
			console.log(err);
		}
		else if (trip == null){
			res.send(JSON.stringify({ status: "error", message: "User is already in this trip" }));
			console.log(JSON.stringify({ status: "error", message: "User is already in this trip" }));
		}			
		else{
			trip.updateOne(update, function(err, tripupdate){
				if (err){
					res.send(JSON.stringify({ status: "error", message: "Error on adding user" }));
					console.log(err);
				}
				else{
					res.send(JSON.stringify({ status: "ok", message: "User: " + JsonObject.userId + " added to trip: " + trip._id }));
					console.log(JSON.stringify({ status: "ok", message: "User: " + JsonObject.userId + " added to trip: " + trip._id }));
				};
			});
		};
	});
});
*/
/****************************************/
//Api per rimuovere un utente da un viaggio
/*
router.post('/removeParticipant', function(req,res){
	
	var JsonObject = req.body;

	var buddy = {
		"userId": JsonObject.userId
	};	
	var conditions = {							
		_id: JsonObject.tripId,
		partecipant: {$elemMatch: {userId: JsonObject.userId} } 	
	};
	var update = {
		$pull: {partecipant: buddy}
	};
	
	TripSchema.findOne(conditions, function (err, trip) {
		if (err){
			res.send(JSON.stringify({ status: "error", message: "Error with ObjectId" }));
			console.log(err);
		}
		else if (trip == null){
			res.send(JSON.stringify({ status: "error", message: "User is not in this trip" }));
			console.log(JSON.stringify({ status: "error", message: "User is not in this trip" }));
		}			
		else{
			trip.updateOne(update, function(err, tripupdate){
				if (err){
					res.send(JSON.stringify({ status: "error", message: "Error on removing user" }));
					console.log(err);
				}
				else{
					res.send(JSON.stringify({ status: "ok", message: "User: " + JsonObject.userId + " removed from trip: " + trip._id }));
					console.log(JSON.stringify({ status: "ok", message: "User: " + JsonObject.userId + " removed from trip: " + trip._id }));
				};
			});
		};
	});
});

*/

module.exports = router;

