'use strict';

angular.module('main')
  .service('TablesService', ['$http', function ($http) {

    var vm = this;

    var allTablesJson = '[{"id":123,"building":"BNB","floor":1,"home":"red","away":"blue","last_goald_scored":"2015-12-14T15:20+01:00"},{"id":124,"building":"BMO","floor":1,"home":"red","away":"blue","last_goald_scored":"2015-12-14T15:20+01:00"},{"id":456,"building":"BMO","floor":2,"home":"red","away":"blue","last_goald_scored":"2015-12-14T15:20+01:00"}]';

    vm.getTables = function () {
      return $http.get('/tables')
        .then(
          function (response) {
            return response.data;
          },
          function (error) {
            // TODO Handle error
            return null;
          });
    };

    vm.getTableById = function (tableId) {
      return $http.get('/tables/' + tableId)
        .then(
          function (response) {
            return response.data;
          },
          function (error) {
            // TODO Handle error
            return null;
          });
    };

    vm.getLocations = function () {
      var locations = [];
      var tables = [];
      return vm.getTables()
        .then(
          function (response) {
            if (response !== null) {
              tables = response;
            }
            else {
              // TODO Change when API is ready
              // vm.locations = {};
              var parsedJson = JSON.parse(allTablesJson);
              tables = parsedJson;
            }
            tables.forEach(function (table) {
              if (locations.indexOf(table.building) === -1) {
                locations.push(table.building);
              }
            });
            return locations.sort(compare);
          },
          function (error) {
            // TODO Handle error
            return null;
          }
        );
    };

    vm.getTablesByLocation = function (location) {
      var tables = [];
      var locationTablesByFloor = [];
      var aux;

      return vm.getTables()
        .then(
          function(response) {
            if (response !== null) {
              tables = response;
            }
            else {
              // TODO Change when API is ready
              // vm.locations = {};
              var parsedJson = JSON.parse(allTablesJson);
              tables = parsedJson;
            }

            tables.forEach(function (table) {
              if (table.building === location) {
                aux = locationTablesByFloor.find(function (tablesInFloor) {
                  return tablesInFloor.floor === table.floor;
                });
                if (aux) {
                  aux.tables.push(table);
                }
                else {
                  aux = {floor: table.floor, tables: [table]};
                  locationTablesByFloor.push(aux);
                }
              }
            });

            return locationTablesByFloor;
          },
          function(error) {
            // TODO Handle error
            return null;
          }
        );
    };

    vm.isTableFree = function(tableId) {
      return $http.get('/tables/' + tableId + '/current_game')
        .then(
          function (response) {
            if (response.status === 200) {
              return false;
            }
            else if (response.status === 404) {
              return true;
            }
            else {
              // TODO Handle unexpected response type
              return false;
            }
          },
          function (error) {
            // TODO Handle error
            return false;
          });
    };

    function compare (a, b) {
      if (a < b) {
        return -1;
      }
      if (a > b) {
        return 1;
      }
      return 0;
    };
  }]);
