// TODO use this to loop through nested menuItem list with unknown depth
// 'use strict';
//
// angular.module('appComponent.dashboard').directive('member', function ($compile) {
//     return {
//         restrict: "E",
//         replace: true,
//         scope: {
//             member: '='
//         },
//         templateUrl: "./components/dashboard/member(temp)/member.template.html",
//         link: function (scope, element) {
//             if (angular.isArray(scope.member.children)) {
//                 var newElement = angular.element("<menuItem.collection menuItem.collection='member.children'></menuItem.collection>");
//                 element.append(newElement);
//                 $compile(newElement)(scope);
//             }
//         },
//         controller: 'memberCtrl',
//         controllerAs: 'vmMember'
//     }
// });