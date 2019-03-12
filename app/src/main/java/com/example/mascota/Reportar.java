package com.example.mascota;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Reportar extends AppCompatActivity {
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportar);
    }

    public void guardarDatos(View view){
       textView = (TextView)findViewById(R.id.input_tipo);
       final String tipo = textView.getText().toString();
       textView = (TextView)findViewById(R.id.input_nombre_mascota);
       final String nombre_mascota = textView.getText().toString();
       textView = (TextView)findViewById(R.id.input_direccion);
       final String direccion = textView.getText().toString();
       textView = (TextView)findViewById(R.id.input_estado);
       final String estado = textView.getText().toString();
       textView = (TextView)findViewById(R.id.input_tel);
       final String telefono = textView.getText().toString();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://vqmzn2zar0.execute-api.us-west-2.amazonaws.com/dev/");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("nombre", nombre_mascota);
                    jsonParam.put("direccion", direccion);
                    jsonParam.put("Telefono", telefono);
                    jsonParam.put("Estado", estado);
                    jsonParam.put("Tipo", tipo);

                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();

                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

    }
}
