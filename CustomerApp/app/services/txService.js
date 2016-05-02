angular
    .module('app.services', [])
    .constant('appConstants', {serverURI: 'http://localhost:8002/history/'})
    .factory("TxService", ['$resource', function($resource, appConstants) {

	    return $resource(appConstants.serverURI + ':id', {id:'@id'}, {
            query: {method: 'GET', isArray: true},
            get: {method: 'GET'}
        });
    }]);