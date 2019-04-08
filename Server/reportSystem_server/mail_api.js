var express = require('express');
//var mongoose = require('mongoose');
//var MailSchema = require('./Schema_mongoose/mail_schema');
var nodemailer = require("nodemailer");

//var database = require('./database');
//var firebase = require("./firebase");

var url = process.env.DATA || "mongodb://127.0.0.1:27017/TravelMate";


//Faccio collegare mongoose al database
//mongoose.connect(url,{ useNewUrlParser: true });

//Firebase
//var admin = firebase.getAdmin();

//Creo il router
var router = express.Router(); 


// create reusable transport method (opens pool of SMTP connections)
var transporter = nodemailer.createTransport({
    service: 'Gmail',
    auth: {
        user: 'user.mail', //mettere la mail
        pass: 'xxxxx' // mettere la passw della mail
    }
});

/******************************************
			  Inizio API
******************************************/

/*****************************************/
//Api per testare il funzionamento di mail_api.js
//Api verificata

router.get('/', function (req, res) {
	/*var token = req.headers.authorization;
	if(token == undefined){
		res.send(JSON.stringify({"status":"error","type":"-12"}));
	}
	else{
		admin.auth().verifyIdToken(token).then(function(decodedToken) {
    		//var userUid = decodedToken.uid;*/
    		res.send("The mail router works completely");
    		/*}).catch(function(error) {
    			res.send(JSON.stringify({"status":"error","type":"-12"}));
  		});
  	}*/
});


/*****************************************/

//Api per testare funzionamento mail
//example use /reportUser?userUid=abcd1234&reportedUid=efgh5678&typeReport=klisuiy&text


router.get('/reportUser', function (req,res){
	var input = req.body;
	
	
	let message = {
		to: 'pumpkin.software.italy@gmail.com',
		subject: input.typeReport,
		text: 'Hello from TravelMate'
	}
	
	transporter.sendMail(message, (err, info) => {
        if (err) {
			res.send(JSON.stringify({ status: "error", type: "err in sendMail" }));
            console.log('Error occurred');
            console.log(err.message);
        }

        console.log('Message sent successfully!');
        console.log(nodemailer.getTestMessageUrl(info));
		res.send(JSON.stringify({ status: "success", type: "mail sent" }));
        // only needed when using pooled connections
        transporter.close();
	});
	
});



module.exports = router;

