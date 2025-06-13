package com.juanjooriveroo.jwtauthservice.service;

import com.juanjooriveroo.jwtauthservice.dto.ChangePasswordRequest;
import com.juanjooriveroo.jwtauthservice.dto.DeleteUserRequest;
import com.juanjooriveroo.jwtauthservice.dto.UpdateIdentifiersRequest;

public interface UserService {
    boolean updateIdentifiers(UpdateIdentifiersRequest request, String token);
    boolean changePassword(ChangePasswordRequest request, String token);
    boolean deleteUser(DeleteUserRequest request, String token);
}
