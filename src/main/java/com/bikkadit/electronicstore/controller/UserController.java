package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.dtos.ImageResponse;
import com.bikkadit.electronicstore.dtos.PageableResponse;
import com.bikkadit.electronicstore.dtos.UserDtos;
import com.bikkadit.electronicstore.helper.AppConstants;
import com.bikkadit.electronicstore.helper.AppResponse;
import com.bikkadit.electronicstore.service.FileService;
import com.bikkadit.electronicstore.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;
    @Value("${user.profile.image.path}")
    private  String imageUploadPath;

    @PostMapping()
    public ResponseEntity<UserDtos> saveUser(@Valid @RequestBody UserDtos userDtos) {
        logger.info("Initiating Service request for save user {} UserDtos:" + userDtos);
        UserDtos user = userService.createUser(userDtos);
        logger.info("Completed Service request for save user {} UserDtos:" + userDtos);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDtos> updateUser(@Valid @RequestBody UserDtos userDtos, @PathVariable Long userId) {
        logger.info("Initiating Service request for update user {} UserId:" + userId);
        UserDtos userDtos1 = userService.updateUser(userDtos, userId);
        logger.info("Completed Service request for update user {} UserId:" + userId);
        return new ResponseEntity<>(userDtos1, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<AppResponse> deleteUser(@PathVariable Long userId) {
        logger.info("Initiating Service request for delete user {} UserId:" + userId);
        userService.deleteUser(userId);
        AppResponse appResponse = AppResponse.builder()
                .message(AppConstants.RESOURCE_DELETED_SUCCESS)
                .success(true)
                .status(HttpStatus.OK).build();
        logger.info("Completed Service request for delete user {} UserId:" + userId);
        return new ResponseEntity<>(appResponse, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<PageableResponse<UserDtos>> getAllUser(
            @RequestParam(value = "pageNumber" , defaultValue = "0" ,required = false) Integer pageNumber,
            @RequestParam(value = "pageSize" , defaultValue = "10" , required = false) Integer pageSize ,
            @RequestParam(value = "sortBy" , defaultValue = "name" , required = false) String   sortBy ,
            @RequestParam(value = "sortDir" , defaultValue = "asc" ,required = false) String sortDir
    ) {
        logger.info("Initiating Service request for get All user");
        PageableResponse<UserDtos> allUser = userService.getAllUser(pageNumber , pageSize , sortBy , sortDir);
        logger.info("Completed Service request for get All user");
        return new ResponseEntity<>(allUser, HttpStatus.FOUND);
    }

    @GetMapping("userId/{userId}")
    public ResponseEntity<UserDtos> getByUserId(@PathVariable Long userId) {
        logger.info("Initiating Service request for get_User By Id {} UserId:" + userId);
        UserDtos userDto = userService.getUserById(userId);
        logger.info("Completed Service request for get_User By Id {} UserId:" + userId);
        return new ResponseEntity<>(userDto, HttpStatus.FOUND);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDtos> getUserByEmail(@PathVariable String email) {
        logger.info("Initiating Service request for get_User By Email {} Email:" + email);
        UserDtos userDto = userService.getUserByEmail(email);
        logger.info("Completed Service request for get_User By Email {} Email:" + email);
        return new ResponseEntity<>(userDto, HttpStatus.FOUND);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDtos>> searchNameContainning(@PathVariable String keyword) {
        logger.info("Initiating Service request for searching User By Name {} userName:" + keyword);
        List<UserDtos> listUserDtos = userService.searching(keyword);
        logger.info("Initiating Service request for searching User By Name {} userName:" + keyword);
        return new ResponseEntity<>(listUserDtos, HttpStatus.FOUND);

    }

    // upload user image
    @PostMapping("/image/{userId}")
    public  ResponseEntity<ImageResponse> uploadUserImage(
            @PathVariable("userId") Long userId ,
            @RequestParam("userImage")MultipartFile image) throws IOException {
         String imageName = fileService.uploadImage(image, imageUploadPath);

         UserDtos user = userService.getUserById(userId);
         user.setImage(imageName);

         UserDtos userDtos = userService.updateUser(user, userId);

        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).message("Image uploaded successfully !!").success(true).status(HttpStatus.CREATED).build();

         return  new ResponseEntity<>(imageResponse , HttpStatus.CREATED);
    }


    // serve user image

    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable Long userId , HttpServletResponse response) throws IOException {
         UserDtos user = userService.getUserById(userId);
         logger.info("User image name : {}",user.getImage());
         InputStream resource = fileService.getResource(imageUploadPath, user.getImage());

         response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource , response.getOutputStream());

    }

}
