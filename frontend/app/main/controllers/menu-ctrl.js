'use strict';
angular.module('main')
  .controller('MenuCtrl', function ($scope, NFCService, $cordovaOauth) {
    $scope.hasNFC = NFCService.hasNFC();

    var self = this;

    var clientId = '8ffed888ba9f8bc1f283';
    var clientSecret = 'd880a5aa2bf3edafdfe28d3a1a2959fc5dff2ef4';
    var options = { redirect_uri: 'https://kicker-server.hackweek.zalan.do/' };
    
    this.parseQuery = function (qstr) {
      var query = {};
      var a = qstr.split('&');
      for (var i = 0; i < a.length; i++) {
        var b = a[i].split('=');
        query[decodeURIComponent(b[0])] = decodeURIComponent(b[1] || '');
      }
      return query;
    }

    $cordovaOauth.github(clientId, clientSecret, ["email"], options)
      .then(function(result) {
        var data = self.parseQuery(result);
        var accessToken = data.access_token;
      }, function(error) {
        console.log("Unable to auth with GitHub: " + error);
      });

  });
