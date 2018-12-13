package org.gh.onboarding.service;

import org.gh.onboarding.BaseServiceTest;
import org.gh.onboarding.model.component.style.Style;
import org.gh.onboarding.repository.StyleRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StyleServiceTest extends BaseServiceTest {

    @InjectMocks
    private StyleService styleService;

    @Mock
    private StyleRepository styleRepository;

    private static Style backgroundStyle;
    private static Style fontStyle;

    @Before
    public void setUp() throws Exception {

        backgroundStyle = new Style();
        backgroundStyle.setId(1);
//        ((BackgroundStyle) backgroundStyle).setColor("black");
//        ((BackgroundStyle) backgroundStyle).setImage(null);
//        ((BackgroundStyle) backgroundStyle).setOpacity("100");

        fontStyle = new Style();
        fontStyle.setId(2);
//        ((FontStyle) fontStyle).setColor("pink");
//        ((FontStyle) fontStyle).setAlign("Center");

        when(styleRepository.save(backgroundStyle)).thenReturn(backgroundStyle);
        when(styleRepository.save(fontStyle)).thenReturn(fontStyle);
        when(styleRepository.findOne(1)).thenReturn(backgroundStyle);
        when(styleRepository.findOne(2)).thenReturn(fontStyle);
        when(styleRepository.findAll()).thenReturn(Arrays.asList(backgroundStyle, fontStyle));
    }

    @Test
    public void create() throws Exception {
        //GIVEN

        //WHEN
        Style test = this.styleService.create(backgroundStyle);

        //THEN
        verify(styleRepository, Mockito.times(1)).save(test);
//        Assert.assertNotNull("failure - expected not null", test);
//        Assert.assertNotNull("failure - expected id attribute not null", test.getId());
//        Assert.assertEquals("failure - expected color attribute match", ((BackgroundStyle)backgroundStyle).getColor(), ((BackgroundStyle) test).getColor());
//        Assert.assertEquals("failure - expected opacity attribute match",((BackgroundStyle)backgroundStyle).getOpacity(), ((BackgroundStyle) test).getOpacity());
//        Assert.assertEquals("failure - expected image attribute match", ((BackgroundStyle) backgroundStyle).getImage(),((BackgroundStyle) test).getImage());
    }

    @Test
    public void findOne() throws Exception {
        //GIVEN
        Integer id = 1;

        //WHEN
        Style styleEntity = this.styleService.findOne(id);

        //THEN
        Assert.assertNotNull("failure - expected not null", styleEntity);
        Assert.assertEquals("failure - expected id attribute match", id, styleEntity.getId());
    }

    @Test
    public void update() throws Exception {
        //GIVEN
        when(styleRepository.exists(backgroundStyle.getId())).thenReturn(true);

        //WHEN
//        String updatedColor = ((BackgroundStyle) backgroundStyle).getColor() + " test";
//        ((BackgroundStyle) backgroundStyle).setColor(updatedColor);
//        Style updatedStyle = this.styleService.update(backgroundStyle);

        //THEN
//        verify(styleRepository, Mockito.times(1)).save(updatedStyle);
//        Assert.assertNotNull("failure - expected not null", updatedStyle);
//        Assert.assertEquals("failure - expected id attribute match", backgroundStyle.getId(), updatedStyle.getId());
//        Assert.assertEquals("failure - expected color attribute match", updatedColor, ((BackgroundStyle) updatedStyle).getColor());
//        Assert.assertEquals("failure - expected opacity  attribute match", ((BackgroundStyle) backgroundStyle).getOpacity(), ((BackgroundStyle) updatedStyle).getOpacity());
    }

    @Test(expected = Exception.class)
    public void updateNotFound() throws Exception {
        //GIVEN
        fontStyle.setId(Integer.MAX_VALUE);

        //WHEN
//        ((FontStyle) fontStyle).setAlign("test");
//        this.styleService.update(fontStyle);
    }

    @Test
    public void updateWrongType() throws Exception {
        //GIVEN
        Exception exception = null;
        Integer id = 1;
        Integer change = 2;

        //WHEN
        Style entity = this.styleService.findOne(id);
        entity.setId(change);
//        String updatedColor = ((BackgroundStyle) entity).getColor() + " test";
//        ((BackgroundStyle) entity).setColor(updatedColor);
        try {
            this.styleService.update(entity);
        } catch (Exception e) {
            exception = e;
        }

        //THEN
        Assert.assertNotNull("failure - expected exception", exception);
        Assert.assertTrue("failure - expected exception", true);
    }

}