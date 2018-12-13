'use strict';

/**
 * this service provides all the http requests to back-end related to section.
 */
angular.module('appServiceAPI').service('sectionService', function ($http) {

    var self = this;
    var http = 'http://';
    var localhost = "localhost";
    var secure = '/secure';
    var baseUrl = http + localhost + ':8090/sections';

    /**
     * Create a new section.
     * Returns the created section.
     * @param section; section to be created
     * @returns {*}
     */
    self.createSection = function (section) {
        return $http.post(baseUrl + secure, section);
    };

    /**
     * Returns a list of rows with the updated sequences.
     * @param sequence; sequence position and onwards to be updated
     * @param pageId; pageId of the page the row belongs to
     * @returns {*}
     */
    self.updateSequences = function (sequence, pageId) {
        return $http.put(baseUrl + secure + "/?sequence=" + sequence + "&page=" + pageId, '');
    };

    self.findOneSection = function (id) {
        return $http.get(baseUrl + "/" + id);
    };

    self.findAllPageSections = function (pageId) {
        return $http.get(baseUrl + '/pages/' + pageId);
    };

    self.updateSection = function (section) {
        return $http.put(baseUrl + secure, section);
    };

    self.swapSectionsSequence = function (sectionId1, sectionId2) {
        return $http.put(baseUrl + secure + "/" + sectionId1 + "/" + sectionId2);
    };

    self.deleteSection = function (id) {
        return $http.delete(baseUrl + secure + '/' + id);
    };

    /**
     * Deletes the row with the given id.
     * Returns a list of rows with the updated sequences.
     * @param id; id of the row to be deleted
     * @param pageId; pageId of the page the row belongs to
     * @returns {*}
     */
    self.deleteRow = function (id, pageId) {
        return $http.delete(baseUrl + secure + '/?row=' + id + "&page=" + pageId);
    };
});