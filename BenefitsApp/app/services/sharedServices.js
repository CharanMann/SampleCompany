angular
    .module('app.services', [])
    .factory('AuthService', ['$http', 'appConstants', function($http, appConstants) {

        var users = [];

        $http.get('data/creds.json').success(function(data) {
            users = data;
        });

        var authService = {
            authenticate: function(uid, password) {
                var user = getById(users, uid);
                if (null !== user && user.password === password) {
                    return true;
                }
                return false;
            }
        };

        function getById(arr, uid) {
            for (var d = 0, len = arr.length; d < len; d += 1) {
                if (arr[d].username === uid) {
                    return arr[d];
                }
            }
        }

        return authService;
    }]);
