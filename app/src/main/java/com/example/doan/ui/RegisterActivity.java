package com.example.doan.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.example.doan.R;
import com.example.doan.model.User;
import com.google.gson.Gson;

public class RegisterActivity extends AppCompatActivity {
    private ImageButton btBackC;
    private Button btRegisterC;
    private EditText edUserNameC;
    private EditText edPasswordC;
    private EditText edConfirmPasswordC;
    private EditText edEmailC;
    private EditText edPhoneNumberC;

    private SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    private final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("Register");
        //
        sharedPreferences = getSharedPreferences(Utils.SHARE_PREFERENCES_APP, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        //
        anhxadulieu();
        taosukien();
    }

    void anhxadulieu()
    {
        edUserNameC = findViewById(R.id.edUserNameRe);
        edPasswordC = findViewById(R.id.edPasswordRe);
        edConfirmPasswordC = findViewById(R.id.edConfirmPass);
        edPhoneNumberC = findViewById(R.id.edPhoneNum);
        edEmailC = findViewById(R.id.edEmail);
        btRegisterC = findViewById(R.id.btRegister);
        btBackC = findViewById(R.id.imbBack);
    }

    void taosukien()
    {
        btRegisterC.setOnClickListener(v -> sukienRegister());
        btBackC.setOnClickListener(v -> finish());
    }

    void sukienRegister()
    {
        String userName = edUserNameC.getText().toString().trim();
        String password = edPasswordC.getText().toString().trim();
        String confirmPassword = edConfirmPasswordC.getText().toString().trim();
        String email = edEmailC.getText().toString().trim();
        String phoneNumber = edPhoneNumberC.getText().toString().trim();
        //
        int sex = 1;
        boolean isValid = checkUserName(userName) && checkPassword(password,confirmPassword);

        if(isValid)
        {
            User userNew = new User();
            userNew.setUserName(userName);
            userNew.setPassword(password);
            userNew.setEmail(email);
            userNew.setPhoneNumber(phoneNumber);

            String userStr = gson.toJson(userNew);
            editor.putString(Utils.KEY_USER,userStr);
            editor.commit();

            Toast.makeText(RegisterActivity.this,"Dang ky tai khoan thanh cong", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private boolean checkUserName(String userName)
    {
        if(userName.isEmpty())
        {
            edUserNameC.setError("Vui long nhap thong tin");
            return false;
        }
        if(userName.length() < 6)
        {
            edUserNameC.setError("Vui long nhap du 6 ky tu");
            return false;
        }
        return true;
    }

    private boolean checkPassword(String password, String confirmPassword)
    {
        if(password.isEmpty())
        {
            edPasswordC.setError("Vui long nhap nhap mat khau");
            return false;
        }
        if(password.length() < 8)
        {
            edUserNameC.setError("Mat khau phai du 8 ky tu");
            return false;
        }
        if(!password.equals(confirmPassword))
        {
            edConfirmPasswordC.setError("Mat khau khong trung khop");
            return false;
        }
        return true;
    }
}