package org.gh.onboarding.service;

import org.gh.onboarding.BaseServiceTest;
import org.gh.onboarding.model.component.element.*;
import org.gh.onboarding.model.component.section.Block;
import org.gh.onboarding.repository.ElementRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ElementServiceTest extends BaseServiceTest {

    @InjectMocks
    private ElementService elementService;

    @Mock
    private ElementRepository elementRepository;

    private static Element image;
    private static Element link;
    private static Element text;
    private static Element video;

    @Before
    public void setUp() throws Exception {

        image = new Image();
        image.setId(1);
        image.setIdentifier("image-1");
        image.setParent(new Block());
        ((Image) image).setName("image-1");
        ((Image) image).setUrl("/image/1");

        link = new Link();
        link.setId(2);
        link.setIdentifier("link-1");
        link.setParent(new Block());
        ((Link) link).setValue("link-1");
        ((Link) link).setUrl("/http://www.link-1.org");

        text = new Text();
        text.setId(3);
        text.setIdentifier("text-1");
        text.setParent(new Block());
        ((Text) text).setContent("welcome on-board...");

        video = new Video();
        video.setId(4);
        video.setIdentifier("video-1");
        video.setParent(new Block());
        ((Video) video).setTitle("video-1");
        ((Video) video).setUrl("/https://www.yotube.com/video/1");

        when(elementRepository.save(image)).thenReturn(image);
        when(elementRepository.save(link)).thenReturn(link);
        when(elementRepository.save(text)).thenReturn(text);
        when(elementRepository.save(video)).thenReturn(video);
        when(elementRepository.findOne(1)).thenReturn(image);
        when(elementRepository.findOne(2)).thenReturn(link);
        when(elementRepository.findOne(3)).thenReturn(text);
        when(elementRepository.findOne(4)).thenReturn(video);
        when(elementRepository.findAll()).thenReturn(Arrays.asList(image, link, text, video));
    }

    @Test
    public void create() throws Exception {
        //GIVEN

        //WHEN
        Element test = this.elementService.create(text);

        //THEN
        verify(elementRepository, Mockito.times(1)).save(test);
        Assert.assertNotNull("failure - expected not null", test);
        Assert.assertNotNull("failure - expected id attribute not null", test.getId());
        Assert.assertEquals("failure - expected content match", "welcome on-board...", ((Text) test).getContent());
        Assert.assertNotNull("failure - expected block not null", test.getParent());
    }

    @Test
    public void findOne() throws Exception {
        //GIVEN
        Integer id = 1;

        //WHEN
        Element elementEntity = this.elementService.findOne(id);

        //THEN
        Assert.assertNotNull("failure - expected not null", elementEntity);
        Assert.assertEquals("failure - expected id attribute match", id, elementEntity.getId());
    }

    @Test(expected = Exception.class)
    public void findOneNotFound() throws Exception {
        //GIVEN
        Integer id = Integer.MAX_VALUE;

        //WHEN
        this.elementService.findOne(id);

        //THEN
        //Expected Exception
    }

    @Test
    public void update() throws Exception {
        //GIVEN
        when(elementRepository.exists(link.getId())).thenReturn(true);

        //WHEN
        String updatedValue = ((Link) link).getValue() + " test";
        ((Link) link).setValue(updatedValue);
        Element updatedElement = this.elementService.update(link);

        //THEN
        verify(elementRepository, Mockito.times(1)).save(updatedElement);
        Assert.assertNotNull("failure - expected not null", updatedElement);
        Assert.assertEquals("failure - expected id attribute match", link.getId(), updatedElement.getId());
        Assert.assertEquals("failure - expected value attribute match", updatedValue, ((Link) updatedElement).getValue());
        Assert.assertNotNull("failure - expected parent not null", updatedElement.getParent());
    }

    @Test(expected = Exception.class)
    public void updateNotFound() throws Exception {
        //GIVEN
        image.setId(Integer.MAX_VALUE);

        //WHEN
        ((Image) image).setName("test");
        this.elementService.update(image);
    }

    @Test
    public void updateWrongType() throws Exception {
        //GIVEN
        Exception exception = null;
        Integer id = 2;
        Integer change = 3;

        //WHEN
        Element entity = this.elementService.findOne(id);
        entity.setId(change);
        String updatedValue = ((Link) entity).getValue() + " test";
        ((Link) entity).setValue(updatedValue);
        try {
            this.elementService.update(entity);
        } catch (Exception e) {
            exception = e;
        }

        //THEN
        Assert.assertNotNull("failure - expected exception", exception);
        Assert.assertTrue("failure - expected exception", true);
    }

    @Test(expected = Exception.class)
    public void delete() throws Exception {
        //GIVEN
        Integer id = 4;

        //WHEN
        this.elementService.delete(id);

        this.elementService.findOne(id);

        //THEN
        //Expected Exception
    }
}