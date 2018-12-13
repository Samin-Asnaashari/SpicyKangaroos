'use strict';

angular.module('appComponent.templateView').directive('template3View', function () {
    return {
        restrict: 'E',
        templateUrl: './components/template/template3/view/template3.view.html',
        scope: {
            row:'='
        },
        controller: function ($scope) {
            var vm = this;

            vm.row = $scope.row;
            vm.defaultPadding = 50;

            /**
             * Returns the element in the block with the given identifier.
             */
            vm.getElement = function (identifier) {
                var element = {};

                for (var i = 0; i < vm.row.blocks.length; i++) {
                    if (vm.row.blocks[i].identifier == identifier) {
                        element = vm.row.blocks[i].elements[0];
                        break;
                    }
                }
                return element.content;
            };
        },
        controllerAs: 'vmTemplate3View'
    }
});


