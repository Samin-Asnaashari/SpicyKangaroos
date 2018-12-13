package org.gh.onboarding.service;

import org.gh.onboarding.BaseServiceTest;
import org.gh.onboarding.model.Page;
import org.gh.onboarding.model.component.menu.MenuItem;
import org.gh.onboarding.repository.PageRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;


public class PageServiceTest extends BaseServiceTest {

    @InjectMocks
    private PageService pageService;

    @Mock
    private PageRepository pageRepository;
    @Mock
    private MenuItemService menuItemService;

    private static Page page1;
    private static Page page2;

    @Before
    public void setUP() throws Exception {
        //start up preparation methods.

        page1 = new Page();
        page1.setId(1);
        page1.setTitle("home");
        page1.setRows(null);

        page2 = new Page();
        page2.setId(2);
        page2.setTitle("about");
        page2.setRows(null);

        //Stubbing the methods of mocked objects with mocked data.
        when(pageRepository.findOne(1)).thenReturn(page1);
        when(pageRepository.findOne(2)).thenReturn(page2);
        when(pageRepository.findAll()).thenReturn(Arrays.asList(page1, page2));
    }

    @Test
    public void create() throws Exception {
        //GIVEN
        MenuItem menuItem = new MenuItem();
        menuItem.setId(1);
//        when(menuItemService.create()).thenReturn(menuItem);
        when(pageRepository.save(page1)).thenReturn(page1);

        //WHEN
//        Page test = this.pageService.create(page1);

        //THEN
//        verify(menuItemService).create();
//        verify(pageRepository, Mockito.times(1)).save(page1);
//        verifyNoMoreInteractions(menuItemService);
//        Assert.assertNotNull("failure - expected not null", test);
//        Assert.assertNotNull("failure - expected id attribute not null", test.getId());
//        Assert.assertEquals("failure - expected title attribute match", "home", test.getTitle());
//        Assert.assertNotNull("failure - expected menuItem not null", test.getMenuItem());
//        Assert.assertEquals("failure - expected id attribute not null", menuItem.getId(), test.getMenuItem().getId());
//        Assert.assertNull("failure - expected rows to be null", test.getRows());
    }

    @Test
    public void findAll() throws Exception {
        //GIVEN

        //WHEN
        List<Page> pages = this.pageService.findAll();

        //THEN
        Assert.assertNotNull("failure - expected not null", pages);
        Assert.assertEquals("failure - expected size", 2, pages.size());
    }

    @Test
    public void findOne() throws Exception {
        //GIVEN
        Integer id = 1;

        //WHEN
        Page pageEntity = this.pageService.findOne(id);

        //THEN
        Assert.assertNotNull("failure - expected not null", pageEntity);
        Assert.assertEquals("failure - expected id attribute match", id, pageEntity.getId());
    }

    @Test(expected = Exception.class)
    public void findOneNotFound() throws Exception {
        //GIVEN
        Integer id = Integer.MAX_VALUE;

        //WHEN
        this.pageService.findOne(id);

        //THEN
        //Expected Exception
    }

    @Test
    public void update() throws Exception {
        //GIVEN
        when(pageRepository.exists(page2.getId())).thenReturn(true);
        when(pageRepository.save(page2)).thenReturn(page2);

        //WHEN
        String updatedTitle = page2.getTitle() + " test";
        page2.setTitle(updatedTitle);
        Page updatedPage = this.pageService.update(page2);

        //THEN
        verify(pageRepository, Mockito.times(1)).save(page2);
        Assert.assertNotNull("failure - expected not null", updatedPage);
        Assert.assertEquals("failure - expected id attribute match", page2.getId(), updatedPage.getId());
        Assert.assertEquals("failure - expected title attribute match", updatedTitle, updatedPage.getTitle());
    }

    @Test(expected = Exception.class)
    public void updateNotFound() throws Exception {
        //GIVEN
        page2.setId(Integer.MAX_VALUE);

        //WHEN
        page2.setTitle("test");
        this.pageService.update(page2);

        //THEN
        //Expected Exception
    }

    @Test(expected = Exception.class)
    public void delete() throws Exception {
        //GIVEN
        Integer id = 1;

        //WHEN
        this.pageService.delete(id);
        List<Page> list = this.pageService.findAll();

        //THEN
        Assert.assertEquals("failure - expected size", 1, list.size());

        //AND WHEN
        this.pageService.findOne(id);

        //THEN
        //Expected Exception
    }
}