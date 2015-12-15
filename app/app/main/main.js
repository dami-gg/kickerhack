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
        url: '/game/:gameId',
        views: {
          'pageContent': {
            templateUrl: 'main/templates/game.html',
            controller: 'GameController as gameCtrl'
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
