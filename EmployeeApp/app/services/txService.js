angular
    .module('app.services', [])
    .factory("TxService", ['$resource', 'appConstants', function($resource, appConstants) {

	    return $resource( appConstants.serverURI + ':id', {id:'@id'}, {
            query: {method: 'GET', isArray: true},
            get: {method: 'GET'}
        });
    }]);