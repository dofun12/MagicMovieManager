/**
 * 
 */
angular.module('MainModule')
.service('CadastroSerieService', function($http, $browser , $window) {
		
	this.baseUrl = $window.location.origin+$window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	
	this.listarSeries = function() {
		return $http.get( this.baseUrl + '/listarSeries');
	};
	
	this.testarRegex = function(serviceModel) {
		return $http.post( this.baseUrl + '/testarRegex',serviceModel);
	};
	
});
