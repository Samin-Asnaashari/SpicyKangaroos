package org.gh.onboarding.service.inteface;

import org.gh.onboarding.model.component.style.Style;
import org.gh.onboarding.model.upload.ImageData;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUploadService {

    Style uploadBackgroundImage(MultipartFile file, Integer styleId) throws Exception;

    List<ImageData> getBackgroundImages() throws Exception;

    Style updateBackgroundImage(Integer id, ImageData imageData) throws Exception;
}
