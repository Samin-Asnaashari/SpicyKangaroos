package org.gh.onboarding.repository;

import org.gh.onboarding.model.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageRepository extends JpaRepository<Page, Integer> {

    Page findByMenuItemId(Integer id);

    Page findByMenuItemUrl(String url);
}
