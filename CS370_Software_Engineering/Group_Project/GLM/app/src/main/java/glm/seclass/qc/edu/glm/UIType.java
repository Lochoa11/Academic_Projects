package glm.seclass.qc.edu.glm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

/**
 * Created by Lin on 11/22/17.
 */

public class UIType extends AppCompatActivity{

    String listName;
    String typeName;
    MyTasks myTasks;
    Context context = this;
    LinearLayout llTypes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.type_view);

        llTypes = findViewById(R.id.TypeLayout);

        Bundle bundle = getIntent().getExtras();
        listName = bundle.getString("thisListName");
        List<ItemType> allTypes = myTasks.getAllTypes();
        if (allTypes == null){
            return;
        }
        for(int i = 0; i < allTypes.size(); i++){
            Button itemTypeButton = new Button(this);
            itemTypeButton.setText(allTypes.get(i).getItemType());
            llTypes.addView(itemTypeButton);
            itemTypeButton.setOnClickListener(itemsInType(itemTypeButton));
        }
    }

    View.OnClickListener itemsInType(final Button button)  {
        return new View.OnClickListener() {
            public void onClick(View v) {
                Intent ItemInTypeScreenIntent = new Intent(context, UIItemsInType.class);
                ItemInTypeScreenIntent.putExtra("thisListName", listName);
                ItemInTypeScreenIntent.putExtra("thisTypeName", button.getText().toString());
                startActivity(ItemInTypeScreenIntent);
                finish();
            }
        };
    }
}
