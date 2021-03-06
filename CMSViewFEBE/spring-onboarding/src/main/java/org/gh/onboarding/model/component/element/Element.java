package org.gh.onboarding.model.component.element;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.gh.onboarding.model.component.section.Block;

import javax.persistence.*;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "elementType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Image.class, name = "Image"),
        @JsonSubTypes.Type(value = Link.class, name = "Link"),
        @JsonSubTypes.Type(value = Text.class, name = "Text"),
        @JsonSubTypes.Type(value = Video.class, name = "Video")
})
@Entity
@DiscriminatorColumn(name = "element_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Element {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String identifier;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(nullable = false)
    private Block parent;

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

    public Block getParent() {
        return parent;
    }

    public void setParent(Block parent) {
        this.parent = parent;
    }
}
