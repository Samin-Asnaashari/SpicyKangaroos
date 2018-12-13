package org.gh.onboarding.service.inteface;

import org.gh.onboarding.model.component.menu.MenuItem;

import java.util.List;

public interface IMenuItemService {

    MenuItem create(Integer parentId) throws Exception;

    MenuItem findOne(Integer id) throws Exception;

    List<MenuItem> findAll() throws Exception;

    List<MenuItem> findTopMenuItems() throws Exception;

    List<MenuItem> findTopLiveMenuItems() throws Exception;

    List<MenuItem> findTrashTopMenuItems() throws Exception;

    MenuItem findParent(Integer childId) throws Exception;

    MenuItem update(MenuItem menuItem) throws Exception;

    MenuItem updateParent(MenuItem menuItem, Integer parentId) throws Exception;

    MenuItem restoreTrash(MenuItem menuItem) throws Exception;

    void updateChangesAfterDragAndDropAction(List<MenuItem> menuItems) throws Exception;

    List<MenuItem> deletePageByMenuItemAndUpdateSequences(Integer id) throws Exception;

    void deleteForever(Integer menuItem) throws Exception;

    void clearAllTrash() throws Exception;
}
