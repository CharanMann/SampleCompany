var fs = require("fs");
var users;

// Read the file and send to the callback
fs.readFile('./data/txDetails.json', 'utf8', function(err, data) {

    if (err) throw err;

    users = JSON.parse(data);
});

var appRouter = function(server) {

    // test route to make sure everything is working (accessed at GET http://localhost:8010/history/info)
    server.get('/history/info', function(req, res) {
        res.json({
            message: 'Welcome to Tx History REST APIs !!! Endpoints: 1.Get user tx history: /history/{id} 2.Get all users tx history: /txHistory/all'
        });
    });

    // Get a specific id; http://localhost:8010/history/emp1
    server.get('/history/:id', function(req, res) {

        for (var i = 0; i < users.length; ++i) {
            if (users[i].id == req.params.id) {
                var user = JSON.stringify(users[i]);
                res.setHeader('Content-Type', 'application/json; charset=utf-8');

                res.end(user);
            }
        }

        if (req.params.id == 'all') {
            // If 'all' is specified, then return all users history; http://localhost:8010/history/all
            res.setHeader('Content-Type', 'application/json; charset=utf-8');

            res.end(JSON.stringify(users));
        }

        res.end(null);
    });

    // Get all Tx History; http://localhost:8010/txHistory/all
    server.get('/txHistory/all', function(req, res) {
        res.setHeader('Content-Type', 'application/json; charset=utf-8');

        res.end(JSON.stringify(users));
    });
};

module.exports = appRouter;
