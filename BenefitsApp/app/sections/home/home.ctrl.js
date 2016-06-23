angular
    .module('app.core')
    .controller("BenefitsController", ['$http', function($http) {
        var benefits = this;
        benefits.title = 'Benefits Portal';

        benefits.enrollments = [];

        $http.get('data/benefits.json').success(function(data) {
            benefits.enrollments = data;
        });
    }]);
