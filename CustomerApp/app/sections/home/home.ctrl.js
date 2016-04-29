angular
    .module('app.core')
    .controller("StoreController", ['$http',function($http){
        var store = this;
        store.title = 'TV-Series Review Portal';
        store.searchInput = '';

        store.products = [];

        $http.get('data/store-products.json').success(function(data){
            store.products=data;
        });
}]);