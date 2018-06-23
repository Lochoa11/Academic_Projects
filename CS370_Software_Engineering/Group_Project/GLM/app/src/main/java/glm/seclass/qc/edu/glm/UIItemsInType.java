package glm.seclass.qc.edu.glm;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Lin on 11/22/17.
 */

public class UIItemsInType extends AppCompatActivity{

    String typeName;
    String listName;
    TextView typeTitle;
    MyTasks myTasks;
    Context context = this;
    LinearLayout llItemsOfType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.items_in_type_view);
        Bundle bundle = getIntent().getExtras();
        typeName = bundle.getString("thisTypeName");
        listName = bundle.getString("thisListName");
        typeTitle = findViewById(R.id.typetitle);
        llItemsOfType = findViewById(R.id.ItemsOfTypeLayout);
        typeTitle.setText(typeName);
        List<Item> ofType = myTasks.getItemsOfType(typeName);
        for (int i = 0; i < ofType.size(); i++){
            Button button = new Button(this);
            button.setText(ofType.get(i).getItemName());
            button.setOnClickListener(addToList(button));
            llItemsOfType.addView(button);
        }
    }

//    inputs specific item to list also sends a result back to main activity
    View.OnClickListener addToList(final Button button)  {
        return new View.OnClickListener() {
            public void onClick(View v) {

                if(myTasks.itemIsInList(listName, button.getText().toString())){
                    CharSequence text = button.getText().toString() + " is already in " + listName;
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else{
                    myTasks.insertToList(listName, button.getText().toString());
                    setResult(1);
                    finish();
                }
            }
        };
    }

}
