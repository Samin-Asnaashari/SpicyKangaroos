'use strict';

/**
 * this service provides all the http requests to back-end related to style.
 */
angular.module('appServiceAPI').service('styleService', function ($http) {

    var self = this;
    var http = 'http://';
    var localhost = "localhost";
    var secure = '/secure';
    var baseUrl = http + localhost + ':8090/styles';

    self.findOneStyle = function (id) {
        return $http.get(baseUrl + '/' + id);
    };

    self.updateStyle = function (style) {
        return $http.put(baseUrl + secure, style);
    };
});