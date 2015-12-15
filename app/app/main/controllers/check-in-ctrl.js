'use strict';
angular.module('main')
.controller('CheckInController', function ($stateParams, $log) {
	this.table = $stateParams.table;
	this.position = $stateParams.position;
});
