package org.gh.onboarding.service;

import org.gh.onboarding.model.component.menu.MenuItem;
import org.gh.onboarding.repository.*;
import org.gh.onboarding.service.inteface.IMenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.List;

@Service
public class MenuItemService implements IMenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;
    @Autowired
    private PageService pageService;

    @Value("${maxLevel}")
    private String maxLevel;

    /**
     * Creates a MenuItem object, depending of it's a parent or a child, it sets the right attributes.
     */
    @Transactional
    @Override
    public MenuItem create(Integer parentId) throws Exception {
        MenuItem menuItem = new MenuItem();
        menuItem.setDraft(true);
        menuItem.setTrash(false);
        if (parentId != -1) {
            MenuItem parent = this.menuItemRepository.findOne(parentId);
            if (parent.getLevel() + 1 <= Integer.valueOf(maxLevel)) {
                menuItem.setSequence(parent.getChildren().size() + 1);
                menuItem.setLevel(parent.getLevel() + 1);
            } else {
                throw new Exception("Cannot create Page. MenuItems reached the max Level!");
            }
        } else {
            menuItem.setSequence(this.menuItemRepository.countByMenuItemIsNull() + 1);
            menuItem.setLevel(0);
        }
        return this.menuItemRepository.save(menuItem);
    }

    /**
     * Find one menu item with the given id, and returns it.
     */
    @Override
    public MenuItem findOne(Integer id) throws Exception {
        MenuItem menuItem = this.menuItemRepository.findOne(id);

        if (menuItem == null) {
            throw new Exception("MenuItem with id=" + id + " does not exist.");
        }
        return menuItem;
    }

    /**
     * Find all menu items and return a list ordered by sequence.
     */
    @Override
    public List<MenuItem> findAll() throws Exception {
        return this.menuItemRepository.findByOrderBySequence();
    }

    /**
     * Returns all the top (parent) menu items that are not in the trash.
     */
    @Override
    public List<MenuItem> findTopMenuItems() throws Exception {
        return this.menuItemRepository.findByMenuItemIsNullAndTrashIsFalseOrderBySequence();
    }

    /**
     * Returns all the top (parent) menu items that are live (This means that they're draft attribute is false).
     * Returns all the children with them.
     */
    @Override
    public List<MenuItem> findTopLiveMenuItems() throws Exception {
        List<MenuItem> topLives = this.menuItemRepository.findByMenuItemIsNullAndDraftIsFalseAndTrashIsFalseOrderBySequence();
        for (MenuItem topLiveMenu : topLives) {
            topLiveMenu.setChildren(returnTopLiveMenuItemsWithTheirLiveChildren(topLiveMenu.getChildren(), 0));
        }

        if (topLives.size() == 0) {
            throw new Exception("No MenuItem found.");
        }

        return topLives;
    }

    /**
     * Return all the top (parent) menu items that are live, and only return their live children with them.
     * If they have draft children, they will not be included.
     */
    private List<MenuItem> returnTopLiveMenuItemsWithTheirLiveChildren(List<MenuItem> menuItemChildren, int i) {
        if (i < menuItemChildren.size()) {
            if (menuItemChildren.get(i).getDraft()) {
                menuItemChildren.remove(menuItemChildren.get(i));
                i--;
            } else {
                if (menuItemChildren.get(i).getChildren().size() != 0) {
                    return returnTopLiveMenuItemsWithTheirLiveChildren(menuItemChildren.get(i).getChildren(), 0);
                }
            }
            return returnTopLiveMenuItemsWithTheirLiveChildren(menuItemChildren, i + 1);
        }
        return menuItemChildren;
    }

    /**
     * Returns all the top (parent) menu items have their draft attribute to true.
     */
    @Override
    public List<MenuItem> findTrashTopMenuItems() throws Exception {
        return this.menuItemRepository.findByMenuItemIsNullAndTrashIsTrue();
    }

    /**
     * Find a parent with the specific child id, and return that parent.
     */
    @Override
    public MenuItem findParent(Integer childId) throws Exception {
        return this.menuItemRepository.findByChildrenId(childId);
    }

    /**
     * Updates a menu item with the given information.
     */
    @Transactional
    @Override
    public MenuItem update(MenuItem menuItem) throws Exception {
        if (menuItemRepository.exists(menuItem.getId())) {
            MenuItem menuItemToSave = this.menuItemRepository.findOne(menuItem.getId());
            menuItemToSave.setSequence(menuItem.getSequence());
            menuItemToSave.setDraft(menuItem.getDraft());
            menuItemToSave.setLevel(menuItem.getLevel());
            menuItemToSave.setTrash(menuItem.getTrash());
            // set the name, remove extra whitespaces
            String name = menuItem.getName().replaceAll("\\s{2,}", " ").trim().toLowerCase();
            menuItemToSave.setName(name);
            // set the url according to the name
            String url = name.replace(" ", "-");
            menuItemToSave.setUrl(url);
            return menuItemRepository.save(menuItemToSave);
        }
        throw new Exception("MenuItem with id=" + menuItem.getId() + " does not exist.");
    }

    /**
     * Updates the MenuItem to have a reference to a new parent, or be a top parent itself (null).
     * If the MenuItem is now a parent, it updates the level to be 0, otherwise it's the level of
     * its parent +1.
     */
    @Transactional
    @Override
    public MenuItem updateParent(MenuItem menuItem, Integer parentId) throws Exception {
        if (menuItemRepository.exists(menuItem.getId())) {
            MenuItem menuItemToSave = this.menuItemRepository.findOne(menuItem.getId());
            if (parentId == -1) {
                menuItemToSave.setMenuItem(null);
                menuItemToSave.setLevel(0);
            } else {
                if (menuItemRepository.exists(parentId)) {
                    MenuItem parent = this.menuItemRepository.findOne(parentId);
                    menuItemToSave.setMenuItem(parent);
                    menuItemToSave.setLevel(parent.getLevel() + 1);
                } else {
                    throw new Exception("Parent MenuItem with id=" + parentId + " does not exist.");
                }
            }
            return menuItemRepository.save(menuItemToSave);
        }
        throw new Exception("MenuItem with id=" + menuItem.getId() + " does not exist.");
    }

    /**
     * Restore a MenuItem from trash, to be not in trash. It will be automatically in draft mode when restored.
     * Returns the restored menu item. If it's a child, it will become a parent. The MenuItem will get a bottom sequence.
     */
    @Transactional
    @Override
    public MenuItem restoreTrash(MenuItem menuItem) throws Exception {
        if (menuItemRepository.exists(menuItem.getId())) {
            MenuItem menuItemToSave = this.menuItemRepository.findOne(menuItem.getId());
            if (menuItemToSave.getMenuItem() != null) {
                menuItemToSave = this.updateParent(menuItemToSave, -1);
                menuItemToSave.setLevel(0);
            }
            menuItemToSave.setSequence(this.findTopMenuItems().size() + 1);
            menuItemToSave.setTrash(false);
            List<MenuItem> children = menuItemToSave.getChildren();
            for (int i = 0; i < children.size(); i++) {
                children.get(i).setTrash(false);
                children.get(i).setSequence(i + 1);
                menuItemRepository.save(children.get(i));
            }
            return menuItemRepository.save(menuItemToSave);
        }
        throw new Exception("MenuItem with id=" + menuItem.getId() + " does not exist.");
    }

    /**
     * Updates the MenuItems's sequences of the given list in the database.
     */
    @Transactional
    @Override
    public void updateChangesAfterDragAndDropAction(List<MenuItem> menuItems) throws Exception {
        for (MenuItem item : menuItems) {
            if (menuItemRepository.exists(item.getId())) {
                MenuItem menuItemToSave = this.menuItemRepository.findOne(item.getId());
                menuItemToSave.setSequence(item.getSequence());
                menuItemRepository.save(menuItemToSave);
            } else {
                throw new Exception("MenuItem with id=" + item.getId() + " does not exist.");
            }
        }
    }

    /**
     * Delete a page (Sent to trash). Uses the private helper method to loop through the items.
     * The MenuItem can have children, so the children will go with the parent to the trash.
     */
    @Transactional
    @Override
    public List<MenuItem> deletePageByMenuItemAndUpdateSequences(Integer id) throws Exception {
        MenuItem menuItem = this.menuItemRepository.findOne(id);
        MenuItem parent = this.findParent(id);
        if (parent == null) {
            List<MenuItem> menuItems = findTopMenuItems();
            return updateSequenceAndDeletePageHelper(menuItems, menuItem);
        } else {
            List<MenuItem> children = parent.getChildren();
            return updateSequenceAndDeletePageHelper(children, menuItem);
        }
    }

    /**
     * Helper for sending a MenuItem to the trash. If the MenuItem has children, it will also set the children's
     * trash attribute to be true.
     */
    @Transactional
    private List<MenuItem> updateSequenceAndDeletePageHelper(List<MenuItem> menuItems, MenuItem menuItem) throws Exception {
        Integer index = menuItems.indexOf(menuItem);
        menuItems.subList(0, index + 1).clear();
        for (MenuItem item : menuItems) {
            int updatedSeq = item.getSequence() - 1;
            item.setSequence(updatedSeq);
            this.menuItemRepository.save(item);
        }
        for (MenuItem child : menuItem.getChildren()) {
            child.setDraft(true);
            child.setTrash(true);
            child.setSequence(0);
            this.menuItemRepository.save(child);
        }
        menuItem.setDraft(true);
        menuItem.setTrash(true);
        menuItem.setSequence(0);
        this.menuItemRepository.save(menuItem);
        return menuItems;
    }

    /**
     * Deletes a page forever from the database.
     */
    @Transactional
    @Override
    public void deleteForever(Integer menuItemId) throws Exception {
        pageService.deleteByMenuItemId(menuItemId);
    }

    /**
     * Deletes all the pages that has the trash attribute true for ever.
     */
    @Transactional
    @Override
    public void clearAllTrash() throws Exception {
        for (MenuItem trashItem : this.findTrashTopMenuItems()) {
            pageService.deleteByMenuItemId(trashItem.getId());
        }
    }
}
