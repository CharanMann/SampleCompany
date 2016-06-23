angular
    .module('app.core')
    .controller("LoginController", function($cookies, $location, $rootScope) {

        var vm = this;
        vm.title = 'Login to Benefits Portal';
        vm.login = login;

        function login() {


            $rootScope.globals = {
                currentUser: {
                    username: username,
                    password: password
                }
            };

            //$http.defaults.headers.common['Authorization'] = 'Basic ' + username; // jshint ignore:line
            $cookies.put('globals', $rootScope.globals);
            $location.path('/');
            $cookies.put('myFavorite', 'oatmeal');
        }

        // (function initController() {
        //     // reset login status
        //     // AuthenticationService.ClearCredentials();
        // })();
        //
        // function login() {
        //     // AuthenticationService.Login(vm.username, vm.password, function(response) {
        //     //     if (response.success) {
        //     //         AuthenticationService.SetCredentials(vm.username, vm.password);
        //     //         $location.path('/');
        //     //     }
        //     // });
        // }

    });
