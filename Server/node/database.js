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

 		databaseObject.createCollection("trips", function(err, res) {
    		if (err) throw err;
    		console.log("Collection trips created!");
  		});

  		databaseObject.createCollection("users", function(err, res) {
    		if (err) throw err;
    		console.log("Collection users created!");
  		});
	});
}

exports.getDatabaseObject = function(){
	if(databaseObject != null)
		return databaseObject;
	else 
		return null;
}
