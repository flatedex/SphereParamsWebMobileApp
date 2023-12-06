package work.sphere7term;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;

public class MainActivity extends AppCompatActivity {
    private String something = "";
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
                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.enterDiam), Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    //Парсинг диаметра и инициализация листа с результатами
                    double num = Double.parseDouble(text);

                    TextView result = findViewById(R.id.resSurfaceArea);
                    Spinner spin = findViewById(R.id.spinner);
                    String selected = spin.getSelectedItem().toString();
                    //Заполнение листа с результатами
                    int index = spin.getSelectedItemPosition();

                    try {
                        ArrayList<String> listResults = new ArrayList<>();
                        Thread process = new Thread(GetSolve(num, index));
                        process.start();
                        process.join();
                        String resultJson = something;
                        JSONObject json = new JSONObject(resultJson);
                        listResults.add(json.getString("area"));
                        listResults.add(json.getString("volume"));
                        listResults.add(json.getString("mass"));
                        //Заполнение элементов интерфейса ответами, округленными до 3 знаков
                        resArea.setText(listResults.get(0));
                        resVolume.setText(listResults.get(1));
                        resMass.setText(listResults.get(2));
                    } catch (Exception e) {
                        Toast toast = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }

            }
        });
    }
    private Runnable GetSolve(double diam, int material){
        return new Runnable() {
            @Override
            public void run() {
                try {

                    String address = getResources().getString(R.string.serverURL);
                    URL url = new URL("https://" + address + "?diam=" + diam + "&material=" + material);
                    HttpURLConnection http = (HttpURLConnection)url.openConnection();
                    http.setConnectTimeout(5000);

                    if (http.getResponseCode() == HttpURLConnection.HTTP_OK){
                        String line = "";
                        String request = "";
                        BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream()));
                        while ((line = br.readLine()) != null) {
                            request += line;
                        }
                        something = request;
                    } else {
                        throw new RuntimeException(getResources().getString(R.string.connErr));
                    }

                } catch (Exception e){
                    throw new RuntimeException(e);
                }
            }
        };
    }
}