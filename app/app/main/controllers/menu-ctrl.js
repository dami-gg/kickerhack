'use strict';
angular.module('main')
.controller('MenuCtrl', function ($scope, NFCService) {
	$scope.hasNFC = NFCService.hasNFC();
});
