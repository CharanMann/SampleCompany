angular
    .module('app.services', [])
    .factory("TxService", ['$resource', function($resource) {

	    return $resource('http://localhost:8002/history/:id', {id:'@id'}, {
            query: {method: 'GET', isArray: true},
            get: {method: 'GET'}
        });
    }]);