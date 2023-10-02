package work.sphere7term;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.ArrayList;
import java.lang.Math;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Инициализация компонентов
        Button button = findViewById(R.id.buttonCulc);
        TextView resVolume = findViewById(R.id.resVolume);
        TextView resMass = findViewById(R.id.resMass);
        TextView resArea = findViewById(R.id.resSurfaceArea);
        //Обработка нажатия на кнопку
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Поиск и считывание диаметра шара
            EditText diamEditText = findViewById(R.id.NumberDiameter);
            String text = diamEditText.getText().toString();
            //Проверка на пустоту
            if (text.isEmpty()){
                //Если диаметр пуст, вывести подсказку
                Toast toast = Toast.makeText(getApplicationContext(), "Укажите диаметр", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                //Парсинг диаметра и инициализация листа с результатами
                double num = Double.parseDouble(text);
                ArrayList<Double> listResults = new ArrayList<>();

                TextView result = findViewById(R.id.resSurfaceArea);
                Spinner spin = findViewById(R.id.spinner);
                String selected = spin.getSelectedItem().toString();
                //Заполнение листа с результатами
                listResults = BallParameters(num, selected);
                //Заполнение элементов интерфейса ответами, округленными до 3 знаков
                resArea.setText(String.format("%.3f",(listResults.get(0))));
                resVolume.setText(String.format("%.3f",(listResults.get(1))));
                resMass.setText(String.format("%.3f",(listResults.get(2))));
            }

            }
        });
    }
    //Функция для расчета параметров шара
    private ArrayList BallParameters(double diam, String material){
        ArrayList<Double> list = new ArrayList<>();
        double pi = Math.PI;
        //Вычисление площади поверхности
        double area = pi * diam * diam;
        list.add(area);
        double dens = 0;
        //Вычисление объема
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
        //Вычисление массы
        double mass = volume * dens;
        list.add(mass);
        return list;
    }
}