'use strict';

/**
 * this service provides all the http requests to back-end related to page.
 * in page overview section each page is tightly coupled with its menuItem.
 * hence in methods the existence of both is needed to manage the connection between menuItem and its related page.
 */
angular.module('appServiceAPI').service('pageService', function ($http) {

    var self = this;
    var http = 'http://';
    var localhost = "localhost";
    var secure = '/secure';
    var baseUrl = http + localhost + ':8090/pages';

    self.createPage = function (page) {
        return $http.post(baseUrl + secure, page);
    };

    self.createPageWithMenuItem = function (menuItemName, parentId) {
            var page = {};
            // page.title = pageTitle;
            return $http.post(baseUrl + secure + '/?menuItemName=' + menuItemName + '&parentId=' + parentId, page);
    };

    self.findOnePage = function (id) {
        return $http.get(baseUrl + '/' + id);
    };

    self.findOnePageForMenuItem = function (menuItemId) {
        return $http.get(baseUrl + '/?menu=' + menuItemId);
    };

    self.findOnePageByMenuItemUrl = function (menuItemUrl) {
        return $http.get(baseUrl + '/url/' + menuItemUrl);
    };

    self.findAllPages = function () {
        return $http.get(baseUrl);
    };

    self.updatePage = function (page) {
        return $http.put(baseUrl + secure, page);
    };

    self.deletePage = function (id) {
        return $http.delete(baseUrl + secure + '/' + id);
    };

    self.deletePageByMenuItemId = function (menuItemId) {
        return $http.delete(baseUrl + secure + '/?menu=' + menuItemId);
    };
});