var express = require('express');
var mongoose = require('mongoose');
var UserSchema = require('./Schema_mongoose/user_schema');
var url = process.env.DATA || "mongodb://127.0.0.1:27017/TravelMate";

//Faccio collegare mongoose al database
mongoose.connect(url);

var router = express.Router(); 

/******************************************
			  Inizio API
******************************************/


/*****************************************/
//Api per testare il funzionamento di trip_api.js
router.get('/', function (req, res) {
    res.send("Mi trovo in user_api.js");
});

module.exports = router;


/*****************************************/
//Api per inserire un nuovo utente

router.post('/new_user', function(req,res){

	var clientInput = req.body;

	var toInsert = new UserSchema({
		name : clientInput.name,
        surname: clientInput.surname,
        nickname: clientInput.nickname,
        age: clientInput.age,
        gender: clientInput.gender,
        birthday: clientInput.birthday,
        relationship: clientInput.relationship,
		email: clientInput.email 
	});

	toInsert.save(function (err) {
        if (err){ 
            console.log(err);
        	res.send("Error to add "+toInsert.name+" on the database.");
        	console.log("Error to add "+toInsert.name+" on the database.");
        }
        else {
        	res.send("User " + toInsert.name + " created!");
        	console.log("User " + toInsert.name + " created!");
        }
    });

});


module.exports = router;
