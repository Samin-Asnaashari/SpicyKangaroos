'use strict';

angular.module('appComponent.templateView').directive('template1View', function (EventResizeWindow) {
    return {
        restrict: 'E',
        templateUrl: './components/template/template1/view/template1.view.html',
        scope: {
            row: '='
        },
        controller: function ($scope, $window) {
            var vm = this;

            vm.row = $scope.row;
            vm.defaultPadding = 50;

            // TODO: This is being used in multiple places. Create a service/factory to reuse the function
            var calculateHeight = function () {
                var mobile = 768;
                // Mobile view
                if ($window.innerWidth < mobile) {
                    vm.row.style.height = vm.row.style.imageHeight;
                }
                // Non-mobile
                else if (vm.row.style.imageHeight && vm.row.style.imageWidth) {
                    vm.row.style.height = vm.row.style.imageHeight / (vm.row.style.imageWidth / $window.innerWidth);
                }
            };
            calculateHeight();

            // Event resize -- calculate height
            EventResizeWindow.subscribeOnResize($scope, function (event) {
                calculateHeight();
                // TODO: Not sure if $apply is the best practice
                $scope.$apply();
            });
        },
        controllerAs: 'vmTemplate1View'
    }
});


