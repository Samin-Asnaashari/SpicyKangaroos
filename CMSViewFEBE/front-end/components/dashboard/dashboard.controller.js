'use strict';

angular.module('appComponent.dashboard').controller('dashboardCtrl', function ($state, $scope, menuItemResolve, trashMenuItemResolve,
                                                                               menuItemService, pageService,
                                                                               Notification, EventMenuItemMenu) {
    var vm = this;
    vm.topMenuItems = menuItemResolve.topMenuItems;
    vm.trashMenuItems = trashMenuItemResolve.trashMenuItems;
    var path = [];

    EventMenuItemMenu.subscribeOnMenuDelete($scope, function (event, data) {
        path = [];

        vm.findPath(vm.topMenuItems, data.menuItem);

        if (path[1] == undefined) {
            return vm.updateMenuItemListForRemove(data.menuItem, vm.topMenuItems);
        } else {
            return vm.updateMenuItemListForRemove(data.menuItem, path[1].children);
        }
    });

    vm.findPath = function (arr, item) {
        if (arr) {
            for (var i = 0; i < arr.length; i++) {
                if (arr[i].id == item.id) {
                    path.push(arr[i]);
                    return arr[i];
                }
                var found = vm.findPath(arr[i].children, item);
                if (found) {
                    path.push(arr[i]);
                    return found;
                }
            }
        }
    };

    vm.updateMenuItemListForRemove = function (menuItemToDelete, list) {
        return menuItemService.deletePageByMenuItemAndUpdateSequences(menuItemToDelete.id)
            .then(function (response) {
                var updatedMenuItems = response.data;
                var index = list.indexOf(menuItemToDelete);
                var j = 0;
                for (var i = index + 1; i < list.length; i++) {
                    list[i].sequence = updatedMenuItems[j].sequence;
                    j++;
                    vm.updateNestedContainers();
                }
                list.splice(index, 1);
                angular.forEach(menuItemToDelete.children, function (child) {
                    child.draft = true;
                    child.trash = true;
                    child.sequence = 0;
                });
                menuItemToDelete.draft = true;
                menuItemToDelete.trash = true;
                menuItemToDelete.sequence = 0;
                vm.trashMenuItems.push(menuItemToDelete);
            }, function (error) {
                return error;
            });
    };

    EventMenuItemMenu.subscribeOnStatusChange($scope, function (event, data) {
        (data.menuItem).draft = data.draftStatus;
        return menuItemService.updateMenuItem(data.menuItem)
            .then(function (response) {
                angular.forEach(vm.topMenuItems, function (item) {
                    if (item.id === response.data.id) {
                        item.draft = response.data.draft;
                        // TODO break;
                    }
                });
            }, function (error) {
                return error;
            });
    });

    /**
     * Drag and Drop
     * change the sequence between top menuItems, update the sequences of the menus that have been affected.
     * change the sequence between children.
     * drag a child and drop it into another parent, in specific index.
     * drag parent into another child if it has no sub page itself.
     * drag child to become parent.
     * * here the 'Containers' refers to only nested containers.
     * the method considers scenarios on the level 1 and 2 only
     */
    EventMenuItemMenu.subscribeOnDragAndDrop($scope, function (event, data) {
            /*data is : elIndex, dropIndex, sourceContainerIndex, targetContainerIndex, isTop*/
            if (data.isTop == 'true') {
                vm.dndTopMenuScenarios(data.elIndex, data.dropIndex, data.targetContainerIndex);
            } else {
                vm.dndSubMenuScenarios(data.elIndex, data.dropIndex, data.sourceContainerIndex, data.targetContainerIndex);
            }
        }
    );

    /**
     * drag/drop Top menu between top menu list.
     * drag the top menu and drop it into a nested level (!important if it does not have any sub pages).
     */
    vm.dndTopMenuScenarios = function (elIndex, dropIndex, targetContainerIndex) {
        var tempMenuItems = [];
        // if (targetContainerIndex != undefined) { //top is dropped to be a sub page of another top menu
        //     if (vm.topMenuItems[targetContainerIndex].children[dropIndex].children.length == 0) {
        //         menuItemService.updateParent(vm.topMenuItems[targetContainerIndex].children[dropIndex], vm.topMenuItems[targetContainerIndex].id)
        //             .then(function (response) {
        //                 vm.topMenuItems[targetContainerIndex].children[dropIndex] = response.data;
        //                 //update source
        //                 tempMenuItems = vm.updateSequences(0, (vm.topMenuItems.length - 1), vm.topMenuItems);
        //                 vm.updateAllChangedSequenceInBackEnd(tempMenuItems);
        //                 //update target
        //                 tempMenuItems = vm.updateSequences(0, (vm.topMenuItems[targetContainerIndex].children.length - 1), vm.topMenuItems[targetContainerIndex].children);
        //                 vm.updateAllChangedSequenceInBackEnd(tempMenuItems);
        //             }, function (error) {
        //                 return error;
        //             })
        //     } else {
        //         //TODO fix the index: it should go back to the place where it was before(so only elIndex)
        //         console.log("elIndex is " + elIndex);
        //         vm.topMenuItems.splice(elIndex - 1, 0, vm.topMenuItems[targetContainerIndex].children[dropIndex]);
        //         vm.topMenuItems[targetContainerIndex].children.splice(dropIndex, 1);
        //         vm.updateNestedContainers();
        //         console.log("Fault! The maximum level must be at most 2!");
        //     }
        // } else {
        if (elIndex < dropIndex) { //Direction is Up to Down between top menuItems
            tempMenuItems = vm.updateSequences(elIndex, dropIndex, vm.topMenuItems);
        } else { //Direction is Down to Up between top menuItems
            tempMenuItems = vm.updateSequences(dropIndex, elIndex, vm.topMenuItems);
        }
        vm.updateAllChangedSequenceInBackEnd(tempMenuItems);
        // }
    };

    /**
     * drag/drop sub menu between same list.
     * drag the sub menu and drop it into another parent.
     * drag sub menu and make top menu.
     */
    vm.dndSubMenuScenarios = function (elIndex, dropIndex, sourceContainerIndex, targetContainerIndex) {
        var tempMenuItems = [];
        // if (targetContainerIndex == undefined) { //make sub menu to become a top menu
        //     menuItemService.updateParent(vm.topMenuItems[dropIndex], -1)
        //         .then(function (response) {
        //             vm.topMenuItems[dropIndex] = response.data;
        //             //update source
        //             tempMenuItems = vm.updateSequences(0, (vm.topMenuItems[sourceContainerIndex].children.length - 1), vm.topMenuItems[sourceContainerIndex].children);
        //             vm.updateAllChangedSequenceInBackEnd(tempMenuItems);
        //             //update target
        //             tempMenuItems = vm.updateSequences(0, (vm.topMenuItems.length - 1), vm.topMenuItems);
        //             vm.updateAllChangedSequenceInBackEnd(tempMenuItems);
        //             // vm.updateNestedContainers();
        //         }, function (error) {
        //             return error;
        //         });
        /*} else*/ if (sourceContainerIndex == targetContainerIndex) { //Change the sequences between children
            if (elIndex < dropIndex) { //Direction is Up to Down
                tempMenuItems = vm.updateSequences(elIndex, dropIndex, vm.topMenuItems[sourceContainerIndex].children);
            } else { //Direction is Down to Up
                tempMenuItems = vm.updateSequences(dropIndex, elIndex, vm.topMenuItems[sourceContainerIndex].children);
            }
            vm.updateAllChangedSequenceInBackEnd(tempMenuItems);
        } else { //child dropped to another parent
            menuItemService.updateParent(vm.topMenuItems[targetContainerIndex].children[dropIndex], vm.topMenuItems[targetContainerIndex].id)
                .then(function (response) {
                    //update source
                    tempMenuItems = vm.updateSequences(0, (vm.topMenuItems[sourceContainerIndex].children.length - 1), vm.topMenuItems[sourceContainerIndex].children);
                    vm.updateAllChangedSequenceInBackEnd(tempMenuItems);
                    //update target
                    tempMenuItems = vm.updateSequences(0, (vm.topMenuItems[targetContainerIndex].children.length - 1), vm.topMenuItems[targetContainerIndex].children);
                    vm.updateAllChangedSequenceInBackEnd(tempMenuItems);
                }, function (error) {
                    return error;
                });
        }
    };

    /**
     * for each drag and drop update the affected menus.
     * @param firstIndex
     * @param lastIndex
     * @param list
     * @returns {Array}
     */
    vm.updateSequences = function (firstIndex, lastIndex, list) {
        var temp = [];
        for (var i = firstIndex; i <= lastIndex; i++) {
            list[i].sequence = i + 1;
            temp.push(list[i]);
        }
        return temp;
    };

    vm.updateAllChangedSequenceInBackEnd = function (menuItems) {
        return menuItemService.updateAllChangedSequence(menuItems)
            .then(function (response) {
                //Notification.success("The action was Successful");
            }, function (error) {
                return error;
            });
    };

    vm.updateNestedContainers = function () {
        EventMenuItemMenu.notifyOnUpdateNestedContainers();
    };


    EventMenuItemMenu.subscribeOnRestoreMenuItemFromTrash($scope, function (event, data) {
        return menuItemService.restoreMenuItem(data.menuItem)
            .then(function (response) {
                path = [];
                var index;
                vm.findPath(vm.trashMenuItems, data.menuItem);
                if (path[1] == undefined) {
                    index = vm.trashMenuItems.indexOf(data.menuItem);
                    vm.trashMenuItems.splice(index, 1);
                } else {
                    index = path[1].children.indexOf(data.menuItem);
                    path[1].children.splice(index, 1);
                }
                vm.topMenuItems.push(data.menuItem);
                vm.updateNestedContainers();
            }, function (error) {
                return error;
            });
    });

    EventMenuItemMenu.subscribeOnDeleteForever($scope, function (event, data) {
        return menuItemService.deletePageForever(data.menuItem.id)
            .then(function (response) {
                var index = vm.trashMenuItems.indexOf(data.menuItem);
                vm.trashMenuItems.splice(index, 1);
            }, function (error) {
                return error;
            });
    });

    vm.clearTrash = function () {
        return menuItemService.clearTrash()
            .then(function (response) {
                vm.trashMenuItems = [];
            }, function (error) {
                return error;
            });
    };
});