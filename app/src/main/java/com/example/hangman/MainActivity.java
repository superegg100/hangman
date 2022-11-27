package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.util.Random;
import java.util.stream.Stream;

public class MainActivity extends AppCompatActivity {
    ImageView hangman;
    EditText letter;
    TextView error, word, used_words;
    String[] Ewords, NoWords, Hwords, Exwords;
    char[] setword;
    boolean InWord, IsEasy, IsNormal, IsHard, IsExtreme, DidWin;
    int i;
    int randint;
    int StartLooking;
    String help, used_words_help;
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hangman = findViewById(R.id.hangman);
        letter = findViewById(R.id.letters);
        error = findViewById(R.id.error);
        word = findViewById(R.id.word);
        Intent intent = new Intent(MainActivity.this, EndScrn.class);
        DidWin = true;
        help = "";
        Ewords = new String[]{"what", "duck", "bear", "play", "good", "ride", "cold", "four", "cool", "boom"};
        NoWords = new String[]{"flock", "pizza", "angry", "cross", "event", "grand", "japan", "north", "radio", "smoke"};
        Hwords = new String[]{"accept", "corner", "decide", "hidden", "server", "travel", "winter", "prince", "relate", "notice"};
        Exwords = new String[]{"balloon", "anxiety", "chapter", "extreme", "factory", "licence", "program", "support", "welcome", "traffic"};
        i = 1;
        counter = 0;
        used_words_help = "Used Letters: ";
        StartLooking = used_words_help.length();
        used_words = (TextView) findViewById(R.id.used_words);
        letter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                InWord = false;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (letter.getText().toString().length() < 1) {
                    error.setText("input a letter !");
                }
                if (letter.getText().toString().length() > 1) {
                    error.setText("input just one letter !");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

                WordCheck(IsEasy, intent, Ewords);
                WordCheck(IsNormal, intent, NoWords);
                WordCheck(IsHard,intent,Hwords);
                WordCheck(IsExtreme,intent,Exwords);
                letter.getText().clear();

            }
        });

    }

    public void loadpics1() {
        int ImageKey = getResources().getIdentifier("hangman" + i, "drawable", getPackageName());
        hangman.setImageResource(ImageKey);
        i++;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();

        if (id == R.id.easy_diff) {
            SetByDiff(Ewords);
            IsEasy = true;
            IsNormal = false;
            IsHard = false;
            IsExtreme = false;
            ChangeDiff();
        } else if (R.id.normal_diff == id) {
            SetByDiff(NoWords);
            IsEasy = false;
            IsNormal = true;
            IsHard = false;
            IsExtreme = false;
            ChangeDiff();
        } else if (R.id.hard_diff == id) {
            SetByDiff(Hwords);
            IsEasy = false;
            IsNormal = false;
            IsHard = true;
            IsExtreme = false;
            ChangeDiff();
        } else if (R.id.extreme_diff == id) {
            SetByDiff(Exwords);
            IsEasy = false;
            IsNormal = false;
            IsHard = false;
            IsExtreme = true;
            ChangeDiff();        }
        return true;

    }

    public void WordCheck(boolean diff, Intent intent, String[] arr){
        if (letter.getText().toString().length() == 1 && diff && !Alreadytyped(used_words_help.toString(), letter.getText().toString().charAt(0))) {
            used_words_help += letter.getText().toString() + " , ";
            for (int i = 0; i < arr[randint].length(); i++) {
                if (letter.getText().toString().charAt(0) == arr[randint].charAt(i)) {
                    setword[i] = letter.getText().toString().charAt(0);
                    counter++;
                    InWord = true;
                }

            }
            word.setText(ChangeWord(setword));
            used_words.setText(used_words_help);

            if (counter == arr[randint].length()){
                DidWin = true;
                intent.putExtra("DidWin", DidWin);
                intent.putExtra("word", arr[randint]);
                startActivity(intent);
                Reset();
            }

            if (InWord) {

            }

            else {
                loadpics1();
                if (i == 7) {
                    DidWin = false;
                    intent.putExtra("DidWin", DidWin);
                    intent.putExtra("word", arr[randint]);
                    startActivity(intent);
                    Reset();
                }
            }
        }
    }

    public String ChangeWord(char[] toword) {
        String help = "";
        for (int i = 0; i < toword.length; i++) {
            help += toword[i];
        }
        return help;
    }
    public void SetByDiff(String[] Words)
    {
        setword = new char[Words[0].length()];
        help = "";
        word.setText(help);
        Toast.makeText(this, "Start Guessing !", Toast.LENGTH_SHORT).show();
        Random rand = new Random();
        randint = rand.nextInt((10 - 1) + 1);
        for (int j = 0; j < Words[randint].length(); j++)
            setword[j] = '_';
        word.setText(ChangeWord(setword));
    }
    public void ChangeDiff(){
        i = 1;
        hangman.setImageResource(getResources().getIdentifier("hangman0", "drawable", getPackageName()));
        used_words.setText("Used Letters: ");
        counter = 0;
        letter.setText("");

    }
    public boolean Alreadytyped(String usedwords, char letter)
    {
        for(int i = 14; i < usedwords.length(); i++){
            if(usedwords.charAt(i) == letter) {
                return true;
            }
        }
        return false;
    }
    public void Reset()
    {
        ChangeDiff();
        word.setText("Word:");
        IsEasy = false;
        IsNormal = false;
        IsHard = false;
        IsExtreme = false;
        used_words_help = "Used Letters: ";
    }

}