package org.gh.onboarding.repository;

import org.gh.onboarding.BaseTestIT;
import org.gh.onboarding.model.Page;
import org.gh.onboarding.model.component.section.Section;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PageRepositoryIT extends BaseTestIT {

    @Before
    public void setUp() throws Exception {

        this.initial();
    }

    @After
    public void tearDown() throws Exception {
        this.cleanUp();
    }

    @Test
    public void shouldCheckTheMethodFindByMenuItemId() {
        Page page = this.pageRepository.findByMenuItemId(home.getId());
        Assert.assertEquals("failure - expected pages id match", home.getId(), page.getId());
        Assert.assertEquals("failure - expected pages title match", home.getTitle(), page.getTitle());
    }

    @Test
    public void shouldCheckNotUpdateRowsWhileUpdatingPage() throws Exception {
        String tempIdentifier = row.getIdentifier();
        home.setTitle("test");
        home.getRows().get(0).setIdentifier("test");
        this.pageRepository.save(home);
        Assert.assertEquals("failure - expected row attribute match", tempIdentifier, this.sectionRepository.findOne(row.getId()).getIdentifier());
    }

    @Test
    public void shouldCheckNotUpdateMenuItemWhileUpdatingPage() throws Exception {
        String tempName = homeMenuItem.getName();
        home.setTitle("test");
        home.getMenuItem().setName("test");
        this.pageRepository.save(home);
        Assert.assertEquals("failure - expected menuItem attribute match", tempName, this.menuItemRepository.findOne(homeMenuItem.getId()).getName());
    }

    @Test
    public void shouldCheckDeleteRowsWhenPageDeleted() throws Exception {
        this.pageRepository.delete(home.getId());
        Section check = this.sectionRepository.findOne(row.getId());
        Assert.assertNull("failure - expected null", check);
    }

    @Test
    public void shouldCheckDeleteMenuItemWhenPageDeleted() throws Exception {
        this.pageRepository.delete(home.getId());
        Assert.assertNull("failure - expected null", this.pageRepository.findByMenuItemId(home.getMenuItem().getId()));
        Assert.assertTrue(!menuItemRepository.exists(home.getMenuItem().getId()));
    }
}

