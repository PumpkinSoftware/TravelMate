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
        sumReview2: Number,
        numReview2: Number,
        sumReview3: Number,
        numReview3: Number,
        sumReview4: Number,
        numReview4: Number,
        sumReview5: Number,
        numReview5: Number,
        trips: [{tripId: String}],
        favouriteTrips: [{tripId: String}],
        myReview: [{userUid: String,addDate:Date}]
	}

);

var User = mongoose.model("user", UserSchema);
module.exports = User;
