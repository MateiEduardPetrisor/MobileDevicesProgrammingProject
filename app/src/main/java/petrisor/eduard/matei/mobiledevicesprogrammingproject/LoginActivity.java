package petrisor.eduard.matei.mobiledevicesprogrammingproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    DatabaseHelper dbHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {
            credentialsPreferencesLoad();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button btnLogon = (Button) findViewById(R.id.btnLogon);
        btnLogon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    authUser();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });


        Button btnCreateAccount = (Button) findViewById(R.id.btnRegister);
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(register);
            }
        });

        Button btnAbout = (Button) findViewById(R.id.btnAbout);
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent about = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(about);
            }
        });
    }

    public void authUser() throws Exception {
        String user = ((EditText) findViewById(R.id.etUsername)).getText().toString();
        String pass = ((EditText) findViewById(R.id.etPassword)).getText().toString();
        String passwordSearch = dbHelper.searchPassword(user);
        if (pass.equals(passwordSearch)) {
            Intent mainMenu = new Intent(getApplicationContext(), MainMenuActivity.class);
            credentialsPreferencesSave(user, pass);
            mainMenu.putExtra("user", user);
            startActivity(mainMenu);
        } else {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }

    public void credentialsPreferencesSave(String userName, String password) throws Exception {
        SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", userName);
        editor.putString("password", password);
        editor.apply();
    }

    public void credentialsPreferencesLoad() throws Exception {
        SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);
        String userPref = sp.getString("username", "");
        String passPref = sp.getString("password", "");
        ((EditText) findViewById(R.id.etUsername)).setText(userPref);
        ((EditText) findViewById(R.id.etPassword)).setText(passPref);
    }
}