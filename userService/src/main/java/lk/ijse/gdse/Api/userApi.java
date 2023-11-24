package lk.ijse.gdse.Api;

import lk.ijse.gdse.Dto.UserDto;
import lk.ijse.gdse.Entity.sec.ErrorRes;
import lk.ijse.gdse.Service.UserService;
import lk.ijse.gdse.exception.CreateFailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/user")
public class userApi {
    @Autowired
    private UserService service;

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


}
