package lk.ijse.gdse.Api;


import lk.ijse.gdse.Dto.ItemDto;
import lk.ijse.gdse.Dto.UserDto;
import lk.ijse.gdse.Entity.sec.ErrorRes;
import lk.ijse.gdse.Service.ItemService;
import lk.ijse.gdse.exception.CreateFailException;
import lk.ijse.gdse.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/item")
public class itemApi {
    @Autowired
    private ItemService service;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping(value = "/signup", consumes = "multipart/form-data")
    public ResponseEntity save(@RequestPart(value = "desc") String name,
                               @RequestPart(value = "qty") String qty,
                               @RequestPart(value = "price") String price) {

        System.out.println(name);
        System.out.println(qty);
        try {
            ItemDto itemDto = new ItemDto();
            itemDto.setName(name);
            int qty1 = Integer.parseInt(qty);
            itemDto.setQty(qty1);
            double price1 = Double.parseDouble(price);
            itemDto.setPrice(price1);

            int i = service.addItem(itemDto);
            itemDto.setId(i);
            return ResponseEntity.ok(itemDto);
        } catch (CreateFailException e) {
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @DeleteMapping("/{desc:.+}")
    public ResponseEntity deleteItem(@PathVariable String desc) {
        try {
            service.deleteItem(desc);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
    @PutMapping(value = "/{id}",consumes = "multipart/form-data")
    public ResponseEntity updateItem(@PathVariable int id,
                                     @RequestPart(value = "desc") String name,
                                     @RequestPart(value = "qty") int qty,
                                     @RequestPart(value = "price") double price) {
        try {
            ItemDto existingItem = service.searchItemByid(id);

            if (existingItem == null) {
                return new ResponseEntity<>("Item not found with ID: " + id, HttpStatus.NOT_FOUND);
            }

            // Update the fields of the existing item with the values from the updatedItem
            existingItem.setName(name);
            existingItem.setQty(qty);
            existingItem.setPrice(price);

            // Save the updated item
            service.updateItem(existingItem);

            return new ResponseEntity<>(existingItem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
    @GetMapping("/save")
    public String getuser(){
        return restTemplate.getForObject("http://localhost:8081/api/v1/user/save",String.class);
    }

    public ResponseEntity updateUser(){
        return new ResponseEntity<>(200,HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity getAllCustomers(){
        List<ItemDto> all;
        try {
            all = service.getAll();
            System.out.println(all);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(all,HttpStatus.OK);
    }

    @PostMapping("/add")
    public UserDto addUserFromItem(@RequestBody UserDto user){
        user.setContact("0712330251");
        return user;
    }
}
