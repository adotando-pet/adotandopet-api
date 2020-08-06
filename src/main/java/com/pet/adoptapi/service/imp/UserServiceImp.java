package com.pet.adoptapi.service.imp;

import com.pet.adoptapi.exception.BusinessException;
import com.pet.adoptapi.model.User;
import com.pet.adoptapi.repository.UserRepository;
import com.pet.adoptapi.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    private UserRepository repository;

    public UserServiceImp(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User save(User user) {
        if (repository.existsByEmail(user.getEmail())){
            throw new BusinessException("Email já cadastrado.");
        }
        return repository.save(user);
    }
}
