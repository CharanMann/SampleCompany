angular
    .module('app.core')
    .controller("BenefitsController", function($http) {
        var benefits = this;
        benefits.title = 'Benefits Portal';

        benefits.enrollments = [];

        $http.get('data/benefits.json').success(function(data) {
            benefits.enrollments = data;
        });
    });
