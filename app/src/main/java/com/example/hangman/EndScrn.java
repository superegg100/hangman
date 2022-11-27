package com.example.hangman;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EndScrn extends AppCompatActivity {
    boolean DidWin;
    String theword;
    TextView WOL, wordwas;
    ImageView LOB;
    Button RESTART, EXIT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_scrn);
        Intent intent = getIntent();
        LOB = findViewById(R.id.like_or_ben);
        WOL = findViewById(R.id.win_or_lose);
        wordwas = findViewById(R.id.wordwas);
        RESTART = findViewById(R.id.restart);
        EXIT = findViewById(R.id.exit);
        DidWin = intent.getExtras().getBoolean("DidWin");
        theword = intent.getExtras().getString("word");
        if (DidWin == true){
            WOL.setText("YOU WON");
            LOB.setImageResource(R.drawable.like);
            wordwas.setText("The Word Was: " + theword);
        }
        else{
            WOL.setText("YOU LOST");
            LOB.setImageResource(R.drawable.hanged);
            wordwas.setText("The Word Was: " + theword);
        }
        RESTART = findViewById(R.id.restart);
        EXIT = findViewById(R.id.exit);
        EXIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert=new AlertDialog.Builder(EndScrn.this);
                alert.setTitle("EXIT");
                alert.setMessage("ARE YOU SURE YOU WANT TO EXIT?");
                alert.setPositiveButton("YES",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finishAffinity();
                        System.exit(0);
                    }
                });
                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(EndScrn.this,"I want to stay",Toast.LENGTH_LONG).show();
                    }
                });
                alert.create().show();
            }
        });
        RESTART.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert=new AlertDialog.Builder(EndScrn.this);
                alert.setTitle("RESTART");
                alert.setMessage("ARE YOU SURE YOU WANT TO RESTART?");
                alert.setPositiveButton("YES",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();

                    }
                });
                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                });
                alert.create().show();
            }
        });
    }
}