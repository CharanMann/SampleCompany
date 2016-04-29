angular
    .module('app.core')
    .controller("TxController", ['TxService',function(TxService) {
         var tx = this;

         tx.history = TxService.query();
}]);