'use strict';

angular.module('appComponent.dashboard').directive('trashMenuItemCollection', function () {
    return {
        restrict: "E",
        replace: true,
        scope: {
            trashMenuItems: '='
        },
        templateUrl: "./components/dashboard/trash.collection/trash.collection.template.html"
    }
});