'use strict';
angular.module('main')
.constant('Config', {

  // gulp environment: injects environment vars
  // https://github.com/mwaylabs/generator-m-ionic#gulp-environment
  ENV: {
    /*inject-env*/
    'SERVER_URL': 'https://kicker-server.hackweek.zalan.do'
    /*endinject*/
  },

  // gulp build-vars: injects build vars
  // https://github.com/mwaylabs/generator-m-ionic#gulp-build-vars
  BUILD: {
    /*inject-build*/
    /*endinject*/
  },

  AUTH: {
    'CLIENT_ID': '8ffed888ba9f8bc1f283',
    'CLIENT_SECRET': 'd880a5aa2bf3edafdfe28d3a1a2959fc5dff2ef4',
    'CALLBACK_URL': 'https://kicker-server.hackweek.zalan.do'
  },

  CONSTS: {
    'SIDE_AWAY': 'away',
    'SIDE_HOME': 'home',
    'POSITION_ATTACK': 'attack',
    'POSITION_DEFENSE': 'defense'
  }

});
