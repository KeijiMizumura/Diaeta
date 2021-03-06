package tnt_codefest.diaeta.BMI_Calculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import tnt_codefest.diaeta.R;


public class MainBMICalculator extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private EditText field_feet, field_inches, field_pounds;
    private Button button_calculate;
    private TextView label_result;
    private Spinner spinner_bmi_category;

    ArrayList<String> list_bmi_category = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calculator);

        field_feet = findViewById(R.id.field_feet);
        field_inches = findViewById(R.id.field_inches);
        field_pounds = findViewById(R.id.field_pounds);
        button_calculate = findViewById(R.id.button_calculate);
        label_result = findViewById(R.id.label_bmi_category);
        spinner_bmi_category = findViewById(R.id.spinner_bmi_category);

        spinner_bmi_category.setOnItemSelectedListener(this);

        list_bmi_category.add("Standard");
        list_bmi_category.add("Metric");
        ArrayAdapter<String> adapter_bmi_category = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list_bmi_category);
        spinner_bmi_category.setAdapter(adapter_bmi_category);


        button_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double result = 0;

                if (spinner_bmi_category.getSelectedItem().toString().equals("Standard")){
                    double feet = Double.parseDouble(field_feet.getText().toString());
                    double inches = Double.parseDouble(field_inches.getText().toString());
                    double pounds = Double.parseDouble(field_pounds.getText().toString());

                    result = calculateStandard(feet, inches, pounds);
                }
                else if (spinner_bmi_category.getSelectedItem().toString().equals("Metric")){
                    double centimeters = Double.parseDouble(field_feet.getText().toString());
                    double kilograms = Double.parseDouble(field_pounds.getText().toString());

                    result = calculateMetric(centimeters ,kilograms);
                }

                Intent intent = new Intent(getApplicationContext(), WeightClassification.class);
                intent.putExtra("result_bmi", result);
                startActivity(intent);


            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        switch (item){
            case "Standard":
                field_inches.setText(null);
                field_inches.setVisibility(View.VISIBLE);

                field_feet.setText(null);
                field_feet.setHint("Feet");

                field_pounds.setHint("Pounds");
                field_pounds.setText(null);

                Toast.makeText(this, "STANDARD", Toast.LENGTH_SHORT).show();
                break;
            case "Metric":
                field_inches.setText(null);
                field_inches.setVisibility(View.INVISIBLE);

                field_feet.setText(null);
                field_feet.setHint("Centimeters");

                field_pounds.setText(null);
                field_pounds.setHint("Kilograms");
                Toast.makeText(this, "METRIC", Toast.LENGTH_SHORT).show();
            default:
                Toast.makeText(this, "NOTHING", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private double calculateStandard(double feet, double inches, double pounds){
        double height = ((feet * 12) + inches) * 0.025;
        double weight  = pounds * 0.45;

        return (double) Math.round((weight / (height * height)) * 100.0) / 100.0;
    }

    private double calculateMetric(double centimeters, double kilograms){
        return (double) Math.round(((kilograms / centimeters / centimeters) * 10000) * 100.0) / 100.0;
    }


}
