'use strict';
angular.module('main')
  .controller('LoginController', ['AuthService', 'LoginService', 'Config', '$http', '$timeout', '$state', '$log', 
    function (AuthService, LoginService, Config, $http, $timeout, $state, $log) {
    $log = $log.getInstance('LoginController');
    $log.log('Starting');

    function initialize () {
      AuthService.authenticate().then(function () {

        var toState = LoginService.getRedirectState();
        $state.go(toState.name, toState.params);

      }, function() {

        $state.go('main.home');
        
      });
    };

    initialize();

  }]);
