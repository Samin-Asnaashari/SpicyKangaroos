package org.gh.onboarding.service;

import org.gh.onboarding.model.component.element.*;
import org.gh.onboarding.repository.*;
import org.gh.onboarding.service.inteface.IElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ElementService implements IElementService {

    @Autowired
    private ElementRepository elementRepository;

    /**
     * Creates an element and save it. Return the created element.
     */
    @Transactional
    @Override
    public Element create(Element element) throws Exception {
        return this.elementRepository.save(element);
    }

    /**
     * Find all elements and return the found list of elements.
     */
    @Override
    public List<Element> findAll(Integer blockId) throws Exception {
        return this.elementRepository.findByParentId(blockId);
    }

    /**
     * Find one element with the given id, and return the found element.
     */
    @Override
    public Element findOne(Integer id) throws Exception {
        Element element = this.elementRepository.findOne(id);

        if (element == null) {
            throw new Exception("Element with id=" + id + " does not exist.");
        }
        return element;
    }

    /**
     * Update the element, and return it.
     * Different scenario's of elements, depending on the type.
     */
    @Transactional
    @Override
    public Element update(Element element) throws Exception {
        if (elementRepository.exists(element.getId())) {
            Element elementToSave = this.elementRepository.findOne(element.getId());

            // Check the type; Should be the same type.
            if (!element.getClass().equals(elementToSave.getClass())) {
                throw new Exception("Element with id=" + element.getId() + " is of a different type! ("
                        + elementToSave.getClass().getSimpleName() + ")");
            }

            elementToSave.setIdentifier(element.getIdentifier());

            if (element instanceof Image) {
                // Setting the Image fields.
                ((Image) elementToSave).setName(((Image) element).getName());
                ((Image) elementToSave).setUrl(((Image) element).getUrl());
            } else if (element instanceof Link) {
                // Setting the Link fields.
                ((Link) elementToSave).setUrl(((Link) element).getUrl());
                ((Link) elementToSave).setValue(((Link) element).getValue());
            } else if (element instanceof Text) {
                // Setting the Text fields.
                ((Text) elementToSave).setContent(((Text) element).getContent());
            } else if (element instanceof Video) {
                // Setting the Video fields.
                ((Video) elementToSave).setUrl(((Video) element).getUrl());
                ((Video) elementToSave).setTitle(((Video) element).getTitle());
            }
            // Update and return.
            return this.elementRepository.save(elementToSave);
        }
        throw new Exception("Element with id=" + element.getId() + " does not exist.");
    }

    /**
     * Delete an element with the given id.
     */
    @Transactional
    @Override
    public void delete(Integer id) throws Exception {
        if (this.elementRepository.exists(id)) {
            this.elementRepository.delete(id);
            return;
        }
        throw new Exception("Element with id=" + id + " does not exist.");
    }
}
