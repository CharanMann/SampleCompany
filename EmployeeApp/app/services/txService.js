angular
    .module('app.services', [])
    .factory('TxService', ['$resource', 'appConstants', function($resource, appConstants) {

	    return $resource( appConstants.apisURL + ':id', {id:'@id'}, {
            get: {method: 'GET'}
        });
    }]);