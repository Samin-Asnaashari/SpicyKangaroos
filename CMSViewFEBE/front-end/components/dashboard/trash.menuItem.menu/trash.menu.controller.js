'use strict';

angular.module('appComponent.dashboard').controller('trashMenuCtrl', function ($state, $scope, $mdDialog, EventMenuItemMenu) {

    var vm = this;

    vm.openMenu = function ($mdOpenMenu, ev) {
        $mdOpenMenu(ev);
    };

    vm.restoreFromTrash = function (menuItem) {
        EventMenuItemMenu.notifyOnRestoreMenuItemFromTrash(menuItem);
    };

    vm.deleteForever = function (menuItem) {
        EventMenuItemMenu.notifyOnDeleteForever(menuItem);
    };
});



