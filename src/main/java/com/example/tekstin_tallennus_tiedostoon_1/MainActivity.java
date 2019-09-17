package com.example.tekstin_tallennus_tiedostoon_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {


    // luodaan tarvittavat muuttujat uusi teidosto ja EditText -kentän sisällölle muuttuja

    private static final String FILE_NAME = "tallennettu_tieto.txt";
    EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // mEditText arvo haetaan elementin nimellä edit_text ja sen sisältö otetaan tähän talteen

        mEditText = (findViewById(R.id.edit_text));
    }

    public void tallenna(View view){
        String text = mEditText.getText().toString();
        closeKeyboard();

        // FileOutputStream tyyppinen muuttuja nimeltä fos saa arvon null;
        FileOutputStream fos = null;

        // muuttuja fos saa arvon metodista openFIleOutput, jolla parametrinä ainnettu tiedostonnimi sekä käyttö estetty muilta
        // sovelluksilta
        // try / catch saadaan insert-code tyyppisesti. Tämä virheiden varalle esim. mitä tehdään virhetilanteessa

        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text.getBytes());

            //tallennuksen jälkeen mEditText -komponentti tyhjennetään
            mEditText.getText().clear();
            // ja käyttäjän notifikaatio tallennettu + polku
            Toast.makeText(this, "Tiedosto tallennettu ("+getFilesDir()+"/"+FILE_NAME+")", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // tarkastetaan fos -muuttujan arvo, jos tyhjä / ei
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }


}

    public void hae(View view){
        FileInputStream fis = null;
        closeKeyboard();

        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();

            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");

                mEditText.setText(sb.toString());

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
