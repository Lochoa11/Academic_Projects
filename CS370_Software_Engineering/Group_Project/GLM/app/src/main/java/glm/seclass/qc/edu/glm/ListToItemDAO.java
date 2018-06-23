package glm.seclass.qc.edu.glm;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Lin on 11/15/17.
 */

//List to Item DAO

@Dao
public interface ListToItemDAO {

    @Query("SELECT * FROM ListToItem WHERE list_id = :listId AND item_id = :itemId")
    ListToItem get(int listId, int itemId);

    @Query("SELECT * FROM ListToItem WHERE list_id = :listId")
    List<ListToItem> getAllItems(int listId);

    @Query("DELETE FROM ListToItem WHERE list_id = :listId")
    void deleteItems(int listId);

    @Insert
    void insertAll(List<ListToItem> listToItems);

    @Insert
    void insert(ListToItem listToItem);

    @Update
    void update(ListToItem listToItem);

    @Delete
    void delete(ListToItem listToItem);
}
