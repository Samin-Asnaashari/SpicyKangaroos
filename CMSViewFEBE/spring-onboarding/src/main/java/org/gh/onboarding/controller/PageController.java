package org.gh.onboarding.controller;

import org.gh.onboarding.model.Page;
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
@RequestMapping(value = "/pages")
@Api(
        name = "Page model API",
        description = "provides the list of methods that manages the pages.",
        stage = ApiStage.RC
)
public class PageController {

    private final String secure = "/secure";

    private IPageService pageService;

    @Autowired
    public PageController(IPageService pageService) {
        this.pageService = pageService;
    }

    @RequestMapping(value = secure + "/", method = RequestMethod.POST, consumes = "application/json")
    @ApiMethod(description = "Creates a new page including its MenuItem data with the given information.")
    public Page createPageWithMenuItem(@RequestBody Page page, @RequestParam(value = "menuItemName") String menuItemName, @RequestParam(value = "parentId") Integer parentId) throws Exception {
        return this.pageService.createPageWithMenuItem(page, menuItemName, parentId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiMethod(description = "Returns a page with the given id.")
    public Page findOnePage(@ApiPathParam(name = "id") @PathVariable Integer id) throws Exception {
        return this.pageService.findOne(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiMethod(description = "Returns a page with the given id of the menu-item.")
    public Page findOnePageByMenuItemId(@RequestParam(value = "menu") Integer menuId) throws Exception {
        return this.pageService.findByMenuItemId(menuId);
    }

    @RequestMapping(value = "/url/{url}", method = RequestMethod.GET)
    @ApiMethod(description = "Returns a page with the given url of the menu-item.")
    public Page findOnePageByMenuItemUrl(@ApiPathParam(name = "url") @PathVariable String url) throws Exception {
        return this.pageService.findByMenuItemUrl(url);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiMethod(description = "Returns all created pages.")
    public List<Page> findAllPages() throws Exception {
        return this.pageService.findAll();
    }

    @RequestMapping(value = secure, method = RequestMethod.PUT)
    @ApiMethod(description = "Updates an existing page data. Consumes the complete JSON object with the page fields.")
    public Page updatePage(@RequestBody Page page) throws Exception {
        return this.pageService.update(page);
    }

    @RequestMapping(value = secure + "/{id}", method = RequestMethod.DELETE)
    @ApiMethod(description = "Deletes a page with the given page id (and any children object in the page).")
    public void deletePage(@ApiPathParam(name = "id") @PathVariable Integer id) throws Exception {
        this.pageService.delete(id);
    }

    @RequestMapping(value = secure, method = RequestMethod.DELETE)
    @ApiMethod(description = "Deletes a page with the given menu-id (and any children object in the page).")
    public void deletePageByMenuItemId(@ApiPathParam(name = "menuItemId") @RequestParam(value = "menu") Integer menuId) throws Exception {
        this.pageService.deleteByMenuItemId(menuId);
    }
}
