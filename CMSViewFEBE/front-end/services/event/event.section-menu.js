'use strict';

/**
 * this module provides all the events related to sections.
 * for each events there is a subscription method and notifyOn method.
 */
angular.module('appServiceEvent').factory('EventSectionMenu', function ($rootScope) {
    return {

        /*************************************************************
         * Event: Moving a section up or down
         *************************************************************/

        subscribeOnMoveUpOrDown: function (scope, callback) {
            var handler = $rootScope.$on('EventSectionMenu::onMoveUpOrDown', callback);
            scope.$on('$destroy', handler);
        },

        notifyOnMoveUpOrDown: function (id, direction) {
            $rootScope.$emit('EventSectionMenu::onMoveUpOrDown', {
                id: Number(id),
                direction: direction
            });
        },

        /*************************************************************
         * Event: Duplicating a section
         *************************************************************/

        subscribeOnDuplicate: function (scope, callback) {
            var handler = $rootScope.$on('EventSectionMenu::onDuplicate', callback);
            scope.$on('$destroy', handler);
        },

        notifyOnDuplicate: function (sectionId) {
            $rootScope.$emit('EventSectionMenu::onDuplicate', {sectionId: Number(sectionId)});
        },

        /*************************************************************
         * Event: Deleting a section
         *************************************************************/

        subscribeOnDelete: function (scope, callback) {
            var handler = $rootScope.$on('EventSectionMenu::onDelete', callback);
            scope.$on('$destroy', handler);
        },

        notifyOnDelete: function (id) {
            $rootScope.$emit('EventSectionMenu::onDelete', {id: Number(id)});
        }
    };
});