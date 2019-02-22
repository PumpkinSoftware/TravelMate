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
        vehicle: String,
        owner: String,
        maxPartecipant: Number,
        tag: String,
        partecipant: [{userId:String}]
	}

);

var Trip = mongoose.model("trip", TripSchema);
module.exports = Trip;
