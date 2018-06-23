package glm.seclass.qc.edu.glm;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by don on 11/9/17.
 */

//item DAO

@Dao
public interface ItemDAO {

    //when doing this add % to beginning and end of search term
    @Query("SELECT * FROM Item WHERE item_name LIKE :name")
    List<Item> searchItem(String name);

    @Query("SELECT * FROM Item")
    List<Item> getAllItems();

    @Query("SELECT item_name FROM Item")
    List<String> getAllItemName();

    @Query("SELECT * FROM Item WHERE item_name IS :itemName")
    Item getItemWithName(String itemName);

    @Query("SELECT * FROM Item WHERE item_id = :itemId")
    Item getItem(int itemId);

    @Query("SELECT * FROM Item WHERE item_name = :itemName")
    int getItemId(String itemName);

    @Query("SELECT * FROM Item WHERE type_id = :typeId")
    List<Item> getItemsOfType(int typeId);

    @Insert
    void insertAll(List<Item> items);

    @Insert
    void insert(Item item);

    @Update
    void update(Item item);

    @Delete
    void delete(Item item);
}
