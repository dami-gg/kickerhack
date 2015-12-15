'use strict';
angular.module('main')
.service('NFCService', function ($log, $timeout) {
  /*global nfc:true*/

  this.hasNFC = function () {
    return (typeof nfc !== 'undefined');
  };

  this.initialize = function () {
    $log.log('Initializing NFC...');

    nfc.addNdefListener(this.onNFCTag, function () {
      $log.log('Listening for NDEF tags...');
    }, function (reason) {
      $log.log('Error adding NFC Listener ' + reason);
    });
  };

  this.onNFCTag = function (tag) {
    // TODO: notify whoever is interested...
    $log.log(JSON.stringify(nfcEvent.tag, null, 4));
  };

});
