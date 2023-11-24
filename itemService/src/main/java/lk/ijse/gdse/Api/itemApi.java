package lk.ijse.gdse.Api;


import lk.ijse.gdse.Dto.ItemDto;
import lk.ijse.gdse.Entity.sec.ErrorRes;
import lk.ijse.gdse.Service.ItemService;
import lk.ijse.gdse.exception.CreateFailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
                               @RequestPart(value = "qty") int qty,
                               @RequestPart(value = "price") double price) {

        System.out.println(name);
        System.out.println(qty);
        try {
            ItemDto itemDto = new ItemDto();
            itemDto.setName(name);
            itemDto.setQty(qty);
            itemDto.setPrice(price);

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
    @PutMapping("/{id}")
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

}
