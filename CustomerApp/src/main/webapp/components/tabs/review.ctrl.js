angular
    .module('app.core')
    .controller("ReviewController", function(){

        this.review = {};

        this.addReview = function(product){
            product.reviews.push(this.review);
        this.review = {};
        };
});