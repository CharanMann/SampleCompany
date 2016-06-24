process.env.NODE_ENV = process.env.NODE_ENV || 'development';

var config = require('./config/config');
var express = require("express");

var server = express();

server.use(config.applicationContext, express.static('./app'));
server.use(config.applicationContext + '/node_modules', express.static('./node_modules'));

// START THE SERVER
server.listen(config.port);
console.log('Customer App (Direct Access) - server running @ ' + config.url);
console.log('Customer App (Via OpenIG) - server running @ ' + config.OpenIG_url);
