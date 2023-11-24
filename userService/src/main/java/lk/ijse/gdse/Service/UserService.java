package lk.ijse.gdse.Service;


import lk.ijse.gdse.Dto.UserDto;
import lk.ijse.gdse.exception.*;

import java.util.List;

public interface UserService {
    UserDto searchUserByEmail(String email) throws UserNotFoundException;
    void updateUser(UserDto email) throws UpdateFailException;
    int addUsers(UserDto email) throws CreateFailException;
    void deleteUser(String email) throws DeleteFailException;
    List<UserDto> getAll(String email) throws UserNotFoundException;
}
