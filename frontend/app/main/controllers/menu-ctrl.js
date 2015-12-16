'use strict';
angular.module('main')
  .controller('MenuCtrl', function ($log, $http, $scope, NFCService) {
    $scope.hasNFC = NFCService.hasNFC();

    var self = this;

  });
