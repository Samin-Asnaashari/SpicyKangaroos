package org.gh.onboarding.service;

import org.gh.onboarding.BaseServiceTest;
import org.gh.onboarding.model.component.menu.MenuItem;
import org.gh.onboarding.repository.MenuItemRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

public class MenuItemServiceTest extends BaseServiceTest {

    @InjectMocks
    private MenuItemService menuItemService;

    @Mock
    private MenuItemRepository menuItemRepository;

    private static MenuItem menuItem1;
    private static MenuItem menuItem2;

    @Before
    public void setUp() throws Exception {

        menuItem1 = new MenuItem();
        menuItem1.setId(1);
        menuItem1.setName("home");
        menuItem1.setSequence(1);
        menuItem1.setMenuItem(null);
        menuItem1.setChildren(Arrays.asList(menuItem2));

        menuItem2 = new MenuItem();
        menuItem2.setId(2);
        menuItem2.setName("home-item");
        menuItem2.setSequence(1);
        menuItem2.setMenuItem(menuItem1);
        menuItem2.setChildren(null);

        when(menuItemRepository.findAll()).thenReturn(Arrays.asList(menuItem1, menuItem2));
    }

    @Test
    public void create() throws Exception {
        //GIVEN
        when(menuItemRepository.save(Matchers.any(MenuItem.class))).thenReturn(menuItem1);

        //WHEN
//        MenuItem test = this.menuItemService.create();

        //THEN
//        Assert.assertNotNull("failure - expected not null", test);
//        Assert.assertNotNull("failure - expected id attribute not null", test.getId());
//        Assert.assertEquals("failure - expected name attribute match", "home", test.getName());
//        Assert.assertEquals("failure - expected sequence attribute match",menuItem1.getSequence(), test.getSequence());
    }

    @Test
    public void findAll() throws Exception {
        //GIVEN

        //WHEN
        List<MenuItem> menuItems = this.menuItemService.findAll();

        //THEN
        Assert.assertNotNull("failure - expected not null", menuItems);
        Assert.assertEquals("failure - expected size", 2, menuItems.size());
    }

    @Test
    public void update() throws Exception {
        //GIVEN
        when(menuItemRepository.exists(menuItem2.getId())).thenReturn(true);
        when(menuItemRepository.findOne(menuItem2.getId())).thenReturn(menuItem2);
        when(menuItemRepository.save(menuItem2)).thenReturn(menuItem2);

        //WHEN
        String updatedName = menuItem2.getName() + " test";
        menuItem2.setName(updatedName);
        MenuItem updatedMenuItem = this.menuItemService.update(menuItem2);

        //THEN
        Assert.assertNotNull("failure - expected not null", updatedMenuItem);
        Assert.assertEquals("failure - expected id attribute match", menuItem2.getId(), updatedMenuItem.getId());
        Assert.assertEquals("failure - expected name attribute match", updatedName, updatedMenuItem.getName());
        Assert.assertEquals("failure - expected sequence attribute match", menuItem2.getSequence(), updatedMenuItem.getSequence());
        Assert.assertNotNull("failure - expected not null", menuItem2.getMenuItem());
    }

    @Test(expected = Exception.class)
    public void updateNotFound() throws Exception {
        //GIVEN
        menuItem1.setId(Integer.MAX_VALUE);

        //WHEN
        menuItem1.setName("test");
        this.menuItemService.update(menuItem1);

        //THEN
        //Expected Exception
    }
}