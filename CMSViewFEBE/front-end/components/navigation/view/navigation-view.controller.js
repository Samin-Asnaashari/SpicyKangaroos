'use strict';

angular.module('appComponent.navigationView').controller('navigationViewCtrl', function ($stateParams, $state, $scope,
                                                                                         menuItemResolve) {

    var vm = this;
    vm.navigation = menuItemResolve.topMenuItems;
    vm.activeMenuId = null;

    // Active menu if there is a $stateParams.url present
    /**
     * IFFE - Get active menu the first time
     */
    (function () {
        for (var i = 0; i < vm.navigation.length; i++) {
            if (vm.navigation[i].url == $state.$current.locals.globals.$stateParams.url) {
                vm.activeMenuId = vm.navigation[i].id;
                break;
            }

            // Check the children
            for (var j = 0; j < vm.navigation[i].children.length; j++) {
                if (vm.navigation[i].children[j].url == $state.$current.locals.globals.$stateParams.url) {
                    vm.activeMenuId = vm.navigation[i].children[j].id;
                    break;
                }
            }
        }
    })();

    // Go to first state if there is no $stateParams.url present
    if (vm.activeMenuId == null) {
        $state.go('live.content', {
            id: vm.navigation[0].id,
            url: vm.navigation[0].url
        });
        vm.activeMenuId = vm.navigation[0].id;
    }

    vm.menuOpen = [];
    vm.xsNavOpen = false;

    /**
     * IFFE - Create mobile menu interaction (open/close variable)
     */
    (function () {
        for (var i = 0; i < vm.navigation.length; i++) {
            vm.menuOpen.push(false);
        }
    })();

    vm.logoSelect = function () {
        vm.activeMenuId = vm.navigation[0].id;

        $state.go('live.content', {
            id: vm.navigation[0].id,
            url: vm.navigation[0].url
        });
    };

    vm.menuSelect = function (menu) {
        // change active menu
        vm.activeMenuId = menu.id;
        $state.go('live.content', {
            id: menu.id,
            url: menu.url
        });

        // close all opened menu's
        for (var i = 0; i < vm.menuOpen.length; i++) {
            vm.menuOpen[i] = false;
        }
    };
});