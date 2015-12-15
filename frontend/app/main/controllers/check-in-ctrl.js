'use strict';
angular.module('main')
  .controller('CheckInController', ['$http', '$timeout', '$stateParams', '$log', 
    function ($http, $timeout, $stateParams, $log) {
    var self = this;
    var tagDataUrl = 'https://kicker-server.hackweek.zalan.do/nfc-data/' + this.tagId;

    this.tagId = $stateParams.tagId;

    this.fallbackTagData = function (error) {
      $log.log('fallback');
      var json = '{"uuid": "9f5b5bf9-7e74-44cb-b9d3-f915224a6a44", "table_id": 2, "side": "home", "position": "offense"}';
      $timeout(function () {
        self.tagData = JSON.parse(json);
      }, 2000);
    };

    this.checkIn = function () {
      $log.log('go..');
      
      var registerUrl = 'https://kicker-server.hackweek.zalan.do/tables/' 
        + self.tagData.table_id 
        + '/current_game';
      var registerData = self.tagData;
      $http.put(registerUrl, registerData).then(function (response) {
        $log.log(response.status);
      }, function (error) {
        $log.log(error);
      });

    };
    
    $http.get(tagDataUrl).then(function (response) {
      self.tagData = response.data;
    }, this.fallbackTagData);

  }]);
