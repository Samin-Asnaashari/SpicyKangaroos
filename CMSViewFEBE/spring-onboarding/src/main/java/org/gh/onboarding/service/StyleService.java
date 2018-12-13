package org.gh.onboarding.service;

import org.gh.onboarding.model.component.style.Style;
import org.gh.onboarding.repository.*;
import org.gh.onboarding.service.inteface.IStyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StyleService implements IStyleService {

    @Autowired
    private StyleRepository styleRepository;

    /**
     * Create a new style, and return the created style.
     */
    @Transactional
    @Override
    public Style create(Style style) throws Exception {
        return this.styleRepository.save(style);
    }

    /**
     * Find one style with the given id.
     */
    @Override
    public Style findOne(Integer id) throws Exception {
        Style style = this.styleRepository.findOne(id);

        if (style == null) {
            throw new Exception("Style with id=" + id + " does not exist.");
        }
        return style;
    }

    /**
     * Updates the style, and return the updates style.
     */
    @Transactional
    @Override
    public Style update(Style style) throws Exception {
        if (styleRepository.exists(style.getId())) {
            Style styleToSave = this.styleRepository.findOne(style.getId());

            styleToSave.setBackgroundColor(style.getBackgroundColor());
            styleToSave.setBackgroundImage(style.getBackgroundImage());
            styleToSave.setImageHeight(style.getImageHeight());
            styleToSave.setImageWidth(style.getImageWidth());
            styleToSave.setTextColor(style.getTextColor());
            styleToSave.setPaddingTop(style.getPaddingTop());
            styleToSave.setPaddingBottom(style.getPaddingBottom());

            // Update and return.
            return this.styleRepository.save(styleToSave);
        }
        throw new Exception("Style with id=" + style.getId() + " does not exist.");
    }
}
