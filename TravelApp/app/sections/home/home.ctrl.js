angular
    .module('app.core')
    .controller("TravelController", ['$http', function($http) {
        var travel = this;
        travel.title = 'Travel Portal';

        travel.trips = [];

        $http.get('data/trips.json').success(function(data) {
            travel.trips = data;
        });
    }]);
