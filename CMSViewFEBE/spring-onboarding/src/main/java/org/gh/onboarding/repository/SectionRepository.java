package org.gh.onboarding.repository;

import org.gh.onboarding.model.component.section.Row;
import org.gh.onboarding.model.component.section.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, Integer> {

    List<Row> findByPageIdOrderBySequence(Integer pageId);
}
