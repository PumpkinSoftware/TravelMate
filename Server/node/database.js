var MongoClient = require('mongodb').MongoClient;


var databaseObject = null;

exports.connect = function(url){
	MongoClient.connect(url, function(err, db) {
		if (err){
			console.log("Error open database");
			throw err;
  		}
  		console.log("Database Connected");

  		databaseObject = db.db("TravelMate");

 		databaseObject.createCollection("trip", function(err, res) {
    		if (err) throw err;
    		console.log("Collection trip created!");
  		});

  		databaseObject.createCollection("user", function(err, res) {
    		if (err) throw err;
    		console.log("Collection user created!");
  		});
	});
}

exports.getDatabaseObject = function(){
	if(databaseObject != null)
		return databaseObject;
	else 
		return null;
}
