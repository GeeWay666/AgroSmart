package com.example.agrosmart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/*
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
*/
public class MainActivity extends AppCompatActivity {
    TextView txt_humSuel, txt_lum, txt_temp, txt_hum;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    /*FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Bomba");
    DatabaseReference refEstado, refNivel, refFlujo;*/
    ToggleButton btnBomba;
    private static final String TAG = "MENSAJE---";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        refEstado = myRef.child("estado");
        refNivel = myRef.child("nivel");
        refFlujo = myRef.child("flujo");

        btnBomba = (ToggleButton) findViewById(R.id.toggleButton);
        btnNivel = (ToggleButton) findViewById(R.id.Button_nivel);
        btnFlujo = (ToggleButton) findViewById(R.id.Button_flujo);
        controlBomba(refEstado, refNivel, refFlujo, btnBomba, btnNivel, btnFlujo);
    */
        txt_humSuel = (TextView) findViewById(R.id.txt_humSuel);
        txt_lum = (TextView) findViewById(R.id.txt_lum);
        txt_temp = (TextView) findViewById(R.id.txt_temp);
        txt_hum = (TextView) findViewById(R.id.txt_hum);
        btnBomba = (ToggleButton) findViewById(R.id.btnBomba);
/*
        DocumentReference docRef = db.collection("id-invernadero").document("id-temp");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        txt_humSuel.setText(document.getString("humedadSuelo"));
                        txt_lum.setText(document.getString("luminosidad"));
                        txt_temp.setText(document.getString("temperatura"));
                        txt_hum.setText(document.getString("humedad"));
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
*/
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
/*
        db.collection("id-invernadero").document("id-temp").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String hS = documentSnapshot.getString("humedadSuelo");
                    txt_humSuel.setText(hS);
                    txt_lum.setText(documentSnapshot.getString("luminosidad"));
                    txt_temp.setText(documentSnapshot.getString("temperatura"));
                    txt_hum.setText(documentSnapshot.getString("humedad"));
                }
            }
        });
*/
    }

/*
    private void controlBomba(final DatabaseReference refEstado, final DatabaseReference refNivel, final DatabaseReference refFlujo,  final ToggleButton toggle_btnBomba, final ToggleButton toggle_btnNivel, final ToggleButton toggle_btnFlujo ) {

        toggle_btnNivel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                refNivel.setValue(isChecked);
            }
        });

        toggle_btnFlujo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                refFlujo.setValue(isChecked);
            }
        });

        refEstado.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean estado_led  = (Boolean) dataSnapshot.getValue();
                toggle_btnBomba.setChecked(estado_led);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }

        });

        refNivel.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean estado_led  = (Boolean) dataSnapshot.getValue();
                toggle_btnNivel.setChecked(estado_led);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }

        });

        refFlujo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean estado_led  = (Boolean) dataSnapshot.getValue();
                toggle_btnFlujo.setChecked(estado_led);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }

        });
    }*/
}