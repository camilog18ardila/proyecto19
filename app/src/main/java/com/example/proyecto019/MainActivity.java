package com.example.proyecto019;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText et1,et2,et3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1=findViewById(R.id.et1);
        et2=findViewById(R.id.et2);
        et3=findViewById(R.id.et3);

    }
    public void alta(View v) {
        if (validarcampos()){
            if (codigosiguales()) {
                AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                        "administracion", null, 1);
                SQLiteDatabase bd = admin.getWritableDatabase();
                String cod = et1.getText().toString();
                String descri = et2.getText().toString();
                String descrip = descri.trim().replaceAll("\\s{2,}+", " ");
                String pre = et3.getText().toString();
                ContentValues registro = new ContentValues();
                registro.put("codigo", cod);
                registro.put("descripcion", descrip);
                registro.put("precio", pre);
                bd.insert("articulos", null, registro);
                bd.close();
                et1.setText("");
                et2.setText("");
                et3.setText("");
                Toast.makeText(this, "Se cargaron los datos del artículo",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

    private boolean validarcampos(){
         boolean validar=true;
         if(et1.getText().toString().isEmpty()){
             validar= false;
             Toast.makeText(this, "el campo del codigo esta vacio",
                     Toast.LENGTH_SHORT).show();
             et1.setError("Este campo no puede quedar vacio");
         }else if(et2.getText().toString().isEmpty()){
             validar= false;
             Toast.makeText(this, "el campo de la descripcion esta vacio",
                     Toast.LENGTH_SHORT).show();
             et2.setError("Este campo no puede quedar vacio");
         }else if (et3.getText().toString().isEmpty()){
             validar= false;
             Toast.makeText(this, "el campo del precio esta vacio",
                     Toast.LENGTH_SHORT).show();
             et3.setError("Este campo no puede quedar vacio");
         }
         return validar;
    }

    private boolean codigosiguales(){
        boolean validar=true;
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String cod = et1.getText().toString();
        Cursor fila = bd.rawQuery("select descripcion,precio from articulos where codigo=" + cod, null);
        if (fila.moveToFirst()){
            Toast.makeText(this, "ya existe un artículo con dicho código",
                    Toast.LENGTH_SHORT).show();
            validar=false;
        } else

        bd.close();

        return validar;
    }

    public void consultaporcodigo(View v) {
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                    "administracion", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            if (et1.getText().toString().isEmpty()) {
                Toast.makeText(this, "el campo del codigo esta vacio  para la consulta",
                        Toast.LENGTH_SHORT).show();
                et1.setError("Este campo no puede quedar vacio");
            }else {

                String cod = et1.getText().toString();
                Cursor fila = bd.rawQuery(
                        "select descripcion,precio from articulos where codigo=" + cod, null);
                if (fila.moveToFirst()) {
                    et2.setText(fila.getString(0));
                    et3.setText(fila.getString(1));
                } else
                    Toast.makeText(this, "No existe un artículo con dicho código",
                            Toast.LENGTH_SHORT).show();
                bd.close();
            }
    }

    public void consultapordescripcion(View v) {
        if (et2.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "el campo de la descripcion esta vacio para la consulta",
                    Toast.LENGTH_SHORT).show();
            et2.setError("Este campo no puede quedar vacio");
        } else {
                AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                        "administracion", null, 1);
                SQLiteDatabase bd = admin.getWritableDatabase();
                String descri = et2.getText().toString();
                 String descrip = descri.trim().replaceAll("\\s{2,}+", " ");
                Cursor fila = bd.rawQuery(
                        "select codigo,precio from articulos where descripcion='" + descrip + "'", null);
                if (fila.moveToFirst()) {
                    et1.setText(fila.getString(0));
                    et3.setText(fila.getString(1));
                } else
                    Toast.makeText(this, "No existe un artículo con dicha descripción",
                            Toast.LENGTH_SHORT).show();
                bd.close();
            }
    }
    public void bajaporcodigo(View v) {
        if (et1.getText().toString().isEmpty()) {
            Toast.makeText(this, "el campo del codigo esta vacio  para la consulta",
                    Toast.LENGTH_SHORT).show();
            et1.setError("Este campo no puede quedar vacio");
        }else {
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                    "administracion", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            String cod = et1.getText().toString();
            int cant = bd.delete("articulos", "codigo=" + cod, null);
            bd.close();
            et1.setText("");
            et2.setText("");
            et3.setText("");
            if (cant == 1)
                Toast.makeText(this, "Se borró el artículo con dicho código",
                        Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "No existe un artículo con dicho código",
                        Toast.LENGTH_SHORT).show();
        }
    }
    public void modificacion(View v) {
        if (validarcampos()){

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String cod = et1.getText().toString();
        String descri = et2.getText().toString();
        String descrip = descri.trim().replaceAll("\\s{2,}+", " ");
        String pre = et3.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("codigo", cod);
        registro.put("descripcion", descrip);
        registro.put("precio", pre);
        int cant = bd.update("articulos", registro, "codigo=" + cod, null);
        bd.close();
        if (cant == 1) {
            Toast.makeText(this, "se modificaron los datos", Toast.LENGTH_SHORT)
                    .show();
            et1.setText("");
            et2.setText("");
            et3.setText("");
        }else
            Toast.makeText(this, "no existe un artículo con el código ingresado",
                    Toast.LENGTH_SHORT).show();
    }
}}
