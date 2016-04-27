// call the packages we need
var express = require("express");
var bodyParser = require("body-parser");
var fs = require("fs");
var app = express();

// configure app to use bodyParser()
// this will let us get the data from a POST
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

var port = process.env.PORT || 8002;        // set our port

// ROUTES FOR OUR API
var router = express.Router();              // get an instance of the express Router

// test route to make sure everything is working (accessed at GET http://localhost:8002/api)
router.get('/', function(req, res) {
    res.json({ message: 'Welcome to our Common Services APIs !!! ' });
});

// more routes for our API will happen here
// on routes that end in /bears

app.get('/users/:name', function (req, res) {
   console.log('Reading user ' + req.params.name);

  // Read the file and send to the callback
  fs.readFile('./data/users.json', 'utf8', function (err, data) {

    if (err) throw err;

    var users = JSON.parse(data);
    console.log("Total no. of users: " + users.length);

    for (var i = 0; i < users.length; ++i) {
        if (users[i].name == req.params.name)
        {
            var user = JSON.stringify(users[i]);
            console.log("Found user: " + user);
            res.end(user);
        }
    }
  });

});

// REGISTER OUR ROUTES
// all of our routes will be prefixed with /api
app.use('/api', router);

// START THE SERVER
app.listen(port);
console.log('Magic happens on port ' + port);