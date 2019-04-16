var express = require('express');
var nodemailer = require("nodemailer");
var fs = require('fs');
var handlebars = require('handlebars');

//var database = require('./database');
//var firebase = require("./firebase");

//var url = process.env.DATA || "mongodb://127.0.0.1:27017/TravelMate";


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
        user: '',//'travelmate.system@gmail.com', 
        pass: ''// // mettere la passw della mail
    }
});

var readHTMLFile = function(path, callback) {
    fs.readFile(path, {encoding: 'utf-8'}, function (err, html) {
        if (err) {
            throw err;
            callback(err);
        }
        else {
            callback(null, html);
        }
    });
};


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
    		res.send("The Report System router works completely");
    		/*}).catch(function(error) {
    			res.send(JSON.stringify({"status":"error","type":"-12"}));
  		});
  	}*/
});


/*****************************************/

//Api per mandare segnalazione utente 
//example use /reportUser?userUid=abcd1234&reportedUid=efgh5678&typeReport=SegnalazioneUtente&text=segnaloutenteperchè


router.get('/reportUser', function (req,res){
	var input = req.body;
	
	
	let message = {
		to: 'pumpkin.software.italy@gmail.com',
		subject: '[' + input.typeReport + ']',
		text: 'Utente segnalatore: ' + input.userUid + '\nUtente segnalato: ' + input.reportedUid + '\nDescrizione segnalazione: ' + input.text
	}
	
	transporter.sendMail(message, (err, info) => {
        if (err) {
			console.log('Error occurred in sending ReportUser');
            console.log(err.message); 
			res.send(JSON.stringify({ status: "error", type: "error in sendMail" }));
        }
		else{
			console.log('User reported successfully!');
			console.log(nodemailer.getTestMessageUrl(info));
			res.send(JSON.stringify({ status: "success", type: "User Reported" }));
			// only needed when using pooled connections
			transporter.close();
		}	
	});
	
});

/*****************************************/

//Api per mandare segnalazione viaggio
//example use /reportTrip?userUid=abcd1234&reportedTripId=efgh5678&typeReport=SegnalazioneViaggio&text=segnaloviaggioperchè


router.get('/reportTrip', function (req,res){
	var input = req.body;
	
	
	let message = {
		to: 'pumpkin.software.italy@gmail.com',
		subject: '[' + input.typeReport + ']',
		text: 'Utente segnalatore: ' + input.userUid + '\nViaggio segnalato: ' + input.reportedTripId + '\nDescrizione segnalazione: ' + input.text
	}
	
	transporter.sendMail(message, (err, info) => {
        if (err) {
			console.log('Error occurred in sending reportTrip');
            console.log(err.message); 
			res.send(JSON.stringify({ status: "error", type: "error in sendMail" }));
        }
		else{
			console.log('Message sent successfully!');
			console.log(nodemailer.getTestMessageUrl(info));
			res.send(JSON.stringify({ status: "success", type: "Trip Reported" }));
			// only needed when using pooled connections
			transporter.close();
		}	
	});
	
});

/*****************************************/

//Api per avvisare un utente che ha violato i termini delle condizioni o ha ricevuto un ban
//example use /violationsTerms?name=Carlo&surname=Rossi&emailUser=example@gmail.com&typeReport=violazioneTerminiCondizioni


router.get('/violationsTerms', function (req,res){
	var input = req.body;
	
	readHTMLFile(__dirname + '/template_email_html/index.html', function(err, html) {
		var template = handlebars.compile(html);
		var replacements = {
			username: input.name
		};
		var htmlToSend = template(replacements);	
		let message = {
			to: input.emailUser,
			subject: '[' + input.typeReport + ']',
			html: htmlToSend
			//text: 'Utente segnalatore: ' + input.userUid + '\nViaggio segnalato: ' + input.reportedTripId + '\nDescrizione segnalazione: ' + input.text
		}
	
		transporter.sendMail(message, (err, info) => {
			if (err) {
				console.log('Error occurred in sending reportTrip');
				console.log(err.message); 
				res.send(JSON.stringify({ status: "error", type: "error in sendMail" }));
			}
			else{
				console.log('Message sent successfully!');
				console.log(nodemailer.getTestMessageUrl(info));
				res.send(JSON.stringify({ status: "success", type: "Violation sent" }));
				// only needed when using pooled connections
				transporter.close();
			}	
		});
	});
});


module.exports = router;

