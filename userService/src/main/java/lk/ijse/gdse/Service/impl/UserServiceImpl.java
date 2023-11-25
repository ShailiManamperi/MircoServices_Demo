package lk.ijse.gdse.Service.impl;

import lk.ijse.gdse.Dto.UserDto;
import lk.ijse.gdse.Entity.User;
import lk.ijse.gdse.Repo.UserRepo;
import lk.ijse.gdse.Service.UserService;
import lk.ijse.gdse.exception.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public UserDto searchUserByEmail(String email) throws UserNotFoundException {
        User byEmail = userRepo.findByEmail(email);
        UserDto userDto = modelMapper.map(byEmail, UserDto.class);
        return userDto;
    }

    @Override
    public UserDto searchUserById(int id) throws UserNotFoundException {
        User byId = userRepo.findById(id);
        UserDto userDto = modelMapper.map(byId, UserDto.class);
        return userDto;
    }

    @Override
    public void updateUser(UserDto userDto) throws UpdateFailException {
        User byEmail = userRepo.findByEmail(userDto.getEmail());
        if (byEmail != null){
            userRepo.save(modelMapper.map(userDto,User.class));
        }
    }

    @Override
    public int addUsers(UserDto email) throws CreateFailException {
        User save = userRepo.save(modelMapper.map(email, User.class));
        return save.getId();
    }

    @Override
    public void deleteUser(String email) throws DeleteFailException {
        User byEmail = userRepo.findByEmail(email);
        userRepo.delete(byEmail);
    }

    @Override
    public List<UserDto> getAll(String email) throws UserNotFoundException {
        return null;
    }
}
