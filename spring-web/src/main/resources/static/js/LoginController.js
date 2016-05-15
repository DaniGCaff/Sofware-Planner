angular.module('appControllers').controller('LoginController', function($http, $location, $scope, AppAuth) {
    $scope.commit = function() {
    	$http.post("/user/login", {"id":$("#login-username").val(),
    		"password":$("#login-password").val()})
    	.then(function successCallback(resp) {
			if(resp.data.response == "ok") {
				AppAuth.name = resp.data.name;
				AppAuth.status = true;
				AppAuth.id=resp.data.id;
				AppAuth.trelloUserId = resp.data.trelloUserId;
				AppAuth.gitHubUserId = resp.data.gitHubUserId;
				$http.get("/repos/"+AppAuth.id).success(function(dato) {
					AppAuth.repos = dato;
				}).error(function() {
					AppAuth.repos = null;
				});
				$http.get("/boards/"+AppAuth.id).success(function(dato) {
					AppAuth.boards = dato;
				}).error(function() {
					AppAuth.boards = null;
				});
				$location.path("#/app/home");
			} else {
				alert(resp.data.response);
			}
    	}, function errorCallback(resp) {
			console.log(resp.data);
			alert(resp.data);
    	});
    }
});