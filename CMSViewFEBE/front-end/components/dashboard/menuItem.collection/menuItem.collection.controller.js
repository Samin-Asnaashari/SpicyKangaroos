'use strict';

angular.module('appComponent.dashboard').controller('menuItemCtrl', function ($scope, menuItemService, EventMenuItemMenu, $element, $state, dragularService, $timeout) {

    var vm = this;
    var container = {};
    var parentContainers = [];
    var nestedContainers = [];
    var parentContainerModel = $scope.topMenuItems;
    var childContainerModel = [];

    (function () {
        var parent = parentContainerModel;
        for (var i = 0; i < parent.length; i++) {
            childContainerModel.push(parent[i].children);
        }
    })();

    var parentContainerOption = {
        accepts: function (el, target, source, sibling) {
            return true;
        },
        canBeAccepted: function (el, target, source, sibling) {
            return true;
        },
        revertOnSpill: true,
        removeOnSpill: false,
        moves: function (el, container, handle) {
            return handle.classList.contains('row-handle');
        },
        containersModel: parentContainerModel,
        // nameSpace: ['parents', 'children'],
        nameSpace: ['parents'],
        scope: $scope
    };

    var nestedContainerOption = {
        accepts: function (el, target, source, sibling) {
            return true;
        },
        canBeAccepted: function (el, target, source, sibling) {
            return true;
        },
        revertOnSpill: true,
        removeOnSpill: false,
        moves: function (el, container, handle) {
            return handle.classList.contains('child-handle');
        },
        containersModel: childContainerModel,
        // nameSpace: ['parents', 'children'],
        nameSpace: ['children'],
        scope: $scope
    };

    vm.reNamePage = function (menuItem) {
        return menuItemService.updateMenuItem(menuItem)
            .then(function (response) {
            }, function (error) {
                return error;
            });
    };

    vm.changeDraftStatus = function (menuItem, draftStatus) {
        EventMenuItemMenu.notifyOnStatusChange(menuItem, draftStatus);
    };

    vm.updateMenuItemStructure = function (elIndex, dropIndex, sourceContainerIndex, targetContainerIndex, isTop) {
        EventMenuItemMenu.notifyOnDragAndDrop(elIndex, dropIndex, sourceContainerIndex, targetContainerIndex, isTop);
    };

    EventMenuItemMenu.subscribeOnUpdateNestedContainers($scope, function (event) {
        $timeout(function () {
            container = angular.element('#parent-container');
            parentContainers = container.children(); //top-menu
            // collect nested containers
            nestedContainers = [];
            for (var i = 0; i < parentContainers.length; i++) {
                nestedContainers.push(parentContainers.eq(i).children().eq(0).children().eq(1)[0]);
            }
            parentContainerModel = $scope.topMenuItems;
            childContainerModel = [];
            childContainerModel = (function () {
                for (var i = 0; i < parentContainerModel.length; i++) {
                    childContainerModel.push(parentContainerModel[i].children);
                }
                return childContainerModel;
            })();
        });
    });

    $timeout(function () {

        //set containers
        container = angular.element('#parent-container');
        parentContainers = container.children(); //top-menu
        // collect nested containers
        for (var i = 0; i < parentContainers.length; i++) {
            nestedContainers.push(parentContainers.eq(i).children().eq(0).children().eq(1)[0]);
        }

        dragularService.cleanEnviroment();
        dragularService(container, parentContainerOption);
        dragularService(nestedContainers, nestedContainerOption);

        /**
         * Events dragular (drag & drop)
         */
        $scope.$on('dragulardrop', function (event, el, targetContainer, sourceContainer, conModel, elIndex, targetModel, dropIndex) {
            event.stopPropagation();

            container = angular.element('#parent-container');
            parentContainers = container.children(); //top-menu
            // collect nested containers
            nestedContainers = [];
            for (var i = 0; i < parentContainers.length; i++) {
                nestedContainers.push(parentContainers.eq(i).children().eq(0).children().eq(1)[0]);
            }
            parentContainerModel = $scope.topMenuItems;
            childContainerModel = [];
            childContainerModel = (function () {
                for (var i = 0; i < parentContainerModel.length; i++) {
                    childContainerModel.push(parentContainerModel[i].children);
                }
                return childContainerModel;
            })();

            // console.log(container);
            // console.log(parentContainers);
            // console.log(nestedContainers);
            // console.log(parentContainerModel);
            // console.log(childContainerModel );

            var isTop = 'false';
            var sourceContainerIndex = undefined;
            var targetContainerIndex = undefined;

            for (var j = 0; j < nestedContainers.length; j++) {
                if (nestedContainers[j] == targetContainer) {
                    // console.log("index of targetContainer: " + j); //gives the target container(top-menuItem)index
                    targetContainerIndex = j;
                }
                if (nestedContainers[j] == sourceContainer) {
                    // console.log("index of sourceContainer: " + j); //gives the source container(top-menuItem) index
                    sourceContainerIndex = j;
                }
            }
            if (sourceContainerIndex == undefined && targetContainerIndex == undefined) {
                isTop = 'true';
            } else if (sourceContainerIndex == undefined && targetContainerIndex != undefined) {
                isTop = 'true';
            }

            vm.updateMenuItemStructure(elIndex, dropIndex, sourceContainerIndex, targetContainerIndex, isTop);

            // TODO use parentContainers to set the isTOp
        });

    });

});