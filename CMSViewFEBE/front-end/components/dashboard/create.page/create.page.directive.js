'use strict';

angular.module('appComponent.dashboard').directive('createPageClick', function () {
        return {
            restrict: "A",
            scope: {
                parentId: '='
            },
            controller: 'createPageCtrl',
            controllerAs: 'vmCreatePage',
            link: function (scope, element) {
                element.on('click', function () {
                    scope.vmCreatePage.showCreatePageDialog(scope.parentId);
                })
            }
        }
    }
);