package org.gh.onboarding.model.component.section;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.gh.onboarding.model.Page;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "Row")
public class Row extends Section {

    @JsonBackReference
    @ManyToOne
    @JoinColumn(nullable = false)
    private Page page;

    @Column(nullable = false)
    private Integer sequence;

    public Row() {
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
}
