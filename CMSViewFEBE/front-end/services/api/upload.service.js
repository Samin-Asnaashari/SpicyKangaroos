'use strict';

/**
 * this service provides all the http requests to back-end related to upload image.
 */
angular.module('appServiceAPI').service('uploadService', function ($http) {

    var self = this;
    var http = 'http://';
    var localhost = "localhost";
    var secure = '/secure';
    var baseUrl = http + localhost + ':8090/uploads';

    self.findAllBackgrounds = function () {
        return $http.get(baseUrl + '/backgrounds');
    };

    self.updateBackgroundImage = function (styleId, image) {
        return $http.put(baseUrl + secure + '/?id=' + styleId, image);
    }
});