package org.gh.onboarding.controller;

import org.gh.onboarding.model.component.style.Style;
import org.gh.onboarding.model.upload.ImageData;
import org.gh.onboarding.service.UploadService;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.pojo.ApiStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/uploads")
@Api(
        name = "Upload API",
        description = "provides the list of methods that makes uploading possible.",
        stage = ApiStage.RC
)
public class UploadController {

    private final String secure = "/secure";

    @Autowired
    private UploadService uploadService;

    @RequestMapping(value = "/backgrounds", method = RequestMethod.POST)
    @ResponseBody
    public Style uploadBackground(@RequestParam("file") MultipartFile file,
                                  @RequestParam("styleId") Integer styleId) throws Exception {
        return uploadService.uploadBackgroundImage(file, styleId);
    }

    @RequestMapping(value = "/backgrounds", method = RequestMethod.GET)
    @ResponseBody
    public List<ImageData> getBackgroundImages() throws Exception {
        return uploadService.getBackgroundImages();
    }

    @RequestMapping(value = secure + "/", method = RequestMethod.PUT)
    @ResponseBody
    public Style updateBackgroundImage(@RequestParam("id") Integer id, @RequestBody ImageData imageData) throws Exception {
        return uploadService.updateBackgroundImage(id, imageData);
    }
}
