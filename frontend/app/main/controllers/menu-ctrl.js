'use strict';
angular.module('main')
  .controller('MenuCtrl', function ($log, $scope, NFCService, Config, $cordovaOauth) {
    $scope.hasNFC = NFCService.hasNFC();

    var self = this;
    
    this.parseQuery = function (qstr) {
      var query = {};
      var a = qstr.split('&');
      for (var i = 0; i < a.length; i++) {
        var b = a[i].split('=');
        query[decodeURIComponent(b[0])] = decodeURIComponent(b[1] || '');
      }
      return query;
    }

    var options = { redirect_uri: Config.AUTH.CALLBACK_URL };
    $cordovaOauth.github(Config.AUTH.CLIENT_ID, Config.AUTH.CLIENT_SECRET, ["email"], options)
      .then(function(result) {
        var data = self.parseQuery(result);
        var accessToken = data.access_token;
        $log.log(accessToken);
      }, function(error) {
        $log.log("Unable to auth with GitHub: " + error);
      });

  });
