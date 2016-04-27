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

    // Get a specific user; http://localhost:8002/users/emp1
    server.get('/users/:name', function (req, res) {
        console.log('Reading user ' + req.params.name);

        for (var i = 0; i < users.length; ++i) {
            if (users[i].name == req.params.name)
            {
                var user = JSON.stringify(users[i], null, '\t');
                console.log("Found user: " + user);
                res.end(user);
            }
        }
    });

    // Get all; http://localhost:8002/users/all
    server.get('/users', function (req, res) {
        console.log('Reading all users');

        res.end(JSON.stringify(users, null, '\t'));
    });
}

module.exports = appRouter;