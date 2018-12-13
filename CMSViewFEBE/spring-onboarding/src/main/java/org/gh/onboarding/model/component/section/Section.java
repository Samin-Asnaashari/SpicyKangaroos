package org.gh.onboarding.model.component.section;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.gh.onboarding.model.component.footer.Footer;
import org.gh.onboarding.model.component.menu.Navigation;
import org.gh.onboarding.model.component.style.Style;

import javax.persistence.*;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "sectionType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Row.class, name = "Row"),
        @JsonSubTypes.Type(value = Navigation.class, name = "Navigation"),
        @JsonSubTypes.Type(value = Footer.class, name = "Footer")
})
@Entity
@DiscriminatorColumn(name = "section_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String identifier;

    @OneToOne(cascade = CascadeType.REMOVE , orphanRemoval = true)
    @JoinColumn
    private Style style;

    @JsonManagedReference
    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Block> blocks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }
}
