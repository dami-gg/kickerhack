'use strict';
angular.module('main')
  .controller('LoginController', ['AuthService', 'LoginService', 'Config', '$ionicHistory', '$http', '$timeout', '$state', '$log',
    function (AuthService, LoginService, Config, $ionicHistory, $http, $timeout, $state, $log) {
      $log = $log.getInstance('LoginController');
      $log.log('Starting');

      function initialize () {
        AuthService.authenticate().then(function () {

          var toState = LoginService.getRedirectState();

          $ionicHistory.nextViewOptions({
            disableBack: true
          });

          $state.go(toState.name, toState.params);

        }, function () {

          $ionicHistory.nextViewOptions({
            disableBack: true
          });

          $state.go('main.home');

        });
      }

      initialize();

    }]);
