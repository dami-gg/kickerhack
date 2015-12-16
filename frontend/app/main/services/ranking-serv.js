'use strict';
angular.module('main')
  .service('RankingService', ['$http', function ($http) {

    this.getRanking = function () {
      return $http.get('/ranking/')
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
