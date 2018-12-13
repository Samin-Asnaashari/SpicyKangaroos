package org.gh.onboarding.model.component.menu;

import org.gh.onboarding.model.component.section.Section;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "Navigation")
public class Navigation extends Section {

    public Navigation() {
    }
}
