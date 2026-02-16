package com.s23358.ecommercex.product.service.impl;

import com.s23358.ecommercex.brand.entity.Brand;
import com.s23358.ecommercex.brand.repository.BrandRepository;
import com.s23358.ecommercex.category.entity.Category;
import com.s23358.ecommercex.category.repository.CategoryRepository;
import com.s23358.ecommercex.exception.NotFoundException;
import com.s23358.ecommercex.exception.ProductImageStorageException;
import com.s23358.ecommercex.product.dto.CreateProductRequest;
import com.s23358.ecommercex.product.dto.EditProductRequest;
import com.s23358.ecommercex.product.dto.ProductResponse;
import com.s23358.ecommercex.product.entity.Product;
import com.s23358.ecommercex.product.repository.ProductRepository;
import com.s23358.ecommercex.product.service.ProductService;
import com.s23358.ecommercex.res.Response;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    private final FileStorageService fileStorageService;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public Response<ProductResponse> createProduct(CreateProductRequest request, List<MultipartFile> files) {

        List<String> imageUrls = new ArrayList<>();

        try {
            Brand brand = brandRepository.findById(request.getBrandId())
                    .orElseThrow(() -> new NotFoundException("Brand not found"));

            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("Category not found"));

             if(files != null && !files.isEmpty()){
                 imageUrls = fileStorageService.saveProductImages(files);
            }


            Product product = Product.builder()
                    .name(request.getName())
                    .price(request.getPrice())
                    .description(request.getDescription())
                    .unit(request.getUnit())
                    .stockQuantity(request.getStockQuantity())
                    .weight(request.getWeight())
                    .images(new  ArrayList<>(imageUrls))
                    .isActive(request.isActive())
                    .brand(brand)
                    .belongsToCategory(category)
                    .build();

            Product saved = productRepository.save(product);

            ProductResponse productResponse = productToProductResponse(saved);

            return Response.<ProductResponse>builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Product created successfully")
                    .data(productResponse)
                    .build();

        }catch (Exception e){
            fileStorageService.deleteByUrls(imageUrls);
            throw new ProductImageStorageException("Failed to store product images", e);
        }

    }

    @Override
    @Transactional
    public Response<ProductResponse> editProduct(EditProductRequest request, List<MultipartFile> files) {

        List<String> newUploadedUrls = new ArrayList<>();
        List<String> urlsToDeleteAfterSave = new ArrayList<>();

        try {
            Product productToEdit = productRepository.findById(request.getId())
                    .orElseThrow(() -> new NotFoundException("Product not found"));

            Brand brand = brandRepository.findById(request.getBrandId())
                    .orElseThrow(() -> new NotFoundException("Brand not found"));

            Category category = categoryRepository.findById(request.getBelongsToCategory())
                    .orElseThrow(() -> new NotFoundException("Category not found"));


            List<String> oldImages = productToEdit.getImages() == null
                    ? new ArrayList<>()
                    : new ArrayList<>(productToEdit.getImages());


            if (files != null && !files.isEmpty()) {
                newUploadedUrls = fileStorageService.saveProductImages(files);
            }


            productToEdit.setName(request.getName());
            productToEdit.setPrice(request.getPrice());
            productToEdit.setDescription(request.getDescription());
            productToEdit.setUnit(request.getUnit());
            productToEdit.setStockQuantity(request.getStockQuantity());
            productToEdit.setWeight(request.getWeight());
            productToEdit.setActive(request.getIsActive());
            productToEdit.setBrand(brand);
            productToEdit.setBelongsToCategory(category);


            if (request.getImages() != null) {

                List<String> keepImages = new ArrayList<>(request.getImages());


                for (String oldUrl : oldImages) {
                    if (!keepImages.contains(oldUrl)) {
                        urlsToDeleteAfterSave.add(oldUrl);
                    }
                }


                keepImages.addAll(newUploadedUrls);
                productToEdit.setImages(keepImages);

            } else {

                if (!newUploadedUrls.isEmpty()) {
                    List<String> merged = new ArrayList<>(oldImages);
                    merged.addAll(newUploadedUrls);
                    productToEdit.setImages(merged);
                }
            }


            Product saved = productRepository.save(productToEdit);

            if (!urlsToDeleteAfterSave.isEmpty()) {
                fileStorageService.deleteByUrls(urlsToDeleteAfterSave);
            }

            return Response.<ProductResponse>builder()
                    .statusCode(HttpStatus.OK.value())
                    .data(productToProductResponse(saved))
                    .message("Product updated")
                    .build();

        } catch (Exception e) {
            fileStorageService.deleteByUrls(newUploadedUrls);
            throw new ProductImageStorageException("Failed to update product images", e);
        }
    }

    @Override
    public Response<Page<ProductResponse>> getAllByCategoryId(Long categoryId, int page, int size) {
        PageRequest pageable = PageRequest.of(page,size, Sort.by("createdAt").descending());

        Page<ProductResponse> responses = productRepository
                .findAllByBelongsToCategory_Id(categoryId, pageable)
                .map(this::productToProductResponse);

        return Response.<Page<ProductResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Products fetched")
                .data(responses)
                .build();
    }

    @Override
    public Response<ProductResponse> getProductById(Long id) {

        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));

        ProductResponse productResponse = productToProductResponse(product);

        return Response.<ProductResponse>builder()
                .data(productResponse)
                .message("Product fetched success")
                .statusCode(200)
                .build();
    }

    @Override
    @Transactional
    public Response<Void> deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        List<String> urlsToDelete = product.getImages() == null
                ? List.of()
                : new ArrayList<>(product.getImages());

        productRepository.delete(product);

        try {
            fileStorageService.deleteByUrls(urlsToDelete);
        } catch (Exception e) {
            System.out.println("Failed to delete product images: " + e.getMessage());
        }

        return Response.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Product deleted")
                .data(null)
                .build();
    }

    private ProductResponse productToProductResponse(Product p){
        return ProductResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .price(p.getPrice())
                .unit(p.getUnit())
                .description(p.getDescription())
                .stockQuantity(p.getStockQuantity())
                .weight(p.getWeight())
                .isActive(p.isActive())
                .images(p.getImages() == null ? new ArrayList<>() : new ArrayList<>(p.getImages()))
                .brandId(p.getBrand() != null ? p.getBrand().getId() : null)
                .categoryId(p.getBelongsToCategory() != null ? p.getBelongsToCategory().getId() : null)
                .build();
    }


}
