package mtn.momo.contract.repayment.service;

import mtn.momo.contract.repayment.exception.UserAlreadyExistException;
import mtn.momo.contract.repayment.model.UserDto;

public interface UserService {
    UserDto registerNewUser(UserDto user) throws UserAlreadyExistException;
}
