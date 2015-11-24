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
	
	this.ultimaSerieAssistida = function(serie) {
		return $http.post( this.baseUrl + '/ultimaSerieAssistida', serie);
	};
	
	this.buscarHistoricoEpisodio = function(serieFile) {
		return $http.post( this.baseUrl + '/buscarHistoricoEpisodio', serieFile);
	};
	
	this.rodarComando = function(id) {
		return $http.get( this.baseUrl + '/runCommand/'+id);
	};
	
	this.listarSerieSecreta = function(serie) {
		return $http.post( this.baseUrl + '/listarSerieSecreta', serie);
	};
	
	
	
});
