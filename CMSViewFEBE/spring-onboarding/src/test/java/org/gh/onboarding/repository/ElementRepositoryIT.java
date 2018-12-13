package org.gh.onboarding.repository;

import org.gh.onboarding.BaseTestIT;
import org.gh.onboarding.model.component.element.Element;
import org.gh.onboarding.model.component.element.Text;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ElementRepositoryIT extends BaseTestIT {

    @Before
    public void setUp() throws Exception {
        this.initial();
    }

    @After
    public void tearDown() throws Exception {
        this.cleanUp();
    }

    @Test
    public void shouldCheckMethodFindByParentId() throws Exception {
        List<Element> elements = this.elementRepository.findByParentId(block.getId());
        Assert.assertEquals("failure - expected element id match", textElement.getId(), elements.get(0).getId());
        Assert.assertEquals("failure - expected element id match", ((Text) textElement).getContent(), ((Text) elements.get(0)).getContent());
    }
}