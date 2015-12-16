'use strict';
angular.module('main')
  .service('UsersService', ['$http', function ($http) {

    /**
     * Get all existing users
     * @returns {*}
       */
    this.getUsers = function () {
      return $http.get('/users/')
        .then(
          function (response) {
            if (response.status === 200) {
              return response.data;
            }
            else {
              // TODO Handle error
              return null;
            }
          },
          function (error) {
            // TODO Handle error
            return null;
          });
    };

    /**
     * Get specific user data based on its id
     * @param userId
     * @returns {*}
     */
    this.getUserById = function(userId) {
      return $http.get('/users/' + userId)
        .then(
          function (response) {
            if (response.status === 200) {
              return response.data;
            }
            else if (response.status === 404) {
              return undefined;
            }
            else {
              // TODO Handle error
              return null;
            }
          },
          function (error) {
            // TODO Handle error
            return null;
          });
    };
  }]);
