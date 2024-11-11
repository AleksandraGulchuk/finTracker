package org.nure.fintracker.servise;

import org.nure.fintracker.dto.user.UserLoginDto;
import org.nure.fintracker.dto.user.UserSetupDto;
import org.nure.fintracker.entity.UserAccount;
import org.nure.fintracker.exception.EntityAlreadyExistsException;
import org.nure.fintracker.exception.EntityNotFoundException;
import org.nure.fintracker.mapper.UserMapper;
import org.nure.fintracker.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private UserMapper userMapper;


    public UUID create(UserSetupDto userSetupDto) {
        boolean present = userAccountRepository
                .findByEmail(userSetupDto.getEmail())
                .isPresent();
        if (present) {
            throw new EntityAlreadyExistsException("User with email: " + userSetupDto.getEmail() + " already exists");
        }
        UserAccount user = userMapper.createUserFromDto(userSetupDto);
        return userAccountRepository.save(user).getId();
    }

    public UUID login(UserLoginDto user) {
        return userAccountRepository
                .findIdByEmailAndPassword(user.getEmail(), user.getPassword())
                .orElseThrow(() -> new EntityNotFoundException("User not found. Wrong email or password"));
    }

}
