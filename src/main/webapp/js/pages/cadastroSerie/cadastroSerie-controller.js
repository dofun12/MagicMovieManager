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
	
	$scope.sortListViewFiles = function(){
		var ar = $scope.viewFiles;
		for(var i = (ar.length - 1); i >= 0; i--){
			for (var j = 1; j <= i; j++){
				if (!ar[j-1].directory){
					var temp = ar[j-1];
					ar[j-1] = ar[j];
					ar[j] = temp;
				} 
			}
		}
		$scope.viewFiles = ar;
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
		var files = $scope.serieObj.files;
		$scope.files = [];
		$scope.arquivos = [];
		for(var i=0;i<files.length;i++){
			var file = files[i];
			var split = file.filePath.split("\\");
			var name = split[split.length-1];
			var fileModel = {name:name,path:file.filePath,episodio:file.pk.episodio};
			$scope.files[i] = fileModel;
			$scope.arquivos[i] = fileModel;
		}
		
	};
	
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
			$scope.arquivos = $scope.arquivos.concat(response.data);
		});
	};
	$scope.lastSelectedViewFile = $scope.selectedViewFile;
	$scope.mostrarTituloFileView = false;
	$scope.viewDirectory = function(obj) {
		if(angular.isDefined(obj) && obj!=null && !obj==""){
			CadastroSerieService.viewDirectory(obj).then(function(response) {
				$scope.selectedFiles = [];
				
				$scope.lastSelectedViewFile = $scope.selectedViewFile;
				$scope.selectedViewFile = obj;
				$scope.viewFiles = response.data;
				$scope.sortListViewFiles();
				if(angular.isDefined(obj.path)){
					$scope.mostrarTituloFileView = true;				
				}else{
					$scope.mostrarTituloFileView = false;
				}
				
			});
		}	
	};
	
	$scope.viewParentDirectory = function(obj) {
		if(angular.isDefined(obj) && obj!=null && !obj==""){
			CadastroSerieService.viewParentDirectory(obj).then(function(response) {
				$scope.selectedFiles = [];
				
				$scope.lastSelectedViewFile = $scope.selectedViewFile;
				$scope.selectedViewFile = response.data.parentfile;
				$scope.viewFiles = response.data.files;
				$scope.sortListViewFiles();
				if(angular.isDefined(obj.path)){
					$scope.mostrarTituloFileView = true;				
				}else{
					$scope.mostrarTituloFileView = false;
				}
				
			});
		}	
	};
	
	$scope.selectedFiles = [];
	
	$scope.selectFile = function(id){
		console.log(id);
		if($scope.selectedFiles[id]){
			$scope.selectedFiles[id] = false;
		}else{
			$scope.selectedFiles[id] = true;
		}
	};
	
	$scope.isSelected = function(id){
		return $scope.selectedFiles[id];
	};
	
	
	$scope.selectedViewFile = null;
	$scope.viewDirectory({});

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
				$scope.files = response.data;
			});
		}
	};

	$scope.testarRegex = function() {
		var obj = {
			regex : $scope.serieRegex,
			files : $scope.files
		};

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
	
	$scope.adicionarArquivos = function(){
		$scope.files = [];
		console.log("Teste",$scope.selectedFiles);
		for(var i=0;i<$scope.selectedFiles.length;i++){
			console.log($scope.selectedFiles[i]);
			for(var x=0;x<$scope.viewFiles.length;x++){
				if($scope.selectedFiles[i]){
					var tmp = $scope.viewFiles[x]; 
					if(tmp.id == i){
						$scope.arquivos.push(tmp);		
					}
				}
			}
		}
	};
	
	
	$scope.salvarSerie = function() {
		var serieModel = {};
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
