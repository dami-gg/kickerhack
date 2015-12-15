'use strict';

angular.module('main')
  .controller('TablesController', ['TablesService', '$stateParams',
    function (TablesService, $stateParams) {

      this.location = $stateParams.location;
      this.tablesByFloor = TablesService.getTablesByLocation(this.location);
  }]);
