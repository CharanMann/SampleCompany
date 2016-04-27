// call the packages we need
var express = require("express");
var bodyParser = require("body-parser");

var server = express();

// configure server to use bodyParser()
// this will let us get the data from a POST
server.use(bodyParser.json());
server.use(bodyParser.urlencoded({ extended: true }));

var routes = require("./routes/users.js")(server);

// START THE SERVER
var port = process.env.PORT || 8002;
server.listen(port);
console.log('Magic happens on port ' + port);