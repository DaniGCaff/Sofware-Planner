angular.module('appControllers').controller('PlanController', function($http, $location, $scope, $routeParams, AppAuth) {
	var self = this;
	self.repoId = $routeParams.repoId;
	$scope.selectedTitle = "Planificador de tareas";
	$scope.selectedMessage = "Empieza creando tareas en trello, asocialas mediante commits mágicos desde GitHub y utiliza esta herramienta para gestionar tu tiempo y equipo de la forma más óptima y eficaz posible.";
	$scope.selectedTaskId = -1;
	
	$http.get("/repos/board/" + self.repoId).success(function(dato) {
		if(dato.response == "ok") {
			self.requestAsocBoard = dato.boardId;
			$http.get("/tasks/board/" + self.requestAsocBoard).success(function(taskResult) {
				$scope.selectedTasks = taskResult;
			});
		} else {
			alert(dato.response);
		}
	}).error(function() {
		alert("ERROR");
	});
	
	
	$scope.detail = function(taskId) {
		$scope.selectedTaskId = taskId;
		$http.get("/tasks/" + taskId).success(function(task) {
			$scope.selectedTitle = task.name;
			$scope.selectedMessage = "Ver más en trello: " + task.shortUrl;
		}).error(function() {
			alert("ERROR");
		});
		
		$http.get("/tasks/commits/" + taskId).success(function(commits) {
			$scope.selectedCommits = commits;
		}).error(function() {
			alert("ERROR");
		});
	}
	
});