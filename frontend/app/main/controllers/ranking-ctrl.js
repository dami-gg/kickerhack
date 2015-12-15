'use strict';

angular.module('main')
  .controller('RankingController', [function () {

    var json = '[{"id":1,"name":"Player 1","wins":10,"losses":27,"goals":16,"points":8},{"id":2,"name":"Player 2","wins":14,"losses":12,"goals":25,"points":14},{"id":3,"name":"Player 3","wins":17,"losses":14,"goals":21,"points":12}]';

    this.ranking = JSON.parse(json);

    this.orderByFilter = '-points';
  }]);
