package glm.seclass.qc.edu.glm;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Lin on 11/13/17.
 */

//List table

@Entity
public class GroceryList {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "list_id")
    private int listId;

    @ColumnInfo(name = "list_name")
    @NonNull
    private String listName;

    public GroceryList(){};

    public GroceryList(String listName){
        this.listName = listName;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }
}
