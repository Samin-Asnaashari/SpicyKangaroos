'use strict';

angular.module('appComponent.styleMenu').controller('styleMenuCtrl', function ($scope, $mdDialog, Upload,
                                                                               styleService, $window,
                                                                               uploadService, Notification) {

    var vm = this;

    /**
     * Style dialog methods (with own controller)
     */

    vm.showStyleDialog = function (sectionStyle, show) {
        $mdDialog.show({
            templateUrl: './components/style.menu/style.dialog.html',
            parent: angular.element(document.body),
            // targetEvent: event,
            clickOutsideToClose: false,
            locals: {style: sectionStyle, show: show}, // pass data to controller
            resolve: {
                imagesResolve: function (uploadService) {
                    return uploadService.findAllBackgrounds()
                        .then(function (response) {
                            return {images: response.data};
                        }, function (error) {
                            // TODO: Error
                            console.log(error);
                        });
                }
            },
            controllerAs: 'vmSectionStyle',
            controller: function ($scope, $mdDialog, $mdToast, style, show, imagesResolve) {
                /**
                 * Scope of the dialog
                 */
                var vm = this;
                vm.show = show;
                vm.style = angular.copy(style);
                var styleRestore = angular.copy(vm.style);

                vm.backgroundImages = imagesResolve.images;
                var chosenImage = null;

                // dummy text
                vm.dummyHeader = "Preview Text";
                vm.dummyParagraph = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
                    "This text will help you into styling the colors.";

                // padding options (User Interface)
                vm.paddingOptions = [0, 25, 50, 75, 100, 125, 150, 175, 200];

                /**
                 * Close the dialog (x)
                 */
                vm.closeDialog = function () {
                    $mdDialog.cancel();
                };

                /**
                 * Save the changes, and close the dialog.
                 */
                vm.saveChanges = function () {
                    if (chosenImage == null && show[4] == true) {
                        // TODO
                        alert("TODO..." + " You didn't do anything...")
                    }
                    else if (chosenImage != null && show[4] == true) {
                        uploadService.updateBackgroundImage(style.id, chosenImage)
                            .then(function (response) {
                                style.backgroundColor = response.data.backgroundColor;
                                style.backgroundImage = response.data.backgroundImage;

                                // Calculating height
                                // TODO: This is used in more places (template1), make a service out of it
                                if (response.data.imageHeight && response.data.imageWidth) {
                                    style.height = response.data.imageHeight / (response.data.imageWidth / $window.innerWidth);
                                }

                                $mdDialog.cancel();
                            }, function (error) {
                                // Restore values
                                style.paddingTop = styleRestore.paddingTop;
                                style.paddingBottom = styleRestore.paddingBottom;
                                style.backgroundColor = styleRestore.backgroundColor;
                                style.textColor = styleRestore.textColor;
                                style.backgroundImage = styleRestore.backgroundImage;
                                style.imageWidth = styleRestore.imageWidth;
                                style.imageHeight = styleRestore.imageHeight;

                                // TODO notify
                                //Notification.error("Something went wrong!");
                            });
                    }
                    else {
                        style.paddingTop = vm.style.paddingTop;
                        style.paddingBottom = vm.style.paddingBottom;
                        style.backgroundColor = vm.style.backgroundColor;
                        style.textColor = vm.style.textColor;

                        // Remove the background image properties (if exist)
                        style.backgroundImage = null;
                        style.imageWidth = null;
                        style.imageHeight = null;


                        styleService.updateStyle(style)
                            .then(function (response) {
                                // Success
                                style.height = null;
                                $mdDialog.cancel();

                                // TODO notify
                                //Notification.success("Saved");
                            }, function (error) {
                                // Restore values
                                style.paddingTop = styleRestore.paddingTop;
                                style.paddingBottom = styleRestore.paddingBottom;
                                style.backgroundColor = styleRestore.backgroundColor;
                                style.textColor = styleRestore.textColor;
                                style.backgroundImage = styleRestore.backgroundImage;
                                style.imageWidth = styleRestore.imageWidth;
                                style.imageHeight = styleRestore.imageHeight;

                                // TODO notify
                                //Notification.error("Something went wrong!");
                            });
                    }
                };

                /**
                 * Revert the changes back to when the dialog was opened.
                 */
                vm.revertChanges = function () {
                    vm.style.paddingTop = styleRestore.paddingTop;
                    vm.style.paddingBottom = styleRestore.paddingBottom;
                    vm.style.backgroundColor = styleRestore.backgroundColor;
                    vm.style.textColor = styleRestore.textColor;
                    vm.style.backgroundImage = styleRestore.backgroundImage;
                    vm.style.imageWidth = styleRestore.imageWidth;
                    vm.style.imageHeight = styleRestore.imageHeight;
                    $mdDialog.cancel();
                };

                /**
                 * Uploading an image as a background image. Call the RESTful API, and send the image to it.
                 * If successful, the dialog will be closed, and the uploaded background image will be shown.
                 */
                vm.uploadFile = function (file, errFiles) {
                    if (file) {
                        Upload.upload({
                            url: 'http://localhost:8090/uploads/backgrounds',
                            file: file,
                            data: {styleId: vm.style.id}
                        }).then(function (response) {
                            style.backgroundColor = response.data.backgroundColor;
                            style.backgroundImage = response.data.backgroundImage;

                            // Calculating height
                            // TODO: This is used in more places (template1), make a service out of it
                            if (response.data.imageHeight && response.data.imageWidth) {
                                style.height = response.data.imageHeight / (response.data.imageWidth / $window.innerWidth);
                            }

                            $mdDialog.cancel();
                        }, function (error) {
                            // TODO: Error
                            console.log(error);
                        });
                    }
                };

                vm.chooseImage = function (item) {
                    chosenImage = item;
                };

                $(function () {
                    $('div.product-chooser').not('.disabled').find('div.product-chooser-item').on('click', function () {
                        $(this).parent().parent().find('div.product-chooser-item').removeClass('selected');
                        $(this).addClass('selected');
                        $(this).find('input[type="radio"]').prop("checked", true);

                    });
                });
            }
        });
    };
});