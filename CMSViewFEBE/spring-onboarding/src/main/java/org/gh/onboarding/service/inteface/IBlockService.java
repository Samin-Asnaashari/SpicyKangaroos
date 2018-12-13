package org.gh.onboarding.service.inteface;

import org.gh.onboarding.model.component.section.Block;

public interface IBlockService {

    Block create(Block block) throws Exception;

    Block findOne(Integer id) throws Exception;

    Block update(Block block) throws Exception;

    void delete(Integer id) throws Exception;
}
