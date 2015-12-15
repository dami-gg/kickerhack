'use strict';
angular.module('main')
  .controller('CheckInController', ['$stateParams', function ($stateParams) {
    this.tagId = $stateParams.tagId;
    
    
  }]);
