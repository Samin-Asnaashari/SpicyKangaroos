package org.gh.onboarding.repository;

import org.gh.onboarding.BaseTestIT;
import org.gh.onboarding.model.component.section.Block;
import org.gh.onboarding.model.component.section.Row;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class SectionRepositoryIT extends BaseTestIT {

    @Before
    public void setUp() throws Exception {
        this.initial();
    }

    @After
    public void tearDown() throws Exception {
        this.cleanUp();
    }

    @Test
    public void shouldCheckMethodFindByPageIdOrderBySequenceForRows() throws Exception {
        List<Row> sections = this.sectionRepository.findByPageIdOrderBySequence(home.getId());
        Assert.assertEquals("failure - expected row id match", row.getId(), sections.get(0).getId());
        Assert.assertEquals("failure - expected row identifier match", row.getIdentifier(), sections.get(0).getIdentifier());
        Assert.assertEquals("failure - expected size", 1, sections.size());
    }

    @Test
    public void shouldCheckNotUpdateBlocksWhileUpdatingSection() throws Exception {
        String tempIdentifier = block.getIdentifier();
        row.setIdentifier("test");
        row.getBlocks().get(0).setIdentifier("test");
        this.sectionRepository.save(row);
        Assert.assertEquals("failure - expected block attribute match", tempIdentifier, this.blockRepository.findOne(block.getId()).getIdentifier());
    }

    @Test
    public void ShouldCheckNotUpdateStyleWhileUpdatingSection() throws Exception {
//        String tempColor = ((BackgroundStyle) rowStyle).getColor();
//        row.setIdentifier("test");
//        ((BackgroundStyle) row.getStyle()).setColor("test");
//        this.sectionRepository.save(row);
//        Assert.assertEquals("failure - expected style attribute match", tempColor, ((BackgroundStyle) this.styleRepository.findOne(rowStyle.getId())).getColor());
    }

    @Test
    public void ShouldCheckDeleteBlocksWhenSectionDeleted() throws Exception {
        this.sectionRepository.delete(row.getId());
        Block check = this.blockRepository.findOne(block.getId());
        Assert.assertNull("failure - expected null", check);
    }
}