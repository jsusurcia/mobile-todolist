package com.example.to_do_list;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.to_do_list.database.TareaDAO;
import com.example.to_do_list.models.Tarea;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    TareaDAO tareaDAO;
    EditText txtTitulo, txtDescripcion;
    Button btnGuardar;
    LinearLayout contenedorTareas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        tareaDAO = new TareaDAO(this);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        btnGuardar = findViewById(R.id.btnGuardar);
        contenedorTareas = findViewById(R.id.contenedorTareas);

        mostrarTareas();

        btnGuardar.setOnClickListener(v -> {
            String titulo = txtTitulo.getText().toString();
            String descripcion = txtDescripcion.getText().toString();
            insertarTarea(titulo, descripcion);
        });
    }

    private void insertarTarea(String titulo, String descripcion) {
        tareaDAO.open();
        long nuevaTarea = tareaDAO.insertarTarea(titulo, descripcion);
        tareaDAO.close();

        if (nuevaTarea != -1) {
            Toast.makeText(this, "Tarea guardada", Toast.LENGTH_SHORT).show();
            limpiarFormulario();
            mostrarTareas();
        } else {
            Toast.makeText(this, "Ha ocurrido un error al guardar la tarea", Toast.LENGTH_SHORT).show();
        }
    }

    private void mostrarTareas() {
        contenedorTareas.removeAllViews();

        tareaDAO.open();
        List<Tarea> tareas = tareaDAO.obtenerTodos();
        tareaDAO.close();

        for (Tarea tarea : tareas) {
            crearLinearLayout(tarea.getNombre(), tarea.getDescripcion(), tarea.getFechaCreado());
        }
    }

    private void crearLinearLayout(String titulo, String descripcion, String fechaCreado) {
        //Contenedor general
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10, 10, 10, 10);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        linearLayout.setLayoutParams(params);

        //Checkbox con el título
        CheckBox chkTituloTarea = new CheckBox(this);
        chkTituloTarea.setText(titulo);
        chkTituloTarea.setTextSize(18);
        chkTituloTarea.setPadding(0, 0, 0, 5);

        //Cambiar estilo del checkbox si se marca
        chkTituloTarea.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                chkTituloTarea.setPaintFlags(chkTituloTarea.getPaintFlags() | android.graphics.Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                chkTituloTarea.setPaintFlags(chkTituloTarea.getPaintFlags() & ~android.graphics.Paint.STRIKE_THRU_TEXT_FLAG);
            }
        });

        //TextView para la descripcion
        TextView txtDescripcionTarea = new TextView(this);
        txtDescripcionTarea.setText(descripcion);
        txtDescripcionTarea.setTextSize(14);
        txtDescripcionTarea.setPadding(0, 0, 0, 10);

        //TextView para la fecha
        TextView txtFechaTarea = new TextView(this);
        txtFechaTarea.setText(fechaCreado);
        txtFechaTarea.setTextSize(10);
        txtFechaTarea.setPadding(0, 0, 0, 10);

        //Añadir componentes al LinearLayout
        linearLayout.addView(chkTituloTarea);
        linearLayout.addView(txtDescripcionTarea);
        linearLayout.addView(txtFechaTarea);

        contenedorTareas.addView(linearLayout);
    }

    private void limpiarFormulario() {
        txtTitulo.setText("");
        txtDescripcion.setText("");
    }
}