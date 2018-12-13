package org.gh.onboarding.controller;

import org.gh.onboarding.model.component.element.Element;
import org.gh.onboarding.service.inteface.*;
import org.jsondoc.core.annotation.*;
import org.jsondoc.core.pojo.ApiStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/elements")
@Api(
        name = "Element model API",
        description = "provides the list of methods that manages the elements.",
        stage = ApiStage.RC
)
public class ElementController {

    private final String secure = "/secure";

    @Autowired
    private IElementService elementService;

    @RequestMapping(value = secure, method = RequestMethod.POST, consumes = "application/json")
    @ApiMethod(description = "Creates a new element with the given information. An element can be of different types.")
    public Element createElement(@RequestBody Element element) throws Exception {
        return this.elementService.create(element);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiMethod(description = "Returns an element with the given id.")
    public Element findOneElement(@ApiPathParam(name = "id") @PathVariable Integer id) throws Exception {
        return this.elementService.findOne(id);
    }

    @RequestMapping(value = secure, method = RequestMethod.PUT)
    @ApiMethod(description = "Updates an existing element data. Consumes the complete JSON object with the element fields.")
    public Element updateElement(@RequestBody Element element) throws Exception {
        return this.elementService.update(element);
    }

    @RequestMapping(value = secure + "/{id}", method = RequestMethod.DELETE)
    @ApiMethod(description = "Deletes an element with the given id.")
    public void deleteElement(@ApiPathParam(name = "id") @PathVariable Integer id) throws Exception {
        this.elementService.delete(id);
    }
}
