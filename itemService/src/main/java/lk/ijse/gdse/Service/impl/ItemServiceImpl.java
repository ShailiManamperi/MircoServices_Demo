package lk.ijse.gdse.Service.impl;

import lk.ijse.gdse.Dto.ItemDto;
import lk.ijse.gdse.Entity.Item;
import lk.ijse.gdse.Repo.ItemRepo;
import lk.ijse.gdse.Service.ItemService;
import lk.ijse.gdse.exception.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemServiceImpl implements ItemService {
    @Autowired
    ItemRepo itemRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ItemDto searchitemBydesc(String desc) throws NotFoundException {
        Item byDesc = itemRepo.findByName(desc);
        ItemDto map = modelMapper.map(byDesc, ItemDto.class);
        return map;
    }

    @Override
    public ItemDto searchItemByid(int id) throws NotFoundException {
        Item byId = itemRepo.findById(id);
        return modelMapper.map(byId,ItemDto.class);
    }

    @Override
    public int updateItem(ItemDto itemDto) throws UpdateFailException {
        Item byDesc = itemRepo.findByName(itemDto.getName());
        if ( byDesc != null){
            itemRepo.save(modelMapper.map(itemDto,Item.class));
            return byDesc.getId();
        }
        return -1;
    }

    @Override
    public int addItem(ItemDto itemDto) throws CreateFailException {
        Item byDesc = itemRepo.findByName(itemDto.getName());
        if ( byDesc == null){
            itemRepo.save(modelMapper.map(itemDto,Item.class));
            return byDesc.getId();
        }
        return byDesc.getId();
    }

    @Override
    public void deleteItem(String desc) throws DeleteFailException {
        Item byDesc = itemRepo.findByName(desc);
        itemRepo.delete(byDesc);
    }

    @Override
    public List<ItemDto> getAll() throws NotFoundException {
        return null;
    }
}
