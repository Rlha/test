(function (angular) {
    var AppController = function ($scope, Item, $http, $location) {
        $scope.itemsPerPage = 4;
        $scope.currentPage = 1;
        $scope.items = [];
        $scope.pageSize = 10;

        $scope.currentHost = $location.protocol() + '://'+ $location.host() +':'+  $location.port() + "/";
        $scope.urlParameter = "";
        $scope.additionalParam = "";

        function getPagedItems() {
            $http.get($scope.currentHost + "items/" + $scope.urlParameter +
                "?page=" + $scope.currentPage + "&size=" + $scope.itemsPerPage +
                $scope.additionalParam)
                .then(function (response) {
                    $scope.pageSize = Math.ceil(response.data.totalElements / $scope.itemsPerPage) + 1;
                    $scope.items = response.data.content ? response.data.content : [];
                });
            $scope.$apply();
        }

        getPagedItems();

        $scope.updateItem = function (item) {
            $http.put($scope.currentHost + "items/" + item.id, item)
                .success(function (data, status, headers) {
                })
                .error(function (data, status, header) {
                });
        };

        $scope.deleteItem = function (item) {
            $http.delete($scope.currentHost + "items/" + item.id, item)
                .success(function (data, status, headers) {
                    $scope.items.splice($scope.items.indexOf(item), 1);
                })
                .error(function (data, status, header) {
                });
        };

        $scope.prevPage = function () {
            if ($scope.currentPage > 1) {
                $scope.currentPage--;
            }
            console.log($scope.currentPage)
            getPagedItems();
            $scope.$apply();
        };

        $scope.nextPage = function () {
            if ($scope.currentPage < $scope.pageSize - 1) {
                $scope.currentPage++;
            }
            console.log($scope.currentPage)
            getPagedItems();
            $scope.$apply();
        };

        $scope.setPage = function () {
            $scope.currentPage = this.n;
            console.log($scope.currentPage)
            getPagedItems();
            $scope.$apply();
        };

        $scope.addItem = function (businessStatus, description) {
            var newItem = new Item({
                businessStatus: businessStatus,
                description: description
            });
            $http.post($scope.currentHost + "items", newItem)
                .success(function (data, status, headers) {
                    $scope.items.push(data);
                })
                .error(function (data, status, header) {
                });
            $scope.$apply();
            $scope.businessStatus = "";
            $scope.description = "";
        };

        $scope.availableStatuses = [
            {name: 'IN_PROGRESS'},
            {name: 'DONE'},
            {name: 'CANCELLED'},
            {name: 'PAUSED'}
        ];

        $scope.getItemsByBusinessStatus = function(businessStatusElement) {
            $scope.currentPage = 1;
            $scope.urlParameter = "by-status/";
            $scope.additionalParam = "&businessStatus=" + businessStatusElement;
            getPagedItems();
        }

        $scope.getItemsByDescription = function(searchText) {
            $scope.currentPage = 1;
            $scope.urlParameter = "by-description/";
            $scope.additionalParam = "&descriptionPart=" + searchText;
            getPagedItems();
        }

    };

    AppController.$inject = ['$scope', 'Item', '$http', '$location'];
    angular.module("myApp.controllers").controller("AppController", AppController);
}(angular));