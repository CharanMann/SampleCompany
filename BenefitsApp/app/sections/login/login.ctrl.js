angular
    .module('app.core')
    .controller("LoginController", function($cookies, $location, $rootScope) {

        var vm = this;
        vm.title = 'Login to Benefits Portal';
        vm.login = login;

        function login() {
            $rootScope.ssoToken = {
                "username": vm.username
            };

            $cookies.put('ssoToken', JSON.stringify($rootScope.ssoToken));
            $location.path('#/');
        }

    });
