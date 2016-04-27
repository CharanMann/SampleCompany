var appRouter = function(app) {
    app.get("/account", function(req, res) {
        var accountMock = {
            "username": "nraboy",
            "password": "1234",
            "twitter": "@nraboy",
            "email": "nraboy@gmail.com"
        }
        if(!req.query.username) {
            return res.send({"status": "error", "message": "missing username"});
        } else if(req.query.username != accountMock.username) {
            return res.send({"status": "error", "message": "wrong username"});
        } else {
            return res.send(accountMock);
        }
    });
}

module.exports = appRouter;
