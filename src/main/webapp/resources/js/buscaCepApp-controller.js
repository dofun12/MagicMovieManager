/**
 * 
 */

var app = angular.module('buscaCepApp', []);
app.controller('EnderecoController', function($scope, $http) {
	$scope.cepEncontrado = false;
	$scope.cepSubstituido = false;
	$scope.cepInvalido = false;
	$scope.cepNaoEncontrado = false;
	$scope.isValid = false;
	$scope.selectedEndereco = {};
	
	$scope.isSelected = function(obj){
		return (obj==$scope.selectedEndereco);
	}
	
	$scope.oldCEP = "";
	var fillInputs = function(object) {
		if (object != null && angular.isDefined(object)) {
			$scope.inputRua = object.rua;
			$scope.inputcep = object.cep;
			$scope.inputBairro = object.bairro;
			$scope.inputCidade = object.cidade;
			$scope.inputUF = object.uf;
			$scope.inputNumero = object.numero;
			$scope.codigo = object.id;
			$scope.inputComplemento = object.complemento;

		}

	}
	$scope.procurarCEP = function() {
		$scope.oldCEP = $scope.inputcep;
		
		var busca = {
				"cep" : $scope.inputcep
		};

		$http.post('/buscaCEP/', busca, {}).success(
				function(data) {
					$scope.pesquisaFinalizada = false;
					$scope.endereco = data;
					
					if (data.response == "CEP_SUBSTITUIDO") {
						$scope.cepInvalido = false;
						$scope.cepEncontrado = false;
						$scope.cepSubstituido = true;
						$scope.cepNaoEncontrado = false;
						fillInputs($scope.endereco);
						
						$scope.validateCEP();
					} else if(data.response == "CEP_INVALIDO"){
						$scope.cepInvalido = true;
						$scope.cepEncontrado = false;
						$scope.cepSubstituido = false;
						$scope.cepNaoEncontrado = false;
						
						$scope.validateCEP();
					}else if(data.response == "CEP_NAO_ENCONTRADO"){
						$scope.cepInvalido = false;
						$scope.cepEncontrado = false;
						$scope.cepSubstituido = false;
						$scope.cepNaoEncontrado = true;
						
						$scope.validateCEP();
					}else{
						$scope.cepInvalido = false;
						$scope.cepEncontrado = true;
						$scope.cepSubstituido = false;
						$scope.cepNaoEncontrado = false;
						
						fillInputs($scope.endereco);
						$scope.validateCEP();
					}
				});

	}
	$scope.hideAllMessages = function(){
		$scope.showMensagemSubstuido = false;
		$scope.showMensagemInvalido = false;
		$scope.showMensagemNaoEncontrado = false;
		$scope.showMensagemCamposObrigatorios = false;
	}
	
	$scope.validateForm = function(){
		$scope.hideAllMessages();
		$scope.messages = {
			"CAMPOS_OBRIGATORIOS": "Os seguintes campos são obrigatórios: "
		};
		
		var addCampoObrigatorio = function(campo){
			$scope.messages["CAMPOS_OBRIGATORIOS"] = $scope.messages["CAMPOS_OBRIGATORIOS"]+" "+campo+",";
			
		}
		$scope.isValid = true;
		
		$scope.invalidInputs = {};
		if($scope.inputRua == null ||$scope.inputRua=="" || !angular.isDefined($scope.inputRua)){
			$scope.invalidInputs['inputRua'] = true;
			addCampoObrigatorio("Rua");
			$scope.isValid = false;
		}else{
			$scope.invalidInputs['inputRua'] = false;
		}
		
		if($scope.inputNumero == null || $scope.inputNumero=="" || !angular.isDefined($scope.inputNumero)){
			$scope.invalidInputs['inputNumero'] = true;
			addCampoObrigatorio("Número");
			$scope.isValid = false;
		}else{
			$scope.invalidInputs['inputNumero'] = false;
		}
		
		if($scope.inputCidade == null || $scope.inputCidade=="" || !angular.isDefined($scope.inputCidade)){
			$scope.invalidInputs['inputCidade'] = true;
			$scope.isValid = false;
			addCampoObrigatorio("Cidade");
		}else{
			$scope.invalidInputs['inputCidade'] = false;
		}
		
		if($scope.inputcep == null ||$scope.inputcep=="" || !angular.isDefined($scope.inputcep)){
			$scope.invalidInputs['inputcep'] = true;
			addCampoObrigatorio("CEP");
			$scope.isValid = false;
		}else{
			$scope.invalidInputs['inputcep'] = false;
		}
		
		if($scope.inputUF == null ||$scope.inputUF=="" || !angular.isDefined($scope.inputUF)){
			$scope.invalidInputs['inputUF'] = true;
			addCampoObrigatorio("Estado");
			$scope.isValid = false;
		}else{
			$scope.invalidInputs['inputUF'] = false;
		}
		
		if(!$scope.isValid){
			var semVirgula = $scope.messages["CAMPOS_OBRIGATORIOS"];
			semVirgula = semVirgula.substring(0,semVirgula.length-1);
			$scope.messages["CAMPOS_OBRIGATORIOS"] = semVirgula;
			$scope.showMensagemCamposObrigatorios = true;
		}		
		return $scope.isValid;
	}
	
	$scope.validateCEP = function(){
		$scope.messages = {
			"CEP_SUBSTITUIDO": "O CEP: "+$scope.oldCEP+" não foi encontrado, porém foi encontrado o "+$scope.inputcep,
			"CEP_INVALIDO": "O valor: "+$scope.oldCEP+" é um cep inválido.",
			"CEP_NAO_ENCONTRADO": "O CEP: "+$scope.oldCEP+" não foi encontrado.",
		};
		
		$scope.isValid = true;
		$scope.showMensagemSubstuido = false;
		$scope.showMensagemInvalido = false;
		$scope.showMensagemNaoEncontrado = false;
		$scope.showMensagemCamposObrigatorios = false;
		
		if($scope.cepInvalido){
			$scope.showMensagemInvalido = true;
			$scope.isValid = false;
		}else{
			$scope.showMensagemInvalido = false;
		}
		
		if($scope.cepSubstituido){
			$scope.showMensagemSubstuido = true;
		}else{
			$scope.showMensagemSubstuido = false;
		}
		
		if($scope.cepNaoEncontrado){
			$scope.showMensagemNaoEncontrado = true;
			$scope.isValid = false;
		}else{
			$scope.showMensagemNaoEncontrado = false;
		}
		return $scope.isValid;
		
	}

	$scope.salvar = function(){
		
		if($scope.validateForm()){
			var codigo = $scope.inputcep+":"+$scope.inputNumero;
			var obj = {
					"id": codigo,
					"rua":$scope.inputRua,
					"cep":$scope.inputcep,
					"bairro":$scope.inputBairro,
					"cidade":$scope.inputCidade,
					"uf":$scope.inputUF,
					"numero":$scope.inputNumero,
					"complemento":$scope.inputComplemento
			}
			$http.post('/saveCEP/', obj, {}).success(
				function() {
					$scope.listar();
				}
			);
		}	
	}

	$scope.deletar = function(obj){
		$http.post('/removerCEP/', obj, {}).success(
			function() {
				$scope.listar();
			}
		);
	}
	$scope.listar = function(){
		$http.get('/listCEPS/').success(
			function(data) {
				$scope.listaCEPS = data; 
				
			}
		);
	}

	$scope.selecionar = function(obj){
		$scope.mostrarMensagem = false;
		$scope.mostrarMensagemInvalida = false;

		
		fillInputs(obj);
		$scope.validateForm();
		$scope.selectedEndereco = obj; 
	}
	$scope.listar();
});