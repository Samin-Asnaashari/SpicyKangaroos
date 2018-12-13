package org.gh.onboarding.service.inteface;

import org.gh.onboarding.model.component.style.Style;

public interface IStyleService {

    Style create(Style style) throws Exception;

    Style findOne(Integer id) throws Exception;

    Style update(Style style) throws Exception;
}
