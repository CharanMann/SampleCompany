angular
    .module('app.core')
    .controller("TxController", function($scope, $http, $cookies, appConstants, TxService, OpenAMService) {

        // Get OpenAM cookie from browser
        $scope.openAMCookie = $cookies.get(appConstants.openAMCookie);

        //TODO Using password grant for now, should be changed to OAuth2 implicit
        var params = {
            "grant_type": "password",
            "scope": "uid mail",
            "username": 'emp1',
            "password": 'password'
        };

        OpenAMService.oauth2PasswordCredFlow(params).success(function(data) {
            $scope.oauth2 = data;

            // Perform OpenAM token validation and get uid
            OpenAMService.validateToken($scope.openAMCookie).success(function(data) {
                $scope.validate = data;

                // In case no cookie is not found or validation fails.
                if (!$scope.validate.valid) {
                    $scope.error = "No valid OpenAM cookie found !";
                    return;
                }

                // Get user's tx history
                TxService.historyUsingAT($scope.validate.uid, $scope.oauth2.access_token).success(function(data) {
                    $scope.users = data;
                }, function(data) {
                    $scope.error = "Error in invoking Tx Service !";
                });

            }, function() {
                $scope.error = "OpenAM server(s) are not reachable !";
            });
        });


    });