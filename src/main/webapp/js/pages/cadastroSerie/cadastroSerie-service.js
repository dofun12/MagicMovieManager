/**
 * 
 */
angular.module('MainModule')
.service('CadastroSerieService', function($http, $browser , $window) {
		
	this.baseUrl = $window.location.origin+$window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	
	this.listarSeries = function() {
		return $http.get( this.baseUrl + '/listarSeries');
	};
	
	this.testarRegex = function(obj) {
		return $http.post( this.baseUrl + '/testarRegex',obj);
	};
	
	this.adicionarSerie = function(obj) {
		return $http.post( this.baseUrl + '/adicionarSerie',obj);
	};
	
	this.validar = function(obj) {
		return $http.post( this.baseUrl + '/validarSeries',obj);
	};
	
	this.buscarArquivos = function(obj) {
		return $http.get( this.baseUrl + '/fileChooser');
	};
	
});
