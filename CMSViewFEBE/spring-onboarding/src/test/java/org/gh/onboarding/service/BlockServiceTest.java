package org.gh.onboarding.service;

import org.gh.onboarding.BaseServiceTest;
import org.gh.onboarding.model.component.section.Block;
import org.gh.onboarding.model.component.section.Row;
import org.gh.onboarding.model.component.style.Style;
import org.gh.onboarding.repository.BlockRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class BlockServiceTest extends BaseServiceTest {

    @InjectMocks
    private BlockService blockService;

    @Mock
    private BlockRepository blockRepository;
    @Mock
    private StyleService styleService;

    private static Block block;

    @Before
    public void setUp() throws Exception {

        block = new Block();
        block.setId(1);
        block.setIdentifier("block-1");
        block.setParent(new Row()); //or Nav or Footer
        block.setElements(null);

        when(blockRepository.findOne(block.getId())).thenReturn(block);
    }

    @Test
    public void create() throws Exception {
        //GIVEN
        Style style = new Style();
        style.setId(1);
        when(styleService.create(Matchers.any(Style.class))).thenReturn(style);
        when(blockRepository.save(block)).thenReturn(block);

        //WHEN
        Block test = this.blockService.create(block);

        //THEN
        verify(styleService).create(Matchers.any());
        verify(blockRepository, Mockito.times(1)).save(block);
        verifyNoMoreInteractions(styleService);
        Assert.assertNotNull("failure - expected not null", test);
        Assert.assertNotNull("failure - expected id attribute not null", test.getId());
        Assert.assertEquals("failure - expected identifier attribute match", "block-1", test.getIdentifier());
        Assert.assertNotNull("failure - expected parent not null", test.getParent());
        Assert.assertNotNull("failure - expected style not null", test.getStyle());
        Assert.assertNull("failure - expected elements to be null", test.getElements());
    }

    @Test
    public void findOne() throws Exception {
        //GIVEN
        Integer id = 1;

        //WHEN
        Block entity = this.blockService.findOne(id);

        //THEN
        Assert.assertNotNull("failure - expected not null", entity);
        Assert.assertEquals("failure - expected id attribute match", id, entity.getId());
    }

    @Test(expected = Exception.class)
    public void findOneNotFound() throws Exception {
        //GIVEN
        Integer id = Integer.MAX_VALUE;

        //WHEN
        this.blockService.findOne(id);

        //THEN
        //Expected Exception
    }

    @Test
    public void update() throws Exception {
        //GIVEN
        when(blockRepository.exists(block.getId())).thenReturn(true);
        when(blockRepository.save(block)).thenReturn(block);

        //WHEN
        String updatedIdentifier = block.getIdentifier() + " test";
        block.setIdentifier(updatedIdentifier);
        Block updatedBlock = this.blockService.update(block);

        //THEN
        verify(blockRepository, Mockito.times(1)).save(block);
        Assert.assertNotNull("failure - expected not null", updatedBlock);
        Assert.assertEquals("failure - expected id attribute match", block.getId(), updatedBlock.getId());
        Assert.assertEquals("failure - expected identifier attribute match", updatedIdentifier, updatedBlock.getIdentifier());
    }

    @Test(expected = Exception.class)
    public void updateNotFound() throws Exception {
        //GIVEN
        block.setId(Integer.MAX_VALUE);

        //WHEN
        block.setIdentifier("test");
        this.blockService.update(block);

        //THEN
        //Expected Exception
    }

    @Test(expected = Exception.class)
    public void delete() throws Exception {
        //GIVEN
        Integer id = 1;

        //WHEN
        this.blockService.delete(id);

        this.blockService.findOne(id);

        //THEN
        //Expected Exception
    }

}