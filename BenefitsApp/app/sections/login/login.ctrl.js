angular
    .module('app.core')
    .controller("LoginController", function($cookies, $location, $rootScope, $scope, appConstants, AuthService) {

        var vm = this;
        vm.title = 'Login to Benefits Portal';
        vm.login = login;

        function login() {

            var response = AuthService.authenticate(vm.username, vm.password);

            if (response.success) {
                AuthService.setSSOToken(vm.username);
                $location.path('#/');
            } else {
                $scope.error = response.message;
                $location.path('/login');
            }
        }

    });
