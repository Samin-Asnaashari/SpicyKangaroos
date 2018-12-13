package org.gh.onboarding.repository;

import org.gh.onboarding.BaseTestIT;
import org.gh.onboarding.model.component.menu.MenuItem;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class MenuItemRepositoryIT extends BaseTestIT {

    @Before
    public void StartUp() {
        this.initial();
    }

    @After
    public void tearDown() throws Exception {
        this.cleanUp();
    }

    @Test
    public void shouldReturnEmptyListOfMenuItems() {
        this.pageRepository.deleteAll();
        List<MenuItem> list = menuItemRepository.findByMenuItemIsNullAndTrashIsFalseOrderBySequence();
        Assert.assertTrue(list.size() == 0);
    }

    @Test
    public void shouldReturnMenuItems() {
        List<MenuItem> list = menuItemRepository.findAll();
        Assert.assertTrue(list.size() > 0);
    }

    @Test
    public void shouldCheckGettingTheTopMenuItemsByUsingFindByMenuItemIsNull() {
        List<MenuItem> topMenuItems = this.menuItemRepository.findByMenuItemIsNullAndTrashIsFalseOrderBySequence();
        Assert.assertEquals("failure - expected size", 1, topMenuItems.size());
        Assert.assertEquals("failure - expected id attribute match", homeMenuItem.getId(), topMenuItems.get(0).getId());
    }

    @Test
    public void shouldCheckNotUpdateMenuItemWhileUpdatingTopMenuItem() {
        String tempName = aboutMenuItem.getName();
        homeMenuItem.setName("test");
        homeMenuItem.getChildren().get(0).setName("test");
        this.menuItemRepository.save(homeMenuItem);
        Assert.assertEquals("failure - expected menuItem name attribute match", tempName, this.menuItemRepository.findOne(aboutMenuItem.getId()).getName());
    }

    @Test
    public void shouldCheckDeleteReferenceMenuItemWhenTopMenuItemDeleted() {
        this.pageRepository.delete(home.getId());
        Assert.assertNull("failure - expected null", this.menuItemRepository.findOne(aboutMenuItem.getId()).getMenuItem());
        Assert.assertTrue(!menuItemRepository.exists(homeMenuItem.getId()));
    }
}