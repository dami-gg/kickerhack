'use strict';
angular.module('main', ['log.ex.uo', 'ionic', 'ngCordova', 'ui.router', 'ngCordovaOauth', 'LocalStorageModule'])
  .config(['logExProvider', function (logExProvider) {
    logExProvider.enableLogging(true);
  }])
  .config(function (localStorageServiceProvider) {
    localStorageServiceProvider
      .setPrefix('kickerhack')
      .setStorageType('sessionStorage')
      .setNotify(true, true);
  })
  .config(function ($stateProvider, $urlRouterProvider) {

    // ROUTING with ui.router
    $urlRouterProvider.otherwise('/main/home');
    $stateProvider
    // this state is placed in the <ion-nav-view> in the index.html
      .state('main', {
        url: '/main',
        abstract: true,
        templateUrl: 'main/templates/menu.html',
        controller: 'MenuCtrl as menu'
      })
      .state('main.home', {
        url: '/home',
        views: {
          'pageContent': {
            templateUrl: 'main/templates/home.html',
            // controller: '<someCtrl> as ctrl'
          }
        }
      })
      .state('main.locations', {
        url: '/locations',
        views: {
          'pageContent': {
            templateUrl: 'main/templates/locations.html',
            controller: 'LocationsController as locationsCtrl'
          }
        }
      })
      .state('main.tables', {
        url: '/locations/:location',
        views: {
          'pageContent': {
            templateUrl: 'main/templates/tables.html',
            controller: 'TablesController as tablesCtrl'
          }
        }
      })
      .state('main.table', {
        url: '/tables/:tableId',
        views: {
          'pageContent': {
            templateUrl: 'main/templates/table.html',
            controller: 'TableController as tableCtrl'
          }
        }
      })
      .state('main.game', {
        url: '/tables/:tableId/game',
        views: {
          'pageContent': {
            templateUrl: 'main/templates/game.html',
            controller: 'GameController as gameCtrl'
          }
        }
      })
      .state('main.ranking', {
        url: '/ranking',
        views: {
          'pageContent': {
            templateUrl: 'main/templates/ranking.html',
            controller: 'RankingController as rankingCtrl'
          }
        }
      })
      .state('main.login', {
        url: '/login',
        views: {
          'pageContent': {
            templateUrl: 'main/templates/login.html',
            controller: 'LoginController as loginCtrl'
          }
        }
      })
      .state('main.check-in', {
        url: '/check-in/:tagId',
        views: {
          'pageContent': {
            templateUrl: 'main/templates/check-in.html',
            controller: 'CheckInController as checkInCtrl'
          }
        },
        authenticate: true
      });
  })
  .run(function ($rootScope, $location, $state, $log, AuthService, LoginService) {
    $rootScope.$on('$stateChangeStart', function (event, toState, toParams) {

      var isLoggedIn = AuthService.isLoggedIn();

      if (toState.authenticate && !isLoggedIn) {
        event.preventDefault();
        LoginService.setRedirectState(toState.name, toParams);
        $state.go('main.login');
      }

    });
  })
  .run(function ($ionicPlatform, $q, $log, NFCService) {
    $log = $log.getInstance('main');

    $ionicPlatform.ready(function () {
      $q.all([NFCService.initialize()]).finally(function () {
        $log.log('Initialization completed.');
      });
    });
  });
