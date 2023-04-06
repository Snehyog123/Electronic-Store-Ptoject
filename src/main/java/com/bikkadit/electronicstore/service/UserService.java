package com.bikkadit.electronicstore.service;

import com.bikkadit.electronicstore.dtos.PageableResponse;
import com.bikkadit.electronicstore.dtos.UserDtos;


import java.util.List;


public interface UserService {

    // create

    UserDtos createUser(UserDtos userDtos);


    // update
    UserDtos updateUser(UserDtos userDtos, Long userId);


    // delete
    void deleteUser(Long userId);


    // getAllUser
    PageableResponse<UserDtos> getAllUser(Integer pageNumber , Integer pageSize , String sortBy , String sortDir);

    // getUserById
    UserDtos getUserById(Long userId);


    // getUserByEmail
    UserDtos getUserByEmail(String email);

    // searching

    List<UserDtos> searching(String keyword);


}
