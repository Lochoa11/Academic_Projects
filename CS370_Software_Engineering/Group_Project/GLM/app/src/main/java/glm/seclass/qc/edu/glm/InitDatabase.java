package glm.seclass.qc.edu.glm;

import android.arch.persistence.room.Room;
import android.content.Context;
/**
 * Created by Lin on 11/15/17.
 */

//singleton for db

public class InitDatabase{

    private static GLDatabase db;

    private InitDatabase() {
    }


    public static void create(Context myContext) {
        if (db == null) {
            db = Room.databaseBuilder(myContext.getApplicationContext(),
                    GLDatabase.class, "grocerylist-database").build();
        }
    }

    public static void destroy(Context myContext) {
        db = null;
        myContext.deleteDatabase("grocerylist-database");
    }

    public static GLDatabase getDB() {
        return db;
    }
}
