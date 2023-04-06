package com.bikkadit.electronicstore.service;

import com.bikkadit.electronicstore.dtos.CategoryDto;
import com.bikkadit.electronicstore.dtos.PageableResponse;

import java.util.List;

public interface CategoryService {

    // create

    CategoryDto createCategory(CategoryDto categoryDto);


    // update
    CategoryDto updateCategory(Long categoryId , CategoryDto categoryDto);

    //delete

    void deleteCategory(Long categoryId );

    // get byId

    CategoryDto getCategoryById(Long categoryId);

    //getAll

    PageableResponse<CategoryDto> getAllCategory(Integer pageSize , Integer pageNumber, String sortBy , String sortDir);


    //  search
    List<CategoryDto> searching(String keyword);




}
