angular
    .module('app.services', [])
    .factory('OpenAMService', ['$http', '$httpParamSerializer', 'appConstants', function($http, $httpParamSerializer, appConstants) {

        var openAMService = {
            authenticate: function(uid, password) {
                var req = {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'X-OpenAM-Username': uid,
                        'X-OpenAM-Password': password
                    },
                    data: {},
                    url: appConstants.openAMURL + '/json/employees/authenticate'
                };
                return $http(req);
            },
            validateToken: function(tokenId) {
                var req = {
                    'method': 'POST',
                    headers: {
                        'Accept': 'application/json'
                    },
                    url: appConstants.openAMURL + "/json/employees/sessions/" + encodeURIComponent(tokenId),
                    data: {},
                    params: {
                        _action: 'validate'
                    }
                };
                return $http(req);
            },
            getUserProfile: function(uid, tokenId) {
                var req = {
                    'method': 'GET',
                    headers: {
                        'iPlanetDirectoryPro': tokenId
                    },
                    url: appConstants.openAMURL + "/json/employees/users/" + encodeURIComponent(uid)
                };
                return $http(req);
            }
        };

        return openAMService;
    }]);
