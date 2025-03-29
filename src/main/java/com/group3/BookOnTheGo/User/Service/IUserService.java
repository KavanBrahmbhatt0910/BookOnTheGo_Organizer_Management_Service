package com.group3.BookOnTheGo.User.Service;

import com.group3.BookOnTheGo.User.DataTransferObject.UserUpdateRequestDto;
import org.springframework.http.ResponseEntity;

public interface IUserService {


    ResponseEntity<Object> getUserById(Long id, String token);

    ResponseEntity<Object> getUserDetails(String token);

    ResponseEntity<Object> updateUserDetails(UserUpdateRequestDto request, String token);

}
