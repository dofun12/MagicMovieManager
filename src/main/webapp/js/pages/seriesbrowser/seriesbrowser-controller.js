/**
 * 
 */

angular.module('MainModule')
.controller('SeriesBrowserController', function($scope, $browser, $window, $browser, $interval , SeriesBrowserService) {
	$scope.isSerieSelecionada = false;
	$scope.isWatching = false;
	
	
	$scope.isLastWatched = function(serieFile){
		if($scope.lastWatched!=null && $scope.lastWatched!=""){
			if(serieFile.pk.episodio == $scope.lastWatched.pk.episodio 
					&& serieFile.pk.idSerie == $scope.lastWatched.pk.idSerie){
				return true;
			}else{
				return false;
			}			
		}else{
			return false;
		}	
	};
	
	
	
	
	$scope.selecionarSerie = function(obj) {
		$scope.selectedSerie = obj;
		$scope.isSerieSelecionada = true;
		console.log($scope.selectedSerie);
		$scope.buscarultimaSerieAssistida(obj);
	};
	
	$scope.buscarultimaSerieAssistida = function(obj){
		SeriesBrowserService.ultimaSerieAssistida(obj).success( function (response) {
			$scope.lastWatched = response;
			if($scope.lastWatched!=""){
				$scope.lastWatched.percent = Math.round($scope.lastWatched.percentWatched)+"%";
			}
			console.log("Last",$scope.lastWatched);
		});
	}
	
	SeriesBrowserService.listarSeries().then( function (response) {
		console.log(response.data);
		$scope.seriesList = response.data;
	});
	
	$scope.voltar = function(){
		$scope.isSerieSelecionada = false;
	};
	
	var delay = 3000;
	var increment = 1000;
	$scope.isRunning = false;
	
	$scope.updateStatus = function(){
		SeriesBrowserService.getStatusMPHC().success( function (response) {
			if(response.file==null){
				console.log(response);
				$scope.isRunning = false;
			}else{
				$scope.isRunning = true;
				$scope.jsonData = response;
				$scope.lastPos = Number(response.position);
			}
			
			
		});
	};
	
	$interval(function() {
		$scope.updateStatus();
	}, delay);
	$scope.updateStatus();
	
	$interval(function() {
		if ($scope.isRunning) {
			$scope.lastPos += increment;
			$scope.totalPlayed = Number(($scope.lastPos / $scope.jsonData.duration) * 100).toFixed(1);
			$scope.percentPlayed = Math.abs($scope.totalPlayed)+"%"; 
			if ($scope.totalPlayed >= 100) {
				SeriesBrowserService.getStatusMPHC().success( function (response) {
					if (response.statusType == "RUNNING") {
						console.log("LOOP 2", response);
						$scope.isRunning = true;
						$scope.jsonData = response;
						$scope.lastPos = Number(response.position);
					} else {
						$scope.isRunning = false;
					}
				});
			}
		}
	}, increment);
	
	$scope.play = function(episodio){
		var obj = {};
		if($scope.isLastWatched(episodio)){
			obj = {serie:episodio,position:$scope.lastWatched.positionstring,fullscreen:true}
		}else{
			obj = {serie:episodio,position:null,fullscreen:true}
		}
		SeriesBrowserService.play(obj);
		
		$scope.buscarultimaSerieAssistida($scope.selectedSerie);
		
	};
	
	
	
	

});
