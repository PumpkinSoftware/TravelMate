var mongoose = require('mongoose');

var TripSchema = new mongoose.Schema({
        "name": String,
        "description": String,
		"partecipant": [{"email":String}]
	}

);

var Trip = mongoose.model("trip", TripSchema);
module.exports = Trip;
