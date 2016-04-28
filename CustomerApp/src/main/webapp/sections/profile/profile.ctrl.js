angular
    .module('app.core')
    .controller("ProfileController", ['$http',function($http){
        var profile = this;

        profile.user = null;

        $http.get('http://localhost:8002/users/emp1').success(function(data){
            profile.user = data;
        });
}]);