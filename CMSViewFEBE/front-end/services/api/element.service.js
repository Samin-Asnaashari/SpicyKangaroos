'use strict';

/**
 * this service provides all http requests to back-end related to element.
 */
angular.module('appServiceAPI').service('elementService', function ($http) {

    var self = this;
    var http = 'http://';
    var localhost = "localhost";
    var secure = '/secure';
    var baseUrl = http + localhost + ':8090/elements';

    self.createElement = function (element) {
        return $http.post(baseUrl + secure, element);
    };

    self.findOneElement = function (id) {
        return $http.get(baseUrl + '/' + id);
    };

    self.updateElement = function (element) {
        return $http.put(baseUrl + secure, element);
    };

    self.deleteElement = function (id) {
        return $http.delete(baseUrl + secure + '/' + id);
    };
});