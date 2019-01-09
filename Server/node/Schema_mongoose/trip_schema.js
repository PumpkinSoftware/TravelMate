var mongoose = require('mongoose');

var TripSchema = new mongoose.Schema({
        name: String,
        description: String,
	partecipant: [{email:String}]
	}

);

module.exports = mongoose.model("Trip", TripSchema);
