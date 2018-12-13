package org.gh.onboarding.model.component.menu;

import com.fasterxml.jackson.annotation.*;
import org.gh.onboarding.model.Page;

import javax.persistence.*;
import java.util.List;

@Entity
public class MenuItem {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column (unique = true)
    private String url;

    @Column
    private Integer sequence;

    @Column
    private Integer level;

    @Column
    private Boolean draft;

    @Column
    private Boolean trash;

    // Parent menuItem
    @JsonBackReference(value = "MenuItem-MenuItem")
    @ManyToOne
    @JoinColumn
    private MenuItem menuItem;

    @JsonManagedReference(value = "MenuItem-MenuItem")
    @OrderBy(value = "sequence")
    @OneToMany(mappedBy = "menuItem", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<MenuItem> children;

    @JsonBackReference(value = "MenuItem-page")
    @OneToOne(mappedBy = "menuItem", cascade = CascadeType.REMOVE)
    @JoinColumn
    private Page page;

    public MenuItem() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public List<MenuItem> getChildren() {
        return children;
    }

    public void setChildren(List<MenuItem> children) {
        this.children = children;
    }

    public Boolean getDraft() {
        return draft;
    }

    public void setDraft(Boolean draft) {
        this.draft = draft;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Boolean getTrash() {
        return trash;
    }

    public void setTrash(Boolean trash) {
        this.trash = trash;
    }
}
