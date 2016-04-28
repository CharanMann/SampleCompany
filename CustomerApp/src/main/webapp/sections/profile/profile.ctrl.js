angular
    .module('app.core')
    .controller("ProfileController", ['TxService',function(TxService) {
         var profile = this;

         profile.tUsers = [];

         profile.dUsers = [{"TOm":"Jane"}];

         tUsers = TxService.query();
}]);