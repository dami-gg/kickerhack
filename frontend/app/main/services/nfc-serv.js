'use strict';
angular.module('main')
.service('NFCService', function ($state, $log) {
  /*global nfc:true*/

  var self = this;

  this.hasNFC = function () {
    return (typeof nfc !== 'undefined');
  };

  this.registerListener = function () {
    $log.log('Initializing NFC...');

    nfc.addMimeTypeListener('application/zalando', this.onNFCTag, function () {
      $log.log('Listening for NDEF tags...');
    }, function (reason) {
      $log.log('Error adding NFC Listener ' + reason);
    });
  };

  this.onNFCTag = function (tagEvent) {
    var message = tagEvent.tag.ndefMessage[0];
    var stateParams = self.hasNFC() 
      ? self.decodePayload(message.payload) 
      : { tagId: 'unknown' };
    $state.go('main.check-in', stateParams);  
  };

  this.decodePayload = function (payload) {
    var value = nfc.bytesToString(payload);
    $log.log('Parsing NDEF payload: ' + value);
    return { tagId: value };
  };

});
