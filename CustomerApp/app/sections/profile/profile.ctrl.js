angular
    .module('app.core')
    .controller("ProfileController", function($scope, $http, $cookies, appConstants, OpenAMService) {

         // Get OpenAM cookie from browser
        $scope.openAMCookie = $cookies.get(appConstants.openAMCookie);

        // Perform OpenAM token validation and get uid
        OpenAMService.validateToken($scope.openAMCookie).success(function(data) {
            $scope.validate = data;

            // In case no cookie is not found or validation fails.
            if (!$scope.validate.valid) {
                $scope.error = "No valid OpenAM cookie found !";
                return;
            }

            // Get user's profile
            OpenAMService.getUserProfile($scope.validate.uid, $scope.openAMCookie).success(function(data) {
                $scope.profile = data;
            });

        }, function() {
            $scope.error = "OpenAM server(s) are not reachable !";
        });
    });
