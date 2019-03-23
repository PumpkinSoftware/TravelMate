var mongoose = require('mongoose');

var UserSchema = new mongoose.Schema({
        name: String,
        surname: String,
        birthday: Date,
        gender: String,
        relationship: String,
        email: String,
        description: String,
        uid: String,
        avatar: String,
        cover: String,
        sumReview: Number,
        numReview: Number,
        sumReview1: Number,
        numReview1: Number,
        trips: [{tripId: String}],
        favouriteTrips: [{tripId: String}],
        myReview: [{userUid: String,tripId: String,sumReview: Number,sumReview1: Number,addDate:Date}]
	}

);

var User = mongoose.model("user", UserSchema);
module.exports = User;
