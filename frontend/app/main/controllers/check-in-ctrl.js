'use strict';
angular.module('main')
  .controller('CheckInController', ['Config', '$http', '$timeout', '$stateParams', '$state', '$log', 'TablesService',
    function (Config, $http, $timeout, $stateParams, $state, $log, TablesService) {
      $log = $log.getInstance('CheckInController');
      $log.log('Starting');

      var vm = this;
      var baseUrl = Config.ENV.SERVER_URL;
      var tagDataUrl = baseUrl
        + '/nfc-data/'
        + this.tagId;

      this.tagId = $stateParams.tagId;

      this.fallbackTagData = function (error) {
        $log.log('fallback');
        var json = '{"uuid": "9f5b5bf9-7e74-44cb-b9d3-f915224a6a44", "table_id": 2, "side": "home", "position": "offense"}';
        $timeout(function () {
          vm.tagData = JSON.parse(json);
        }, 2000);
      };

      this.checkIn = function () {
        TablesService.registerPlayerInTable(vm.tagData.table_id, vm.tagData.side, vm.tagData.position)
          .then(function (response) {
            if (response === true) {
              $state.go('main.game', {'tableId': vm.tagData.table_id});
            }
            else {
              // TODO Handle error
            }
          });
      };

      $http.get(tagDataUrl).then(function (response) {
        vm.tagData = response.data;
      }, this.fallbackTagData);

    }]);
