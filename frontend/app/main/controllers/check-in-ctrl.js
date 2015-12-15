'use strict';
angular.module('main')
  .controller('CheckInController', ['$stateParams', function ($stateParams) {
    this.table = $stateParams.table;
    this.position = $stateParams.position;
  }]);
