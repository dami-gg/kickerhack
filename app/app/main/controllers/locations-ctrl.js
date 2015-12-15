'use strict';

angular.module('main')
  .controller('LocationsController', ['TablesService', function (TablesService) {

    this.tables = TablesService.getTables();

    this.locations = TablesService.getLocations(this.tables);
  }]);
