'use strict';
angular.module('main')
  .service('GamesService', ['$http', function ($http) {

    this.getGameById = function (gameId) {
      return $http.get('/games/' + gameId)
        .then(
          function (response) {
            return response.data;
          },
          function (error) {
            // TODO Handle error
            return null;
          });
    };

  }]);
