'use strict';

angular.module('appComponent.dashboard').directive('menuItemCollection', function () {
    return {
        restrict: "E",
        replace: true,
        scope: {
            topMenuItems: '='
        },
        templateUrl: "./components/dashboard/menuItem.collection/menuItem.collection.template.html",
        controller: 'menuItemCtrl',
        controllerAs: 'vmMenuItem'
    }
});