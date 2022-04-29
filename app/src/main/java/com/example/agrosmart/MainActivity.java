package com.example.agrosmart;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivity extends AppCompatActivity {
    TextView txt_humSuel, txt_lum, txt_temp, txt_hum;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ToggleButton btnBomba;

    private static final String TAG = "MENSAJE---";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_humSuel = (TextView) findViewById(R.id.txt_humSuel);
        txt_lum = (TextView) findViewById(R.id.txt_lum);
        txt_temp = (TextView) findViewById(R.id.txt_temp);
        txt_hum = (TextView) findViewById(R.id.txt_hum);
        btnBomba = (ToggleButton) findViewById(R.id.btnBomba);


        final DocumentReference docRef = db.collection("id-invernadero").document("id-temp");
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d(TAG, "Current data: " + snapshot.getData());
                    txt_humSuel.setText(snapshot.getString("humedadSuelo"));
                    txt_lum.setText(snapshot.getString("luminosidad"));
                    txt_temp.setText(snapshot.getString("temperatura"));
                    txt_hum.setText(snapshot.getString("humedad"));
                    //Boolean estado_led  = (Boolean) snapshot.getBoolean("bomba");
                    btnBomba.setChecked(snapshot.getBoolean("bomba"));
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });

    }

}