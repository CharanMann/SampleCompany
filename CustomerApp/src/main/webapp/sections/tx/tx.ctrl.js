angular
    .module('app.core')
    .controller("TxController", ['TxService',function(TxService) {
         var tx = this;

         tx.details = TxService.get({id: 'emp1'});
}]);