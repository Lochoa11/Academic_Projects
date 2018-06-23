package glm.seclass.qc.edu.glm;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
public class SearchEngine extends AppCompatActivity {

    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;
    ListView listView;
    EditText editText;
    MyTasks myTasks;
    String listName;
    Button addItemBtn;
    Context context = this;
    String type, itemName;
    String addNewItemText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_engine);
        listView = (ListView) findViewById(R.id.listview);
        editText = (EditText) findViewById(R.id.txtsearch);
        addItemBtn = (Button) findViewById(R.id.addItemButton);
       // addNewItemText = "Add a new item";
        initList();
        Bundle bundle = getIntent().getExtras();
        listName = bundle.getString("listName");

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().equals("")) {
                    initList();
                } else {
                    //perform search
                    searchItem(s.toString());
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        addItemBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                View mView = getLayoutInflater().inflate(R.layout.add_item_prompt,null);
                mBuilder.setTitle("Add new item");
                final Spinner mSpinner = (Spinner) mView.findViewById(R.id.spinner);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.types_array));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(adapter);
                final EditText editItemName = (EditText) mView.findViewById(R.id.item_name);

                mBuilder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(!mSpinner.getSelectedItem().toString().equalsIgnoreCase("Choose a type...")){
                                type = mSpinner.getSelectedItem().toString();
                            itemName = editItemName.getText().toString();
                            if(itemName.isEmpty()){
                                Log.e("itemName" , "Item name Is empty");
                            }
                            Log.e("itemName" , itemName);
                            if(myTasks.itemExists(itemName) ){
                                Log.e("exists" , "exists");

                            } else {
                                myTasks.insertItemOfType(itemName, type);
                                initList();
                                Log.e("tagfdsf" , "hey");
                            }
                        }
                        }

                } );

                mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String itemName = (String) listView.getItemAtPosition(position);
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                AlertDialog.Builder alert = new AlertDialog.Builder(context);

                alert.setMessage("Add this item?");

//                if(itemName == addNewItemText){
//                    alert.setMessage("Add a new item");
////                    View mView = getLayoutInflater().inflate(R.layout.add_item_prompt , null);
////                    Spinner spinner = (Spinner) mView.findViewById(R.id.spinner);
////                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.types_array));
////                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////                    spinner.setAdapter(adapter);
//                } else{
//                    alert.setMessage("Add this item?");
//                }
                //final EditText userInput = (EditText) promptsView.findViewById(R.id.newListName);

                alert
                        .setCancelable(false)
                        .setPositiveButton("Enter",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                         if(myTasks.itemIsInList(listName , itemName)){
                                            CharSequence text = "That Item already exists!";
                                            Log.e("SearchEngine" , "This item already exists");
                                            int duration = Toast.LENGTH_SHORT;
                                            Toast toast = Toast.makeText(context, text, duration);
                                            toast.show();
                                        } else{
                                            myTasks.insertToList(listName , itemName );
                                            setResult(1);
                                            finish();
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
    }


    public void searchItem(String textToSearch){
        String[] arr = new String[listItems.size()];
        arr = listItems.toArray(arr);
        for(String item:arr){
            if(!item.toLowerCase().contains(textToSearch.toString().toLowerCase())){
                listItems.remove(item);
            }
        }

        adapter.notifyDataSetChanged();
    }
    public void initList(){
        myTasks = new MyTasks(this);

        List<Item> itemList = myTasks.getItems();

        listItems = new ArrayList<>();
        for(int i = 0; i < itemList.size() ; i++ ){
            listItems.add(itemList.get(i).getItemName());
        }

       // listItems.add(addNewItemText);

        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.txtitem, listItems);
        listView.setAdapter(adapter);

    }
}