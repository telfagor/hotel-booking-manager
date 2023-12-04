package com.bolun.hotel.service;

import com.bolun.hotel.connection.PropertiesUtil;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static lombok.AccessLevel.PRIVATE;
import com.bolun.hotel.servlet.ImageServlet;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.io.InputStream;

import static java.nio.file.StandardOpenOption.*;
import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PRIVATE)
public class ImageService {

    private static final ImageService INSTANCE = new ImageService();

    private final String baseUrl = PropertiesUtil.getValue("image.base.url");

    @SneakyThrows
    public Optional<InputStream> get(String imagePath) {
        Path imageFullPath = Path.of(baseUrl, imagePath);

        return Files.exists(imageFullPath)
                ? Optional.of(Files.newInputStream(imageFullPath))
                : Optional.empty();

    }

    @SneakyThrows
    public void upload(String imagePath, InputStream imageContent) {
        Path imageFullPath = Path.of(baseUrl, imagePath);
        if (imageContent.available() != 0) {
            try (imageContent) {
                Files.createDirectories(imageFullPath.getParent());
                Files.write(imageFullPath, imageContent.readAllBytes(), CREATE, TRUNCATE_EXISTING);
            }
            try (imageContent) {
                Files.createDirectories(imageFullPath.getParent());
                Files.write(imageFullPath, imageContent.readAllBytes(), CREATE, TRUNCATE_EXISTING);
            }
        }
    }

    public static ImageService getInstance() {
        return INSTANCE;
    }
}
