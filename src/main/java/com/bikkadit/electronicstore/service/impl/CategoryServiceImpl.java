package com.bikkadit.electronicstore.service.impl;

import com.bikkadit.electronicstore.dtos.CategoryDto;
import com.bikkadit.electronicstore.dtos.PageableResponse;
import com.bikkadit.electronicstore.entities.Category;
import com.bikkadit.electronicstore.exception.ResourceNotFoundException;
import com.bikkadit.electronicstore.repository.CategoryRepository;
import com.bikkadit.electronicstore.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        logger.info("Initiating Dao request for create Category CategoryDto {}:" + categoryDto);
        Category category = modelMapper.map(categoryDto, Category.class);
        Category saveCategory = categoryRepository.save(category);
        CategoryDto categoryDto1 = modelMapper.map(saveCategory, CategoryDto.class);
        logger.info("Completed Dao request for create Category CategoryDto {}:" + categoryDto);
        return categoryDto1;
    }

    @Override
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
        logger.info("Initiating Dao request for update Category CategoryDto & CategoryId {}:" + categoryDto, categoryId);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not Found !!"));
        category.setCategoryId(categoryDto.getCategoryId());
        category.setDescription(categoryDto.getDescription());
        category.setTitle(categoryDto.getTitle());
        category.setCoverImage(categoryDto.getCoverImage());
        CategoryDto categoryDto1 = modelMapper.map(category, CategoryDto.class);
        logger.info("Completed Dao request for update Category CategoryDto & CategoryId {}:" + categoryDto, categoryId);
        return categoryDto1;
    }

    @Override
    public void deleteCategory(Long categoryId) {
        logger.info("Initiating Dao request for delete Category CategoryId {}:" + categoryId);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not Found !!"));
        categoryRepository.delete(category);
        logger.info("Completed Dao request for delete Category CategoryId {}:" + categoryId);
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        logger.info("Initiating Dao request for Get Category BYId CategoryId {}:" + categoryId);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not Found !!"));
        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
        logger.info("Completed Dao request for Get Category BYId CategoryId {}:" + categoryId);
        return categoryDto;
    }

    @Override
    public PageableResponse<CategoryDto> getAllCategory( Integer pageNumber,Integer pageSize , String sortBy, String sortDir) {
        logger.info("Initiating Dao request for Get All Category {}:");

        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

         Page<Category> page = categoryRepository.findAll(pageRequest);
         List<Category> pageContent = page.getContent();
        List<CategoryDto> categories = pageContent.stream().map((category) -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());

       PageableResponse<CategoryDto> response=new PageableResponse<>();
        response.setContent(categories);
       response.setPageNumber(page.getNumber());
       response.setPageSize(page.getSize());
       response.setTotalElement(page.getTotalElements());
       response.setTotalPages(page.getTotalPages());
       response.setLastPage(page.isLast());

        logger.info("Completed Dao request for Get All Category {}:");
        return response;
    }

    @Override
    public List<CategoryDto> searching(String keyword) {
        logger.info("Initiating Dao request for Search Category by Title  Title {}:" + keyword);
        List<Category> categories = categoryRepository.findByTitleContaining(keyword).orElseThrow(() -> new ResourceNotFoundException("Category with this title not Found!!"));
        List<CategoryDto> categoryDtos = categories.stream().map((category) -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
        logger.info("Completed Dao request for Search Category by Title  Title {}:" + keyword);
        return categoryDtos;
    }
}
