'use strict';

angular.module('main')
  .controller('GameController', ['$http', function ($http) {

    var json = '[{"id":1,"name":"Team A","players":[{"id":1,"name":"Player A1"},{"id":2,"name":"Player A2"}]},{"id":2,"name":"Team B","players":[{"id":3,"name":"Player B1"},{"id":4,"name":"Player B2"}]}]';

    this.teams = JSON.parse(json);

    this.game = {
      teamA: this.teams[0].name,
      teamB: this.teams[1].name,
      scoreA: 0,
      scoreB: 0
    };

    this.scoreGoal = function () {

    };
  }]);

