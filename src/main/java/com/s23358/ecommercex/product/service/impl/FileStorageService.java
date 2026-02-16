package com.s23358.ecommercex.product.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path root;

    public FileStorageService(@Value("${app.uploads.dir:./uploads}") String uploadsDir) {
        this.root = Paths.get(uploadsDir).toAbsolutePath().normalize();
    }

    public List<String> saveProductImages(List<MultipartFile> files) {
        try {
            Path productsDir = root.resolve("products");
            Files.createDirectories(productsDir);

            List<String> urls = new ArrayList<>();

            for (MultipartFile file : files) {
                if (file == null || file.isEmpty()) continue;

                String original = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
                String ext = "";

                int dot = original.lastIndexOf('.');
                if (dot >= 0) ext = original.substring(dot);

                String filename = UUID.randomUUID() + ext;
                Path target = productsDir.resolve(filename);

                Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

                // URL, które front będzie używał
                urls.add("/uploads/products/" + filename);
            }

            return urls;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store files", e);
        }
    }
}
