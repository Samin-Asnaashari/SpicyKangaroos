'use strict';

angular.module('appComponent.dashboard').controller('menuItemMenuCtrl', function ($state, $scope, $mdDialog, EventMenuItemMenu) {

    var vm = this;
    vm.maxLevel = 1; //TODO check

    vm.openMenu = function ($mdOpenMenu, ev) {
        $mdOpenMenu(ev);
    };

    //TODO remove
    vm.gotToEditPage = function (id) {
        $state.go('page', {id: id});
    };

    vm.showDeletePageConfirmationDialog = function (menuItem) {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: './components/dashboard/menuItem.menu/delete.confirmation/delete.confirmation.template.html',
            clickOutsideToClose: true,
            controller: function ($mdDialog) {

                var vm = this;

                vm.cancel = function () {
                    $mdDialog.cancel();
                };

                vm.deletePageWithUpdateSequence = function () {
                    EventMenuItemMenu.notifyOnMenuDelete(menuItem);
                    $mdDialog.cancel();
                };
            },
            controllerAs: 'vmConfirmDelete'
        });
    };
});



