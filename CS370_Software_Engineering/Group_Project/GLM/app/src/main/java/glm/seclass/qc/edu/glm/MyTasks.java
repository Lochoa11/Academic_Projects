package glm.seclass.qc.edu.glm;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lin on 11/20/17.
 */

public class MyTasks {


    private static GLDatabase db;
    private static Context context;
    private static boolean populated = false;

//    All methods that return information or set db info

    public MyTasks(Context context) {
        this.context = context;
        InitDatabase.create(context);
        this.db = InitDatabase.getDB();
    }

    public void populateDB() {
        if(populated) return;

        new Populate().execute();
    }

    public void renameList(String listName, String newListName){
        try {
//            String[] s = {itemName, listName};
            Void wait = new RenameList().execute(listName , newListName).get();
        } catch (Exception e) {
            Log.e("Tag", "Error in mytask getLists method");
        }
    }


    public void insertItemOfType(String itemName , String typeName){
        try {
//            String[] s = {itemName, listName};
            Void wait = new InsertItemOfType().execute(itemName , typeName).get();
        } catch (Exception e) {
            Log.e("Tag", "Error in mytask getLists method");
        }

    }

    private static boolean itemExists;
    public boolean itemExists(String itemName){
        try {
//            String[] s = {itemName, listName};
            Void wait = new ItemExists().execute(itemName).get();
        } catch (Exception e) {
            Log.e("Tag", "Error in itemExists getLists method");
        }
        return itemExists;
    }
    private static boolean itemIsInList;
    public static boolean itemIsInList(String listName , String itemName){
        try {
            Void wait = new ItemIsInList().execute(listName,itemName).get();
        } catch (Exception e) {
            Log.e("Tag", "Error in mytask items is in list method");
        }
        return itemIsInList;
    }

    private static List<GroceryList> allLists;

    public List<GroceryList> getLists() {
        try {
//            this makes main thread wait for new thread to finish
            Void wait = new GetLists().execute().get();
        } catch (Exception e) {
            Log.e("Tag", "Error in mytask getLists method");
        }
        return allLists;
    }

    private static List<Item> allItems;
    public List<Item> getItems(){
        try {
            Void wait = new GetItems().execute().get();
        }
        catch (Exception e){
            Log.e("Tag", "Error in mytask getLists method");
        }
        return allItems;
    }

    public static void populateList(String listName){
        try {
            Void wait = new PopulateList().execute(listName).get();
        }
        catch (Exception e){
            Log.e("another tag", "error in insert to list method");
        }
    }

    private static Boolean found;

    public Boolean findExistingList(String listName) {
        try {
            Void wait = new FindList().execute(listName).get();
        } catch (Exception e) {
            Log.e("Tag", "Error in mytaskfind");
        }
        return found;
    }

    public static void insertNewList(String newListName) {
        try {
//            this makes main thread wait for new thread to finish
            Void wait = new InsertNewList().execute(newListName).get();
        } catch (Exception e) {
            Log.e("tag", "Error in mytask insert new list method");
        }
    }

    private static List<ListToItem> listItemsList;

    public static List<ListToItem> getListItems(String listName){

        try{
            Void wait = new GetListItems().execute(listName).get();

        }
        catch (Exception e){
            Log.e("Some tag", "error in get listItems method in my task");

        }
        return listItemsList;
    }

    private static String itemName;
    public static String getItem(int itemId){
        try{
            Void wait = new GetItem().execute(itemId).get();
        }
        catch (Exception e){
            Log.e("tag", "something went wrong getting item in mytask method");
        }
        return itemName;
    }

    private static List<ListToItem> listItems;
    private static List<Item> listOfItemsFromSearch;
    public static List<Item> searchSimilarItems() {
        try {
            Void wait = new SearchSimilarItems().execute().get();
        } catch (Exception e) {
            Log.e("Tag", "Error in searchSimilarItems method");
        }
        return listOfItemsFromSearch;
    }

    private static List<ItemType> allItemTypes;
    public static List<ItemType> getAllTypes(){
        try{

            Void wait = new GetAllTypes().execute().get();

        }catch (Exception e){
            Log.e("Tag", "error in get all types");
        }
        return allItemTypes;
    }

    private static List<Item> itemsOfType;
    public static List<Item> getItemsOfType(String typeName){
        try{
            Void wait = new GetItemsOfType().execute(typeName).get();
        }
        catch (Exception e){
            Log.e("tag", "error in get items of type method in my task");
        }
        return itemsOfType;
    }

    public static void insertToList(String listName, String itemName){
        try {

            Void wait = new InsertToList().execute(listName, itemName).get();
        }
        catch (Exception e){
            Log.e("tag", "error in inserToList method myTask");
        }
    }

    public static  void deleteList(String listName){
        try {
            Void wait = new DeleteList().execute(listName).get();
        }
        catch (Exception e){
            Log.e("tag", "error in deleteList method mytask");
        }
    }

    public static void deleteItemFromList(String listName, String itemName){
        try{
            Void wait = new DeleteItemFromList().execute(listName, itemName).get();
        }
        catch (Exception e){
            Log.e("tag", "error in deleteItemFromList");
        }
    }

    public static void check(ListToItem listToItem){
        try {
            Void wait = new Check().execute(listToItem).get();
        }
        catch (Exception e){

        }
    }

    private static int qty;
    public static int getQty(ListToItem listToItem){
        try {
            Void wait = new GetQty().execute(listToItem).get();
        }
        catch (Exception e){
            Log.e("tag", "error in getQTY method");
        }
        return qty;
    }

    private static ItemType getMeasurementVariable;
    public static String getMeasurement(String itemName){
        try {
            Void wait = new GetMeasurement().execute(itemName).get();
        }
        catch (Exception e){
            Log.e("tag", "error in getMeasurement");
        }
        return getMeasurementVariable.getMeasurement();
    }

    public static void setQty(ListToItem listToItem){
        try {
            Void wait = new SetQty().execute(listToItem).get();
        }
        catch (Exception e){
            Log.e("tag", "error in setting qty method mytask");
        }
    }


    public static void updateListToItem(List<ListToItem> listToItem){
        try {
            Void wait = new UpdateListToItems().execute(listToItem).get();
        }
        catch (Exception e){
            Log.e("tag", "error in updateListToItem");
        }
    }

//    All db operations that are done on second thread

    private static class Populate extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Perform pre-adding operation here.
        }
        @Override

        protected Void doInBackground(Void... params) {
            try {
                if (!db.itemDAO().getAllItems().isEmpty()) return null;
                ItemType drinks = new ItemType("Drink", "Ounce");
                ItemType fruits = new ItemType("Fruit", "Ounce");
                ItemType meat = new ItemType("Meat", "Ounce");

                List<ItemType> listOfTypes = new ArrayList<>();
                listOfTypes.add(drinks);
                listOfTypes.add(fruits);
                listOfTypes.add(meat);

                long[] typeID = db.itemTypeDAO().insert(listOfTypes.get(0), listOfTypes.get(1), listOfTypes.get(2));
                String[] drinksAry = {"Sprite", "Coke", "Pepsi", "Fanta", "Vitamin Water", "Gatorade", "Mountain Dew",
                        "Orange Juice", "Water", "Beer"};
                String[] fruitsAry = {"Apple", "Orange", "Banana", "Tangerine", "Lemon", "Mango", "Watermelon",
                        "Raspberries", "Grapefruit", "Peach"};
                String[] meatAry = {"Beef", "Steak", "Chicken", "Bacon", "Turkey", "Ham", "Pepperoni", "Spam",
                        "Hotdog", "Meatball"};
                List<Item> listOfItems = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    listOfItems.add(new Item(drinksAry[i], (int) typeID[0]));
                    listOfItems.add(new Item(fruitsAry[i], (int) typeID[1]));
                    listOfItems.add(new Item(meatAry[i], (int) typeID[2]));
                }
                db.itemDAO().insertAll(listOfItems);
            } catch (Exception e) {

                Log.e("Tag", e.getMessage() + "Error in populate async dib");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            populated = true;
            super.onPostExecute(aVoid);
        }

    }

    private static class GetLists extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Perform pre-adding operation here.
        }

        @Override
        protected Void doInBackground(Void... params) {
            allLists = db.groceryListDAO().getAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //To after addition operation here.
        }

    }

    private static class FindList extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Perform pre-adding operation here.
        }

        @Override
        protected Void doInBackground(String... name) {

            GroceryList listCandidate = db.groceryListDAO().find(name[0]);

            if (listCandidate != null) {
                found = true;
            } else {
                found = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void params) {
            super.onPostExecute(params);
            //To after addition operation here.
        }

    }

    private static class InsertNewList extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... strings) {
            GroceryList newlist = new GroceryList();
            newlist.setListName(strings[0]);
            db.groceryListDAO().insert(newlist);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

    }

    private static class SearchSimilarItems extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(String... strings) {
            listItems = db.listToItemDAO().getAllItems(db.groceryListDAO().find(strings[0]).getListId());
            String s = "%" + strings[0] + "%";
            listOfItemsFromSearch = db.itemDAO().searchItem(s);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

    }

    private static class GetListItems extends AsyncTask<String, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... strings) {
            try{
                String listName = strings[0];
                GroceryList list = db.groceryListDAO().find(listName);

                int listId = list.getListId();

                List<ListToItem> temp = db.listToItemDAO().getAllItems(listId);

                List<ItemType> itemTypes = db.itemTypeDAO().getAll();


                ArrayList<ListToItem> temp2 = new ArrayList<>();

                for(int j = 0; j < itemTypes.size(); j++){
                    for(int i = 0; i < temp.size(); i++){
                        if(itemTypes.get(j).getTypeId() == db.itemDAO().getItem(temp.get(i).getItemId()).getTypeId()){
                            temp2.add(temp.get(i));
                        }
                    }
                }
                listItemsList = temp2;
            }
            catch (Exception e){
                Log.e("tag", "Error in async task" + e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

    }

    private static class GetItem extends AsyncTask<Integer, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Integer... ints) {
            Item item = db.itemDAO().getItem(ints[0]);
            itemName = item.getItemName();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

    }

    private static class PopulateList extends AsyncTask<String, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(String... strings) {
            try{
                ItemType newType = new ItemType("dummyType", "dummy oz");
                db.itemTypeDAO().insert(newType);

                int typeId = db.itemTypeDAO().get("dummyType");
                Item someItem = new Item("dummy", typeId);
                db.itemDAO().insert(someItem);

                ListToItem listToItem = new ListToItem();

                int itemId = db.itemDAO().getItemId("dummy");
                listToItem.setItemId(itemId);
                GroceryList list = db.groceryListDAO().find(strings[0]);
                listToItem.setListId(list.getListId());
                db.listToItemDAO().insert(listToItem);
            }
            catch (Exception e){
                Log.e("Tag", " error in dib Populate" + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

    }

    private static class GetItems extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Perform pre-adding operation here.
        }

        @Override
        protected Void doInBackground(Void... params) {
            allItems = db.itemDAO().getAllItems();
            return null;
        }
        @Override
        protected void onPostExecute(Void params) {
            super.onPostExecute(params);
        }

    }

    private static class GetAllTypes extends AsyncTask<Void, Void, Void>{
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            allItemTypes = db.itemTypeDAO().getAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

    }

    private static class GetItemsOfType extends AsyncTask<String, Void, Void>{
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... strings) {
            int typeId = db.itemTypeDAO().get(strings[0]);
            itemsOfType = db.itemDAO().getItemsOfType(typeId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

    }

    private static class InsertToList extends AsyncTask<String, Void, Void>{
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... strings) {
            ListToItem listToItem = new ListToItem();
            int listId = db.groceryListDAO().find(strings[0]).getListId();
            listToItem.setListId(listId);
            int itemId = db.itemDAO().getItemId(strings[1]);
            listToItem.setItemId(itemId);
            db.listToItemDAO().insert(listToItem);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

    }

    private static class DeleteList extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground( String... strings) {
            GroceryList list = db.groceryListDAO().find(strings[0]);
            db.listToItemDAO().deleteItems(list.getListId());
            db.groceryListDAO().delete(list);
            return null;
        }
    }

    private static class DeleteItemFromList extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground( String... strings) {
            int listId = db.groceryListDAO().find(strings[0]).getListId();
            int itemId = db.itemDAO().getItemId(strings[1]);
            db.listToItemDAO().delete(db.listToItemDAO().get(listId, itemId));
            return null;
        }
    }

    private static class ItemIsInList extends AsyncTask<String, Void, Void>{
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {

                String listName = strings[0];
                String itemName = strings[1];

                int itemID = db.itemDAO().getItemId(itemName);

                GroceryList groceryList = db.groceryListDAO().find(listName);

                int listID = groceryList.getListId();
                ListToItem listToItem = db.listToItemDAO().get(listID,itemID);
                if(listToItem != null ){
                    itemIsInList = true;
                } else {
                    itemIsInList = false;
                }
            } catch(Exception e){
                Log.e("mytask tag" , e.getMessage());

            }

            return null;


        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

    }


    private static class ItemExists extends AsyncTask<String, Void, Void>{
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... strings) {
            String itemName = strings[0];

            try {
                Item item = db.itemDAO().getItemWithName(itemName);
                if(item == null ){
                    itemExists = false;
                    return null;
                }
                if(item.getItemName().isEmpty()){
                    itemExists = false;
                    Log.e("null " , "isNull");
                } else{
                    itemExists = true;
                    Log.e("notnull" , "notnull");
                    Log.e("name" , item.getItemName());
                }

            } catch(Exception e){
                Log.e("mytask tag" , e.getMessage());

            }

            return null;


        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

    }
    private static class InsertItemOfType extends AsyncTask<String, Void, Void>{
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... strings) {
            String itemName = strings[0];
            String typeName = strings[1];
            Log.e("fklsdjfldk" , itemName);
            int typeID = db.itemTypeDAO().get(typeName);
            db.itemDAO().insert(new Item(itemName , typeID));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

    }

    private static class Check extends AsyncTask<ListToItem, Void, Void>{
        @Override
        protected Void doInBackground(ListToItem... listToItems) {
            db.listToItemDAO().update(listToItems[0]);
            return null;
        }
    }

    private static class GetQty extends AsyncTask<ListToItem, Void, Void>{
        @Override
        protected Void doInBackground(ListToItem... listToItems) {
            qty = db.listToItemDAO().get(listToItems[0].getListId(),listToItems[0].getItemId()).getQuantity();
            return null;
        }
    }

    private static class GetMeasurement extends AsyncTask<String, Void, Void>{
        @Override
        protected Void doInBackground(String ...strings) {
            Item item = db.itemDAO().getItem((db.itemDAO().getItemId(strings[0])));
            getMeasurementVariable = db.itemTypeDAO().getItemType(item.getTypeId());
            return null;
        }
    }

    private static class SetQty extends AsyncTask<ListToItem, Void, Void>{
        @Override
        protected Void doInBackground(ListToItem ... listToItems) {

            db.listToItemDAO().update(listToItems[0]);

            return null;
        }
    }

    private static class UpdateListToItems extends AsyncTask<List<ListToItem>, Void, Void>{
        @Override
        protected Void doInBackground(List<ListToItem> ... listToItems) {

            for(int i = 0; i < listToItems[0].size(); i++){
                db.listToItemDAO().update(listToItems[0].get(i));
            }

            return null;
        }
    }

    private static class RenameList extends AsyncTask<String, Void, Void>{
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... strings) {
            String listName = strings[0];
            Log.e("listname is " , listName);
            String newListName = strings[1];
            int listID = db.groceryListDAO().getID(listName);
            db.groceryListDAO().rename(listID , newListName);

            Log.e("list id is " , Integer.toString(listID));

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

    }
//    Template for asyncTask
//    private static class AnotherAsyncTask extends AsyncTask<String, Void, Void>{
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected Void doInBackground(String... strings) {
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//        }
//
//    }
}
