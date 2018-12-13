package org.gh.onboarding.repository;

import org.gh.onboarding.model.component.menu.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {

    List<MenuItem> findByMenuItemIsNullAndTrashIsFalseOrderBySequence();

    List<MenuItem> findByMenuItemIsNullAndDraftIsFalseAndTrashIsFalseOrderBySequence();

    List<MenuItem> findByOrderBySequence();

    List<MenuItem> findByMenuItemIsNullAndTrashIsTrue();

    Integer countByMenuItemIsNull();

    MenuItem findByChildrenId(Integer childId);
}
