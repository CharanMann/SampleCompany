angular
    .module('app.routes', ['ngRoute'])
    .config(config);

function config ($routeProvider) {
    $routeProvider.
        when('/', {
            templateUrl: 'sections/home/home.html',
            controller: 'NewsController as news'
        })
        .when('/history', {
            templateUrl: 'sections/tx/tx.html',
            controller: 'TxController as tx'
        })
        .otherwise({
            redirectTo: '/'
        });
}