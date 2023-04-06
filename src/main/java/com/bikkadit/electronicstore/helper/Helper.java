package com.bikkadit.electronicstore.helper;

import com.bikkadit.electronicstore.dtos.PageableResponse;
import com.bikkadit.electronicstore.dtos.UserDtos;
import com.bikkadit.electronicstore.entities.User;
import com.bikkadit.electronicstore.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {



    public static <entity, dto> PageableResponse<dto> getPageableResponse(Page<entity> page, Class<dto> type) {
        List<entity> users = page.getContent();
        List<dto> listUserDtos = users.stream()
                .map(obj -> new ModelMapper().map(obj, type)).collect(Collectors.toList());

//        List<UserDtos> listUserDtos = userRepository.findAll(pageReq).getContent().stream().filter(user -> user.getIsActive().equals(AppConstants.YES))
//                .map(user -> modelMapper.map(user, UserDtos.class)).collect(Collectors.toList());

        PageableResponse<dto> response = new PageableResponse<>();
        response.setContent(listUserDtos);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElement(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());
        return response;
    }


}
