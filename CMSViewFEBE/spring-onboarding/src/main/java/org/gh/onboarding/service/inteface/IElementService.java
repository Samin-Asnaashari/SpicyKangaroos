package org.gh.onboarding.service.inteface;

import org.gh.onboarding.model.component.element.Element;

import java.util.List;

public interface IElementService {

    Element create(Element element) throws Exception;

    List<Element> findAll(Integer blockId) throws Exception;

    Element findOne(Integer id) throws Exception;

    Element update(Element element) throws Exception;

    void delete(Integer id) throws Exception;
}
