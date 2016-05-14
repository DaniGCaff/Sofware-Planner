angular.module('appControllers').controller('HomeController', function($http, $location, $scope, AppAuth) {
	var self = this;
	$scope.estado = 1;
	$scope.AppAuth = AppAuth;
	
	$scope.logout = function() {
		$http.post('logout', {}).success(function() {
			self.authenticated = false;
			$location.path("/");
		}).error(function(data) {
			console.log("Logout failed")
			self.authenticated = false;
		});
	};
	
	$scope.asociarRepo = function(id){
		  self.requestAsocRepo=id;
		  $('#modalAsociar').openModal();
	}
	
	$scope.asociarBoard = function(id) {
		self.requestAsocBoard=id;
		self.requestAsoc();
	}
	
	$scope.requestAsoc = function() {
		if(self.requestAsocRepo != null && self.requestAsocBoard != null) {
			$http.get("/repos/asociar/" + self.id + "/" + self.requestAsocRepo+ "/" + self.requestAsocBoard).success(function(dato) {
				if(dato.response == "ok") {
					self.requestAsocRepo = null;
					self.requestAsocBoard = null;
					self.estado = 1;
				} else {
					alert(dato.response)
				}
			}).error(function() {
				alert("ERROR");
			});
		}
	}
    
	$scope.asociarConTrello = function() {
    	self.estado = 2;
    	$('#modalAsociar').closeModal();
    	$http.get("/boards/" + self.trelloUserId).success(function(boards) {
			self.boards = boards;
		}).error(function() {
			alert("ERROR");
		});
	}
});