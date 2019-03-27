var admin = require("firebase-admin");

var serviceAccount = require("./firebase/serviceAccountKey.json");

exports.initializeFirebase = function(){
	admin.initializeApp({
  		credential: admin.credential.cert(serviceAccount),
  		databaseURL: "https://travelmate-ee239.firebaseio.com"
	});
	console.log("Firebase initializated");
}

exports.getAdmin = function(){
	return admin;
}