'use strict';
angular.module('main')
.controller('CheckInController', function ($log, $ionicPlatform) {

  var that = this;
  
  $ionicPlatform.ready(function () {
    /*global nfc:true*/

    that.hasNFC = !(typeof nfc === 'undefined');
    if (that.hasNFC) {
      $log.log('Etablishing NFC listener...');
      nfc.addNdefListener(function (nfcEvent) {
        console.log(JSON.stringify(nfcEvent.tag, null, 4));
      }, function () {
        console.log('Listening for NDEF Tags.');
      }, function (reason) {
        alert('Error adding NFC Listener ' + reason);
      });
    }
  });

});
