angular.module('appControllers').controller('SignUpController', function($http, $location, $scope) {
	var self = this;
	$scope.gitHubUsername = null;
	$scope.trelloUsername = null;
	
	$http.get("/user").success(function(data) {
		$scope.gitHubUsername = data.gitHubUserId;
		$scope.$update;
	});		
	
	$scope.loginIntoTrello = function() {
	    Trello.authorize({
	        type: "popup",
	        name: "Trello dashboard",
	        scope: {
	            read: true,
	            write: false },
	        expiration: "never",
	        success: self.publishTrelloData,
	        error: function() { alert("Failed trello authentication."); }
	    });
	}
	
	self.publishTrelloData = function() {
		Trello.get("members/me", function(datos) {
			$scope.trelloUserId = datos.id;
			$scope.trelloUsername = datos.username;
	        Trello.get("members/me/boards?filter=open", function(datos) {
	        	self.boards = datos;
	        	self.tasks = [];
	        	for(var i = 0; i < self.boards.length; i++) {
	    			var board = self.boards[i];
	        		Trello.get("boards/"+board.id+"/cards", function(boardTasks) {
	        			for(var j = 0; j < boardTasks.length; j++) {
	        				self.tasks.push(boardTasks[j]);
	        			}
	        		},function() {
	        			console.log("Failed to load tasks.");
	        		});
	        	}
	        },function() {
				self.boards = null;
				console.log("Failed to load boards.");
			});
			$scope.$digest();
		},function() {
			alert("Failed to retrive trello data.");
		});
	}
	
    $scope.commit = function() {
    	$http.post("/user/create", {"user":{"id":$("#register-username").val(),
    		"password":$("#register-password").val(),
    		"name":$("#register-name").val(),
    		"trelloUserId":$scope.trelloUserId,
    		"gitHubUserId":$scope.gitHubUsername}, "boardData" : self.boards, "taskData" : self.tasks})
    	.then(function(resp) {
    		if(resp.data.response == "ok")
    			$location.path("/#/app/home");
    		else
    			alert(resp.data.response);
    	}, function(warning) {
    		alert(warning.data);
    	});
    }
});