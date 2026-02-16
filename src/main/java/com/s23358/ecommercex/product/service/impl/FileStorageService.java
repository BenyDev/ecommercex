package com.s23358.ecommercex.product.service.impl;

import com.s23358.ecommercex.exception.BadRequestException;
import com.s23358.ecommercex.exception.BrandImageStorageException;
import com.s23358.ecommercex.exception.ProductImageStorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

@Service
public class FileStorageService {

    private static final long MAX_IMAGE_SIZE = 10L * 1024 * 1024;

    private static final Set<String> ALLOWED_FILE_TYPES = Set.of(
            "image/png",
            "image/jpeg",
            "image/webp"
    );

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("png", "jpg", "jpeg", "webp");

    private final Path root;

    public FileStorageService(@Value("${app.uploads.dir:./uploads}") String uploadsDir) {
        this.root = Paths.get(uploadsDir).toAbsolutePath().normalize();
    }

    public List<String> saveProductImages(List<MultipartFile> files) {
        if (files == null || files.isEmpty()) return List.of();

        Path productsDir = root.resolve("products");

        try {
            Files.createDirectories(productsDir);

            List<String> urls = new ArrayList<>();

            for (MultipartFile file : files) {
                if (file == null || file.isEmpty()) continue;

                validateImage(file);

                String original = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
                String ext = getExtension(original);

                String filename = UUID.randomUUID() + "." + ext;
                Path target = productsDir.resolve(filename);

                Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

                urls.add("/uploads/products/" + filename);
            }

            return urls;

        } catch (IOException e) {
            throw new ProductImageStorageException("Failed to store product images", e);
        }
    }

    private void validateImage(MultipartFile file) {
        if (file.getSize() > MAX_IMAGE_SIZE) {
            throw new BadRequestException("Image is too large. Max size is 10 MB.");
        }


        String contentType = file.getContentType();
        boolean contentTypeOk = (contentType != null && ALLOWED_FILE_TYPES.contains(contentType));


        String original = file.getOriginalFilename();
        String ext = getExtension(original);
        boolean extOk = ALLOWED_EXTENSIONS.contains(ext);


        if (!contentTypeOk && !extOk) {
            throw new BadRequestException("Invalid image type. Allowed: png, jpg, jpeg, webp.");
        }
    }

    private String getExtension(String filename) {
        if (filename == null) return "";
        String clean = StringUtils.cleanPath(filename);
        int dot = clean.lastIndexOf('.');
        if (dot < 0 || dot == clean.length() - 1) return "";
        return clean.substring(dot + 1).toLowerCase(Locale.ROOT);
    }

    public void deleteByUrls(List<String> urls) {
        if (urls == null || urls.isEmpty()) return;

        for (String url : urls) {
            if (url == null || url.isBlank()) continue;

            try {
                String filename = url.substring(url.lastIndexOf('/') + 1);
                Path p = root.resolve("products").resolve(filename);
                Files.deleteIfExists(p);
            } catch (Exception ignored) {
            }
        }
    }

    public String saveBrandLogo(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new BadRequestException("Logo file is required");
        }

        validateImage(file);

        try {
            Path brandsDir = root.resolve("brands");
            Files.createDirectories(brandsDir);

            String original = StringUtils.cleanPath(
                    Objects.requireNonNull(file.getOriginalFilename())
            );

            String ext = getExtension(original);

            String filename = UUID.randomUUID() + "." + ext;

            Path target = brandsDir.resolve(filename);

            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            return "/uploads/brands/" + filename;

        } catch (IOException e) {
            throw new BrandImageStorageException("Failed to store brand logo", e);
        }
    }
}