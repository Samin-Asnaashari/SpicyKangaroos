package org.gh.onboarding.service;

import org.gh.onboarding.model.component.section.Row;
import org.gh.onboarding.model.component.section.Section;
import org.gh.onboarding.model.component.style.Style;
import org.gh.onboarding.repository.*;
import org.gh.onboarding.service.inteface.IPageService;
import org.gh.onboarding.service.inteface.ISectionService;
import org.gh.onboarding.service.inteface.IStyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SectionService implements ISectionService {

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private IPageService pageService;

    @Autowired
    private IStyleService styleService;

    /**
     * Creates a style for the to be created Section.
     * Create the section, and save it. The created section is returned.
     */
    @Transactional
    @Override
    public Section create(Section section) throws Exception {
        Style style = new Style();
        style = this.styleService.create(style);
        section.setStyle(style);
        return this.sectionRepository.save(section);
    }

    /**
     * Finds the section with the given id, and returns the section.
     */
    @Override
    public Section findOne(Integer id) throws Exception {
        Section section = this.sectionRepository.findOne(id);

        if (section == null) {
            throw new Exception("Section with id=" + id + " does not exist.");
        }
        return section;
    }

    /**
     * Find all sections belonging to a specific page.
     * Return the list of found sections in ordered sequence.
     */
    @Override
    public List<Row> findAll(Integer pageId) throws Exception {
        return this.sectionRepository.findByPageIdOrderBySequence(pageId);
    }

    /**
     * Update a section's data with the given section.
     * Returns the updated section.
     */
    @Transactional
    @Override
    public Section update(Section section) throws Exception {
        if (sectionRepository.exists(section.getId())) {
            // Setting the base fields
            Section sectionToSave = this.sectionRepository.findOne(section.getId());
            sectionToSave.setIdentifier(section.getIdentifier());

            // Check the type; Should be the same type.
            if (!section.getClass().equals(sectionToSave.getClass())) {
                throw new Exception("Section with id=" + section.getId() + " is of a different type! (" + sectionToSave.getClass().getSimpleName() + ")");
            }

            if (section instanceof Row) {
                // Setting the Row fields.
                ((Row) sectionToSave).setSequence(((Row) section).getSequence());
            }
            // Update and return.
            return this.sectionRepository.save(sectionToSave);
        }
        throw new Exception("Section with id=" + section.getId() + " does not exist.");
    }

    /**
     * Delete a section
     */
    @Transactional
    @Override
    public void delete(Integer id) throws Exception {
        if (this.sectionRepository.exists(id)) {
            this.sectionRepository.delete(id);
            return;
        }
        throw new Exception("Section with id=" + id + " does not exist.");
    }

    /**
     * Deletes a row. Whenever a row is deleted, the sequence of the other rows
     * belonging to the specific page has to be updated.
     * The affected changed sequence rows are returned.
     */
    @Transactional
    @Override
    public List<Row> deleteRow(Integer rowId, Integer pageId) throws Exception {
        Row row = (Row) this.findOne(rowId);
        this.pageService.findOne(pageId);

        List<Row> rows = this.sectionRepository.findByPageIdOrderBySequence(pageId);

        // Throw exception if row does not belong to the given page.
        if (!rows.contains(row)) {
            throw new Exception("Row with id=" + row.getId() +
                    " does not exist in the page with the given page id=" + pageId);
        }

        // Rows contains the rows after the to be deleted object.
        Integer index = rows.indexOf(row);
        rows.subList(0, index + 1).clear();

        // Delete row
        this.delete(row.getId());

        // Update affected rows and return the affected rows.
        for (Row item : rows) {
            item.setSequence(item.getSequence() - 1);
            this.update(item);
        }
        return rows;
    }

    /**
     * Updating the sequences of the sections belonging the a specific page.
     * The sequence of the other rows belonging to the specific page has to be updated.
     * The affected changed sequence rows are returned.
     */
    @Transactional
    @Override
    public List<Row> updateSequences(Integer sequence, Integer pageId) throws Exception {

        List<Row> rows = this.sectionRepository.findByPageIdOrderBySequence(pageId);

        Integer index = -1;
        for (Row item : rows) {
            if (item.getSequence().equals(sequence)) {
                index = rows.indexOf(item);
                break;
            }
        }

        // Rows contains the rows with sequences to be updated
        rows.subList(0, index + 1).clear();

        // Update affected rows.
        for (Row item : rows) {
            item.setSequence(item.getSequence() + 1);
            this.update(item);
        }
        return rows;
    }

    /**
     * Swapping the sequences of two sections.
     */
    @Transactional
    @Override
    public void swapSequence(Integer sectionId1, Integer sectionId2) throws Exception {
        if (sectionRepository.exists(sectionId1) && sectionRepository.exists(sectionId2)) {

            Section sectionToSave1 = this.sectionRepository.findOne(sectionId1);
            Section sectionToSave2 = this.sectionRepository.findOne(sectionId2);

            Integer tempSeq = ((Row) sectionToSave1).getSequence();
            ((Row) sectionToSave1).setSequence(((Row) sectionToSave2).getSequence());
            ((Row) sectionToSave2).setSequence(tempSeq);

            // Update.
            this.sectionRepository.save(sectionToSave1);
            this.sectionRepository.save(sectionToSave2);

        } else {
            throw new Exception("Section with id=" + sectionId1 + " or " + sectionId2 + " does not exist.");
        }
    }
}
