angular
    .module('app.routes', ['ngRoute'])
    .config(config);

function config($routeProvider) {
    $routeProvider.
        when('/', {
            templateUrl: 'sections/home/home.html',
            controller: 'BenefitsController as benefits'
        })
        .otherwise({
            redirectTo: '/'
        });
}
