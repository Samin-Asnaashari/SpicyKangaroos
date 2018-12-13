'use strict';

/**
 * scope - styleShow structure: Array of booleans. These booleans will determine which style is shown
 * to be edited or not.
 * Sequence in array:
 * [1] padding
 * [2] preview colors (background + font)
 * [3] background color
 * [4] font color
 * [5] background image
 */

angular.module('appComponent.styleMenu').directive('styleMenuClick', function () {
    return {
        restrict: 'A',
        scope: {
            sectionStyle: '=',
            styleShow: '='
        },
        controller: 'styleMenuCtrl',
        controllerAs: 'vmStyleMenu',
        link: function (scope, element, attrs) {
            element.on('click', function () {
                scope.vmStyleMenu.showStyleDialog(scope.sectionStyle, scope.styleShow);
            });
        }
    }
});