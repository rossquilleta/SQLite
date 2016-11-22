package com.calc.rosana.sqliteprueba;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {

    EditText cod, desc, prix;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdminSQLiteHelper admin = new AdminSQLiteHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        cod = (EditText) findViewById(R.id.codigo);
        desc = (EditText) findViewById(R.id.descipcion);
        prix = (EditText) findViewById(R.id.precio);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    public void alta(View view) {
        AdminSQLiteHelper admin = new AdminSQLiteHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();     //objeto bd
        String codg = cod.getText().toString();
        String descr = desc.getText().toString();
        String price = prix.getText().toString();

        ContentValues registro = new ContentValues();  //objeto para insertar datos

        registro.put("codigo", codg);            //metodo para crear registros
        registro.put("descripcion", descr);
        registro.put("precio", price);

        bd.insert("articulos", null, registro);   // insertando datos en bd
        bd.close();

        cod.setText("");
        desc.setText("");
        prix.setText("");

        Toast.makeText(this, "Datos cargados", Toast.LENGTH_SHORT).show();
    }

    public void consultaCdg(View view) {
        AdminSQLiteHelper admin = new AdminSQLiteHelper(this, "administracion", null, 1);
        SQLiteDatabase bdd = admin.getWritableDatabase();
        String codg = cod.getText().toString();
        Cursor fila = bdd.rawQuery("select descripcion, precio from articulos where codigo=" + cod, null);

        if (fila.moveToFirst()) {
            desc.setText(fila.getString(0));
            prix.setText(fila.getString(1));
        } else
            Toast.makeText(this, "No hay datos", Toast.LENGTH_SHORT).show();
        fila.close();
        bdd.close();
    }

    public void consultadescr(View view) {
        AdminSQLiteHelper admin = new AdminSQLiteHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String codg = cod.getText().toString();

        Cursor fila = bd.rawQuery("select codigo, precio from articulos where descripcio='" + desc+"'", null);

        if (fila.moveToFirst()) {
            cod.setText(fila.getString(0));
            prix.setText(fila.getString(1));
        } else
            Toast.makeText(this, "No hay datos", Toast.LENGTH_SHORT).show();
        bd.close();
    }

    public void bajaCdg(View view) {
        AdminSQLiteHelper admin = new AdminSQLiteHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String codg = cod.getText().toString();
        int cant = bd.delete("articulos", "codigo=" + cod, null);
        bd.close();

        cod.setText("");
        desc.setText("");
        prix.setText("");

        if (cant == 1) {
            Toast.makeText(this, "Borrado correctamente", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(this, "No se encontro registro", Toast.LENGTH_SHORT).show();
    }

    public void modificar(View view) {
        AdminSQLiteHelper admin = new AdminSQLiteHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();     //objeto bd
        String codg = cod.getText().toString();
        String descr = desc.getText().toString();
        String price = prix.getText().toString();

        ContentValues registro = new ContentValues();  //objeto para operar con datos

        registro.put("Codigo", codg);            //metodo para crear registros
        registro.put("Descripcion", descr);
        registro.put("Precio", price);

        int cant = bd.update("articulos", registro, "codigo=" + codg, null);
        bd.close();

        cod.setText("");
        desc.setText("");
        prix.setText("");

        if (cant == 1) {
            Toast.makeText(this, "Modificado correctamente", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(this, "No se encontro registro", Toast.LENGTH_SHORT).show();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
