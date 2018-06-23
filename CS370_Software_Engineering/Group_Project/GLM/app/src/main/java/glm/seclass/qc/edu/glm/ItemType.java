package glm.seclass.qc.edu.glm;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

//Item Type table

/**
 * Created by Lin on 11/15/17.
 */
@Entity
public class ItemType {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "type_id")
    private int typeId;

    @ColumnInfo(name = "item_type")
    private String itemType;

    private String measurement;

    public ItemType(String itemType , String measurement){
        this.itemType = itemType;
        this.measurement = measurement;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }
}
