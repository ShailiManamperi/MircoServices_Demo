package lk.ijse.gdse.Api;

import lk.ijse.gdse.Dto.UserDto;
import lk.ijse.gdse.Entity.User;
import lk.ijse.gdse.Entity.sec.ErrorRes;
import lk.ijse.gdse.Service.UserService;
import lk.ijse.gdse.exception.CreateFailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/user")
public class userApi {
    @Autowired
    private UserService service;
    @Autowired
    private RestTemplate restTemplate;


    @PostMapping(value = "/signup", consumes = "multipart/form-data")
    public ResponseEntity save(@RequestPart(value = "name") String name,
                               @RequestPart(value = "email") String email,
                               @RequestPart(value = "contact") String contact) {

        System.out.println(name);
        System.out.println(email);
        try {
            UserDto userDTO = new UserDto();
            userDTO.setContact(contact);
            userDTO.setEmail(email);
            userDTO.setName(name);

            int id = service.addUsers(userDTO);
            userDTO.setId(id);
            return ResponseEntity.ok(userDTO);
        } catch (CreateFailException e) {
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @DeleteMapping("/{email:.+}")
    public ResponseEntity deleteUser(@PathVariable String email) {
        try {
            service.deleteUser(email);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/save")
    public String getuser(){
        return "done the test";
    }

    @PutMapping("/{id}")
    public ResponseEntity updateItem(@PathVariable int id,
                                     @RequestPart(value = "name") String name,
                                     @RequestPart(value = "email") String email,
                                     @RequestPart(value = "contact") String contact) {
        try {
            UserDto existingUser = service.searchUserById(id);

            if (existingUser == null) {
                return new ResponseEntity<>("Item not found with ID: " + id, HttpStatus.NOT_FOUND);
            }

            // Update the fields of the existing item with the values from the updatedItem
            existingUser.setName(name);
            existingUser.setContact(contact);
            existingUser.setEmail(email);

            // Save the updated item
            service.updateUser(existingUser);

            return new ResponseEntity<>(existingUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    @PostMapping("/add")
    public User addUserToItemService(@RequestBody User user){
        return restTemplate.postForObject("http://localhost:8082/api/v1/item/add",user,User.class);
    }
}
