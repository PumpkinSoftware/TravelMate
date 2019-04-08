var mongoose = require('mongoose');

var MailSchema = new mongoose.Schema({
        name: String,
        sender: String
	}

);

var Mail = mongoose.model("mail", MailSchema);
module.exports = Mail;
