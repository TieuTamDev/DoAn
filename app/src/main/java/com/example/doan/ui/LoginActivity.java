package com.example.doan.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.doan.MainActivity;
import com.example.doan.R;
import com.example.doan.model.User;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {
    Button btLogin, btRegister;
    EditText edUserNameC, edPasswordC;
    SharedPreferences.Editor editor;
    private final Gson gson = new Gson();
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login");
        //
        anhxa();
        //
        sharedPreferences = getSharedPreferences(Utils.SHARE_PREFERENCES_APP, Context.MODE_PRIVATE);
        //
        taosukien();
    }
    private void taosukien(){
        btLogin.setOnClickListener(view -> checkUserLogin());
        btRegister.setOnClickListener(funRegister());
    }

    private void anhxa(){
        btLogin = findViewById(R.id.btLogin);
        btRegister = findViewById(R.id.btRegister);
        edUserNameC = findViewById(R.id.edUser);
        edPasswordC = findViewById(R.id.edPass);
    }
    @NonNull
    private View.OnClickListener nhanvaoLogin() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edUserNameC.getText().toString().trim();
                String password = edPasswordC.getText().toString().trim();
                if (checkUserName(username) && checkPassword(password))
                {
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                }

            }
        };
    }

    @NonNull
    private View.OnClickListener funRegister() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        };
    }
    // kiểm tra dữ liệu
    boolean checkUserName(String username){
        if (username.isEmpty()){
            edUserNameC.setError("Vui lòng nhập");
            return false;
        }
        return true;
    }

    boolean checkPassword(String password){
        if (password.isEmpty()){
            edPasswordC.setError("Vui lòng nhập");
            return false;
        }
        return true;
    }
    private void checkUserLogin(){
        String userPerf = sharedPreferences.getString(Utils.KEY_USER, null);
        User user = gson.fromJson(userPerf, User.class);
        //user = null có nghĩa là chưa có user đăng kí.
        if (user == null){
            return;
        }
        //kiểm tra username và password có trùm với đối tượng user trong preference không
        boolean isValid = edUserNameC.getText().toString().trim().equals(user.getUserName()) && edPasswordC.getText().toString().trim().equals(user.getPassword());
// nếu kết quả trùng với user đã đăng kí thì start main activity
        if (isValid){
            Intent intent = new Intent(this, MainActivity.class);
            //khởi tạo bundle để truyền user data qua cho MainActivity
            Bundle bundle = new Bundle();
            //vì user là object nên dùng putSerializable
            bundle.putSerializable(Utils.KEY_USER_PROFILE, user);
            //hoặc có thể dùng putString. nếu chỉ dùng user name
            // bundle.putString(Utils.KEY_USER_NAME, user.getUserName());

            //put handle cho intent.
            intent.putExtras(bundle);
            startActivity(intent);
            //sau khi start main activity thì finish login activity
            finish();
        }
    }
}