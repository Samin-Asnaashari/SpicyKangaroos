'use strict';

angular.module('appComponent.templateEdit').directive('template3', function () {
    return {
        restrict: 'E',
        templateUrl: './components/template/template3/edit/template3.edit.html',
        controller: 'template3Ctrl',
        controllerAs: 'vmTemplate3',
        scope: {
            row: '=',
            canMove: '='
        }
    };
});