'use strict';

angular.module('appComponent.pageEdit').controller('pageCtrl', function ($stateParams, $state, $scope,
                                                                         pageResolve, sectionService, Notification, templateService,
                                                                         EventSectionMenu, EventDefaultTemplate) {

    var vm = this;
    vm.add = false;
    vm.page = pageResolve.page;
    var canMove = {};

    vm.checkCanMove = function (index) {
        canMove.up = (index - 1) >= 0;
        canMove.down = (index + 1) < vm.page.rows.length;
        return canMove;
    };


    /**
     * Event listener - EventSectionMenu: Duplicate a section (row)
     */
    EventSectionMenu.subscribeOnDuplicate($scope, function (event, data) {

        var sectionToDuplicate = {};

        for (var i = 0; i < vm.page.rows.length; i++) {
            if (vm.page.rows[i].id == data.sectionId) {
                sectionToDuplicate = vm.page.rows[i];
                break;
            }
        }

        switch (sectionToDuplicate.identifier) {
            case "template-1":
                // sectionToDuplicate is template 1
                addTemplate1(sectionToDuplicate.sequence, sectionToDuplicate);
                break;
            case "template-2":
                // sectionToDuplicate is template 2
                addTemplate2(sectionToDuplicate.sequence, sectionToDuplicate);
                break;
            case "template-3":
                // sectionToDuplicate is template 3
                addTemplate3(sectionToDuplicate.sequence, sectionToDuplicate);
                break;
        }
    });

    /**
     * Event listener - EventSectionMenu: Delete a section (row)
     */
    EventSectionMenu.subscribeOnDelete($scope, function (event, data) {
        sectionService.deleteRow(data.id, vm.page.id)
            .then(function (response) {
                var updatedRows = response.data;

                angular.forEach(vm.page.rows, function (item) {
                    // Remove the row
                    if (item.id == data.id) {
                        var index = vm.page.rows.indexOf(item);
                        vm.page.rows.splice(index, 1);
                    }

                    // Update sequences
                    angular.forEach(updatedRows, function (row) {
                        if (item.id == row.id) {
                            item.sequence = row.sequence;
                        }
                    });
                });
                //Notification.success("Successfully removed");
            });
    });

    /**
     * Event listener - EventSectionMenu: Move up
     */
    EventSectionMenu.subscribeOnMoveUpOrDown($scope, function (event, data) {
        var section1 = {};
        for (var i = 0; i < vm.page.rows.length; i++) {
            if (vm.page.rows[i].id === data.id) {
                section1 = vm.page.rows[i];
                break;
            }
        }

        var index1 = vm.page.rows.indexOf(section1);
        var index2;
        if (data.direction == 'Up') {
            index2 = index1 - 1;
        } else {
            index2 = index1 + 1;
        }

        var section2 = vm.page.rows[index2];

        if (section2 != undefined) {
            sectionService.swapSectionsSequence(section1.id, section2.id)
                .then(function (response) {

                    var tempSequence = section1.sequence;
                    vm.page.rows[index1].sequence = section2.sequence;
                    vm.page.rows[index2].sequence = tempSequence;

                    var tempSection = vm.page.rows[index1];
                    vm.page.rows[index1] = vm.page.rows[index2];
                    vm.page.rows[index2] = tempSection;

                }, function (error) {
                    return error;
                });
        }
    });

    /**
     * Event listener: Add new template (from default-template)
     */
    EventDefaultTemplate.subscribeOnChoose($scope, function (event, data) {
        /**
         * Event : Hide the default template
         */
        EventDefaultTemplate.notifyOnHide(data.rowId);

        // Build the correct template
        switch (data.templateNr) {
            case 1:
                // Build template 1
                addTemplate1(data.rowSequence, null);
                break;
            case 2:
                // Build template 2
                addTemplate2(data.rowSequence, null);
                break;
            case 3:
                // Build template 3
                addTemplate3(data.rowSequence, null);
                break;
        }
    });

    /**
     * Listeners
     */
    vm.showDefaultTemplate = false;

    /**
     * Event listener: (+) was clicked
     */
    EventDefaultTemplate.subscribeOnOpen($scope, function (event, data) {
        if (data.rowId == 0) {
            vm.showDefaultTemplate = true;
        }
    });

    /**
     * Event listener: Default template should be hidden
     */
    EventDefaultTemplate.subscribeOnHide($scope, function (event, data) {
        if (data.rowId == 0) {
            vm.showDefaultTemplate = false;
        }
    });

    /**
     * Event listener: Default template should be closed
     */
    EventDefaultTemplate.subscribeOnClose($scope, function (event, data) {
        if (data.rowId == 0) {
            vm.showDefaultTemplate = false;
        }
    });

    /**
     * Create/build template 1
     */
    var addTemplate1 = function (sequence, sectionToDuplicate) {
        var updatedRows = [];
        var row = {};

        sectionService.updateSequences(sequence, vm.page.id)
            .then(function (sectionResponse) {
                updatedRows = sectionResponse.data;
                return templateService.buildTemplate1(vm.page.id, sequence + 1, sectionToDuplicate);
            })
            .then(function (buildTemplateResponse) {
                row = buildTemplateResponse;

                // Update the current array of rows
                buildUpdateHelper(updatedRows, sequence, row);

                //Notification.success("Successfully added");
            }, function (error) {
                //Notification.error("Something went wrong!");
            });
    };

    /**
     * Create/build template 2
     */
    var addTemplate2 = function (sequence, sectionToDuplicate) {
        var updatedRows = [];
        var row = {};

        sectionService.updateSequences(sequence, vm.page.id)
            .then(function (sectionResponse) {
                updatedRows = sectionResponse.data;
                return templateService.buildTemplate2(vm.page.id, sequence + 1, sectionToDuplicate);
            })
            .then(function (buildTemplateResponse) {
                row = buildTemplateResponse;

                // Update the current array of rows
                buildUpdateHelper(updatedRows, sequence, row);

                //Notification.success("Successfully added");
            }, function (error) {
                //Notification.error("Something went wrong!");
            });
    };

    /**
     * Create/build template 3
     */
    var addTemplate3 = function (sequence, sectionToDuplicate) {
        var updatedRows = [];
        var row = {};

        sectionService.updateSequences(sequence, vm.page.id)
            .then(function (sectionResponse) {
                updatedRows = sectionResponse.data;
                return templateService.buildTemplate3(vm.page.id, sequence + 1, sectionToDuplicate);
            })
            .then(function (buildTemplateResponse) {
                row = buildTemplateResponse;

                // Update the current array of rows
                buildUpdateHelper(updatedRows, sequence, row);

                //Notification.success("Successfully added");
            }, function (error) {
                //Notification.error("Something went wrong!");
            });
    };

    /**
     * Helper used to build the templates
     */
    var buildUpdateHelper = function (updatedRows, sequence, row) {
        var index = -1;

        angular.forEach(vm.page.rows, function (item) {
            // Get the index position of where to show the new row
            if (item.sequence == sequence) {
                index = vm.page.rows.indexOf(item);
            }
        });

        // Update sequences
        angular.forEach(vm.page.rows, function (item) {
            angular.forEach(updatedRows, function (row) {
                if (item.id == row.id) {
                    item.sequence = row.sequence;
                }
            });
        });

        // Insert new row into the list of rows
        vm.page.rows.splice(index + 1, 0, row);
    }
});