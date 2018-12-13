package org.gh.onboarding.service;

import org.gh.onboarding.model.component.style.Style;
import org.gh.onboarding.model.upload.*;
import org.gh.onboarding.service.inteface.IUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class UploadService implements IUploadService {

    /**
     * Path fields. It's important to know where the uploading is taking place.
     */
    private String frontEndPath = "front-end\\";
    private String projectPath = "spring-onboarding\\.";
    private String backgroundFolder = "asset\\img-background";

    @Autowired
    StyleService styleService;

    /**
     * Redirect to front-end asset backgroundFolder (img-background).
     * Concatenate string to create the path.
     * (front-end, and back-end projects should always be parallel to each other).
     * @return ABSOLUTE PATH to the front-end background folder
     */
    private String getBackgroundFolder() {
        File directory = new File(".");

        String filepath = Paths.get(directory.getAbsolutePath().replace(this.projectPath, ""),
                this.frontEndPath,
                this.backgroundFolder).toString();
        return filepath;
    }

    /**
     * Upload a background image to a Style object.
     * The image background is added the server, and different data is saved (height, width, path)
     * to the Style object.
     */
    @Override
    public Style uploadBackgroundImage(MultipartFile file, Integer styleId) throws Exception {
        // Throw exception if file is not an image
        BufferedImage image = ImageIO.read(file.getInputStream());
        if (image == null) {
            throw new Exception("File is not an image.");
        }

        // Remove special characters, but leave these characters
        String filename = file.getOriginalFilename().replaceAll("[^a-zA-Z0-9._-]", "");

        String filepath = Paths.get(getBackgroundFolder(), filename).toString();

        // Save
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filepath)));
        stream.write(file.getBytes());
        stream.close();

        Style style = this.styleService.findOne(styleId);

        // Throws exception if cannot find style
        if (style == null) {
            throw new Exception("Cannot find style with id=" + styleId + ".");
        }

        // Add extra "\" to prevent escaping
        String imagePath = Paths.get(this.backgroundFolder, filename).toString().replace("\\", "\\\\");

        style.setImageHeight(image.getHeight());
        style.setImageWidth(image.getWidth());
        style.setBackgroundImage(imagePath);
        style.setBackgroundColor("");
        style.setPaddingBottom(0);
        style.setPaddingTop(0);
        style = this.styleService.update(style);

        return style;
    }

    /**
     * Get all background images from the path of where they should be and return the list of background images.
     */
    @Override
    public List<ImageData> getBackgroundImages() throws Exception {
        List<ImageData> files = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get(getBackgroundFolder()))) {
            paths.forEach(file -> {
                if (Files.isRegularFile(file)) {
                    String path = Paths.get(this.backgroundFolder, file.getFileName().toString()).toString();
                    files.add(new ImageData(path, file.getFileName().toString()));
                }
            });
        }
        return files;
    }

    /**
     * Updating the attributes in the Style object of the background image with the given background image information.
     * The parameter id is the id of the Style object to be updated.
     */
    @Override
    public Style updateBackgroundImage(Integer id, ImageData imageData) throws Exception {
        Style style = this.styleService.findOne(id);

        // Throws exception if cannot find style
        if (style == null) {
            throw new Exception("Cannot find style with id=" + id + ".");
        }

        String filepath = Paths.get(getBackgroundFolder(), imageData.getName()).toString();
        File sourceImage = new File(filepath);

        BufferedImage foundImage = ImageIO.read(sourceImage);

        if (foundImage == null) {
            throw new Exception("No image found.");
        }

        // Add extra "\" to prevent escaping
        String imagePath = Paths.get(this.backgroundFolder, imageData.getName()).toString().replace("\\", "\\\\");

        style.setImageHeight(foundImage.getHeight());
        style.setImageWidth(foundImage.getWidth());
        style.setBackgroundImage(imagePath);
        style.setBackgroundColor("");
        style.setPaddingBottom(0);
        style.setPaddingTop(0);
        style = this.styleService.update(style);

        return style;
    }
}
