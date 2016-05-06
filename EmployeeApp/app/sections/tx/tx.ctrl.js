angular
    .module('app.core')
    .controller("TxController", ['TxService', function(TxService) {

        TxService.history('emp1')
            .then(function mySuccess(response) {
                $scope.result = 'succes';
            }, function myError(response) {
                $scope.result = 'err';
            });

//        OpenAMService.authenticate('emp1','password')
//            .then(function mySuccess(response) {
//                        $scope.result1 = 'succes';
//                    }, function myError(response) {
//                        $scope.result1 = 'err';
//                    });
}]);