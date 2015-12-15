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
    $state.go('main.check-in', self.decodePayload(message.payload));
  };

  this.decodePayload = function (payload) {
    var value = nfc.bytesToString(payload);
    $log.log('Parsing NDEF payload: ' + value);

    value = "{\"table\": 4, \"position\": 0}";

    return JSON.parse(value);
  };

});