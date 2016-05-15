var softwarePlannerApp = angular.module('softwarePlannerApp', [
  'ngRoute',
  'appControllers'
]);

softwarePlannerApp.config(['$routeProvider', '$httpProvider',
  function($routeProvider, $httpProvider) {
    $routeProvider.
	  when('/app/asoc/:repoId', {
	      templateUrl: '../views/asoc.html',
	      controller: 'AsocController'
	  }).
      when('/app/plan/:repoId', {
		  templateUrl: '../views/plan.html',
		  controller: 'PlanController'
      }).
      when('/app/home', {
          templateUrl: '../views/home.html',
          controller: 'HomeController'
      }).
      when('/app/login', {
          templateUrl: '../views/login.html',
          controller: 'LoginController'
      }).
      when('/app/signup', {
          templateUrl: '../views/signup.html',
          controller: 'SignUpController'
      }).
      otherwise({
          redirectTo: '/app/home'
      });
    
    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
  }
]);

softwarePlannerApp.factory('AppAuth', function() {
	var authenticated = {status: false, name: "", gitHubUserId: "", trelloUserId: "", id: "", repos: [], boards:[], tasks:[]};
	
	authenticated.logout = function() {
		authenticated = {status: false, name: "", gitHubUserId: "", trelloUserId: "", id: "", repos: [], boards:[], tasks:[]};
	}
	
	return authenticated;
});

angular.module('appControllers', ['ngSanitize']);