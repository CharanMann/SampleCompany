angular
    .module('app.core')
    .controller("LoginController", function($cookies, $location, $rootScope) {

        var vm = this;
        vm.title = 'Login to Benefits Portal';
        vm.login = login;

        function login() {
            $rootScope.userId = vm.username;

            $cookies.put('userId', $rootScope.userId);
            $location.path('#/');
        }

    });
