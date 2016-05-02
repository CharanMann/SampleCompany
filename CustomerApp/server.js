process.env.NODE_ENV = process.env.NODE_ENV || 'development';

var config = require('./config/config');
var express = require("express");

var server = express();

server.use('/customerApp', express.static('./app'));
server.use('/customerApp/node_modules', express.static('./node_modules'));

server.listen(config.port);

console.log(process.env.NODE_ENV + ' server running at http://localhost:' + config.port);