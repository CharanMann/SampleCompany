angular
    .module('app.services', [])
    .factory('TxService', ['$http', 'appConstants', function($http, appConstants) {

        var txService = {
            history: function(empId, oauth2AT) {
                var req = {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    url: appConstants.apisURL + empId
                };
                return $http(req);
            },
            historyUsingAT: function(empId, oauth2AT) {
                var req = {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json',
                        'Authorization': 'Bearer ' + oauth2AT
                    },
                    url: appConstants.apisURLOpenIG + empId,
                    transformResponse: function(data, headers) {
                        data = JSON.parse(data);
                        return data;
                    }
                };
                return $http(req);
            }
        };

        return txService;
    }])
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
            },
            oauth2PasswordCredFlow: function(params) {
                var req = {
                    'method': 'POST',
                    headers: {
                        'Authorization': 'BASIC ZW1wbG95ZWVBcHA6cGFzc3dvcmQ=',
                        'Content-type': 'application/x-www-form-urlencoded; charset=utf-8'
                    },
                    url: appConstants.openAMURL + "/oauth2/employees/access_token",
                    data: $httpParamSerializer(params)
                };
                return $http(req);
            }

        };

        return openAMService;
    }]);
