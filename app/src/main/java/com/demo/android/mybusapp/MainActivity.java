package com.demo.android.mybusapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView ic_route;
    TextView tv_route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ic_route = findViewById(R.id.ic_route);
        tv_route = findViewById(R.id.tv_route);

        WindowInsetsControllerCompat windowInsetsController =
                ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (windowInsetsController == null) {
            return;
        }
// Hide the system bars.
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());

//// Show the system bars.
//        windowInsetsController.show(WindowInsetsCompat.Type.systemBars());


        ic_route.setOnClickListener(listener);
        tv_route.setOnClickListener(listener);

    }

    View.OnClickListener listener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, RouteActivity.class);
            startActivity(intent);
        }
    };
}