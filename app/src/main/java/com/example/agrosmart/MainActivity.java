package com.example.agrosmart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Bomba");
    DatabaseReference refEstado, refNivel, refFlujo;
    ToggleButton btnNivel, btnFlujo, btnBomba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refEstado = myRef.child("estado");
        refNivel = myRef.child("nivel");
        refFlujo = myRef.child("flujo");

        btnBomba = (ToggleButton) findViewById(R.id.toggleButton);
        btnNivel = (ToggleButton) findViewById(R.id.Button_nivel);
        btnFlujo = (ToggleButton) findViewById(R.id.Button_flujo);
        controlBomba(refEstado, refNivel, refFlujo, btnBomba, btnNivel, btnFlujo);
    }

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
    }
}