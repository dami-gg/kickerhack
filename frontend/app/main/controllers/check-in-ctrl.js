'use strict';
angular.module('main')
  .controller('CheckInController', ['$http', '$timeout', '$stateParams', '$log', 
    function ($http, $timeout, $stateParams, $log) {
    var self = this;

    this.tagId = $stateParams.tagId;
    $http.get('http://localhost:3000/asdasd').then(function(response) {
      self.tagData = response.data;
    }, function(error) {
      var json = '{"uuid": "9f5b5bf9-7e74-44cb-b9d3-f915224a6a44", "table_id": 2, "side": "home", "position": "offense"}';
      $timeout(function () {
        self.tagData = JSON.parse(json);
      }, 2000);
    });

    this.checkIn = function() {
      $log.log('go..');
    };
  }]);
