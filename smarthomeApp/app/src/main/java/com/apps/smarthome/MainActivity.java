package com.apps.smarthome;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private SwitchCompat switchLight1, switchLight2, switchTV, switchFan, switchAC;
    private DatabaseReference ledsRef;

    private final HashMap<String, SwitchCompat> switchMap = new HashMap<>();

    private boolean isUpdatingUI = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Firebase reference
        ledsRef = FirebaseDatabase.getInstance().getReference("leds");

        // Bind switches
        switchLight1 = findViewById(R.id.light1_switch);
        switchLight2 = findViewById(R.id.light2_switch);
        switchTV = findViewById(R.id.tv_switch);
        switchFan = findViewById(R.id.fan_switch);
        switchAC = findViewById(R.id.ac_switch);

        // Optional example listener
        switchLight1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isUpdatingUI) {
                ledsRef.child("led1").setValue(isChecked ? 1 : 0);
            }
        });

        MaterialCardView allOffCard = findViewById(R.id.allOff);
        allOffCard.setOnClickListener(v -> {
            isUpdatingUI = true; // Temporarily disable switch listeners
            for (String ledKey : switchMap.keySet()) {
                SwitchCompat sw = switchMap.get(ledKey);
                sw.setChecked(false); // This will update the UI
                ledsRef.child(ledKey).setValue(0); // Also update Firebase
            }
            isUpdatingUI = false;
            Toast.makeText(MainActivity.this, "All devices turned OFF", Toast.LENGTH_SHORT).show();
        });


        // Map switches
        switchMap.put("led1", switchLight1);
        switchMap.put("led2", switchLight2);
        switchMap.put("led3", switchTV);
        switchMap.put("led4", switchFan);
        switchMap.put("led5", switchAC);

        setupListeners();
        syncSwitchStates();
    }

    private void setupListeners() {
        for (String ledKey : switchMap.keySet()) {
            SwitchCompat switchCompat = switchMap.get(ledKey);
            switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (!isUpdatingUI) {
                    ledsRef.child(ledKey).setValue(isChecked ? 1 : 0)
                            
                            .addOnFailureListener(e ->
                                    Toast.makeText(MainActivity.this, "Failed to update " + ledKey, Toast.LENGTH_SHORT).show()
                            );
                }
            });
        }
    }

    private void syncSwitchStates() {
        ledsRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                isUpdatingUI = true;
                for (String ledKey : switchMap.keySet()) {
                    if (snapshot.child(ledKey).exists()) {
                        int state = Integer.parseInt(snapshot.child(ledKey).getValue().toString());
                        SwitchCompat sw = switchMap.get(ledKey);
                        sw.setChecked(state == 1);
                    }
                }
                isUpdatingUI = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed to load states.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
