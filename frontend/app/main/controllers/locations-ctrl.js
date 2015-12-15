'use strict';

angular.module('main')
  .controller('LocationsController', ['TablesService', function (TablesService) {

    this.locations = TablesService.getLocations();
  }]);
