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
    private Button boton; // Botón del fragment
    private String texto; // Texto del botón
    private int numClics, maxClics; // Número de clics y máximo de clics
    private boolean mostrarContadores=false; // Si se muestran los contadores
    // region Interface OnClickListener
    private OnClickListener listener=null; // Listener

    /**
     * Interface para comunicar el fragment con la actividad
     */
    public interface OnClickListener {
        public boolean onClick(int numClic, int maxClics);
        public void ultimoClic();
    }
    // endregion

    /**
     * Establece el listener y el número máximo de clics
     * @param listener listener
     * @param maxClics número máximo de clics
     * @param texto texto del botón
     */
    public void setOnClickListener(OnClickListener listener, int maxClics, String texto) {
        this.listener=listener;
        this.numClics=0;
        this.maxClics =maxClics;
        this.texto=texto;
    }

    /**
     * Establece el texto del botón
     */
    public void setBotonText() {
        StringBuilder sb=new StringBuilder(texto);
        if(mostrarContadores) sb.append("\n").append(numClics).append('/').append(maxClics);
        boton.setText(sb.toString());
    }

    /**
     * Establece si se muestran los contadores
     * @param mostrarContadores true si se muestran los contadores
     */
    public void setMostrarContadores(boolean mostrarContadores) { this.mostrarContadores=mostrarContadores; }

    /**
     * Called to have the fragment instantiate its user interface view.
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_boton, container, false);
    }

    /**
     * Called immediately after {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        boton = (Button) view.findViewById(R.id.botonFrg);
        setBotonText();
        boton.setOnClickListener(v -> botonFrgClic());
    }

    /**
     * Llamado cuando se pulsa el botón
     */
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