var mongoose = require('mongoose');

var TripSchema = new mongoose.Schema({
        name: String,
        description: String}

);

module.exports = mongoose.model("Trip", TripSchema);
