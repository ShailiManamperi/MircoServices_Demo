package lk.ijse.gdse.Service;


import lk.ijse.gdse.Dto.ItemDto;
import lk.ijse.gdse.exception.*;

import java.util.List;

public interface ItemService {
    ItemDto searchitemBydesc(String desc) throws NotFoundException;
    ItemDto searchItemByid(int id) throws NotFoundException;
    int updateItem(ItemDto itemDto) throws UpdateFailException;
    int addItem(ItemDto itemDto) throws CreateFailException;
    void deleteItem(String desc) throws DeleteFailException;
    List<ItemDto> getAll() throws NotFoundException;
}
