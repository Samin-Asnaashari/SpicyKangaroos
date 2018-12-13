/**
 * Route configuration
 */
angular.module('appAdmin').config(function ($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/dashboard');
    $stateProvider
        .state('dashboard', {
            url: '/dashboard',
            templateUrl: './components/dashboard/dashboard.html',
            controller: 'dashboardCtrl as vmDashboard',
            resolve: {
                menuItemResolve: function (menuItemService) {
                    var topMenuItems = [];
                    return menuItemService.findAllTopMenuItems()
                        .then(function (response) {
                            angular.forEach(response.data, function (menu) {
                                topMenuItems.push(menu);
                            });
                            return {topMenuItems: topMenuItems};
                        }, function (error) {
                            // TODO go to error page 404
                            console.log("error: " + error.data.status + ", " + error.data.message);
                        });
                },
                trashMenuItemResolve: function (menuItemService) {
                    var trashMenuItems = [];
                    return menuItemService.findAllTrashTopMenuItems()
                        .then(function (response) {
                            angular.forEach(response.data, function (menu) {
                                trashMenuItems.push(menu);
                            });
                            return {trashMenuItems: trashMenuItems};
                        }, function (error) {
                            console.log("error: " + error.data.status + ", " + error.data.message);
                        });
                }
            }
        })

        .state('page', {
            url: '/page/menu?id',
            templateUrl: './components/page/edit/page.edit.html',
            controller: 'pageCtrl as vmPage',
            resolve: {
                pageResolve: function ($state, $stateParams, pageService) {
                    return pageService.findOnePageForMenuItem($stateParams.id)
                        .then(function (response) {
                            return {page: response.data};
                        }, function (error) {
                            $state.go('dashboard');
                        });
                }
            }
        })
    ;
});





