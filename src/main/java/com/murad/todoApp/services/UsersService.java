package com.murad.todoApp.services;

import com.murad.todoApp.dto.UserPrincipal;
import com.murad.todoApp.exceptions.UsernameNotFoundException;
import com.murad.todoApp.models.UsersModel;
import com.murad.todoApp.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UsersService implements UserDetailsService {
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) {
            throw new UsernameNotFoundException("User: " + username + " was not found");
        }
        UsersModel user = usersRepository.findByUsername(username);
        if (user != null)
            return new UserPrincipal(usersRepository.findByUsername(username));
        throw new UsernameNotFoundException("User: " + username + " was not found");
    }

    public Boolean isAnyUserExists() {
        List<UsersModel> users = usersRepository.findAll();
        return users.size() > 0;

    }

    public void saveUser(UsersModel user) {
        usersRepository.save(user);
    }
}
