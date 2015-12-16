'use strict';

angular.module('main')
  .controller('LocationsController', ['TablesService',
    function (TablesService) {

      var vm = this;

      TablesService.getLocations()
        .then(function (response) {
          if (response !== null) {
            vm.locations = response;
          }
          else {
            vm.locations = [];
          }
        });
    }]);
