package com.bikkadit.electronicstore.service.impl;

import com.bikkadit.electronicstore.controller.UserController;
import com.bikkadit.electronicstore.dtos.PageableResponse;
import com.bikkadit.electronicstore.dtos.UserDtos;
import com.bikkadit.electronicstore.entities.User;
import com.bikkadit.electronicstore.exception.ResourceNotFoundException;
import com.bikkadit.electronicstore.helper.AppConstants;
import com.bikkadit.electronicstore.helper.Helper;
import com.bikkadit.electronicstore.repository.UserRepository;
import com.bikkadit.electronicstore.service.UserService;
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
public class ServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(ServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public UserDtos createUser(UserDtos userDtos) {
        logger.info("Initiating Dao request for create user UserDtos {}:" + userDtos);
        User user = modelMapper.map(userDtos, User.class);
        user.setIsActive(AppConstants.YES);

        User savedUser = userRepository.save(user);
        UserDtos userDtos1 = modelMapper.map(savedUser, UserDtos.class);
        logger.info("Completed Dao request for create user UserDtos {}:" + userDtos);
        return userDtos1;
    }


    @Override
    public UserDtos updateUser(UserDtos userDtos, Long userId) {
        logger.info("Initiating Dao request for update User userId {}:" + userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not Found !!!"));
        user.setName(userDtos.getName());
        user.setPassword(userDtos.getPassword());
        user.setEmail(userDtos.getEmail());
        user.setGender(userDtos.getGender());
        user.setAbout(userDtos.getAbout());
        user.setImage(userDtos.getImage());

        User updateUser = userRepository.save(user);
        UserDtos userDtos1 = modelMapper.map(updateUser, UserDtos.class);
        logger.info("Completed Dao request for update User userId {}:" + userId);
        return userDtos1;
    }

    @Override
    public void deleteUser(Long userId) {
        logger.info("Initiating Dao request for delete by userId userId {}:" + userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not Found !!!"));
//         Soft Delete
        user.setIsActive(AppConstants.NO);
        userRepository.save(user);
        logger.info("Completed Dao request for delete by userId userId {}:" + userId);
//        Hard Delete
//        userRepository.delete(user);

    }

    @Override
    public PageableResponse<UserDtos> getAllUser(Integer pageNumber, Integer PageSize, String sortBy, String sortDir) {

        logger.info("Initiating Dao request for get All user");
        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageReq = PageRequest.of(pageNumber, PageSize, sort);
        Page<User> page = userRepository.findAll(pageReq);
        List<User> users = page.getContent();
        List<UserDtos> listUserDtos = users.stream().filter(user -> user.getIsActive().equals(AppConstants.YES))
                .map(user -> modelMapper.map(user, UserDtos.class)).collect(Collectors.toList());

        PageableResponse<UserDtos> response = new PageableResponse<>();
        response.setContent(listUserDtos);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElement(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());

        //For using Helper class -- This is done for Reusuability of code
//         PageableResponse<UserDtos> response = Helper.getPageableResponse(page, UserDtos.class);

        logger.info("Completed Dao request for get All user");

        return response;
    }

    @Override
    public UserDtos getUserById(Long userId) {
        logger.info("Initiating Dao request for getSingle user userId {}:" + userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not Found !!!"));
        UserDtos userDtos = modelMapper.map(user, UserDtos.class);
        logger.info("Completed Dao request for getSingle user userId {}:" + userId);
        return userDtos;
    }

    @Override
    public UserDtos getUserByEmail(String email) {
        logger.info("Initiating Dao request for get User By Email {} EmailId:" + email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not Found !!!"));
        UserDtos userDtos = modelMapper.map(user, UserDtos.class);
        logger.info("Completed Dao request for get User By Email EmailId {}:" + email);
        return userDtos;
    }

    @Override
    public List<UserDtos> searching(String keyword) {
        logger.info("Initiating Dao request for searching User By Name userName {}:" + keyword);
        List<User> byNameContaining = userRepository.findByNameContaining(keyword);
        List<UserDtos> listDtos = byNameContaining.stream()
                .map((user) -> modelMapper.map(user, UserDtos.class)).collect(Collectors.toList());
        logger.info("Completed Dao request for searching User By Name userName {}:" + keyword);
        return listDtos;
    }
}
