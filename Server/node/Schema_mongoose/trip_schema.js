var mongoose = require('mongoose');

var TripSchema = new mongoose.Schema({
        name: String,
        description: String,
        departure: String,
        destination: String,
        budget: Number,
        startDate: Date,
        endDate: Date,
        pets: Boolean,
        maxPartecipant: Number,
		partecipant: [{email:String}]
	}

);

var Trip = mongoose.model("trip", TripSchema);
module.exports = Trip;
