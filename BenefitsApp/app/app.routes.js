angular
    .module('app.routes', ['ngRoute', 'ngCookies'])
    .config(config)
    .run(run);

function config($routeProvider) {
    $routeProvider.
    when('/benefits', {
            templateUrl: 'sections/home/home.html',
            controller: 'BenefitsController as benefits'
        })
        .when('/login', {
            templateUrl: 'sections/login/login.html',
            controller: 'LoginController as vm'
        })
        .otherwise({
            redirectTo: '/'
        });
}

function run($rootScope, $location, $cookies, $http) {
    // keep user logged in after page refresh
    $rootScope.globals = $cookies.get('globals') || {};
    if ($rootScope.globals.currentUser) {
        $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.username; // jshint ignore:line
    }

    $rootScope.$on('$locationChangeStart', function(event, next, current) {
        // redirect to login page if not logged in and trying to access a restricted page
        var restrictedPage = $.inArray($location.path(), ['/login']) === -1;
        var loggedIn = $rootScope.globals.currentUser;
        if (restrictedPage && !loggedIn) {
            $location.path('/login');
        }
    });
}
