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
        /*.when('/oauth2', {
            templateUrl: 'sections/oauth2/responseConsumer.html',
            controller: 'OAuth2RC as rc'
        })*/
        .otherwise({
            redirectTo: '/'
        });
}