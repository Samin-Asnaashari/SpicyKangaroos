'use strict';

/**
 * this service provides all the http requests to back-end related to block.
 */
angular.module('appServiceAPI').service('blockService', function ($http) {

    var self = this;
    var http = 'http://';
    var localhost = "localhost";
    var secure = '/secure';
    var baseUrl = http + localhost + ':8090/blocks';

    self.createBlock = function (block) {
        return $http.post(baseUrl + secure, block);
    };

    self.findOneBlock = function (id) {
        return $http.get(baseUrl + '/' + id);
    };

    self.updateBlock = function (block) {
        return $http.put(baseUrl + secure, block);
    };

    self.deleteBlock = function (id) {
        return $http.delete(baseUrl + secure + '/' + id);
    };
});