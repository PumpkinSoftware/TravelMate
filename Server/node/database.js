var MongoClient = require('mongodb').MongoClient;

exports.connect = function(url){
	MongoClient.connect(url, function(err, db) {
		if (err){
			console.log("Error open database");
			throw err;
  		}
  		console.log("Database Connected");

  		var dbo = db.db("TravelMate");

 		dbo.createCollection("trip", function(err, res) {
    		if (err) throw err;
    		console.log("Collection trip created!");
  		});

  		dbo.createCollection("user", function(err, res) {
    		if (err) throw err;
    		console.log("Collection user created!");
  		});
	});
}
