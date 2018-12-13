package org.gh.onboarding.controller;

import org.gh.onboarding.model.component.section.Row;
import org.gh.onboarding.model.component.section.Section;
import org.gh.onboarding.service.inteface.*;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.pojo.ApiStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/sections")
@Api(
        name = "Section model API",
        description = "provides the list of methods that manages the sections.",
        stage = ApiStage.RC
)
public class SectionController {

    private final String secure = "/secure";

    @Autowired
    private ISectionService sectionService;

    @RequestMapping(value = secure, method = RequestMethod.POST, consumes = "application/json")
    @ApiMethod(description = "Creates a new section with the given information. A section can be of different types.")
    public Section createSection(@RequestBody Section section) throws Exception {
        return this.sectionService.create(section);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiMethod(description = "Returns a section with the given id.")
    public Section findSection(@ApiPathParam(name = "id") @PathVariable Integer id) throws Exception {
        return this.sectionService.findOne(id);
    }

    @RequestMapping(value = "/pages/{pageId}", method = RequestMethod.GET)
    @ApiMethod(description = "Returns all sections belonging to a page with the given page id.")
    public List<Row> findSections(@ApiPathParam(name = "pageId") @PathVariable Integer pageId) throws Exception {
        return this.sectionService.findAll(pageId);
    }

    @RequestMapping(value = secure, method = RequestMethod.PUT)
    @ApiMethod(description = "Updates an existing section data. Consumes the complete JSON object with the section fields.")
    public Section updateSection(@RequestBody Section section) throws Exception {
        return this.sectionService.update(section);
    }

    @RequestMapping(value = secure + "/{sectionID1}/{sectionID2}", method = RequestMethod.PUT)
    @ApiMethod(description = "Updates an existing section data after the changes in sections sequence.It get the 2 section ID which their sequence need to be swaped.")
    public void swapSectionsSequence(@ApiPathParam(name = "id1") @PathVariable Integer sectionID1,@ApiPathParam(name = "id2") @PathVariable Integer sectionID2) throws Exception {
        this.sectionService.swapSequence(sectionID1,sectionID2);
    }

    @RequestMapping(value = secure + "/{id}", method = RequestMethod.DELETE)
    @ApiMethod(description = "Deletes a section (and any object in the section).")
    public void deleteSection(@ApiPathParam(name = "id") @PathVariable Integer id) throws Exception {
        this.sectionService.delete(id);
    }

    @RequestMapping(value = secure + "/", method = RequestMethod.DELETE)
    @ApiMethod(description = "Deletes a row (and any object in the row) belonging to a page with pageId." +
            "Returns a list of affected rows (with updated sequences).")
    public List<Row> deleteRow(@RequestParam(value = "row") Integer id,
                               @RequestParam(value = "page") Integer pageId) throws Exception {
        return this.sectionService.deleteRow(id, pageId);
    }

    @RequestMapping(value = secure + "/", method = RequestMethod.PUT)
    @ApiMethod(description = "Updates the row-sequences of a specific page." +
            "Returns the list of rows that belongs to the page with the given pageId that were affected.")
    public List<Row> updateSequences(@RequestParam(value = "sequence") Integer sequence,
                                     @RequestParam(value = "page") Integer pageId) throws Exception {
        return this.sectionService.updateSequences(sequence, pageId);
    }
}