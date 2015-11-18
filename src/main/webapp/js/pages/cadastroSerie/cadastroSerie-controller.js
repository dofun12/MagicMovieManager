/**
 * 
 */

angular.module('MainModule', []).controller('CadastroSerieController', function($scope, $browser, $window, $browser, $interval, CadastroSerieService) {
	$scope.regexDisp = [ '.*([0-9]{3}).mp4', '.*([0-9]{2}).mp4', '.*(S[0-9]{2}E[0-9]{2}).*', '.*([0-9]{2}).mpeg' ];
	$scope.temporadas = [];
	for (var temp = 0; temp < 40; temp++) {
		if (temp == 0) {
			$scope.temporadas[temp] = {
				name : "Sem Temporada",
				value : temp
			};
		} else {
			$scope.temporadas[temp] = {
				name : "Temporada " + temp,
				value : temp
			};
		}
	}
	$scope.buscarArquivos = function() {
		CadastroSerieService.buscarArquivos().then(function(response) {
			console.log(response);
			$scope.arquivos = response.data;
		});
	};

	$scope.selecionarRegex = function(regex) {
		$scope.selectedRegex = regex;
		$scope.aplicarRegex();
	};

	$scope.aplicarRegex = function() {
		if ($scope.selectedRegex != null && $scope.selectedRegex != "" && angular.isDefined($scope.selectedRegex)) {
			var obj = {
				regex : $scope.selectedRegex,
				files : $scope.arquivos
			};

			CadastroSerieService.testarRegex(obj).then(function(response) {
				console.log(response);
				$scope.files = response.data;
			});
		}
	};

	$scope.testarRegex = function() {
		var obj = {
			regex : $scope.serieRegex,
			files : $scope.files
		};

		console.log("TEste", $scope.regexDisp);

		$scope.totalEncontrados = 0;

		CadastroSerieService.testarRegex(obj).then(function(response) {
			if (angular.isDefined(response.data)) {
				$scope.totalEncontrados = response.data.length;
			}

		});

	};

	$scope.salvarSerie = function() {
		var serieModel = {
			name : $scope.serieName
		};
		var obj = {
			temporada : $scope.selectedTemporada,
			serie : serieModel,
			episodios : $scope.files
		};
		console.log("Saving",obj);
		CadastroSerieService.adicionarSerie(obj);
	};

});
