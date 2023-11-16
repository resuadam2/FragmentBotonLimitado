package com.resuadam2.fragmentbotonlimitado;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements FrgBotonLimitado.OnClickListener {
    private static final int NUM_MAX_LOGIN = 3;
    EditText etUsuario,etPassword;
    CheckBox chkAceptarCondiciones;
    FrgBotonLimitado frgBotonLimitado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUsuario=findViewById(R.id.etUsuario);
        etPassword=findViewById(R.id.etPassword);
        chkAceptarCondiciones=findViewById(R.id.chkAceptarCondiciones);
        frgBotonLimitado=(FrgBotonLimitado) getSupportFragmentManager().findFragmentById(R.id.frgBotonLimitado);
        frgBotonLimitado.setOnClickListener(this, NUM_MAX_LOGIN,getString(R.string.entrar));
        frgBotonLimitado.setMostrarContadores(true);
    }
    @Override
    public boolean onClick(int numClic, int maxClics) {
        String error="";
        String usuario=etUsuario.getText().toString().trim();
        String password=etPassword.getText().toString();
        if (usuario.equals("") || password.equals(""))
            error+=getString(R.string.introduzcaCredenciales)+"\n";
        if(!chkAceptarCondiciones.isChecked()) error+=getString(R.string.acepteCondiciones)+"\n";
        if(!error.equals("")) {
            Toast.makeText(this, error,Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!credencialesCorrectas(usuario,password))
            Toast.makeText(this, getString(R.string.credencialesIncorrectas)+" "+numClic+"/"+maxClics,Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Correcto en "+numClic,Toast.LENGTH_LONG).show();
        return true;
    }
    private boolean credencialesCorrectas(String usuario,String password) {
        // TODO mejorar (es un ejemplo) validar, etc.
        return usuario.equals("david") && password.equals("sesamo");
    }
    @Override
    public void ultimoClic() {
        Toast.makeText(this, "No más intentos",Toast.LENGTH_LONG).show();
        finish();
    }
}