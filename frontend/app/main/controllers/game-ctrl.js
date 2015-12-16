'use strict';

angular.module('main')
  .controller('GameController', ['$stateParams', 'Config', 'GamesService', 'TablesService',
    function ($stateParams, Config, GamesService, TablesService) {

      var singleGameJson = '{"game_id":1,"table_id":1,"goals_home":0,"goals_away":0,"started":"2015-12-14T15:20+01:00","last_goal_scored":"2015-12-14T15:20+03:00","players":[{"user_id":1,"position":"attack","side":"home"},{"user_id":2,"position":"defense","side":"home"},{"user_id":3,"position":"attack","side":"away"},{"user_id":4,"position":"defense","side":"away"}]}';

      var vm = this;

      vm.tableId = $stateParams.tableId;
      vm.homeTeamPlayers = [];
      vm.awayTeamPlayers = [];

      TablesService.getCurrentGameInTable(this.tableId)
        .then(function (response) {
          if (response !== null) {
            vm.game = response;
            distributePlayers();
          }
          else {
            // TODO Change when API is ready
            // vm.game = {};
            var parsedJson = JSON.parse(singleGameJson);
            vm.game = parsedJson;
            distributePlayers(); // TODO Remove when API is working
          }
        });

      vm.scoreGoal = function (side) {
        TablesService.registerGoalInTable(vm.game.table_id, side)
          .then(
            function (response) {
              if (response === true) {
                updateScore(side);
              }
              else {
                // TODO Handle error
              }
            }
          );
      };

      function updateScore(side) {
        if (side === Config.CONSTS.SIDE_HOME) {
          vm.game.goals_home += 1;
        }
        else if (side === Config.CONSTS.SIDE_AWAY) {
          vm.game.goals_away += 1;
        }
        else {
          // TODO Handle error
        }
      };

      function distributePlayers() {
        vm.game.players.forEach(function(player){
          if (player.side === Config.CONSTS.SIDE_HOME) {
            vm.homeTeamPlayers.push(player.user_id);
          }
          else {
            vm.awayTeamPlayers.push(player.user_id);
          }
        });
      };
    }]);

