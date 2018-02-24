package petrisor.eduard.matei.mobiledevicesprogrammingproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    DatabaseHelper dbHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button btnCreateAccount = (Button) findViewById(R.id.btnCreateAccount);
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    registerUser();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void registerUser() throws Exception {
        String user = ((EditText) findViewById(R.id.etRegUsername)).getText().toString();
        String pass = ((EditText) findViewById(R.id.etRegPassword)).getText().toString();
        String passconfirm = ((EditText) findViewById(R.id.etRegConfirmPassword)).getText().toString();

        if (!(user.equals("") && pass.equals(""))) {
            if (!pass.equals(passconfirm)) {
                Toast.makeText(getApplicationContext(), "Password Mismarch", Toast.LENGTH_SHORT).show();
                emptyRegisterFields();
            } else if (user.equals("")) {
                Toast.makeText(getApplicationContext(), "Empty User Field", Toast.LENGTH_SHORT).show();
                emptyRegisterFields();
            } else if (user.length() < 4) {
                Toast.makeText(getApplicationContext(), "Username minimum length is 4", Toast.LENGTH_SHORT).show();
                emptyRegisterFields();
            } else if (pass.length() < 4) {
                Toast.makeText(getApplicationContext(), "Password minimun length is 4", Toast.LENGTH_SHORT).show();
                emptyRegisterFields();
            } else {
                try {
                    User u = new User();
                    u.setUserName(user);
                    u.setPassword(pass);
                    dbHelper.checkUser(u);
                    dbHelper.insertUser(u);
                    Toast.makeText(getApplicationContext(), "Account Created", Toast.LENGTH_SHORT).show();
                    emptyRegisterFields();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "User already exists", Toast.LENGTH_SHORT).show();
                    emptyRegisterFields();
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "Empty Fields", Toast.LENGTH_SHORT).show();
            emptyRegisterFields();
        }
    }

    public void emptyRegisterFields() {
        EditText edUser = (EditText) findViewById(R.id.etRegUsername);
        edUser.setText("");
        EditText edPassword = (EditText) findViewById(R.id.etRegPassword);
        edPassword.setText("");
        EditText edConfirmPassword = (EditText) findViewById(R.id.etRegConfirmPassword);
        edConfirmPassword.setText("");
    }
}
