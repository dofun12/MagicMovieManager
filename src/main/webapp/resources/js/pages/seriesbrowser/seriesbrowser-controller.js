/**
 * 
 */

angular.module('MainModule')
.controller('SeriesBrowserController', function($scope, $browser, $window, $browser, $interval , SeriesBrowserService) {
	$scope.isSerieSelecionada = false;

	$scope.selecionarSerie = function(obj) {
		$scope.selectedSerie = obj;
		$scope.isSerieSelecionada = true;
	};
	
	
	SeriesBrowserService.listarSeries().then( function (response) {
		console.log(response.data);
		$scope.seriesList = response.data;
	});
	

});
