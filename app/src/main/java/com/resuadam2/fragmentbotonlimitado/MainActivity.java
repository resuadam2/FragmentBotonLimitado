package com.resuadam2.fragmentbotonlimitado;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements FrgBotonLimitado.OnClickListener {
    private static final int NUM_MAX_LOGIN = 3; // Número máximo de intentos
    EditText etUsuario,etPassword; // EditText para el usuario y el password
    CheckBox chkAceptarCondiciones; // CheckBox para aceptar las condiciones
    FrgBotonLimitado frgBotonLimitado; // Fragmento con el botón limitado

    Button btReiniciar; // Botón para reiniciar el contador
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
        btReiniciar=findViewById(R.id.btReiniciar);
        btReiniciar.setOnClickListener(v -> reiniciarBoton());
    }

    /**
     * Reinicia el contador del botón
     */
    private void reiniciarBoton() {
        frgBotonLimitado.reiniciar();
        btReiniciar.setEnabled(false);
        btReiniciar.setVisibility(Button.INVISIBLE);
    }

    /**
     * Clic del botón del fragment (se llama desde el fragment)
     * @param numClic número de clics
     * @param maxClics número máximo de clics
     * @return true si las credenciales son correctas
     */
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

    /**
     * Comprueba si las credenciales son correctas
     * @param usuario usuario
     * @param password password
     * @return true si las credenciales son correctas
     */
    private boolean credencialesCorrectas(String usuario,String password) {
        // TODO mejorar (es un ejemplo) validar, etc.
        return usuario.equals("ejemplo") && password.equals("123abc.");
    }

    /**
     * Último clic del botón del fragment (se llama desde el fragment)
     * (cuando se alcanza el número máximo de clics)
     * (no se llama si se devuelve false en el método onClick)
     * activa el botón para reiniciar el contador
     */
    @Override
    public void ultimoClic() {
        Toast.makeText(this, "No más intentos",Toast.LENGTH_LONG).show();
        // finish(); // Si se quiere salir de la aplicación, no lo veo necesario
        // TODO hacer que se pueda reiniciar el botón limitado
        btReiniciar.setEnabled(true);
        btReiniciar.setVisibility(Button.VISIBLE);
    }
}