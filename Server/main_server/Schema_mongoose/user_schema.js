var mongoose = require('mongoose');

var UserSchema = new mongoose.Schema({
        name: String,
        surname: String,
        age: Number,
        gender: String,
        relationship: String,
        email: String,
        description: String,
        avatar: String,
        cover: String,
        sumReview: Number,
        numReview: Number,
        trips: [{tripId: String}],
        favouriteTrips: [{tripId: String}],
        comments: [{userId: String, comment: String, date: Date}]
	}

);

var User = mongoose.model("user", UserSchema);
module.exports = User;
