ngular
    .module('app.services', [])
    .factory('OpenAMService', function($scope, $http, $q) {

     var openAMService = {
        info: function () {
            var req = {
                method: 'GET',
                url: 'http://openam13.sc.com:8080/openam/json/serverinfo/*',
                return $http(req); // returning with the promise
            }
     }
   });