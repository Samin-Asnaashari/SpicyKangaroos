'use strict';

angular.module('appComponent.addTemplateButton').directive('addTemplate', function (EventDefaultTemplate) {
    return {
        restrict: 'E',
        templateUrl: './components/add.template.button/add.template.view.html',
        link: function (scope, element, attrs) {
            element.on('click', function () {
                var id = Number(attrs.sectionId);
                EventDefaultTemplate.notifyOnOpen(id);
            });
        }
    };
});