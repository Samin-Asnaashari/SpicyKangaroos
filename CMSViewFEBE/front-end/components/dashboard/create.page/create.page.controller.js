'use strict';

angular.module('appComponent.dashboard').controller('createPageCtrl', function ($state, $mdDialog, $scope, pageService) {

    var vm = this;
    // vm.pageTitle = null;
    vm.menuItemName = null;

    vm.showCreatePageDialog = function (parentId) {
        $mdDialog.show({
            templateUrl: './components/dashboard/create.page/create.page.dialog.html',
            parent: angular.element(document.body),
            // targetEvent: event,
            clickOutsideToClose: false,
            locals: {parentId: parentId},
            controller: function () {

                var vm = this;
                vm.conflictErrorMessage = false;

                vm.refresh = function () {
                    vm.conflictErrorMessage = false;
                };

                vm.dismiss = function () {
                    $mdDialog.cancel();
                };

                vm.save = function (menuItemName) {
                    if (menuItemName != undefined) {
                        pageService.createPageWithMenuItem(menuItemName, parentId)
                            .then(function (response) {
                                var page = response.data;
                                $mdDialog.cancel();
                                $state.go('page', {id: page.menuItem.id});
                            }, function (error) {
                                if (error.status === 409) {
                                    // $scope.form.menuItemName.$error.conflictErrorMessage = true;
                                    vm.conflictErrorMessage = true;
                                } else {
                                    alert(error.status);
                                }
                            });
                    }
                };
            },
            controllerAs: 'vmCreatePageDialog'
        });
    }
});