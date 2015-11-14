/**
 * 
 */
angular.module('MainModule')
.service('SeriesBrowserService', function($http, $browser , $window) {
		
	this.baseUrl = $window.location.origin+$window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	
	this.listarSeries = function() {
		return $http.get( this.baseUrl + '/listarSeries');
	};
	
	this.play = function(episodio) {
		return $http.post( this.baseUrl + '/play', episodio);
	};
	
	this.getStatusMPHC = function(){
		return $http.get( this.baseUrl + '/statusMPHC');
	};
	
	
});