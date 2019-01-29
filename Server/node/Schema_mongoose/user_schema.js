var mongoose = require('mongoose');

var UserSchema = new mongoose.Schema({
        name: String,
        surname: String,
        nickname: String,
        age: Number,
        gender: String,
        birthday: Date,
        relationship: String,
        email: String,
	}

);

var User = mongoose.model("user", UserSchema);
module.exports = User;
