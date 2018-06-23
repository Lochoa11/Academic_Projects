package glm.seclass.qc.edu.glm;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

/**
 * Created by Lin on 11/15/17.
 */

//Table for mapping a list to an item

@Entity(primaryKeys = {
        "list_id", "item_id"
},
        foreignKeys = {
        @ForeignKey(entity = GroceryList.class, parentColumns = "list_id", childColumns = "list_id"),
        @ForeignKey(entity = Item.class, parentColumns = "item_id", childColumns = "item_id")
})
public class ListToItem {


    @ColumnInfo(name = "list_id")
    private int listId;

    @ColumnInfo(name = "item_id")
    private int itemId;

    @ColumnInfo(name = "checked_off")
    private Boolean checkedOff;

    @ColumnInfo(name = "quantity")
    private int quantity;

    public ListToItem(){
        checkedOff = false;
        quantity = 1;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public Boolean getCheckedOff() {
        return checkedOff;
    }

    public void setCheckedOff(Boolean checkedOff) {
        this.checkedOff = checkedOff;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
