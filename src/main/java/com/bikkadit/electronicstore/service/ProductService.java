package com.bikkadit.electronicstore.service;

import com.bikkadit.electronicstore.dtos.PageableResponse;
import com.bikkadit.electronicstore.dtos.ProductDto;
import com.bikkadit.electronicstore.helper.AppResponse;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto);

    // update

    ProductDto updateProduct(Integer productId, ProductDto productDto);

    // delete

    void deleteProduct(Integer productId);

    // Get single

    ProductDto getProductById(Integer productId);

    // Get All

    PageableResponse<ProductDto> getAllProduct(Integer pageNumber, Integer pagSize, String sortBy, String sortDir);

    // get all live

    PageableResponse<ProductDto> getAllLiveProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    // search product

    PageableResponse<ProductDto> seachByTitle(String subTitle, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

}
