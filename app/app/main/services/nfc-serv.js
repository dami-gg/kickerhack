'use strict';
angular.module('main')
.service('NFCService', function ($state, $log) {
  /*global nfc:true*/

  this.hasNFC = function () {
    return (typeof nfc !== 'undefined');
  };

  this.registerListener = function () {
    $log.log('Initializing NFC...');

    nfc.addNdefListener(this.onNFCTag, function () {
      $log.log('Listening for NDEF tags...');
    }, function (reason) {
      $log.log('Error adding NFC Listener ' + reason);
    });
  };

  this.onNFCTag = function (event) {
    // TODO: notify whoever is interested...
    //$log.log(JSON.stringify(event, null, 4));
    $state.go('main.check-in', { table: 1, position: 3 });
  };

});
