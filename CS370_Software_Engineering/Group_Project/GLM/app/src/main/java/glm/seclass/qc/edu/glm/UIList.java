package glm.seclass.qc.edu.glm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.List;

/**
 * Created by Lin on 11/21/17.
 */

public class UIList extends AppCompatActivity {


    FloatingActionMenu materialDesignFAM;
    FloatingActionButton addItem;
    FloatingActionButton deleteThisList;
    FloatingActionButton searchForItem;
    FloatingActionButton unCheckAll;
    FloatingActionButton renameThisList; // @+id/rename_btn


    LinearLayout llList;
    MyTasks myTasks;
    String listName;
    TextView title;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        myTasks = new MyTasks(context);
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        unCheckAll = (FloatingActionButton) findViewById(R.id.uncheckall);
        addItem = (FloatingActionButton) findViewById(R.id.add_btn);
        deleteThisList = (FloatingActionButton) findViewById(R.id.delete_btn);
        searchForItem = (FloatingActionButton) findViewById(R.id.search_btn);
        renameThisList = (FloatingActionButton) findViewById(R.id.rename_btn);
        title = findViewById(R.id.listname);
        llList = findViewById(R.id.ListLayout);

        llList.setFocusable(true);
        llList.setFocusableInTouchMode(true);

        Bundle bundle = getIntent().getExtras();

        listName = bundle.getString("thisListName");
        title.setText(listName);
        update();

        unCheckAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ListToItem> listToItems = myTasks.getListItems(listName);
                for(int i = 0; i < listToItems.size(); i++){
                    if (listToItems.get(i).getCheckedOff() == true){
                        listToItems.get(i).setCheckedOff(false);
                    }
                }
                myTasks.updateListToItem(listToItems);
                update();
            }
        });

        addItem.setOnClickListener(displayTypeScreen(addItem));

        renameThisList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View promptsView = layoutInflater.inflate(R.layout.rename_prompt, null);
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setView(promptsView);
                final EditText userInput = (EditText) promptsView.findViewById(R.id.newListName);
                userInput.clearFocus();
                userInput.setSingleLine(true);

                alert
                        .setCancelable(false)
                        .setPositiveButton("Rename",
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
                                            Log.e("Else" , "else is ran");
                                            myTasks.renameList(listName , userText);
                                            listName = userText;
                                            Log.d("List name is " , listName);
                                            title.setText(listName);
                                            update();
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

        deleteThisList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTasks.deleteList(listName);
                setResult(1);
                finish();
            }
        });
        searchForItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent searchEngine = new Intent(context , SearchEngine.class);
                searchEngine.putExtra("listName" , listName);

                startActivityForResult(searchEngine , 1);
            }
        });
    }
//    displays type view from add button
    View.OnClickListener displayTypeScreen(final FloatingActionButton button)  {
        return new View.OnClickListener() {
            public void onClick(View v) {
                Intent showTypeView = new Intent(context, UIType.class);
                showTypeView.putExtra("thisListName", listName);
                startActivityForResult(showTypeView, 1);
            }
        };
    }



    // this function updates the items in the list
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1) {
            update();
        }
    }

    public void update(){
        materialDesignFAM.close(true);
        llList.removeAllViews();



        final List<ListToItem> listToItem = myTasks.getListItems(listName);

        for(int i = 0; i < listToItem.size(); i++){



            LinearLayout horizontalLL = new LinearLayout(this);
            horizontalLL.setOrientation(LinearLayout.HORIZONTAL);

            CheckBox cb = new CheckBox(this);
            cb.setChecked(listToItem.get(i).getCheckedOff());
            cb.setOnClickListener(updateDB(cb, listToItem.get(i)));
            horizontalLL.addView(cb);

            final String itemName = myTasks.getItem(listToItem.get(i).getItemId());
            TextView textViewName = new TextView(this);
            textViewName.setText(itemName);
            horizontalLL.addView(textViewName);


            final EditText editTextQuantity = new EditText(this);
            editTextQuantity.setCursorVisible(false);
            editTextQuantity.setSelectAllOnFocus(true);
            editTextQuantity.setSelected(false);
            editTextQuantity.setInputType(InputType.TYPE_CLASS_NUMBER);
            editTextQuantity.setNextFocusUpId(llList.getId());
            editTextQuantity.getNextFocusUpId();
            editTextQuantity.setImeOptions(EditorInfo.IME_ACTION_DONE);
            editTextQuantity.setText("" +myTasks.getQty(listToItem.get(i)));

            final int j = i;
            editTextQuantity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if(actionId == 6){
                        listToItem.get(j).setQuantity(Integer.parseInt(editTextQuantity.getText().toString()));
                        myTasks.setQty(listToItem.get(j));
                    }
                    return false;
                }
            });
            horizontalLL.addView(editTextQuantity);

            TextView textView = new TextView(this);
            Log.e("tag", itemName);
            textView.setText(myTasks.getMeasurement(itemName));
            horizontalLL.addView(textView);

            // Button Generated Dynamically
            Button deleteItem = new Button(this);
            deleteItem.setText("Delete");
            deleteItem.setTextColor(Color.WHITE);
            deleteItem.setBackgroundColor(Color.parseColor("#FFC0CB"));
            deleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                myTasks.deleteItemFromList(listName, itemName);
                update();
                }
            });
            horizontalLL.addView(deleteItem);

            llList.addView(horizontalLL);
        }
    }
    View.OnClickListener updateDB(final CheckBox cb, final ListToItem listToItem){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listToItem.setCheckedOff(!listToItem.getCheckedOff());
                myTasks.check(listToItem);
            }
        };
    }

}


