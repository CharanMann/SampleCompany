angular
    .module('app.core')
    .controller("NewsController", ['$http',function($http){
        var news = this;
        news.title = 'Employee Intranet Portal';

        news.items = [];

        $http.get('data/news.json').success(function(data){
            news.items=data;
        });
}]);