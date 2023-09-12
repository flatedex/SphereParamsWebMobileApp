package work.sphere7term;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.lang.Math;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.buttonCulc);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            EditText diam = findViewById(R.id.NumberDiameter);
            double num = Double.parseDouble(diam.getText().toString());
            double res = SurfaceArea(num);
            EditText result = findViewById(R.id.NumberResult);

            result.setText(Double.toString(res));
            }
        });
    }
    private double SurfaceArea(double diam){
        double pi = Math.PI;
        //4pr^2
        double res = 2 * pi * diam * diam;
        return res;
    }
}