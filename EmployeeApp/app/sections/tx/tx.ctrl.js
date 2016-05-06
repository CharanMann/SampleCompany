angular
    .module('app.core')
    .controller("TxController", function($scope,$http)
    {
            var tx = this;
            tx.users =null

//            $http.get('http://openam13.sc.com:8080/openam/json/serverinfo/*').success(function(data){
//                                                                               tx.users=data;
//                                                                           });

            $http({
                method : 'POST',
                url : 'http://openam13.sc.com:8080/openam/json/employees/authenticate',
                headers : {
                    'Content-Type': 'application/json',
                    'X-OpenAM-Username': 'emp1',
                    'X-OpenAM-Password': 'password'
                },
                data : {}
            }).then(function mySuccess(response) {
                       $scope.result = response;
                   }, function myError(response) {
                       $scope.result = response;
                   });




    });
