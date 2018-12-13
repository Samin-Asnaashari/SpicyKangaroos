'use strict';

angular.module('appComponent.templateEdit').controller('template1Ctrl', function ($scope, $window,
                                                                                  EventDefaultTemplate, EventResizeWindow) {

    var vm = this;
    vm.hover = false;
    vm.add = false;
    vm.hoverBlock = false;
    vm.defaultPadding = 50;
    vm.row = $scope.row;
    vm.showDefaultTemplate = false;
    vm.canMove = $scope.canMove;

    /**
     * Styling the template: Check out style.menu - style.directive for instructions
     */
    vm.styleOptions = [false, false, false, false, true];
    vm.styleOptionsBlock = [true, true, true, true, false];

    // TODO: This is being used in multiple places. Create a service/factory to reuse the function.
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
});