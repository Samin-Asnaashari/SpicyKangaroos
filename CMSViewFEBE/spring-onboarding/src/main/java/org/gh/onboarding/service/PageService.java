package org.gh.onboarding.service;

import org.gh.onboarding.model.Page;
import org.gh.onboarding.model.component.menu.MenuItem;
import org.gh.onboarding.repository.*;
import org.gh.onboarding.service.inteface.IMenuItemService;
import org.gh.onboarding.service.inteface.IPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PageService implements IPageService {

    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private IMenuItemService menuItemService;

    /**
     * Creates a menu item. Creates a page and add the menu item to the page.
     * Manipulate the 'url' attribute of the item, and update it. (Remove white spaces, and add a dash '-' in their places).
     * Returns the created page.
     */
    @Transactional
    @Override
    public Page createPageWithMenuItem(Page page, String menuItemName, Integer parentId) throws Exception {
        MenuItem menuItem = this.menuItemService.create(parentId);
        menuItem.setName(menuItemName);

        // set the name, remove extra whitespaces
        String name = menuItemName.replaceAll("\\s{2,}", " ").trim().toLowerCase();
        menuItem.setName(name);
        // set the url according to the name
        String url = name.replace(" ", "-");
        menuItem.setUrl(url);

        menuItem = menuItemService.update(menuItem);
        if (parentId != -1) {
            menuItem = menuItemService.updateParent(menuItem, parentId);
        }
        page.setMenuItem(menuItem);
        return this.pageRepository.save(page);
    }

    /**
     * Find all the pages, and returns a list of all the found pages.
     */
    @Override
    public List<Page> findAll() throws Exception {
        return this.pageRepository.findAll();
    }

    /**
     * Find one page with the given id, and return the found page.
     */
    @Override
    public Page findOne(Integer id) throws Exception {
        Page page = this.pageRepository.findOne(id);

        if (page == null) {
            throw new Exception("Page with id=" + id + " does not exist.");
        }
        return page;
    }

    /**
     * Find a page by its MenuItem id, and return the found page.
     */
    @Override
    public Page findByMenuItemId(Integer id) throws Exception {
        Page page = this.pageRepository.findByMenuItemId(id);

        if (page == null) {
            throw new Exception("Menu with id=" + id + " does not exist. No page found.");
        }
        return page;
    }

    /**
     * Find a page by its MenuItem url, and return that page.
     */
    @Override
    public Page findByMenuItemUrl(String url) throws Exception {
        Page page = this.pageRepository.findByMenuItemUrl(url);

        if (page == null) {
            throw new Exception("Menu with url=" + url + " does not exist. No page found.");
        }
        return page;
    }

    /**
     * Update a page's data with the given data, and returns the updated page.
     */
    @Transactional
    @Override
    public Page update(Page page) throws Exception {
        if (pageRepository.exists(page.getId())) {
            Page pageToSave = this.pageRepository.findOne(page.getId());
            pageToSave.setTitle(page.getTitle());
            return this.pageRepository.save(pageToSave);
        }
        throw new Exception("Page with id=" + page.getId() + " does not exist.");
    }

    /**
     * Deletes a page with the given id.
     */
    @Transactional
    @Override
    public void delete(Integer id) throws Exception {
        if (this.pageRepository.exists(id)) {
            this.pageRepository.delete(id);
            return;
        }
        throw new Exception("Page with id=" + id + " does not exist.");
    }

    /**
     * Deletes a page by MenuItem id.
     */
    @Transactional
    @Override
    public void deleteByMenuItemId(Integer id) throws Exception {
        Page page = this.pageRepository.findByMenuItemId(id);
        if (page != null) {
            this.pageRepository.delete(page.getId());
            return;
        }
        throw new Exception("Page with menu-id=" + id + " does not exist.");
    }
}
