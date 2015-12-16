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
        });
    }]);
