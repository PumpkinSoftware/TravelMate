var express = require('express');
var mongoose = require('mongoose');
var Trip = require('./Schema_mongoose/trip_schema');
var database = require('./database');
//var url = process.env.DATA || "mongodb://127.0.0.1:27017/TravelMate";


var router = express.Router(); 



router.get('/', function (req, res) {
    res.send("Mi trovo in trip_api.js");
});

router.get('/new_trip', function(req,res){
    const trip1 = new Trip({
        name: 'First',
        description: 'Sea',
        partecipant: ['dbfhdsh@live.it', 'sjshhds@shdhs.it']
    });

    const trip2 = new Trip({
        name: 'Second',
        description: 'Desert',
        partecipant: ['dbfhdsh@live.it', 'sjshhds@shdhs.it']
    });

    database.getDatabaseObject().collection('trip').insert(trip1);
    database.getDatabaseObject().collection('trip').insert(trip2);

    Trip.findOne({'name': 'Second'}, (err, trip) => {
        if(err)
            throw err;
        console.log(trip);
    });
});

module.exports = router;

