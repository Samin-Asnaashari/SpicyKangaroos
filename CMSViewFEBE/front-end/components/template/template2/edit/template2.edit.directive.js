'use strict';

angular.module('appComponent.templateEdit').directive('template2', function () {
    return {
        restrict: 'E',
        templateUrl: './components/template/template2/edit/template2.edit.html',
        controller: 'template2Ctrl',
        controllerAs: 'vmTemplate2',
        scope: {
            row: '=',
            canMove: '='
        }
    };
});