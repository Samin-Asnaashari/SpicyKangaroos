'use strict';

angular.module('appComponent.dashboard').directive('menuItemMenu', function () {
    return {
        restrict: "E",
        replace: true,
        scope: {
            menuItem: '=',
            form:'='
        },
        templateUrl: "./components/dashboard/menuItem.menu/menuItem.menu.template.html",
        controller: 'menuItemMenuCtrl',
        controllerAs: 'vmMenuItemMenu'
    }
});