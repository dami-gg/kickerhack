'use strict';
angular.module('main')
.service('NFCService', function ($log, $timeout) {

  $log.log('Hello from the NFC service');

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
    $log.log(JSON.stringify(nfcEvent.tag, null, 4));
  };

});
