package glm.seclass.qc.edu.glm;

/**
 * Created by Lin on 11/9/17.
 */

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

//item table

@Entity(foreignKeys = {@ForeignKey(
                            entity = ItemType.class,
                            parentColumns = "type_id",
                            childColumns = "type_id"
)})
public class Item {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "item_id")
    private int itemId;

    @ColumnInfo(name = "item_name")
    private String itemName;

    @ColumnInfo(name = "type_id")
    private int typeId;

    public Item(String itemName , int typeId){
        this.itemName = itemName;
        this.typeId = typeId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}
