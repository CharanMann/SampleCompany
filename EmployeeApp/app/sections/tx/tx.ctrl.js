angular
    .module('app.core')
    .controller("TxController", function($scope, $http, TxService, OpenAMService) {
        var tx = this;

        $scope.users = [];


        $scope.authResult = null;
        $scope.validate = null;
        $scope.uid = null;

        OpenAMService.authenticate('emp1', 'password').success(function(data) {
            $scope.authResult = data;

            OpenAMService.validateToken($scope.authResult.tokenId).success(function(data) {
                $scope.validate = data;

                TxService.history($scope.validate.uid).success(function(data) {
                    $scope.users = data;
                });
            });
        });






    });