'use strict';

angular.module('appComponent.templateEdit').controller('templateDefaultCtrl', function ($scope, $mdDialog, EventDefaultTemplate) {

    var vm = this;
    vm.sequence = Number($scope.sequence);
    vm.rowId = $scope.rowId;

    vm.hoverZoom1 = false;
    vm.image1 = './asset/img-template/template01.jpg';

    vm.hoverZoom2 = false;
    vm.image2 = './asset/img-template/template02.jpg';

    vm.hoverZoom3 = false;
    vm.image3 = './asset/img-template/template03.jpg';

    // TODO: less programming and less repeating code; create an extra component (directive) for the template blocks.

    /**
     * First template
     */
    vm.firstTemplate = $scope.firstTemplate;

    vm.getFirst = function () {
        return !vm.firstTemplate;
    };

    /**
     * Event: User closes (x) the template
     */
    vm.closeTemplate = function () {
        EventDefaultTemplate.notifyOnClose(vm.rowId);
    };

    vm.openImage = function (image) {
        $mdDialog.show({
            templateUrl: './components/template/template.default/template.image.zoom.html',
            parent: angular.element(document.body),
            clickOutsideToClose: true,
            locals: {image: image}, // pass data to controller
            controllerAs: 'vmTemplateImageZoom',
            controller: function menuTabDialogCtrl($scope, $mdDialog, $mdToast, image) {
                /**
                 * Scope of the dialog
                 */
                var vm = this;
                vm.image = image;

                /**
                 * Close the dialog (x)
                 */
                vm.closeDialog = function () {
                    $mdDialog.cancel();
                };
            }
        });
    };

    /**
     * Triggers: User chooses a template
     */

    vm.addTemplate1 = function () {
        EventDefaultTemplate.notifyOnChoose(1, vm.sequence, vm.rowId);
    };

    vm.addTemplate2 = function () {
        EventDefaultTemplate.notifyOnChoose(2, vm.sequence, vm.rowId);
    };

    vm.addTemplate3 = function () {
        EventDefaultTemplate.notifyOnChoose(3, vm.sequence, vm.rowId);
    };
});