package com.example.pertemuan4_actionbar;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import com.example.pertemuan4_actionbar.databinding.ActivitySharedPreferencesBinding;
import android.widget.Toast;
public class SharedPreferences extends AppCompatActivity implements View.OnClickListener {
    MyDbHelper myDbHelper;
    private ActivitySharedPreferencesBinding binding;
    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setup view binding
        binding =
                ActivitySharedPreferencesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        session = new SessionManager(getApplicationContext());
        if (session.isLoggedIn()) {
            Intent intent = new Intent(SharedPreferences.this,
                    ProfileActivity.class);
            startActivity(intent);
            finish();
        }
        session = new SessionManager(getApplicationContext());
        myDbHelper = new MyDbHelper(this);
        SQLiteDatabase sqLiteDatabase =
                myDbHelper.getWritableDatabase();
        binding.Signinbuttonid.setOnClickListener(this);
        binding.SignUpHerebuttonid.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        String username =
                binding.signinusernameEditText.getText().toString();
        String password =
                binding.signinpasswordEditText.getText().toString();
        if (v.getId() == R.id.Signinbuttonid) {
            Boolean result = myDbHelper.findPassword(username,
                    password);
            if (result == true) {
                Intent intent = new Intent(SharedPreferences.this,
                        ProfileActivity.class);
                startActivity(intent);
                session.setLogin(true);
                finish();
            } else {
                Toast.makeText(this, "username and password didn`t match", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.SignUpHerebuttonid) {
            Intent intent = new Intent(SharedPreferences.this,
                    RegisterActivity.class);
            startActivity(intent);
        }
    }
}
