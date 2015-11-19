/**
 * 
 */

angular.module('MainModule', []).controller('CadastroSerieController', function($scope, $browser, $window, $browser, $interval, CadastroSerieService) {
	$scope.regexDisp = [ '.*([0-9]{3}).mp4', '.*([0-9]{2}).mp4', '.*(S[0-9]{2}E[0-9]{2}).*', '.*([0-9]{2}).mpeg' ];
	$scope.temporadas = [];
	$scope.isNovaSerie = true;
	$scope.files = [];
	$scope.arquivos = [];
	
	
	$scope.listarSeries = function(){
		CadastroSerieService.listarSeries().then(function(response) {
			$scope.seriesDisponiveis = response.data;
			if($scope.seriesDisponiveis.length>0){
				//$scope.isNovaSerie = false;
			}else{
				//$scope.isNovaSerie = true;
			}
		});
	};
	
	$scope.listarSeries();
	$scope.onChangeSerie = function(){
		$scope.serieObj = {};
		for(var s=0;s<$scope.seriesDisponiveis.length;s++){
			var serie = $scope.seriesDisponiveis[s];
			if(serie.id = $scope.selectedSerie){
				$scope.serieObj = serie;
			}
		}
		console.log($scope.serieObj);
		var files = $scope.serieObj.files;
		$scope.files = [];
		$scope.arquivos = [];
		for(var i=0;i<files.length;i++){
			var file = files[i];
			var split = file.filePath.split("\\");
			var name = split[split.length-1];
			console.log(file)
			var fileModel = {name:name,path:file.filePath,episodio:file.pk.episodio};
			$scope.files[i] = fileModel;
			$scope.arquivos[i] = fileModel;
		}
		
	}
	
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
			$scope.arquivos = $scope.arquivos.concat(response.data);
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

	$scope.limparCampos = function(){
		$scope.files = [];
		$scope.arquivos = [];
		$scope.serieName = "";
		$scope.selectedTemporada = null;
	};
	
	$scope.salvarSerie = function() {
		var serieModel = {}
		if($scope.isNovaSerie){
			serieModel = {
				name : $scope.serieName
			};
		}else{
			serieModel = $scope.serieObj;
		}	
		var obj = {
			temporada : $scope.selectedTemporada,
			serie : serieModel,
			episodios : $scope.files
		};
		console.log("Trying",obj);
		CadastroSerieService.validar(obj).success(function(response){
			if(response.isValid){
				CadastroSerieService.adicionarSerie(obj).success(function(response){
					$scope.showMensagem = true;
					$scope.showMensagemError = false;
					$scope.message = "Salvo com sucesso";
					$scope.limparCampos();
					$scope.listarSeries();
				});
			}else{
				$scope.showMensagem = false;
				$scope.showMensagemError = true;
				$scope.message = response.message;
			}
		});
		
	};

});
