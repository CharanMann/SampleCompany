angular
    .module('app.routes', ['ngRoute'])
    .config(config);

function config ($routeProvider) {
    $routeProvider.
        when('/', {
            templateUrl: 'sections/home/home.html',
            controller: 'StoreController as store'
        })
        .when('/profile', {
            templateUrl: 'sections/profile/profile.html',
            controller: 'ProfileController as profile'
        })
        .otherwise({
            redirectTo: '/'
        });
}