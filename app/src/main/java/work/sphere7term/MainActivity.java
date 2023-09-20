package work.sphere7term;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.buttonCulc);
        TextView resVolume = findViewById(R.id.resVolume);
        TextView resMass = findViewById(R.id.resMass);
        TextView resArea = findViewById(R.id.resSurfaceArea);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            EditText diamEditText = findViewById(R.id.NumberDiameter);
            double num = Double.parseDouble(diamEditText.getText().toString());
            ArrayList<Double> listResults = new ArrayList<>();

            TextView result = findViewById(R.id.resSurfaceArea);
            Spinner spin = findViewById(R.id.spinner);
            String selected = spin.getSelectedItem().toString();
            listResults = BallParameters(num, selected);
            resArea.setText(String.format("%.3f",(listResults.get(0))));
            resVolume.setText(String.format("%.3f",(listResults.get(1))));
            resMass.setText(String.format("%.3f",(listResults.get(2))));
            }
        });
    }
    private ArrayList BallParameters(double diam, String material){
        ArrayList<Double> list = new ArrayList<>();
        double pi = Math.PI;
        double area = pi * diam * diam;
        list.add(area);
        double dens = 0;
        double volume = pi * diam * diam / 3;
        list.add(volume);
        switch (material){
            case "Вода 1000 кг/м³":
                dens = 1000;
                break;
            case "Золото 19300 кг/м³":
                dens = 19300;
                break;
            case "Латунь 8500 кг/м³":
                dens = 8500;
                break;
            case "Алмаз 3500 кг/м³":
                dens = 3500;
                break;
            case "Ртуть 13600 кг/м³":
                dens = 13600;
                break;
        }
        double mass = volume * dens;
        list.add(mass);
        return list;
    }
}