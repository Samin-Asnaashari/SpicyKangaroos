package org.gh.onboarding.repository;

import org.gh.onboarding.model.component.element.Element;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElementRepository extends JpaRepository<Element, Integer> {

    List<Element> findByParentId(Integer blockId);
}
