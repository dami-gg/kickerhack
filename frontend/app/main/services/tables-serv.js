'use strict';

angular.module('main')
  .service('TablesService', ['$http', function ($http) {

    var vm = this;

    var allTablesJson = '[{"id":123,"building":"BNB","floor":1,"home":"red","away":"blue","last_goal_scored":"2015-12-14T15:20+01:00"},{"id":124,"building":"BMO","floor":1,"home":"red","away":"blue","last_goal_scored":"2015-12-14T15:20+01:00"},{"id":1,"building":"BMO","floor":2,"home":"red","away":"blue","last_goal_scored":"2015-12-14T15:20+01:00"}]';
    var singleGameJson = '{"game_id":1,"table_id":1,"goals_home":0,"goals_away":0,"started":"2015-12-14T15:20+01:00","last_goal_scored":"2015-12-14T15:20+03:00","players":[{"user_id":1,"position":"attack","side":"home"},{"user_id":2,"position":"defense","side":"home"},{"user_id":3,"position":"attack","side":"away"},{"user_id":4,"position":"defense","side":"away"}]}';

    /**
     * Get all existing tables
     * @returns {*}
     */
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

    /**
     * Get specific table data based on its id
     * @param tableId
     * @returns {*}
     */
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

    /**
     * Get all the locations where there are tables
     * @returns {*}
     */
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

    /**
     * Get all tables present in a certain location
     * @param location
     * @returns {*}
     */
    vm.getTablesByLocation = function (location) {
      var tables = [];
      var locationTablesByFloor = [];
      var aux;

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
          function (error) {
            // TODO Handle error
            return null;
          }
        );
    };

    /**
     * Get current game being played in a certain table
     * @param tableId
     * @returns {*} promise with result:
     *  - current game object
     *  - null if there's no game at the moment or an error happens
     */
    vm.getCurrentGameInTable = function (tableId) {
      return $http.get('/tables/' + tableId + '/current_game')
        .then(
          function (response) {
            if (response.status === 200) {
              return response.data;
            }
            else if (response.status === 404) {
              return null;
            }
            else {
              // TODO Handle unexpected response type
              return null;
            }
          },
          function (error) {
            // TODO Handle error
            // return null;
            // TODO Change when API is ready
            if (tableId === 1) {
              var parsedJson = JSON.parse(singleGameJson);
              return parsedJson;
            }
            return null;
          });
    };

    /**
     * Registers a user as a player in a table
     * @param tableId
     * @param side
     * @param position
     * @returns {*} promise with result:
     *  - true if the registration was successful
     *  - false in case of error
     */
    vm.registerPlayerInTable = function (tableId, side, position) {
      return $http.put('/tables/' + tableId + '/current_game',
        {'side': side, 'position': position})
        .then(
          function (response) {
            if (response.status === 200 || response.status === 201) {
              return true;
            }
            else {
              // TODO Handle error
              return false;
            }
          },
          function (error) {
            // TODO Handle error
            return false;
          });
    };

    /**
     * Registers a goal from one side in a certain table
     * @param tableId
     * @param side
     * @returns {*} promise with result:
     *  - true if the registration was successful
     *  - false in case of error
     */
    vm.registerGoalInTable = function (tableId, side) {
      return $http.post('/tables/' + tableId + '/sides/' + side + '/goal')
        .then(
          function (response) {
            if (response.status === 201) {
              return true;
            }
            else {
              // TODO Handle error
              return false;
            }
          },
          function (error) {
            // TODO Handle error
            return false;
          });
    };

    /**
     * Helper method to sort items
     * @param a
     * @param b
     * @returns {number}
     */
    function compare(a, b) {
      if (a < b) {
        return -1;
      }
      if (a > b) {
        return 1;
      }
      return 0;
    }
  }]);
