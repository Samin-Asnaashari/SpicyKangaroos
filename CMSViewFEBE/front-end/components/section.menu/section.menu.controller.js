'use strict';

angular.module('appComponent.sectionMenu').controller('sectionMenuCtrl', function ($scope, EventSectionMenu) {

    /**
     * Fields
     */
    var vm = this;
    var sectionId = $scope.sectionId;
    vm.sectionStyle = $scope.sectionStyle;
    vm.styleOptions = $scope.styleShow;
    vm.canMove = $scope.canMove;

    /**
     * Open menu with options
     */

    vm.openMenu = function ($mdOpenMenu, event) {
        $mdOpenMenu(event);
    };

    /**
     * Choose to duplicate a section. Event is triggered, with the sectionId to be duplicated.
     */

    vm.duplicateSection = function () {
        EventSectionMenu.notifyOnDuplicate(sectionId);
    };

    /**
     * Choose to remove a section. Event is triggered, with the sectionId to be removed.
     */

    vm.removeSection = function () {
        EventSectionMenu.notifyOnDelete(sectionId);
    };

    /**
     * Choose to move section up or down. Event is triggered, with the sectionId to be moved.
     */

    vm.moveTheSectionUpOrDown = function (direction) {
        EventSectionMenu.notifyOnMoveUpOrDown(sectionId, direction);
    };

});