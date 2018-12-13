package org.gh.onboarding.service.inteface;

import org.gh.onboarding.model.Page;
import org.gh.onboarding.model.component.menu.MenuItem;

import java.util.List;

public interface IPageService {

//    Page create(Page page) throws Exception;

    Page createPageWithMenuItem(Page page, String menuItemName, Integer parentId) throws Exception;

    List<Page> findAll() throws Exception;

    Page findOne(Integer id) throws Exception;

    Page findByMenuItemId(Integer id) throws Exception;

    Page findByMenuItemUrl(String url) throws Exception;

    Page update(Page page) throws Exception;

    void delete(Integer id) throws Exception;

    void deleteByMenuItemId(Integer id) throws Exception;
}
