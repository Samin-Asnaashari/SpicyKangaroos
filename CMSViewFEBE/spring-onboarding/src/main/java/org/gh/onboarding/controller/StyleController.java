package org.gh.onboarding.controller;

import org.gh.onboarding.model.component.style.Style;
import org.gh.onboarding.service.inteface.IStyleService;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.pojo.ApiStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/styles")
@Api(
        name = "Style model API",
        description = "provides the list of methods that manages the styles.",
        stage = ApiStage.RC
)
public class StyleController {

    private final String secure = "/secure";

    @Autowired
    private IStyleService styleService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiMethod(description = "Returns a style with the given id.")
    public Style findStyle(@ApiPathParam(name = "id") @PathVariable Integer id) throws Exception {
        return this.styleService.findOne(id);
    }

    @RequestMapping(value = secure, method = RequestMethod.PUT)
    @ApiMethod(description = "Updates an existing style data. Consumes the complete JSON object with the style fields.")
    public Style updateStyle(@RequestBody Style style) throws Exception {
        return this.styleService.update(style);
    }
}
