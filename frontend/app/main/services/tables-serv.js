'use strict';
angular.module('main')
  .service('TablesService', function () {

    var json = '[{"id":123,"building":"BNB","floor":1,"home":"red","away":"blue","last_score":"2015-12-14T15:20+01:00","current_game":{"game_id":234,"table_id":123,"teams":[{"color":"red","offense_player_id":123,"defense_player_id":123},{"color":"blue","offense_player_id":null,"defense_player_id":125}]}},{"id":124,"building":"BMO","floor":1,"home":"red","away":"blue","last_score":"2015-12-14T15:20+01:00","current_game":null},{"id":456,"building":"BMO","floor":2,"home":"red","away":"blue","last_score":"2015-12-14T15:20+01:00","current_game":{"game_id":436,"table_id":456,"teams":[{"color":"red","offense_player_id":456,"defense_player_id":457},{"color":"blue","offense_player_id":null,"defense_player_id":125}]}}]';

    this.getTables = function() {
      return JSON.parse(json);
    };

    this.getLocations = function (tables) {
      var tables = this.getTables();
      var locations = [];

      tables.forEach(function (table) {
        if(locations.indexOf(table.building) === -1) {
          locations.push(table.building);
        }
      });

      return locations.sort(compare);
    };

    this.getTablesByLocation = function(location) {
      var tables = this.getTables();
      var locationTablesByFloor = [];
      var aux;

      tables.forEach(function (table) {
        if (table.building === location) {
          aux = locationTablesByFloor.find(function(tablesInFloor) {
            return tablesInFloor.floor === table.floor;
          })
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
    };

    function compare(a, b) {
      if (a < b) {
        return -1;
      }
      if (a > b) {
        return 1;
      }
      return 0;
    }
  })
;
