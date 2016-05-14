var myApp = angular.module("appSoftwarePlanner", [])
				.config(
						function($httpProvider) {
							$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
						}).controller("HomeController", function($http, $location, $scope) {
					var self = this;
					self.estado = 1;
					$http.get("/user").success(function(data) {
						if (data.gitHubUserId) {
							self.gitHubUserId = data.gitHubUserId;
							if(estado = 1) {
						    	$('#modalRegister').openModal();
							} else {
								self.authenticated = true;
								$http.get("/repos").success(function(dato) {
									self.repos = dato;
									$scope.$update();
								}).error(function() {
									self.repos = null;
								});
							}

						} else {
							self.user = "N/A";
							self.authenticated = false;
						}
					}).error(function() {
						self.user = "N/A";
						self.authenticated = false;
					});
					self.logout = function() {
						$http.post('logout', {}).success(function() {
							self.authenticated = false;
							$location.path("/");
						}).error(function(data) {
							console.log("Logout failed")
							self.authenticated = false;
						});
					};
					
					self.asociarRepo = function(id){
						  self.requestAsocRepo=id;
						  $('#modalAsociar').openModal();
					}
					
					self.verRepo = function(id){
						self.requestAsocRepo=id;
						self.listTasks();
						self.estado = 3;
					}
					
					self.asociarBoard = function(id) {
						  self.requestAsocBoard=id;
						  self.requestAsoc();
					}
					
					self.requestAsoc = function() {
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
					
					self.loginIntoTrello = function() {
					    Trello.authorize({
					        type: "popup",
					        name: "Trello dashboard",
					        scope: {
					            read: true,
					            write: false },
					        expiration: "never",
					        success: self.publishTrelloData,
					        error: function() { console.log("Failed authentication"); }
					    });
					}
					
					self.publishTrelloData = function() {
						Trello.get("members/me", function(datos) {
							self.trelloUserId = datos.id;
							self.trelloUsername = datos.username;
						},function() {
							self.trelloUserId = null;
							self.trelloUsername = null;
						});
						
						
				        Trello.get("members/me/boards?filter=open", function(datos) {
				        	self.boards = datos;
				        	self.tasks = [];
				        	for(var i = 0; i < self.boards.length; i++) {
			        			var board = self.boards[i];
				        		Trello.get("boards/"+board.id+"/cards", function(tasks) {
				        			for(var j = 0; j < tasks.length; j++) {
				        				var task = tasks[i];
					   					var aux = {id: task.id, creation: "", lastModification: "", dateLastActivity: task.dateLastActivity,
					   							idBoard: task.idBoard, idList: task.idList, name: task.name,
					   							pos: task.pos, due: task.due, shortUrl: task.shortUrl};
					   					self.tasks.push(aux);
				        			}
				        		});
				        	}
				        },function() {
							self.boards = null;
							console.log("Failed to load boards");
						});
					}
				    
				    self.asociarConTrello = function() {
				    	self.estado = 2;
	                	$('#modalAsociar').closeModal();
	                	$http.get("/boards/" + self.trelloUserId).success(function(boards) {
							self.boards = boards;
						}).error(function() {
							alert("ERROR");
						});
					}
				    
				    self.listTasks = function() {
				    	$http.get("/repos/board/" + self.requestAsocRepo).success(function(dato) {
							if(dato.response == "ok") {
								self.requestAsocBoard = dato.boardId;
								$http.get("/tasks/" + self.requestAsocBoard).success(function(taskResult) {
									self.selectedTasks = taskResult;
								});
							} else {
								alert(dato.response)
							}
						}).error(function() {
							alert("ERROR");
						});
				    }
				    
				    self.digestTasks = function(datos) {
				    	self.tasks = datos;
				    	$scope.$apply();
				    }
				    
				    self.openRegister = function() {
				    	$('#modalRegister').openModal();
				    }
				    
				    self.commitRegister = function() {
				    	$('#modalRegister').closeModal();
				    	$http.post("/user/create", {"user":{"id":$("#register-username").val(),
				    		"password":$("#register-password").val(),
				    		"name":$("#register-name").val(),
				    		"trelloUserId":self.trelloUserId,
				    		"gitHubUserId":self.gitHubUserId}, "boardData" : self.boards, "taskData" : self.tasks});
				    }
				    
				    self.openLogin = function() {
				    	$('#modalLogin').openModal();
				    }
				    
				    self.commitLogin = function() {
				    	$('#modalLogin').closeModal();
				    	$http.post("/user/login", {"id":$("#login-username").val(),
				    		"password":$("#login-password").val()}).then(function successCallback(resp) {
				    			if(resp.data.response == "ok") {
				    				self.name = resp.data.name;
				    				self.authenticated = true;
				    				self.id=resp.data.id;
				    				self.trelloUserId = resp.data.trelloUserId;
				    				self.gitHubUserId = resp.data.gitHubUserId;
				    				$http.get("/repos/"+self.id).success(function(dato) {
										self.repos = dato;
									}).error(function() {
										self.repos = null;
									});
				    			} else {
				    				alert(resp.data.response);
				    			}
				    		}, function errorCallback(resp) {
				    			console.log(resp.data);
				    			alert(restp.data);
				    		});
				    }
				});