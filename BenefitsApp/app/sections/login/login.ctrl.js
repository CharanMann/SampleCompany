angular
    .module('app.core')
    .controller("LoginController", function($cookies, $location, $rootScope, appConstants, AuthService) {

        var vm = this;
        vm.title = 'Login to Benefits Portal';
        vm.login = login;

        function login() {

            if (AuthService.authenticate(vm.username, vm.password)) {
                $rootScope.ssoToken = {
                    "username": vm.username
                };

                $cookies.put(appConstants.authCookie, JSON.stringify($rootScope.ssoToken));
                $location.path('#/');
            }
        }

    });
