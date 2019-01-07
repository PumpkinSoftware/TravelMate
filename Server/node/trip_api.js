var express = require('express');

var router = express.Router();  

router.get('/', function (req, res) {
    res.send("Mi trovo in trip_api.js");
});

module.exports = router;

