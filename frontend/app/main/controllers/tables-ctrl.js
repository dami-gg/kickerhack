'use strict';

angular.module('main')
  .controller('TablesController', ['$stateParams', 'TablesService',
    function ($stateParams, TablesService) {

      var vm = this;

      vm.location = $stateParams.location;

      TablesService.getTablesByLocation(vm.location)
        .then(function (response) {
          if (response !== null) {
            vm.tablesByFloor = response;
          }
          else {
            vm.tablesByFloor = [];
          }
          vm.updateTablesStatus(vm.tablesByFloor);
        });

      vm.updateTablesStatus = function (tables) {
        tables.forEach(function (table) {
          TablesService.getCurrentGameInTable(table.id)
            .then(function (response) {
              table.available = response !== null;
            });
        });
      };
    }]);
