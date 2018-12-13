package org.gh.onboarding.controller;

import org.gh.onboarding.model.component.section.Block;
import org.gh.onboarding.service.inteface.IBlockService;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.pojo.ApiStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/blocks")
@Api(
        name = "Block model API",
        description = "provides the list of methods that manages the blocks.",
        stage = ApiStage.RC
)
public class BlockController {

    private final String secure = "/secure";

    @Autowired
    private IBlockService blockService;

    @RequestMapping(value = secure, method = RequestMethod.POST, consumes = "application/json")
    @ApiMethod(description = "Creates a new block with the given information.")
    public Block createBlock(@RequestBody Block block) throws Exception {
        return this.blockService.create(block);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiMethod(description = "Returns a block with the given id.")
    public Block findBlock(@ApiPathParam(name = "id") @PathVariable Integer id) throws Exception {
        return this.blockService.findOne(id);
    }

    @RequestMapping(value = secure, method = RequestMethod.PUT)
    @ApiMethod(description = "Updates an existing block data. Consumes the complete JSON object with the block fields.")
    public Block updateBlock(@RequestBody Block block) throws Exception {
        return this.blockService.update(block);
    }

    @RequestMapping(value = secure + "/{id}", method = RequestMethod.DELETE)
    @ApiMethod(description = "Deletes a block (and any element object in the block).")
    public void deleteBlock(@ApiPathParam(name = "id") @PathVariable Integer id) throws Exception {
        this.blockService.delete(id);
    }
}
