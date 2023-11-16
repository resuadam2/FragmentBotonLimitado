package com.resuadam2.fragmentbotonlimitado;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FrgBotonLimitado extends Fragment {
    private Button boton;
    private String texto;
    private int numClics, maxClics;
    private boolean mostrarContadores=false;
    // region Interface OnClickListener
    private OnClickListener listener=null;
    public interface OnClickListener {
        public boolean onClick(int numClic, int maxClics);
        public void ultimoClic();
    }
    // endregion
    public void setOnClickListener(OnClickListener listener, int maxClics, String texto) {
        this.listener=listener;
        this.numClics=0;
        this.maxClics =maxClics;
        this.texto=texto;
    }
    public void setBotonText() {
        StringBuilder sb=new StringBuilder(texto);
        if(mostrarContadores) sb.append("\n").append(numClics).append('/').append(maxClics);
        boton.setText(sb.toString());
    }
    public void setMostrarContadores(boolean mostrarContadores) { this.mostrarContadores=mostrarContadores; }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_boton, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        boton = (Button) view.findViewById(R.id.botonFrg);
        setBotonText();
        boton.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { botonFrgClic(); } });
    }
    public void botonFrgClic() {
        if(listener!=null)
            if(numClics< maxClics) { // innecesario si deshabilitamos el botón
                if(listener.onClick(numClics+1, maxClics)) {
                    if ((++numClics == maxClics)) {
                        boton.setEnabled(false);
                        listener.ultimoClic();
                    }
                    setBotonText();
                }
            }
    }
}