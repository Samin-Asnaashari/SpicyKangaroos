package org.gh.onboarding;

import org.gh.onboarding.model.Page;
import org.gh.onboarding.model.component.element.Element;
import org.gh.onboarding.model.component.element.Text;
import org.gh.onboarding.model.component.menu.MenuItem;
import org.gh.onboarding.model.component.section.Block;
import org.gh.onboarding.model.component.section.Row;
import org.gh.onboarding.model.component.section.Section;
import org.gh.onboarding.model.component.style.Style;
import org.gh.onboarding.repository.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

/**
 * Abstract layer for Integration tests
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseTestIT {

    //TODO divide it

    @Autowired
    public PageRepository pageRepository;
    @Autowired
    public MenuItemRepository menuItemRepository;
    @Autowired
    public SectionRepository sectionRepository;
    @Autowired
    public BlockRepository blockRepository;
    @Autowired
    public ElementRepository elementRepository;
    @Autowired
    public StyleRepository styleRepository;

    protected Page home;
    protected Page about;
    protected MenuItem homeMenuItem;
    protected MenuItem aboutMenuItem;
    protected Style rowStyle;
    protected Section row;
    protected Style blockStyle;
    protected Block block;
    protected Element textElement;

    public void initial() {

        this.home = new Page();
        home.setTitle("home");

        this.about = new Page();
        about.setTitle("about");

        this.homeMenuItem = new MenuItem();
        homeMenuItem.setName("home");
        homeMenuItem.setUrl("home");
        homeMenuItem.setSequence(1);
        homeMenuItem.setDraft(false);
        homeMenuItem.setLevel(0);
        homeMenuItem.setTrash(false);
        this.menuItemRepository.save(homeMenuItem);

        this.aboutMenuItem = new MenuItem();
        aboutMenuItem.setName("about");
        aboutMenuItem.setUrl("about");
        aboutMenuItem.setSequence(1);
        aboutMenuItem.setDraft(true);
        aboutMenuItem.setLevel(0);
        aboutMenuItem.setTrash(false);
        aboutMenuItem.setMenuItem(homeMenuItem);
        this.menuItemRepository.save(aboutMenuItem);

        home.setMenuItem(homeMenuItem);
        about.setMenuItem(aboutMenuItem);

        this.pageRepository.save(home);
        this.pageRepository.save(about);

        this.rowStyle = new Style();
        rowStyle.setBackgroundColor("black");
        rowStyle.setBackgroundImage(null);
        rowStyle.setImageHeight(50);
        rowStyle.setImageWidth(50);
        rowStyle.setPaddingBottom(30);
        rowStyle.setPaddingTop(30);
        rowStyle.setTextColor("white");
        this.styleRepository.save(rowStyle);

        this.row = new Row();
        row.setIdentifier("page1-row1");
        ((Row) row).setSequence(1);
        row.setStyle(rowStyle);
        ((Row) row).setPage(home);
        this.sectionRepository.save(row);

        this.blockStyle = new Style();
        this.styleRepository.save(blockStyle);

        this.block = new Block();
        block.setIdentifier("block-1");
        block.setParent(row);
        block.setStyle(blockStyle);
        this.blockRepository.save(block);

        this.textElement = new Text();
        ((Text) textElement).setContent("welcome");
        textElement.setIdentifier("text-1");
        textElement.setParent(block);
        this.elementRepository.save(textElement);

        home.setRows(Arrays.asList((Row) row));
        block.setElements(Arrays.asList((Text) textElement));
        row.setBlocks(Arrays.asList(block));
        homeMenuItem.setChildren(Arrays.asList(aboutMenuItem));
    }

    public void cleanUp() {
        this.pageRepository.deleteAll();
        this.menuItemRepository.deleteAll();
        this.sectionRepository.deleteAll();
        this.blockRepository.deleteAll();
        this.elementRepository.deleteAll();
        this.styleRepository.deleteAll();
    }


}
