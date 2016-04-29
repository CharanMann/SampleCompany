angular
    .module('app.services', [])
    .factory("TxService", ['$resource', function($resource) {

	    return $resource('http://localhost:8002/history', {}, {
            'query': {method: 'GET', isArray: true}
        });
    }]);