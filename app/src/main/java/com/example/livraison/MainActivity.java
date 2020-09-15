package com.example.livraison;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button add,register,orders;
    EditText ingredient;
    TextView panier,ingredientPrincipal;
    ListView list;
    ArrayList ingPrp = new ArrayList<String>(); // list of all ingredients presented in the app
    ArrayList ingScd = new ArrayList<String>(); // list of all selected iteams + ingredients personally added
    ArrayList Orders = new ArrayList<String>(); // list of all orders made
    // types = new ArrayList<String>(); // list of all types of ingredients: Floor ...
    String [] types={"---CHOOSE THE TYPE OF THE ADDED ITEM ---","Farine","Sweeteners","Fats","Liquids","Stuffing","Others"};

    // array lists of principal ingredients:
    ArrayList farine = new ArrayList<String>();
    ArrayList sweeteners = new ArrayList<String>();
    ArrayList fats = new ArrayList<String>();
    ArrayList liquids = new ArrayList<String>();
    ArrayList stuffing = new ArrayList<String>();
    ArrayList others = new ArrayList<String>();
    //
    int num_order = 1; // number of order made
    int num_adapter=1; // which list is shown on the adapter. So that if an item is clicked on the basket or on the order, It will not be selected

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Cake Delivery");


        add = (Button) findViewById(R.id.add);
        register = (Button) findViewById(R.id.register);
        orders = (Button) findViewById(R.id.orders);
        ingredient = (EditText) findViewById(R.id.ingredient);
        panier = (TextView) findViewById(R.id.panier);
        ingredientPrincipal = (TextView) findViewById(R.id.showIngredient);
        list = (ListView) findViewById(R.id.listview);
        makeliste(); // Fill list of principal ingredients

        final ArrayAdapter<String> adapterPrp = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ingPrp);
        final ArrayAdapter<String> adapterScd = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ingScd);
        final ArrayAdapter<String> adapterOrder = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Orders);

        // show principal ingredient list
        list.setAdapter(adapterPrp);

        // click on list: item added to basket
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                if(num_adapter==1) { // if clicked on list of ingredients and not the other lists
                    if (i != 0 && i != 5 && i != 10 && i != 14 && i != 19 && i != 23) { // i est un titre de sous ingredients
                        // if its type is ...
                        if(i>0 && i<5)
                            farine.add(ingPrp.get(i).toString());
                        if(i>5 && i<10)
                            sweeteners.add(ingPrp.get(i).toString());
                        if(i>10 && i<14)
                            fats.add(ingPrp.get(i).toString());
                        if(i>14 && i<19)
                            liquids.add(ingPrp.get(i).toString());
                        if(i>19 && i<23)
                            stuffing.add(ingPrp.get(i).toString());
                        if(i>23)
                            others.add(ingPrp.get(i).toString());
                        // msg
                        Toast.makeText(MainActivity.this, ingPrp.get(i).toString() + " added to basket", Toast.LENGTH_SHORT).show();
                        ingScd.clear();
                    }
                }
            }
        });

        // Show ingredients that were chosen
        panier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingScd.clear();// empty list from what was there before
                make_ingScd();

                list.setAdapter(adapterScd);
                num_adapter=2; // we are on the second list which shows ingredients chosen
            }
        });

        // Show principal ingredients
        ingredientPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.setAdapter(adapterPrp);
                num_adapter=1; // we are on the first list which shows principal ingredients
            }
        });

        // Add personal ingredients
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ingredient.length() != 0) {
                    //Ingredient to add
                    //ingScd.add(ingredient.getText().toString());
                    //Toast.makeText(MainActivity.this, ingredient.getText().toString() + " added to basket", Toast.LENGTH_SHORT).show();

                    // Choose type of ingredient using AlertDialogue
                    final String mot = ingredient.getText().toString();
                    ingredient.setText("");
                    ingScd.clear();

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Type of the ingredient added");
                    builder.setItems(types, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            switch (i) {
                                case 0: // horse
                                case 1: farine.add(mot);break;
                                case 2: sweeteners.add(mot);break;
                                case 3: fats.add(mot);break;
                                case 4: liquids.add(mot);break;
                                case 5: stuffing.add(mot);break;
                                case 6: others.add(mot);break;
                            }
                        }
                    });
                    // create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }else
                    Toast.makeText(MainActivity.this,  "No ingredient to add", Toast.LENGTH_SHORT).show();
            }
        });

        // Register your order
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                make_ingScd();
                if(farine.size()==0 || fats.size()==0 || others.size()==0){
                    Toast.makeText(MainActivity.this,  "Can't make cake with only these. Please add more", Toast.LENGTH_SHORT).show();
                }
                else if(ingScd.size()==0){
                    Toast.makeText(MainActivity.this,  "No item selected for this order!!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Orders.add("--------------------------------------ORDER " + num_order);
                    make_ingScd();
                    for(int i=0; i<ingScd.size();i++){
                        Orders.add(ingScd.get(i));
                    }

                    ingScd.clear();
                    farine.clear();
                    sweeteners.clear();
                    fats.clear();
                    liquids.clear();
                    stuffing.clear();
                    others.clear();

                    Toast.makeText(MainActivity.this,  "Order registered Successfully!!", Toast.LENGTH_SHORT).show();
                    num_order++;
                }

            }
        });

        // See all orders made
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.setAdapter(adapterOrder);
                num_adapter=3;// we are on the third list which shows all orders made
            }
        });

    }


    // Remplir la liste des ingredients donnés
    public void makeliste(){  // 0 5 10  14  19  23
        ingPrp.add("                                      --FARINE--"); ingPrp.add("Wheat floor"); ingPrp.add("Almond powder");ingPrp.add("Cornscrach");ingPrp.add("Mixte nuts powder");//ingPrp.add("Other (type one)");ingPrp.add("none");
        ingPrp.add("                                  --SWEETENERS--");ingPrp.add("Refined sugar");ingPrp.add("Brown sugar");ingPrp.add("Honey");ingPrp.add("Corn syrup");
        ingPrp.add("                                      --FATS--");ingPrp.add("Regular butter");ingPrp.add("Nuts butter");ingPrp.add("Coconut oil");
        ingPrp.add("                                      --LIQUIDS--");ingPrp.add("Regular milk");ingPrp.add("Nuts milk");ingPrp.add("Regular Cream");ingPrp.add("Nuts cream");
        ingPrp.add("                                    --STUFFING--");ingPrp.add("Berries");ingPrp.add("Chocolate");ingPrp.add("Cream cheese");
        ingPrp.add("                                      --OTHERS--");ingPrp.add("Eggs");ingPrp.add("Baking powder");
    }
    public void make_ingScd(){
        ingScd.clear();
        if(farine.size()!=0){
            ingScd.add("---------------------FLOOR:");
            for(int i=0; i<farine.size();i++)
                ingScd.add(farine.get(i));
        }
        if(sweeteners.size()!=0){
            ingScd.add("---------------------SWEETENERS");
            for(int i=0; i<sweeteners.size();i++)
                ingScd.add(sweeteners.get(i));
        }
        if(fats.size()!=0){
            ingScd.add("---------------------FATS:");
            for(int i=0; i<fats.size();i++)
                ingScd.add(fats.get(i));
        }
        if(liquids.size()!=0){
            ingScd.add("---------------------LIQUIDS:");
            for(int i=0; i<liquids.size();i++)
                ingScd.add(liquids.get(i));
        }
        if(stuffing.size()!=0){
            ingScd.add("---------------------STUFFING:");
            for(int i=0; i<stuffing.size();i++)
                ingScd.add(stuffing.get(i));
        }
        if(others.size()!=0){
            ingScd.add("---------------------OTHERS:");
            for(int i=0; i<others.size();i++)
                ingScd.add(others.get(i));
        }
    }

}
