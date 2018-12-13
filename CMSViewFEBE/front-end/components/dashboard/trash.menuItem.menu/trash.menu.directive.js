'use strict';

angular.module('appComponent.dashboard').directive('trashMenu', function () {
    return {
        restrict: "E",
        replace: true,
        scope: {
            menuItem: '='
        },
        templateUrl: "./components/dashboard/trash.menuItem.menu/trash.menu.template.html",
        controller: 'trashMenuCtrl',
        controllerAs: 'vmTrashMenu'
    }
});