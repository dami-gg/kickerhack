'use strict';

angular.module('main')
  .service('LoginService', ['$http', '$log', '$cordovaOauth', '$q', 'Config', 'localStorageService', 
    function ($http, $log, $cordovaOauth, $q, Config, localStorageService) {
    $log = $log.getInstance('LoginService');

    this.isLoggedIn = isLoggedIn;
    this.getRedirectState = getRedirectState;
    this.setRedirectState = setRedirectState;

    function isLoggedIn () {
      return localStorageService.get('is-logged-in');
    }

    function getRedirectState () {
      var name = localStorageService.get('to-state-name') || 'main.home';
      var params = JSON.parse(localStorageService.get('to-state-params') || '{}');

      return { 'name': name, 'params': params };
    }

    function setRedirectState (name, params) {
      localStorageService.set('to-state-name', name);
      localStorageService.set('to-state-params', JSON.stringify(params));
    }

  }]);
