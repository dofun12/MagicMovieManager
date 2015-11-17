/**
 * 
 */

angular.module('MainModule')
.controller('CadastroSerieController', function($scope, $browser, $window, $browser, $interval , CadastroSerieService) {
	$scope.testarRegex = function(){
		var obj = {
			name: $scope.serieName,
			regex: $scope.serieRegex,
			filepath: $scope.seriePath 
		};
		
		$scope.totalEncontrados = 0;
		
		CadastroSerieService.testarRegex(obj).then( function (response) {
			if(angular.isDefined(response.data)){
				$scope.totalEncontrados = response.data.length;
			}
			
		});
		
	};	
	
	
	

});
