'use strict';

/**
 * this module provides all the events related to menuItems.
 * for each events there is a subscription method and notifyOn method.
 */
angular.module('appServiceEvent').factory('EventMenuItemMenu', function ($rootScope) {
    return {

        /*************************************************************
         * Event: Deleting a menu-item, send it to trash
         *************************************************************/

        subscribeOnMenuDelete: function (scope, callback) {
            var handler = $rootScope.$on('EventMenuItemMenu::onDelete', callback);
            scope.$on('$destroy', handler);
        },

        notifyOnMenuDelete: function (menuItem) {
            $rootScope.$emit('EventMenuItemMenu::onDelete', {
                menuItem: menuItem
            });
        },

        /*************************************************************
         * Event: Change draft status
         *************************************************************/

        subscribeOnStatusChange: function (scope, callback) {
            var handler = $rootScope.$on('EventMenuItemMenu::onStatusChange', callback);
            scope.$on('$destroy', handler);
        },

        notifyOnStatusChange: function (menuItem, draftStatus) {
            $rootScope.$emit('EventMenuItemMenu::onStatusChange', {
                menuItem: menuItem,
                draftStatus: draftStatus
            });
        },

        /*************************************************************
         * Event: Changing Sequence (Drag and Drop)
         *************************************************************/

        subscribeOnDragAndDrop: function (scope, callback) {
            var handler = $rootScope.$on('EventMenuItemMenu::onSequenceChange', callback);
            scope.$on('$destroy', handler);
        },

        notifyOnDragAndDrop: function (elIndex, dropIndex, sourceContainerIndex, targetContainerIndex, isTop) {
            $rootScope.$emit('EventMenuItemMenu::onSequenceChange', {
                elIndex: elIndex,
                dropIndex: dropIndex,
                sourceContainerIndex: sourceContainerIndex,
                targetContainerIndex: targetContainerIndex,
                isTop: isTop
            });
        },

        /*************************************************************
         * Event: Update nested containers
         *************************************************************/

        subscribeOnUpdateNestedContainers: function (scope, callback) {
            var handler = $rootScope.$on('EventMenuItemMenu::onUpdateNestedContainers', callback);
            scope.$on('$destroy', handler);
        },

        notifyOnUpdateNestedContainers: function () {
            $rootScope.$emit('EventMenuItemMenu::onUpdateNestedContainers', {});
        },

        /*************************************************************
         * Event: restore a menu-item from trash
         *************************************************************/

        subscribeOnRestoreMenuItemFromTrash: function (scope, callback) {
            var handler = $rootScope.$on('EventMenuItemMenu::onRestoreMenuItemFromTrash', callback);
            scope.$on('$destroy', handler);
        },

        notifyOnRestoreMenuItemFromTrash: function (menuItem) {
            $rootScope.$emit('EventMenuItemMenu::onRestoreMenuItemFromTrash', {
                menuItem: menuItem
            });
        },

        /*************************************************************
         * Event: Deleting a menu-item forever
         *************************************************************/

        subscribeOnDeleteForever: function (scope, callback) {
            var handler = $rootScope.$on('EventMenuItemMenu::onDeleteForever', callback);
            scope.$on('$destroy', handler);
        },

        notifyOnDeleteForever: function (menuItem) {
            $rootScope.$emit('EventMenuItemMenu::onDeleteForever', {
                menuItem: menuItem
            });
        }

    };
});