'use strict';

angular.module('appComponent.templateEdit').directive('template1', function () {
    return {
        restrict: 'E',
        templateUrl: './components/template/template1/edit/template1.edit.html',
        controller: 'template1Ctrl',
        controllerAs: 'vmTemplate1',
        scope: {
            row: '=',
            canMove: '='
        }
    };
});