'use strict';

angular.module('appComponent.pageView').controller('pageViewCtrl', function ($scope, pageResolve) {

    var vm = this;
    vm.page = pageResolve.page;
});