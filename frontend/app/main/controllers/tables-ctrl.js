'use strict';

angular.module('main')
  .controller('TablesController', ['$stateParams', 'TablesService',
    function ($stateParams, TablesService) {

      this.location = $stateParams.location;
      this.tablesByFloor = TablesService.getTablesByLocation(this.location);
    }]);
