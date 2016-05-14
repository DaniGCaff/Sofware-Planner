angular.module('appControllers').controller('PlanController', function($http, $location, $scope, $routeParams, AppAuth) {
	var self = this;
	self.repoId = $routeParams.repoId;
	
	$http.get("/repos/board/" + self.repoId).success(function(dato) {
		if(dato.response == "ok") {
			self.requestAsocBoard = dato.boardId;
			$http.get("/tasks/" + self.requestAsocBoard).success(function(taskResult) {
				$scope.selectedTasks = taskResult;
			});
		} else {
			alert(dato.response);
		}
	}).error(function() {
		alert("ERROR");
	});
	
	
	$scope.detail = function(taskId) {
		$http.get("/tasks/commits/" + taskId).success(function(commits) {
			$scope.selectedCommits = commits;
		}).error(function() {
			alert("ERROR");
		});
	}
	
});