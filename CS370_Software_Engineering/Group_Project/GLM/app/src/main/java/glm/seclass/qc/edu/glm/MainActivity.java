package glm.seclass.qc.edu.glm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.List;

public class MainActivity extends AppCompatActivity {

   // FloatingActionMenu materialDesignFAM;
    FloatingActionButton destroy;
    FloatingActionButton createList;
    FloatingActionButton search;


    Context context = this;
    LinearLayout scrollView;
    GLDatabase db;
    MyTasks myTasks;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        InitDatabase.destroy(this);
//        return;
       // destroy = findViewById(R.id.destroyDB);
        createList = findViewById(R.id.createList);


     //   materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
     //   destroy = (FloatingActionButton) findViewById(R.id.destroyDB);
        createList = (FloatingActionButton) findViewById(R.id.createList);


        myTasks = new MyTasks(this);
        myTasks.populateDB();


        scrollView = (LinearLayout) findViewById(R.id.MainLayout);

        update();
        createList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View promptsView = layoutInflater.inflate(R.layout.prompt, null);
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setView(promptsView);
                final EditText userInput = (EditText) promptsView.findViewById(R.id.newListName);
                userInput.clearFocus();
                userInput.setSingleLine(true);


                alert
                        .setCancelable(false)
                        .setPositiveButton("Create",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        String userText = userInput.getText().toString();

                                        if (userText.isEmpty()) {
                                            CharSequence text = "Please enter a list name";
                                            int duration = Toast.LENGTH_SHORT;
                                            Toast toast = Toast.makeText(context, text, duration);
                                            toast.show();
                                        }

                                        else if(myTasks.findExistingList(userText)){
                                            CharSequence text = "That list already exists!";
                                            int duration = Toast.LENGTH_SHORT;
                                            Toast toast = Toast.makeText(context, text, duration);
                                            toast.show();
                                        }
                                        else{
                                            myTasks.insertNewList(userText);
                                            Button newList = new Button(context);
                                            newList.setText(userInput.getText());
                                            newList.setOnClickListener(showListView(newList));
                                            scrollView.addView(newList);
                                        }
                                    }
                        })
                        .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }
                        );

                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });
//        destroy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                InitDatabase.destroy(context);
//            }
//        });
    }

//    Adds listener for new list buttons so that they can display new views
    View.OnClickListener showListView(final Button button)  {
        return new View.OnClickListener() {
            public void onClick(View v) {
                Intent showListView = new Intent(context, UIList.class);
                showListView.putExtra("thisListName", button.getText().toString());

                startActivityForResult(showListView, 1);
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1) {
            update();
        }
    }

    public void update(){
        scrollView.removeAllViews();
        final List<GroceryList> allLists = myTasks.getLists();
        for(int i = 0; i < allLists.size(); i++){
            Button button = new Button(context);
            button.setId(i+1);
            button.setText(allLists.get(i).getListName());
            final int finalI = i;
            button.setOnClickListener(showListView(button));
            scrollView.addView(button);
        }
    }
}

