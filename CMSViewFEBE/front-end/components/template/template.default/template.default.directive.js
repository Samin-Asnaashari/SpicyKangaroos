'use strict';

angular.module('appComponent.templateEdit').directive('templateDefault', function () {
    return {
        restrict: 'E',
        templateUrl: './components/template/template.default/template.default.html',
        controller: 'templateDefaultCtrl',
        controllerAs: 'vmTemplateDefault',
        scope: {
            sequence: "@",
            rowId: '@',
            templateVisible: '=',
            firstTemplate: '='
        }
    };
});