package com.example.recettes;



import com.example.recettes.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayRecettes extends Activity {

   int from_Where_I_Am_Coming = 0;
   private DBHelper mydb ;
   TextView Title ;
   TextView Ingredients;
   TextView Realisation;
 
   int id_To_Update = 0;


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_display_recette);
      Title = (TextView) findViewById(R.id.editTextTitle);
      Ingredients = (TextView) findViewById(R.id.editTextIngredients);
      Realisation = (TextView) findViewById(R.id.editTextRealisation);
      
      mydb = new DBHelper(this);

      Bundle extras = getIntent().getExtras(); 
      if(extras !=null)
      {
         int Value = extras.getInt("id");
         if(Value>0){
            //means this is the view part not the add contact part.
            Cursor rs = mydb.getData(Value);
            id_To_Update = Value;
            rs.moveToFirst();
            String Titl = rs.getString(rs.getColumnIndex(DBHelper.RECETTES_COLUMN_TITLE));
            String Ingred = rs.getString(rs.getColumnIndex(DBHelper.RECETTES_COLUMN_INGREDIENTS));
            String Realisa = rs.getString(rs.getColumnIndex(DBHelper.RECETTES_COLUMN_REALISATION));
           
            if (!rs.isClosed()) 
            {
               rs.close();
            }
            Button b = (Button)findViewById(R.id.button1);
            b.setVisibility(View.INVISIBLE);

            Title.setText((CharSequence)Titl);
            Title.setFocusable(false);
            Title.setClickable(false);

            Ingredients.setText((CharSequence)Ingred);
            Ingredients.setFocusable(false); 
            Ingredients.setClickable(false);

            Realisation.setText((CharSequence)Realisa);
            Realisation.setFocusable(false);
            Realisation.setClickable(false);

          
           }
      }
   }
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      Bundle extras = getIntent().getExtras(); 
      if(extras !=null)
      {
         int Value = extras.getInt("id");
         if(Value>0){
            getMenuInflater().inflate(R.menu.display_recette, menu);
         }
         else{
            getMenuInflater().inflate(R.menu.mainmenu, menu);
         }
      }
      return true;
   }

   public boolean onOptionsItemSelected(MenuItem item) 
   { 
      super.onOptionsItemSelected(item); 
      switch(item.getItemId()) 
   { 
      case R.id.Edit_Recette: 
      Button b = (Button)findViewById(R.id.button1);
      b.setVisibility(View.VISIBLE);
      Title.setEnabled(true);
      Title.setFocusableInTouchMode(true);
      Title.setClickable(true);

      Ingredients.setEnabled(true);
      Ingredients.setFocusableInTouchMode(true);
      Ingredients.setClickable(true);

      Realisation.setEnabled(true);
      Realisation.setFocusableInTouchMode(true);
      Realisation.setClickable(true);

    

      return true; 
      case R.id.Delete_Recette:

      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setMessage(R.string.deleteContact)
     .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
         public void onClick(DialogInterface dialog, int id) {
            mydb.deleteContact(id_To_Update);
            Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();  
            Intent intent = new Intent(getApplicationContext(),com.example.recettes.MainActivity.class);
            startActivity(intent);
         }
      })
     .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
         public void onClick(DialogInterface dialog, int id) {
            // User cancelled the dialog
         }
      });
      AlertDialog d = builder.create();
      d.setTitle("Are you sure");
      d.show();

      return true;
      default: 
      return super.onOptionsItemSelected(item); 

      } 
   } 

   public void run(View view)
   {	
      Bundle extras = getIntent().getExtras();
      if(extras !=null)
      {
         int Value = extras.getInt("id");
         if(Value>0){
            if(mydb.updateRecette(id_To_Update,Title.getText().toString(), Ingredients.getText().toString(), Realisation.getText().toString())){
               Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();	
               Intent intent = new Intent(getApplicationContext(),com.example.recettes.MainActivity.class);
               startActivity(intent);
             }		
            else{
               Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();	
            }
		 }
         else{
            if(mydb.insertRecette(Title.getText().toString(), Ingredients.getText().toString(), Realisation.getText().toString())){
               Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();	
            }		
            else{
               Toast.makeText(getApplicationContext(), "not done", Toast.LENGTH_SHORT).show();	
            }
            Intent intent = new Intent(getApplicationContext(),com.example.recettes.MainActivity.class);
            startActivity(intent);
            }
      }
   }
}