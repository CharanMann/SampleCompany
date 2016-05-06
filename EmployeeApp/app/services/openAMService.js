angular
    .module('app.services', [])
    .factory('OpenAMService', ['$http', 'appConstants', function($http, appConstants) {

        var openAMService = {
            authenticate: function(username,password){
                var req = {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'X-OpenAM-Username': username,
                        'X-OpenAM-Password': password
                    },
                    url: 'http://openam13.sc.com:8080/openam/json/employees/authenticate'
                };
                return $http(req);
            }
        };

        return openAMService;
    }]);