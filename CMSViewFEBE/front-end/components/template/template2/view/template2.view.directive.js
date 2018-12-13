'use strict';

angular.module('appComponent.templateView').directive('template2View', function () {
    return {
        restrict: 'E',
        templateUrl: './components/template/template2/view/template2.view.html',
        scope: {
            row:'='
        },
        controller: function ($scope) {
            var vm = this;

            vm.row = $scope.row;
            vm.defaultPadding = 50;
        },
        controllerAs: 'vmTemplate2View'
    }
});


