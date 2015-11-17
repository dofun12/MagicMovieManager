/**
 * 
 */

angular.module('MainModule')
.controller('HomeController', ['$scope', '$browser', '$window', '$interval','HomeService',
function($scope, $browser, $window, $browser, $interval , HomeService) {
	$scope.isSerieSelecionada = false;

	$http.get($browser.url() + '/listarSeries').success(function(response) {
		$scope.seriesList = response;
	});

	$scope.selecionarSerie = function(obj) {
		$scope.selectedSerie = obj;
		$scope.isSerieSelecionada = true;
	};

	$scope.play = function(episodio) {
		console.log(episodio);
		HomeService.play(episodio)
			.success(function(response) {
				$scope.playSession(episodio);
			});
	};

	$scope.playSession = function(episodio) {

		var delay = 3000;
		var increment = 1000;
		$scope.isRunning = false;

		$scope.lastPos = 0;
		$http.get($browser.url() + '/statusMPHC').success(function(response) {
			$scope.jsonData = response;
			$scope.totalPlayed = 0;

			console.log(response);
			$interval(function() {
				$http.get($browser.url() + '/statusMPHC').success(function(response) {
					console.log("LOOP", response);
					if (response.statusType == "RUNNING") {
						$scope.jsonData = response;
						$scope.isRunning = true;
						$scope.lastPos = Number(response.position);

						var objects = {
							media : episodio,
							status : response
						};

						$http.post($browser.url() + '/saveHistory', objects, {}).success(function(response) {
						});
					} else {
						$scope.isRunning = false;
					}
				});

			}, delay);

			$interval(function() {
				if ($scope.isRunning) {
					$scope.lastPos += increment;
					$scope.totalPlayed = Number(($scope.lastPos / $scope.jsonData.duration) * 100).toFixed(1);
					if ($scope.totalPlayed >= 100) {
						$http.get($browser.url() + '/statusMPHC').success(function(response) {
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
		});

	};

}]);