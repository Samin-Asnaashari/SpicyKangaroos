'use strict';

/**
 * Route configuration
 */
angular.module('appView').config(function ($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/');

    $stateProvider
        .state('live', {
            url: '/',
            views: {
                'header': {
                    templateUrl: './components/navigation/view/navigation-view.html',
                    controller: 'navigationViewCtrl as vmNavView',
                    resolve: {
                        menuItemResolve: function (menuItemService) {
                            return menuItemService.findAllTopLiveMenuItems()
                                .then(function (response) {
                                    return {topMenuItems: response.data};
                                }, function (error) {
                                    console.log("error: " + error.data.message);
                                    // TODO go to 404 page
                                });
                        }
                    }
                },
                'content': {
                    templateUrl: './components/page/view/page.view.html'
                },
                'footer': {}
            }
        })

        .state('live.content', {
            url: ':url',
            views: {
                'content@': {
                    templateUrl: './components/page/view/page.view.html',
                    controller: 'pageViewCtrl as vmPageView',
                    resolve: {
                        pageResolve: function ($stateParams, pageService) {
                            return pageService.findOnePageByMenuItemUrl($stateParams.url)
                                .then(function (response) {
                                    return {page: response.data};
                                }, function (error) {
                                    console.log("error: " + error.data.message);
                                    // TODO go to 404 page
                                });
                        }
                    }
                }
            }
        });
});