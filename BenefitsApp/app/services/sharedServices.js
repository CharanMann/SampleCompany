angular
    .module('app.services', [])
    .factory('AuthService', ['$http', '$cookies', '$rootScope', 'appConstants', function($http, $cookies, $rootScope, appConstants) {

        var users = [];

        $http.get('data/creds.json').success(function(data) {
            users = data;
        });

        var authService = {
            authenticate: function(uid, password) {
                var response;
                var user = getUserById(users, uid);
                if (!angular.isUndefined(user) && user.password === password) {
                    response = {
                        success: true
                    };
                } else {
                    response = {
                        success: false,
                        message: 'Username or password is incorrect !!!'
                    };
                }
                return response;
            },

            setSSOToken: function(uid) {
                $rootScope.ssoToken = {
                    "username": uid
                };

                $cookies.put(appConstants.authCookie, JSON.stringify($rootScope.ssoToken));
            }
        };

        function getUserById(arr, uid) {
            for (var d = 0, len = arr.length; d < len; d += 1) {
                if (arr[d].username === uid) {
                    return arr[d];
                }
            }
        }

        return authService;
    }]);
