package hello.itay.com.firebase3103;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DatabaseReference result = mDatabase.getReference().child("messages");

        final ValueEventListener valueEventListener = mDatabase.getReference().
                child("messages").
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot dataSnapshot) {

                        ///////////// new data is committed to the DB

                        StringBuilder sb = new StringBuilder();

                        for(DataSnapshot snapShot : dataSnapshot.getChildren()) {
                            Message message = snapShot.getValue(Message.class);
                            sb.append(message.sender + " : " + message.text + "\n");
                        }

                        Toast.makeText(getBaseContext(),sb, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled( DatabaseError databaseError) {
                        ////////// new data committed - but something went wrong...
                        Toast.makeText(getBaseContext(), "Firebase error", Toast.LENGTH_SHORT).show();
                    }
                });

        final EditText senderET = findViewById(R.id.senderET);
        final EditText textET = findViewById(R.id.textET);
        Button sendBtn = findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message new_msg = new Message();
                new_msg.sender = senderET.getText().toString();
                new_msg.text = textET.getText().toString();
                mDatabase.getReference().child("messages").push().setValue(new_msg);
            }
        });
    }
}
