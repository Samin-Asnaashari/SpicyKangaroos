package org.gh.onboarding.controller;

import org.gh.onboarding.model.component.menu.MenuItem;
import org.gh.onboarding.service.inteface.*;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.pojo.ApiStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/menu-items")
@Api(
        name = "MenuItem model API",
        description = "provides the list of methods that manages the menu items.",
        stage = ApiStage.RC
)
public class MenuItemController {

    private final String secure = "/secure";

    private IMenuItemService menuItemService;

    @Autowired
    public MenuItemController(IMenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiMethod(description = "Returns a MenuItem with the given id.")
    public MenuItem findOneMenuItem(@ApiPathParam(name = "id") @PathVariable Integer id) throws Exception {
        return this.menuItemService.findOne(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiMethod(description = "Returns all created menuItems.")
    public List<MenuItem> findAllMenuItems() throws Exception {
        return this.menuItemService.findAll();
    }

    @RequestMapping(value = "/top", method = RequestMethod.GET)
    @ApiMethod(description = "Returns all top menuItems.")
    public List<MenuItem> findAllTopMenuItems() throws Exception {
        return this.menuItemService.findTopMenuItems();
    }

    @RequestMapping(value = "/top/live", method = RequestMethod.GET)
    @ApiMethod(description = "Returns all top live menuItems, which draft is false.")
    public List<MenuItem> findAllTopLiveMenuItems() throws Exception {
        return this.menuItemService.findTopLiveMenuItems();
    }

    @RequestMapping(value = "/trash", method = RequestMethod.GET)
    @ApiMethod(description = "Returns all trash top menuItems.")
    public List<MenuItem> findAllTrashTopMenuItems() throws Exception {
        return this.menuItemService.findTrashTopMenuItems();
    }

    @RequestMapping(value = "/parent/child/{childId}", method = RequestMethod.GET)
    @ApiMethod(description = "Returns the parent of the MenuItem with the given id.")
    public MenuItem findParentMenuItem(@ApiPathParam(name = "childId") @PathVariable Integer childId) throws Exception {
        return this.menuItemService.findParent(childId);
    }

    @RequestMapping(value = secure, method = RequestMethod.PUT)
    @ApiMethod(description = "Updates an existing menuItem data. Consumes the complete JSON object with the menu-item fields.")
    public MenuItem updateMenuItem(@RequestBody MenuItem menuItem) throws Exception {
        return this.menuItemService.update(menuItem);
    }

    @RequestMapping(value = secure + "/set-parent/{parentId}", method = RequestMethod.PUT)
    @ApiMethod(description = "Updates the parent MenuItem.")
    public MenuItem updateParent(@RequestBody MenuItem menuItem, @ApiPathParam(name = "parentId") @PathVariable Integer parentId) throws Exception {
        return this.menuItemService.updateParent(menuItem, parentId);
    }

    @RequestMapping(value = secure + "/restore", method = RequestMethod.PUT)
    @ApiMethod(description = "restore a menuItem from trash back to draft.")
    public void restoreAMenuItemFromTrash(@RequestBody MenuItem menuItem) throws Exception {
        this.menuItemService.restoreTrash(menuItem);
    }

    @RequestMapping(value = secure + "/afterDnD", method = RequestMethod.PUT)
    @ApiMethod(description = "Updates all the menuItems which their sequence has been effected by drag and drop.")
    public void updateChangesAfterDragAndDropAction(@RequestBody List<MenuItem> menuItems) throws Exception {
        this.menuItemService.updateChangesAfterDragAndDropAction(menuItems);
    }

    @RequestMapping(value = secure + "/send-to-trash/", method = RequestMethod.DELETE)
    @ApiMethod(description = "Updates the sequence of menuItems after deletion(send to trash) in the list(can be TopMenus/children list of the parent) , after the menuItem in with given Id in list and returns the effected menuItems.")
    public List<MenuItem> sendThePageToTrashByMenuItemAndReturnUpdatedStructure(@RequestParam(value = "menuItemId") Integer menuItemId) throws Exception {
        return this.menuItemService.deletePageByMenuItemAndUpdateSequences(menuItemId);
    }

    @RequestMapping(value = secure + "/delete/", method = RequestMethod.DELETE)
    @ApiMethod(description = "delete menuItem forever from trash.")
    public void deleteForever(@RequestParam(value = "menuItemId") Integer menuItemId) throws Exception {
        this.menuItemService.deleteForever(menuItemId);
    }

    @RequestMapping(value = secure + "/clear-trash", method = RequestMethod.DELETE)
    @ApiMethod(description = "delete all trash pages forever by menuItem id.clear recycle bin.")
    public void clearTrash() throws Exception {
        this.menuItemService.clearAllTrash();
    }
}


