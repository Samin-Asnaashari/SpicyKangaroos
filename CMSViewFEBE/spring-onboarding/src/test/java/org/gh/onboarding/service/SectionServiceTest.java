package org.gh.onboarding.service;

import org.gh.onboarding.BaseServiceTest;
import org.gh.onboarding.model.Page;
import org.gh.onboarding.model.component.footer.Footer;
import org.gh.onboarding.model.component.menu.Navigation;
import org.gh.onboarding.model.component.section.Row;
import org.gh.onboarding.model.component.section.Section;
import org.gh.onboarding.model.component.style.Style;
import org.gh.onboarding.repository.SectionRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class SectionServiceTest extends BaseServiceTest {

    @InjectMocks
    private SectionService sectionService;

    @Mock
    private SectionRepository sectionRepository;

    @Mock
    private StyleService styleService;

    private static Section row;
    private static Section navigation;
    private static Section footer;

    @Before
    public void setUp() throws Exception {

        row = new Row();
        row.setId(1);
        row.setIdentifier("row-1");
        row.setBlocks(null);
        ((Row) row).setPage(new Page());
        ((Row) row).setSequence(1);

        navigation = new Navigation();
        navigation.setId(2);
        navigation.setIdentifier("nav-1");
        navigation.setBlocks(null);

        footer = new Footer();
        footer.setId(3);
        footer.setIdentifier("footer-1");
        footer.setBlocks(null);

        when(sectionRepository.save(row)).thenReturn(row);
        when(sectionRepository.save(navigation)).thenReturn(navigation);
        when(sectionRepository.save(footer)).thenReturn(footer);
        when(sectionRepository.findOne(1)).thenReturn(row);
        when(sectionRepository.findOne(2)).thenReturn(navigation);
        when(sectionRepository.findOne(3)).thenReturn(footer);
        when(sectionRepository.findAll()).thenReturn(Arrays.asList(row, navigation, footer));
    }

    @Test
    public void testCreate() throws Exception {
        //GIVEN
        Style style = new Style();
        style.setId(1);
        when(styleService.create(Matchers.any(Style.class))).thenReturn(style);

        //WHEN
        Section test = this.sectionService.create(row);

        //THEN
        verify(styleService).create(Matchers.any());
        verify(sectionRepository, Mockito.times(1)).save(row);
        verifyNoMoreInteractions(styleService);
        Assert.assertNotNull("failure - expected not null", test);
        Assert.assertNotNull("failure - expected id attribute not null", test.getId());
        Assert.assertEquals("failure - expected identifier match", "row-1", row.getIdentifier());
        Assert.assertNotNull("failure - expected style not null", test.getStyle());

        Assert.assertNotNull("failure - expected row parent not null", ((Row) row).getPage());
    }

    @Test
    public void testFindOne() throws Exception {
        //GIVEN
        Integer id = 1;

        //WHEN
        Section sectionEntity = this.sectionService.findOne(id);

        //THEN
        Assert.assertNotNull("failure - expected not null", sectionEntity);
        Assert.assertEquals("failure - expected id attribute match", id, sectionEntity.getId());
    }

    @Test(expected = Exception.class)
    public void testFindOneNotFound() throws Exception {
        //GIVEN
        Integer id = Integer.MAX_VALUE;

        //WHEN
        this.sectionService.findOne(id);

        //THEN
        //Expected Exception
    }

    @Test
    public void testUpdate() throws Exception {
        //GIVEN
        when(sectionRepository.exists(row.getId())).thenReturn(true);

        //WHEN
        String updatedIdentifier = row.getIdentifier() + " test";
        row.setIdentifier(updatedIdentifier);
        Section updatedSection = this.sectionService.update(row);

        //THEN
        Assert.assertNotNull("failure - expected not null", updatedSection);
        Assert.assertEquals("failure - expected id attribute match", row.getId(), updatedSection.getId());
        Assert.assertEquals("failure - expected content match", updatedIdentifier, updatedSection.getIdentifier());
    }

    @Test(expected = Exception.class)
    public void testUpdateNotFound() throws Exception {
        //GIVEN
        footer.setId(Integer.MAX_VALUE);

        //WHEN
        footer.setIdentifier("test");
        this.sectionService.update(footer);

        //THEN
        //Expected Exception
    }

    @Test
    public void testUpdateWrongType() throws Exception {
        //GIVEN
        Exception exception = null;
        Integer id = 1;
        Integer change = 2;

        //WHEN
        Section entity = this.sectionService.findOne(id);
        entity.setId(change);
        String updatedIdentifier = row.getIdentifier() + " test";
        row.setIdentifier(updatedIdentifier);
        try {
            this.sectionService.update(entity);
        } catch (Exception e) {
            exception = e;
        }

        //THEN
        Assert.assertNotNull("failure - expected exception", exception);
        Assert.assertTrue("failure - expected exception", true);
    }

    @Test(expected = Exception.class)
    public void testDelete() throws Exception {
        //GIVEN
        Integer id = 3;

        //WHEN
        this.sectionService.delete(id);

        this.sectionService.findOne(id);

        //THEN
        //Expected Exception
    }

}