package glm.seclass.qc.edu.glm;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

//Associating dao and tables to db
@Database(entities = {GroceryList.class, Item.class , ListToItem.class, ItemType.class}, version = 1)
public abstract class GLDatabase extends RoomDatabase {
    public abstract GroceryListDAO groceryListDAO();
    public abstract ItemDAO itemDAO();
    public abstract ListToItemDAO listToItemDAO();
    public abstract ItemTypeDAO itemTypeDAO();
}
