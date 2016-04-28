angular
    .module('app.core')
    .controller("ProfileController", ['TxService','$log',function(TxService, $log) {
         var profile = this;

         profile.dUsers = [{"TOm":"Jane"}];

         profile.tUsers = TxService.query();

         $log.info(profile.tUsers);

}]);