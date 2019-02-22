var mongoose = require('mongoose');

var TripSchema = new mongoose.Schema({
        name: String,
        description: String,
        departure: String,
        destination: String,
        image: String,
        budget: Number,
        startDate: Date,
        endDate: Date,
        car: Boolean,
        owner: String,
        maxPartecipant: Number,
        tags: [{tag:String}],
		partecipant: [{userId:String}]
	}

);

var Trip = mongoose.model("trip", TripSchema);
module.exports = Trip;