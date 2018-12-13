package org.gh.onboarding.model.component.footer;

import org.gh.onboarding.model.component.section.Section;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "Footer")
public class Footer extends Section {
}
