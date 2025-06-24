package com.dendev.Inventory_Management_System.services;

import com.dendev.Inventory_Management_System.dtos.LoginRequest;
import com.dendev.Inventory_Management_System.dtos.RegisterRequest;
import com.dendev.Inventory_Management_System.dtos.Response;
import com.dendev.Inventory_Management_System.dtos.UserDto;
import com.dendev.Inventory_Management_System.models.User;

public interface UserService {
    Response registerUser(RegisterRequest registerRequest);

    Response loginUser(LoginRequest loginRequest);

    Response getAllUsers();

    User getCurrentLoggedInUsers();

    Response getUserById(Long id);

    Response updateUser(Long id, UserDto userDto);

    Response deleteUser(Long id);

    Response getUserTransaction(Long id);
}
