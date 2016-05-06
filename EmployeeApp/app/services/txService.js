angular
    .module('app.services', [])
    .factory('TxService', ['$http', 'appConstants', function($http, appConstants) {

        var txService = {
            history: function(empId){
                var req = {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    url: appConstants.apisURL + empId,
                    transformResponse: function (data, headers) {
                        data = JSON.parse(data);
                        return data;
                    }
                };
                return $http(req);
            }
        };

        return txService;
    }]);