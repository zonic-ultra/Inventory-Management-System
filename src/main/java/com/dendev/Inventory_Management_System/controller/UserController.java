package com.dendev.Inventory_Management_System.controller;


import com.dendev.Inventory_Management_System.dtos.Response;
import com.dendev.Inventory_Management_System.dtos.UserDto;
import com.dendev.Inventory_Management_System.models.User;
import com.dendev.Inventory_Management_System.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
   private final UserService userService;

   @GetMapping("/all")
   @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllUsers(){
       return ResponseEntity.ok(userService.getAllUsers());
   }

   @GetMapping("/get/{userId}")
    public ResponseEntity<Response> getUSerById(@PathVariable Long userId){
       return ResponseEntity.ok(userService.getUserById(userId));
   }

   @GetMapping("/current")
   public ResponseEntity<User> getCurrentLoggedInUsers(){
       return ResponseEntity.ok(userService.getCurrentLoggedInUsers());
   }

   @PutMapping("/update/{userId}")
    public ResponseEntity<Response> updateUser(@PathVariable Long userId, @RequestBody UserDto userDto){
       return ResponseEntity.ok(userService.updateUser(userId, userDto));
   }

   @DeleteMapping("/delete/{userId}")
   @PreAuthorize("hasAuthority('ADMIN')")

   public ResponseEntity<Response> deleteUser(@PathVariable Long userId){
       return ResponseEntity.ok(userService.deleteUser(userId));
   }

   //======transactions
    @GetMapping("/transactions/{userId}")
    public ResponseEntity<Response> getUserAndTransactions(@PathVariable Long userId){
       return ResponseEntity.ok(userService.getUserTransaction(userId));
    }
}
