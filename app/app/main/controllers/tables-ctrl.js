'use strict';

angular.module('main')
  .controller('TablesController', function ($http) {

    this.tablesByFloor = {};

    var json = '[{' +
      '"floor": 1,' +
      '"tables": [{' +
        '"id": 1,' +
        ' "available": true' +
      '}, {' +
        '"id": 2,'+
        '"available": false' +
      '}]' +
    '}, {' +
      '"floor": 2,' +
      '"tables": [{' +
        '"id": 3,' +
        '"available": true' +
      '}]'+
    '}]';

    this.tablesByFloor = JSON.parse(json);
  });

/*

 [{
 "floor": 1,
 "tables": [{
 "id": 1,
 "available": true
 }, {
 "id": 2,
 "available": false
 }]
 }, {
 "floor": 2,
 "tables": [{
 "id": 3,
 "available": true
 }]
 }]

 */
