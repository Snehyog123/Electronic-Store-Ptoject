package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.dtos.CategoryDto;
import com.bikkadit.electronicstore.dtos.PageableResponse;
import com.bikkadit.electronicstore.helper.AppConstants;
import com.bikkadit.electronicstore.helper.AppResponse;
import com.bikkadit.electronicstore.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    private CategoryService categoryService;

    //create
    @PostMapping()
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        logger.info("Initiating Service request for create Category CategoryDto {}:" + categoryDto);
        CategoryDto savedCategory = categoryService.createCategory(categoryDto);
        logger.info("Completed Service request for create Category CategoryDto {}:" + categoryDto);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    // update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable("categoryId") Long categoryId) {
        logger.info("Initiating Service request for update Category CategoryDto & categoryId {}:" + categoryDto, categoryId);
        CategoryDto categoryDto1 = categoryService.updateCategory(categoryId, categoryDto);
        logger.info("Completed Service request for update Category CategoryDto & categoryId {}:" + categoryDto, categoryId);
        return new ResponseEntity<>(categoryDto1, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<AppResponse> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        logger.info("Initiating Service request for delete Category categoryId {}:" + categoryId);
        categoryService.deleteCategory(categoryId);

        AppResponse response = AppResponse.builder()
                .message(AppConstants.RESOURCE_DELETED_SUCCESS)
                .success(true)
                .status(HttpStatus.OK)
                .build();
        logger.info("Completed Service request for delete Category categoryId {}:" + categoryId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // getById
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long categoryId) {
        logger.info("Initiating Service request for get Category ById categoryId {}:" + categoryId);
        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
        logger.info("Completed Service request for get Category ById categoryId {}:" + categoryId);
        return new ResponseEntity<>(categoryDto, HttpStatus.FOUND);
    }

    //get All
    @GetMapping()
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategory(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "description", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        logger.info("Initiating Service request for GetAll Category {}:" + "PageNumber:" + pageNumber,
                "PageSize:" + pageSize, "SortBy:" + sortBy, "SortDirection:" + sortDir);
        PageableResponse<CategoryDto> allCategory = categoryService.getAllCategory(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed Service request for GetAll Category {}:");
        return new ResponseEntity<>(allCategory, HttpStatus.FOUND);
    }

    // search
    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<List<CategoryDto>> serchByTitle(@PathVariable String keyword) {
        logger.info("Initiating Service request for get Category By Title Title {}:" + keyword);
        List<CategoryDto> categoryDtos = categoryService.searching(keyword);
        logger.info("Completed Service request for get Category By Title Title {}:" + keyword);
        return new ResponseEntity<>(categoryDtos, HttpStatus.FOUND);

    }
}
