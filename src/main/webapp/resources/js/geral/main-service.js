/**
 * 
 */
angular.module('MainModule')
.service('MainService',['$http', '$q', '$browser', 'AppRequestManagerService', 
function($http, $q, $browser, AppRequestManagerService) {
	
	this.play = function(episodio) {
		return $http.post($browser.url() + '/play', episodio);
		
	};
	
}]);