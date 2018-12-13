'use strict';

angular.module('appComponent.sectionMenu').directive('sectionMenu', function () {
    return {
        restrict: 'E',
        templateUrl: './components/section.menu/section.menu.view.html',
        controller: 'sectionMenuCtrl',
        controllerAs: 'vmSectionMenu',
        scope: {
            sectionId: '@',
            sectionStyle: '=',
            styleShow: '=',
            canMove: '='
        }
    };
});