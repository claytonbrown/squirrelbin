var ENDPOINT = 'https://3r196d993g.execute-api.us-east-1.amazonaws.com/beta/acorns';

angular.module('app', ['ngRoute', 'ui.ace'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'partials/root.html'
            })
            .when('/acorns', {
                templateUrl: 'partials/acorn-list.html',
                controller: 'AcornListController'
            })
            .when('/new', {
                templateUrl: 'partials/edit.html',
                controller: 'EditController'
            })
            .otherwise({
                redirectTo: '/'
            });
    }])

    .controller('MainController', ['$scope', '$location', '$http', function ($scope, $location, $http) {
        $scope.routeActive = function (loc) {
            return loc === $location.path();
        };

        $scope.loading = false;

        $scope.ready = function () {
            $scope.loading = false;
        };

        $scope.acornREST = function (method, endpoint, data) {
            $scope.loading = true;
            return $http[method](endpoint, data)
                .catch(console.log)
                .finally($scope.ready);
        };
    }])

    .controller('AcornListController', ['$scope', '$location', '$http', function ($scope, $location, $http) {
        $scope.acornREST('get', ENDPOINT)
            .then(function (response) {
                $scope.acorns = response.data.Items;
            });
    }])

    .controller('EditController', ['$scope', '$location', '$routeParams', '$http', function ($scope, $location, $routeParams, $http) {
        $scope.editing = !!$routeParams.acornId;

        if ($scope.editing) {
            $scope.acornREST('get', ENDPOINT + "/" + $routeParams.acornId)
                .then(function (response) {
                    $scope.acorn = response.data.Item;
                });
        } else {
            // We're making a new acorn!
            $scope.acorn = {
                name: 'untitled',
                author: 'anonymous',
            };
        }

        var patchAcorn = function (acorn) {
            return {
                "item": acorn
            };
        };

        $scope.save = function () {
            var method = $scope.editing ? 'put' : 'post';
            if (!$scope.editing) {
                $scope.acorn.id = CryptoJS.SHA3(JSON.stringify($scope.acorn) + Math.random(), {outputLength: 224}).toString();
            }
            $scope.acornREST(method, ENDPOINT + "/" + $scope.acorn.id, patchAcorn($scope.acorn))
                .then(function () {
                    $location.path("acorns");
                });
        };
    }]);
