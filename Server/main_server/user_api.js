var express = require('express');
var mongoose = require('mongoose');
var UserSchema = require('./Schema_mongoose/user_schema');
var TripSchema = require('./Schema_mongoose/trip_schema');
var firebase = require("./firebase");

var url = process.env.DATA || "mongodb://127.0.0.1:27017/TravelMate";

//Faccio collegare mongoose al database
mongoose.connect(url,{ useNewUrlParser: true });

//Firebase
//var admin = firebase.getAdmin();

var router = express.Router(); 

/******************************************
			  Inizio API
******************************************/

/*****************************************/
//Api per testare il funzionamento di trip_api.js
//Api verificata

router.get('/', function (req, res) {
    /*
	var token = req.headers.authorization;
	if(token == undefined){
		res.send(JSON.stringify({"status":"error","type":"-12"}));
	}
	else{
		admin.auth().verifyIdToken(token).then(function(decodedToken) {
    		//var uid = decodedToken.uid;
            */
    		res.send("The user router works completely");
            /*
  		}).catch(function(error) {
    		res.send(JSON.stringify({"status":"error","type":"-12"}));
  		});
  	}*/
});

/****************************************/
//Api per inserire un nuovo utente
//Api verificata

router.post('/newUser', function(req, res){
	/*var token = req.headers.authorization;
	if(token == undefined){
		res.send(JSON.stringify({"status":"error","type":"-12"}));
	}
	else{
		admin.auth().verifyIdToken(token).then(function(decodedToken) {
    		var userUid = decodedToken.uid;
            */
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
                	uid: /*userUid,*/ clientInput.uid,
                	description: clientInput.description,
                	avatar: clientInput.avatar,
                	cover: clientInput.cover,
                	sumReview: 0,
                	numReview: 0,
                	sumReview1: 0,
                	numReview1: 0,
                	sumReview2: 0,
                	numReview2: 0,
                	sumReview3: 0,
                	numReview3: 0,
                	sumReview4: 0,
                	numReview4: 0,
                	sumReview5: 0,
                	numReview5: 0,
                	trips: [],
                	favouriteTrips: [],
                	myReview: []
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
                	uid: /*userUid,*/ clientInput.uid,
                	description: clientInput.description,
                	avatar: clientInput.avatar,
                	cover: clientInput.cover,
                	sumReview: 0,
                	numReview: 0,
                	sumReview1: 0,
                	numReview1: 0,
                	sumReview2: 0,
                	numReview2: 0,
                	sumReview3: 0,
                	numReview3: 0,
                	sumReview4: 0,
                	numReview4: 0,
                	sumReview5: 0,
                	numReview5: 0,
                	trips: [],
                	favouriteTrips: [],
                	myReview: []
            		});
        		}
            
            	toInsert.save(function (err) {
                	if (err){ 
                    	console.log(err);
                    	res.send(JSON.stringify({ status: "error", type: "-1" }));
                    	console.log(JSON.stringify({ status: "error", type: "-1" }));
                	}
                	else {
                    	res.send(JSON.stringify({ status: "success", message: "User created!" }));
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
//Api per testing che stampa tutti gli utenti registrati
//Api verificata

router.get('/allUsers', function(req, res){
	/*var token = req.headers.authorization;
	if(token == undefined){
		res.send(JSON.stringify({"status":"error","type":"-12"}));
	}
	else{
		admin.auth().verifyIdToken(token).then(function(decodedToken) {
    		//var userUid = decodedToken.uid;
            */
    		UserSchema.find({}).exec( function(err, users){
				if(err){
					console.log(err);
					console.log(JSON.stringify({ status: "error", type: "-1" }));
					res.send(JSON.stringify({ status: "error", type: "-1" }));
				}else{
					res.send(users);
				}
			});
    /*
	}).catch(function(error) {
    		res.send(JSON.stringify({"status":"error","type":"-12"}));
  		});
  	}*/
});

/****************************************/
//Api per ottenere un utente tramite email
//Api verificata

//Example to use getUserByEmail?email=prova@gmail.com

router.get('/getUserByEmail', function(req, res){
	/*var token = req.headers.authorization;
	if(token == undefined){
		res.send(JSON.stringify({"status":"error","type":"-12"}));
	}
	else{
		admin.auth().verifyIdToken(token).then(function(decodedToken) {
    		//var userUid = decodedToken.uid;
            */
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
    /*
    }).catch(function(error) {
    		res.send(JSON.stringify({"status":"error","type":"-12"}));
  		});
  	}*/
});

/****************************************/
//Api per ottenere un utente tramite uid
//Api verificata

//Example to use getUserByUid?userUid=ddeun534FR32ew

router.post('/getUserByUid', function(req, res){
	/*var token = req.headers.authorization;
	if(token == undefined){
		res.send(JSON.stringify({"status":"error","type":"-12"}));
	}
	else{
		admin.auth().verifyIdToken(token).then(function(decodedToken) {
    		var userUid = null;
    		if(req.body.userUid == undefined){
    			userUid = decodedToken.uid;
    		}
    		else{
    			userUid = req.body.userUid;
    		}
            */
    		var conditions = {
        		uid: req.body.userUid /*userUid*/
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
    /*
    }).catch(function(error) {
    		res.send(JSON.stringify({"status":"error","type":"-12"}));
  		});
  	}*/
});

/****************************************/
//Api per ottenere un utente tramite uid
//Api verificata

//Example to use /getUserById?userId=...

router.get('/getUserById', function(req, res){
	/*var token = req.headers.authorization;
	if(token == undefined){
		res.send(JSON.stringify({"status":"error","type":"-12"}));
	}
	else{
		admin.auth().verifyIdToken(token).then(function(decodedToken) {
    		//var userUid = decodedToken.uid;
        */
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
    /*
    }).catch(function(error) {
    		res.send(JSON.stringify({"status":"error","type":"-12"}));
  		});
  	}*/
	
});

/******************************************/
//Api per aggiornare un utente
//Api verificata

router.post('/updateUser', function(req, res){
	/*var token = req.headers.authorization;
	if(token == undefined){
		res.send(JSON.stringify({"status":"error","type":"-12"}));
	}
	else{
		admin.auth().verifyIdToken(token).then(function(decodedToken) {
    		var userUid = decodedToken.uid;
            */
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
        		uid: /*query.uid*/ userUid
    		};

    		console.log(query);
    
			UserSchema.findOne(conditions).exec(function(err, user){
		
			if (err){
				res.send(JSON.stringify({ status: "error", type: "-1" }));
				console.log(err);
				console.log(JSON.stringify({ status: "error", type: "-1" }));
        	}
        
        	else if (user != null){

		    	user.set(query);
		
		    	user.save(function(err, updateuser){
			    	if (err){
				    	res.send(JSON.stringify({ status: "error", type: "-1" }));
				    	console.log(err);
				    	console.log(JSON.stringify({ status: "error", type: "-1" }));
			    	} 
			    	res.send({ status: "success", message: "Your user is updated" });
            	});
        	}
        	else{
            	res.send(JSON.stringify({ status: "error", type: "-2" }));
            	console.log(JSON.stringify({ status: "error", type: "-2" }));
        	}
		});
    /*
	}).catch(function(error) {
    		res.send(JSON.stringify({"status":"error","type":"-12"}));
  		});
  	}*/
});

/******************************************/
//Api per inserire l'id di un viaggio all'interno dell'array favouriteTrips
//Api verificata

//Example to use addFavourTrip?tripId=....&usersUid=....

router.get('/addFavouriteTrip', function(req, res){
	/*var token = req.headers.authorization;
	if(token == undefined){
		res.send(JSON.stringify({"status":"error","type":"-12"}));
	}
	else{
		admin.auth().verifyIdToken(token).then(function(decodedToken) {
    		var userUid = decodedToken.uid;
            */
    		var trip = {
        		"tripId": req.query.tripId
    		};
   
    		var conditions = {
        		uid: req.query.userUid /*userUid*/,
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
							res.send(JSON.stringify({ status: "success", message: "FavouriteTrip added to user"}));
						}
					});
        		}
        		else{
            		res.send(JSON.stringify({ status: "error", type: "-9" }));
            		console.log(JSON.stringify({ status: "error", type: "-9" }));
        		}
    		});
        /*
    	}).catch(function(error) {
    		res.send(JSON.stringify({"status":"error","type":"-12"}));
  		});
  	}*/
});

/******************************************/
//Api per rimuovere l'id di un viaggio all'interno dell'array favouriteTrips
//Api verificata

//Example to use removeFavourTrip?tripId=....&usersUid=....

router.get('/removeFavouriteTrip', function(req, res){
	/*var token = req.headers.authorization;
	if(token == undefined){
		res.send(JSON.stringify({"status":"error","type":"-12"}));
	}
	else{
		admin.auth().verifyIdToken(token).then(function(decodedToken) {
    		var userUid = decodedToken.uid;
            */
    		var trip = {
        		"tripId": req.query.tripId
    		};
   
    		var conditions = {
        		uid: req.query.userUid /*userUid*/,
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
							res.send(JSON.stringify({ status: "success", message: "FavouriteTrip removed to user"}));
						}
					});
        		}
        		else{
            		res.send(JSON.stringify({ status: "error", type: "-10" }));
            		console.log(JSON.stringify({ status: "error", type: "-10" }));
        		}
    		});
    /*
    }).catch(function(error) {
    		res.send(JSON.stringify({"status":"error","type":"-12"}));
  		});
  	}*/
});

/******************************************/
//Api per ottenere tutti gli utenti che abbiano tripId all'interno di trips
//Api verificata

//Example to use /GetUsersByTrip?tripId=...

router.get('/getUsersByTrip', function(req, res){
	/*var token = req.headers.authorization;
	if(token == undefined){
		res.send(JSON.stringify({"status":"error","type":"-12"}));
	}
	else{
		admin.auth().verifyIdToken(token).then(function(decodedToken) {
    		//var userUid = decodedToken.uid;
            */
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
        /*
    	}).catch(function(error) {
    		res.send(JSON.stringify({"status":"error","type":"-12"}));
  		});
  	}*/
});

/******************************************/
//Api per inserire l'id di un viaggio all'interno dell'array Trips
//Api verificata

//Example to use Post /addTrip?tripId=....&userUid=....

router.post('/addTrip', function(req,res){
	/*var token = req.headers.authorization;
	if(token == undefined){
		res.send(JSON.stringify({"status":"error","type":"-12"}));
	}
	else{
		admin.auth().verifyIdToken(token).then(function(decodedToken) {
    		var userUid = decodedToken.uid;
	        */
			var JsonObject = req.body;

	
			var trip = {
				"tripId": JsonObject.tripId
			};
		
			var conditions_A = {							
				_id: JsonObject.tripId,
				$where:'this.partecipants < this.maxPartecipant'	
			};
	
			var conditions_B = {							
				uid: JsonObject.userUid /*userUid*/,
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
											res.send(JSON.stringify({ status: "success", message: "Trip added to user"}));
										}
									});
								};
							});
						};
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
//Api per rimuovere l'id di un viaggio all'interno dell'array Trips
//Api verificata

//Example to use Post /removeTrip?tripId=....&userUid=....

router.post('/removeTrip', function(req,res){
	/*var token = req.headers.authorization;
	if(token == undefined){
		res.send(JSON.stringify({"status":"error","type":"-12"}));
	}
	else{
		admin.auth().verifyIdToken(token).then(function(decodedToken) {
    		var userUid = decodedToken.uid;
	        */
			var JsonObject = req.body;

			var trip = {
				"tripId": JsonObject.tripId
			};	
			var conditions_A = {							
				uid: JsonObject.userUid /*userUid*/,
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
											res.send(JSON.stringify({ status: "success", message: "Trip removed from user"}));
										}
									});
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

/******************************************/
//Api per rimuovere un utente.
//Api verificata

 //Example to use /deleteUser?userUid=...

router.get('/deleteUser', function(req, res){
	/*var token = req.headers.authorization;
	if(token == undefined){
		res.send(JSON.stringify({"status":"error","type":"-12"}));
	}
	else{
		admin.auth().verifyIdToken(token).then(function(decodedToken) {
    		var userUid = decodedToken.uid;
            */
			var uid = req.query.userUid;    

			UserSchema.remove({uid : uid/*userUid*/ }).exec( function(err){
				if(err){
					res.send(JSON.stringify({ status: "error", type: "-1" }));
					console.log(err);
					console.log(JSON.stringify({ status: "error", type: "-1" }));
				}
				else{
					res.send(JSON.stringify({ status: "success", message: "User is deleted" }));						
				}
			});
        /*
		}).catch(function(error) {
    		res.send(JSON.stringify({"status":"error","type":"-12"}));
  		});
  	}*/
});

/******************************************/
//Api che dato un utente restituisce i suoi viaggi con informazioni annesse
//Api verificata
 
//Example to use /getTripByUser?userUid=...

router.get('/getTripsByUser', function(req, res){
	/*var token = req.headers.authorization;
	if(token == undefined){
		res.send(JSON.stringify({"status":"error","type":"-12"}));
	}
	else{
		admin.auth().verifyIdToken(token).then(function(decodedToken) {
    		var userUid = decodedToken.uid;
            */
			var uid = req.query.userUid;

			var conditions1 = {
				uid: uid /*userUid*/
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
        /*
    	}).catch(function(error) {
    		res.send(JSON.stringify({"status":"error","type":"-12"}));
  		});
  	}*/
});

/******************************************/
//Api che dato un utente restituisce i suoi viaggi con informazioni annesse,divisi 
// in in corso e passati
//Api verificata

//Example to use /getTripByUserSplit?userUid=...

router.get('/getTripsByUserSplit',function(req,res){
	/*var token = req.headers.authorization;
	if(token == undefined){
		res.send(JSON.stringify({"status":"error","type":"-12"}));
	}
	else{
		admin.auth().verifyIdToken(token).then(function(decodedToken) {
    		var userUid = decodedToken.uid;
	        */
			var uid = req.query.userUid;

			UserSchema.findOne({uid : uid/*userUid*/}).exec( function(err, user){
        
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
        /*
    	}).catch(function(error) {
    		res.send(JSON.stringify({"status":"error","type":"-12"}));
  		});
  	}*/

});

/******************************************/
//Api che dato un utente restituisce i suoi viaggi passati con informazioni annesse 
//Api verificata

// Example to use /getPassedTripsByUser?userUid=...

router.get('/getPassedTripsByUser',function(req,res){
	/*var token = req.headers.authorization;
	if(token == undefined){
		res.send(JSON.stringify({"status":"error","type":"-12"}));
	}
	else{
		admin.auth().verifyIdToken(token).then(function(decodedToken) {
    		var userUid = decodedToken.uid;
            */
			var uid = req.query.userUid;

			UserSchema.findOne({uid : uid/*userUid*/}).exec( function(err, user){
        
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
        /*
		}).catch(function(error) {
    		res.send(JSON.stringify({"status":"error","type":"-12"}));
  		});
  	}*/
});

/******************************************/
//Api che dato un utente restituisce i suoi viaggi in corso con informazioni annesse 
//Api verificata

// Example to use /getPassedTripsByUser?userUid=...

router.get('/getProgressTripsByUser',function(req,res){
	/*var token = req.headers.authorization;
	if(token == undefined){
		res.send(JSON.stringify({"status":"error","type":"-12"}));
	}
	else{
		admin.auth().verifyIdToken(token).then(function(decodedToken) {
    		var userUid = decodedToken.uid;*/
			var uid = req.query.userUid;

			UserSchema.findOne({uid : uid/*userUid*/}).exec( function(err, user){
        
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
        /*
    	}).catch(function(error) {
    		res.send(JSON.stringify({"status":"error","type":"-12"}));
  		});
  	}
    */
});

/******************************************/
//Api che dato un viaggio e un utente, cambia l'owner del viaggio nell'utente dato e rimuove il precedente owner dai partecipanti del viaggio 

// Example to use /changeOwnerAndRemoveLast?tripId=...&userUid=...

router.post('/changeOwnerAndRemoveLast',function(req,res){
	/*var token = req.headers.authorization;
	if(token == undefined){
		res.send(JSON.stringify({"status":"error","type":"-12"}));
	}
	else{
		admin.auth().verifyIdToken(token).then(function(decodedToken) {
    		var userUid = decodedToken.uid;
	        */
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
										res.send(JSON.stringify({ status: "success", message: "Trip has now the new owner"}));
									}
								});
							}
						});
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
			UserSchema.remove({}).exec(function(err, trip){
		
			if (err){
				res.send(JSON.stringify({ status: "error", type: "-1" }));
				console.log(err);
				console.log(JSON.stringify({ status: "error", type: "-1" }));
			}
			else{
				res.send(JSON.stringify({ status: "success", type: "all users deleted" }));
			}
		});
        /*
		}).catch(function(error) {
    		res.send(JSON.stringify({"status":"error","type":"-12"}));
  		});
  	}*/
});

/****************************************/
//Api per inserire una recensione
router.post('/addReview',function(req,res){
	/*var token = req.headers.authorization;
	if(token == undefined){
		res.send(JSON.stringify({"status":"error","type":"-12"}));
	}
	else{
		admin.auth().verifyIdToken(token).then(function(decodedToken) {
    		var userUid = decodedToken.uid;
            */
			var input = req.body;

			console.log(input);

			UserSchema.findOne({uid:input.userToReview}).exec(function(err,user){
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

				var updates = {
					$inc: {sumReview: input.sumReview, numReview: +1, sumReview1: input.sumReview1, numReview1: +1,sumReview2: input.sumReview2, numReview2: +1,sumReview3: input.sumReview3, numReview3: +1,sumReview4: input.sumReview4, numReview4: +1,sumReview5: input.sumReview5, numReview5: +1}
				};

				user.updateOne(updates).exec(function(err, userupdate){
					if (err){
						res.send(JSON.stringify({ status: "error", type: "-1" }));
						console.log(err);
						console.log(JSON.stringify({ status: "error", type: "-1" }));
					}
					else{
						var conditions_myReview = {
							uid:input.userUid/*userUid*/
						};

						UserSchema.findOne(conditions_myReview).exec(function(err,usermy){
							if (err){
								res.send(JSON.stringify({ status: "error", type: "-1" }));
								console.log(err);
								console.log(JSON.stringify({ status: "error", type: "-1" }));
							}
							else if (usermy == null){
								res.send(JSON.stringify({ status: "error", type: "-11" }));
								console.log(JSON.stringify({ status: "error", type: "-11" }));
							}			
							else{
								var update_my = {
									$push: {myReview: {userUid:input.userToReview,addDate:new Date()}}
								};
								usermy.updateOne(update_my).exec(function(err, userupdate){
									if (err){
										res.send(JSON.stringify({ status: "error", type: "-1" }));
										console.log(err);
										console.log(JSON.stringify({ status: "error", type: "-1" }));
									}
									else{
										res.send(JSON.stringify({ status: "success", type: "Add review correctly" }));
									}
								});	
							}
				    	});
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
//Api per ottenre la lista delle recensioni da fare
router.get('/leftReviews',function(req,res){
	/*var token = req.headers.authorization;
	if(token == undefined){
		res.send(JSON.stringify({"status":"error","type":"-12"}));
	}
	else{
		admin.auth().verifyIdToken(token).then(function(decodedToken) {
    		var userUid = decodedToken.uid;
			//var uid = req.query.userUid;
            */
			UserSchema.findOne({uid : /*uid*/userUid}).exec( function(err, user){
        
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

				TripSchema.find(conditions2, '_id').where('endDate').lte(new Date()).exec(function(err,passed){
					if(err){
						console.log(err);
						console.log(JSON.stringify({ status: "error", type: "-1" }));
						res.send(JSON.stringify({ status: "error", type: "-1" }));
					}
					else{
					
						var list_passed_trips = passed.map(function(trip){
							return trip._id;
						});
					
						var list_user_already_reviewed = user.myReview.map(function(res){
							return res.userUid;
						});
					
						var conditions3 = {
							"trips.tripId": {$in: list_passed_trips},
							$and: [
							{'uid': {$ne: /*uid*/userUid}}, //il secondo uid in questa condizione è quello di req.query.userUid
							{'uid': {$nin: list_user_already_reviewed}} //verifica che l'utente non sia nell'array delle review già fatte
							]
						}
					
						UserSchema.find(conditions3, 'name surname uid avatar').exec(function(err,usertoreview){
							if(err){
								console.log(err);
								console.log(JSON.stringify({ status: "error", type: "-2" }));
								res.send(JSON.stringify({ status: "error", type: "-2" }));
							}
							else {
								res.send(usertoreview);
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
        /*
    	}).catch(function(error) {
    		res.send(JSON.stringify({"status":"error","type":"-12"}));
  		});
  	}*/
});

/****************************************/
//Api per ottenre la lista delle recensioni da fare, numero
router.get('/leftReviewsNumbers',function(req,res){
	/*var token = req.headers.authorization;
	if(token == undefined){
		res.send(JSON.stringify({"status":"error","type":"-12"}));
	}
	else{
		admin.auth().verifyIdToken(token).then(function(decodedToken) {
    		var userUid = decodedToken.uid;*/
			var uid = req.query.userUid;

			UserSchema.findOne({uid : uid/*userUid*/}).exec( function(err, user){
        
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

				TripSchema.find(conditions2, '_id').where('endDate').lte(new Date()).exec(function(err,passed){
					if(err){
						console.log(err);
						console.log(JSON.stringify({ status: "error", type: "-1" }));
						res.send(JSON.stringify({ status: "error", type: "-1" }));
					}
					else{
					
						var list_passed_trips = passed.map(function(trip){
							return trip._id;
						});
					
						var list_user_already_reviewed = user.myReview.map(function(res){
							return res.userUid;
						});
					
						var conditions3 = {
							"trips.tripId": {$in: list_passed_trips},
							$and: [
							{'uid': {$ne: uid/*userUid*/}}, //il secondo uid in questa condizione è quello di req.query.userUid
							{'uid': {$nin: list_user_already_reviewed}} //verifica che l'utente non sia nell'array delle review già fatte
							]
						}
					
						UserSchema.find(conditions3, '_id').exec(function(err,usertoreview){
							if(err){
								console.log(err);
								console.log(JSON.stringify({ status: "error", type: "-2" }));
								res.send(JSON.stringify({ status: "error", type: "-2" }));
							}
							else {
								res.send({"numbers":usertoreview.length});
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
        /*
    	}).catch(function(error) {
    		res.send(JSON.stringify({"status":"error","type":"-12"}));
  		});
  	}*/
});


module.exports = router;
