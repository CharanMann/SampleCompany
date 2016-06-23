angular
    .module('app.routes', ['ngRoute', 'ngCookies'])
    .config(config)
    .run(run);

function config($routeProvider) {
    $routeProvider.
    when('/', {
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
    if (!angular.isUndefined($cookies.get('ssoToken'))) {
        $rootScope.ssoToken = JSON.parse($cookies.get('ssoToken'));
    } else {
        $rootScope.ssoToken = {};
    }

    $rootScope.$on('$locationChangeStart', function(event, next, current) {
        // redirect to login page if not logged in and trying to access a restricted page
        var restrictedPage = $.inArray($location.path(), ['/login']) === -1;
        var loggedIn = $rootScope.ssoToken.username;
        if (restrictedPage && !loggedIn) {
            $location.path('/login');
        }
    });
}
