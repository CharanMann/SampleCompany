var fs = require("fs");
var users;

// Read the file and send to the callback
fs.readFile('./data/users.json', 'utf8', function (err, data) {

    if (err) throw err;

    users = JSON.parse(data);
    console.log("Total no. of users: " + users.length);
});

var appRouter = function(server) {

    // test route to make sure everything is working (accessed at GET http://localhost:8002/users/info)
    server.get('/users/info', function(req, res) {
        res.json({ message: 'Welcome to our Common Services APIs !!! ' });
    });

    // Get a specific id; http://localhost:8002/users/emp1
    server.get('/users/:id', function (req, res) {
        console.log('Reading user ' + req.params.id);

        for (var i = 0; i < users.length; ++i) {
            if (users[i].id == req.params.id)
            {
                var user = JSON.stringify(users[i]);
                console.log(new Date().toString() + " Found user: " + user);

                // Set headers
                res.setHeader('Access-Control-Allow-Origin', "*");
                res.setHeader('Access-Control-Allow-Credentials', true);
                res.setHeader('Content-Type', 'application/json; charset=utf-8');
                res.setHeader('Server', 'sample.com');

                res.end(user);
            }
        }
        res.end(null);
    });

    // Get all; http://localhost:8002/users/all
    server.get('/users', function (req, res) {
        console.log(new Date().toString() + ' Reading all users');

        // Set headers
        res.setHeader('Access-Control-Allow-Origin', "*");
        res.setHeader('Access-Control-Allow-Credentials', true);
        res.setHeader('Content-Type', 'application/json; charset=utf-8');
        res.setHeader('Server', 'sample.com');

        res.end(JSON.stringify(users));
    });
}

module.exports = appRouter;