'use strict';

angular.module('appComponent.templateEdit').controller('template3Ctrl', function ($scope, EventDefaultTemplate) {

    var vm = this;
    vm.hover = false;
    vm.add = false;
    vm.defaultPadding = 50;
    vm.row = $scope.row;
    vm.showDefaultTemplate = false;
    vm.canMove = $scope.canMove;

    /**
     * Styling the template: Check out style.menu - style.directive for instructions
     */
    vm.styleOptions = [true, true, true, true, false];

    /**
     * Event listener: (+) was clicked
     */
    EventDefaultTemplate.subscribeOnOpen($scope, function (event, data) {
        if (data.rowId == vm.row.id) {
            vm.showDefaultTemplate = true;
        }
    });

    /**
     * Event listener: Default template should be hidden
     */
    EventDefaultTemplate.subscribeOnHide($scope, function (event, data) {
        if (data.rowId == vm.row.id) {
            vm.showDefaultTemplate = false;
        }
    });

    /**
     * Event listener: Default template should be closed
     */
    EventDefaultTemplate.subscribeOnClose($scope, function (event, data) {
        if (data.rowId == vm.row.id) {
            vm.showDefaultTemplate = false;
        }
    });

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
        return element;
    };
});