package org.gh.onboarding.service;

import org.gh.onboarding.model.component.section.Block;
import org.gh.onboarding.model.component.style.Style;
import org.gh.onboarding.repository.BlockRepository;
import org.gh.onboarding.service.inteface.IBlockService;
import org.gh.onboarding.service.inteface.IStyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BlockService implements IBlockService {

    @Autowired
    private BlockRepository blockRepository;
    @Autowired
    private IStyleService styleService;

    /**
     * Creates a style for the block to be created.
     * Creates a block, set the created style to it, and save it.
     */
    @Transactional
    @Override
    public Block create(Block block) throws Exception {
        Style style = new Style();
        style = this.styleService.create(style);
        block.setStyle(style);
        return this.blockRepository.save(block);
    }

    /**
     * Find one block with the given id, and return the block.
     */
    @Override
    public Block findOne(Integer id) throws Exception {
        Block block = this.blockRepository.findOne(id);

        if (block == null) {
            throw new Exception("Block with id=" + id + " does not exist.");
        }
        return block;
    }

    /**
     * Update a block with the given block data. Return the updated block.
     */
    @Transactional
    @Override
    public Block update(Block block) throws Exception {
        if (blockRepository.exists(block.getId())) {
            Block blockToSave = this.blockRepository.findOne(block.getId());
            blockToSave.setIdentifier(block.getIdentifier());
            return blockRepository.save(blockToSave);
        }
        throw new Exception("Block with id=" + block.getId() + " does not exist.");
    }

    /**
     * Delete a block with the given id.
     */
    @Transactional
    @Override
    public void delete(Integer id) throws Exception {
        if (this.blockRepository.exists(id)) {
            this.blockRepository.delete(id);
            return;
        }
        throw new Exception("Block with id=" + id + " does not exist.");
    }
}
