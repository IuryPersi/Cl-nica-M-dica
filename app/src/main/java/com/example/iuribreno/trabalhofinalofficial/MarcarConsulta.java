package com.example.iuribreno.trabalhofinalofficial;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.iuribreno.trabalhofinalofficial.ENTIDADES.Consulta;
import com.example.iuribreno.trabalhofinalofficial.ENTIDADES.Usuario;
import com.example.iuribreno.trabalhofinalofficial.HELPER.base64Custom;
import com.example.iuribreno.trabalhofinalofficial.service.UsuarioService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MarcarConsulta extends AppCompatActivity {

    private Button dateButton;
    private Button timeButton;
    private Button salvarButton;
    private Spinner medicosSpinner;
    private Spinner especialidadesSpinner;
    private UsuarioService usuarioService;
    private EditText queixasPacienteEditText;

    private Consulta consulta;
    private Date data;
    private Date hora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcar_consulta);

        usuarioService = new UsuarioService();
        consulta = new Consulta();
        data = new Date();
        hora = new Date();

        dateButton = (Button) findViewById(R.id.openDate);
        timeButton = (Button) findViewById(R.id.openTime);
        salvarButton = (Button) findViewById(R.id.salvar);
        queixasPacienteEditText = (EditText) findViewById(R.id.queixasPacienteEditText);

        salvarButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                consulta.setUsuario(usuarioService.getCurrentUserEmail());
                consulta.setQueixas(queixasPacienteEditText.getText().toString());
                consulta.salvar();

                Toast.makeText(MarcarConsulta.this, "Consulta marcada", Toast.LENGTH_SHORT).show();
                Intent telaMenus = new Intent(MarcarConsulta.this, Menus_Inicial.class);
                startActivity(telaMenus);
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MarcarConsulta.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                data = new Date(year, monthOfYear + 1, dayOfMonth);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                final int mHour = c.get(Calendar.HOUR_OF_DAY);
                final int mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(MarcarConsulta.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                hora.setHours(mHour);
                                hora.setMinutes(mMinute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        addMedicosToSpinner();
        addEspecialidadesToSpinner();
    }


    public void addMedicosToSpinner() {

        List<Usuario> list = usuarioService.getMedicos();
        list.add(new Usuario("Selecione", "selecione"));
        medicosSpinner = findViewById(R.id.medicosSpinner);
        ArrayAdapter<Usuario> adapter = new ArrayAdapter<Usuario>(MarcarConsulta.this,
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        medicosSpinner.setAdapter(adapter);

        medicosSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Usuario user = (Usuario) parent.getSelectedItem();
                consulta.setMedico(user.getEmail());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void addEspecialidadesToSpinner()
    {
        especialidadesSpinner = (Spinner) findViewById(R.id.especialidadesSpinner);
        List<String> list = new ArrayList<String>();
        list.add("Alergia");
        list.add("Cardiologia");
        list.add("Clínico Geral");
        list.add("Endocrinologia");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        especialidadesSpinner.setAdapter(dataAdapter);

        especialidadesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                consulta.setEspecialidade(selectedItemText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
