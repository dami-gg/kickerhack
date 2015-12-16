'use strict';

angular.module('main')
  .service('AuthService', ['$http', '$log', '$cordovaOauth', '$q', 'Config', 'localStorageService', 
    function ($http, $log, $cordovaOauth, $q, Config, localStorageService) {
    $log = $log.getInstance('AuthService');
    var vm = this;

    this.isLoggedIn = isLoggedIn;

    function isLoggedIn () {
      return localStorageService.get('is-logged-in');
    }

    var options = { redirect_uri: Config.AUTH.CALLBACK_URL };

    this.authenticate = function() {
      return $cordovaOauth.github(Config.AUTH.CLIENT_ID, Config.AUTH.CLIENT_SECRET, [], options)
        .then(
          function (response) {
            var accessToken = parseQuery(response).access_token;
            $http.defaults.headers.common['Authentication'] = 'Bearer ' + accessToken;
            localStorageService.set('is-logged-in', true);
            $log.log('Authentication successfully.');
          },
          function (error) {
            localStorageService.set('is-logged-in', false);
            $log.log('Authentication failed (' + error + ').');
          });
    };

    function parseQuery (qstr) {
      var query = {};
      var a = qstr.split('&');
      for (var i = 0; i < a.length; i++) {
        var b = a[i].split('=');
        query[decodeURIComponent(b[0])] = decodeURIComponent(b[1] || '');
      }
      return query;
    }
  }]);
