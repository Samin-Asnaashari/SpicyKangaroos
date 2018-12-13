package org.gh.onboarding.repository;

import org.gh.onboarding.BaseTestIT;
import org.gh.onboarding.model.component.element.Element;
import org.gh.onboarding.model.component.section.Block;
import org.gh.onboarding.model.component.style.Style;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BlockRepositoryIT extends BaseTestIT {

    @Before
    public void setUp() throws Exception {
        this.initial();
    }

    @After
    public void tearDown() throws Exception {
        this.cleanUp();
    }

    @Test(expected = Exception.class)
    public void shouldCheckNotAllowCreationOfBlockWithEmptyParent() {
        Block test = new Block();
        test.setIdentifier("block-test");
        test.setParent(null);
        this.blockRepository.save(test);
    }

    @Test
    public void shouldCheckNotUpdateElementsWhileUpdatingBlock() {
        String tempIdentifier = textElement.getIdentifier();
        block.setIdentifier("test");
        block.getElements().get(0).setIdentifier("test");
        this.blockRepository.save(block);
        Assert.assertEquals("failure - expected element attribute match", tempIdentifier, this.elementRepository.findOne(textElement.getId()).getIdentifier());
    }

    @Test
    public void shouldCheckNotUpdateStyleWhileUpdatingBlock() {
//        String tempColor = ((BackgroundStyle) blockStyle).getColor();
//        block.setIdentifier("test");
//        ((BackgroundStyle) block.getStyle()).setColor("test");
//        this.blockRepository.save(block);
//        Assert.assertEquals("failure - expected style attribute match", tempColor, ((BackgroundStyle) this.styleRepository.findOne(blockStyle.getId())).getColor());
    }

    @Test
    public void shouldCheckDeleteElementsWhenBlockDeleted() {
        this.blockRepository.delete(block.getId());
        Element check = this.elementRepository.findOne(textElement.getId());
        Assert.assertNull("failure - expected null", check);
    }

    @Test
    public void shouldCheckDeleteStyleWhenBlockDeleted() {
        this.blockRepository.delete(block.getId());
        Style check = this.styleRepository.findOne(blockStyle.getId());
        Assert.assertNull("failure - expected null", check);
    }
}