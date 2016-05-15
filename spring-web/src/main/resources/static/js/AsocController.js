angular.module('appControllers').controller('AsocController', function($http, $location, $scope, $routeParams, AppAuth) {
	var self = this;
	self.repoId = $routeParams.repoId;
	$scope.AppAuth = AppAuth;
	
	$scope.asociar = function(boardId) {
		if(self.repoId != null && boardId != null) {
			$http.get("/repos/asociar/" + AppAuth.id + "/" + self.repoId+ "/" + boardId).success(function(dato) {
				if(dato.response == "ok") {
					for(var i = 0; i < AppAuth.repos.length; i++) {
						if(AppAuth.repos[i].id == self.repoId)
							AppAuth.repos[i].asoc = 'true';
					}
					$scope.AppAuth = AppAuth;
					$location.path("#/app/home");
				} else {
					alert(dato.response)
				}
			}).error(function() {
				alert("ERROR");
			});
		}
	}
});