'use strict';

/**
 * this module provides all the events related to DefaultTemplate.
 * for each events there is a subscription method and notifyOn method.
 */
angular.module('appServiceEvent').factory('EventDefaultTemplate', function ($rootScope) {
    return {

        /*************************************************************
         * Event: Choosing a template
         *************************************************************/

        subscribeOnChoose: function (scope, callback) {
            var handler = $rootScope.$on('EventDefaultTemplate::onChoose', callback);
            scope.$on('$destroy', handler);
        },

        notifyOnChoose: function (templateNr, rowSequence, rowId) {
            $rootScope.$emit('EventDefaultTemplate::onChoose', {
                templateNr: Number(templateNr),
                rowSequence: Number(rowSequence),
                rowId: Number(rowId)
            });
        },

        /*************************************************************
         * Event: Closing a template
         *************************************************************/

        subscribeOnClose: function (scope, callback) {
            var handler = $rootScope.$on('EventDefaultTemplate::onClose', callback);
            scope.$on('$destroy', handler);
        },

        notifyOnClose: function (rowId) {
            $rootScope.$emit('EventDefaultTemplate::onClose', {
                rowId: Number(rowId)
            });
        },

        /*************************************************************
         * Event: Hiding a template
         *************************************************************/

        subscribeOnHide: function (scope, callback) {
            var handler = $rootScope.$on('EventDefaultTemplate::onHide', callback);
            scope.$on('$destroy', handler);
        },

        notifyOnHide: function (rowId) {
            $rootScope.$emit('EventDefaultTemplate::onHide', {
                rowId: Number(rowId)
            });
        },

        /*************************************************************
         * Event: Click (+) open a template
         *************************************************************/

        subscribeOnOpen: function (scope, callback) {
            var handler = $rootScope.$on('EventDefaultTemplate::onOpen', callback);
            scope.$on('$destroy', handler);
        },

        notifyOnOpen: function (rowId) {
            $rootScope.$emit('EventDefaultTemplate::onOpen', {
                rowId: Number(rowId)
            });
        }
    };
});