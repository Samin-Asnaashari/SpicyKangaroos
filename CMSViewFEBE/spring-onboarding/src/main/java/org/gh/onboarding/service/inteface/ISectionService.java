package org.gh.onboarding.service.inteface;

import org.gh.onboarding.model.component.section.Row;
import org.gh.onboarding.model.component.section.Section;

import java.util.List;

public interface ISectionService {

    Section create(Section section) throws Exception;

    Section findOne(Integer id) throws Exception;

    List<Row> findAll(Integer pageId) throws Exception;

    Section update(Section section) throws Exception;

    void swapSequence (Integer sectionID1 , Integer sectionID2) throws Exception;

    void delete(Integer id) throws Exception;

    List<Row> deleteRow(Integer rowId, Integer pageId) throws Exception;

    List<Row> updateSequences(Integer sequence, Integer pageId) throws Exception;
}
