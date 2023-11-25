package lk.ijse.gdse.Repo;


import lk.ijse.gdse.Entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepo extends JpaRepository<Item,Integer> {
    Item findByName(String desc);

    Item findById(int id);


}
