'use strict';

angular.module('main')
  .controller('TablesController', ['$stateParams', '$state', 'TablesService',
    function ($stateParams, $state, TablesService) {

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

      vm.updateTablesStatus = function (tablesByFloor) {
        tablesByFloor.forEach(function (floorTables) {
          floorTables.tables.forEach(function (table) {
            TablesService.getCurrentGameInTable(table.id)
              .then(function (response) {
                table.available = response === null;
                if (response !== null) {
                  table.game_id = response.game_id;
                }
              });
          });
        });
      };

      vm.showTableGame = function (table) {
        $state.go('main.game', { 'tableId': table.id });
      };
    }]);
