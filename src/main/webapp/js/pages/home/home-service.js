/**
 * 
 */
angular.module('MainModule')
.service('HomeService',['$http', '$q', '$browser', 'AppRequestManagerService', 
function($http, $q, $browser, AppRequestManagerService) {
	
	this.play = function(episodio) {
		return $http.post($browser.url() + '/play', episodio);
		
	};
	
}]);
