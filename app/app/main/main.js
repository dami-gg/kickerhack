'use strict';
angular.module('main', [
    'ionic',
    'ngCordova',
    'ui.router',
    // TODO: load other modules selected during generation
  ])
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
      .state('main.tables', {
        url: '/tables',
        views: {
          'pageContent': {
            templateUrl: 'main/templates/tables.html',
            controller: 'TablesController as tablesCtrl'
          }
        }
      })
      .state('main.game', {
        url: '/game/:gameID',
        views: {
          'pageContent': {
            templateUrl: 'main/templates/game.html',
            controller: 'GameController as gameCtrl'
          }
        }
      })
      .state('main.check-in', {
        url: '/check-in',
        views: {
          'pageContent': {
            templateUrl: 'main/templates/check-in.html',
            controller: 'CheckInController as checkInCtrl'
          }
        }
      })
      .state('main.debug', {
        url: '/debug',
        views: {
          'pageContent': {
            templateUrl: 'main/templates/debug.html',
            controller: 'DebugCtrl as ctrl'
          }
        }
      });
  });
