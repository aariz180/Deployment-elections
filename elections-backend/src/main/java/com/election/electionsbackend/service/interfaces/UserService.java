package com.election.electionsbackend.service.interfaces;

import com.election.electionsbackend.model.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service

public interface UserService {
    public UserDetailsService getUserDetailsService();

}
