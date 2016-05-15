angular.module('appControllers').controller('HomeController', function($http, $location, $scope, AppAuth) {
	var self = this;
	$scope.AppAuth = AppAuth;
	
	$scope.logout = function() {
		AppAuth.logout();
		$location.path("#/app/home");
	};
});