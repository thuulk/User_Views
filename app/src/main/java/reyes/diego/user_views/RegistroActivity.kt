package reyes.diego.user_views

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Calendar
import java.util.Locale

class RegistroActivity : AppCompatActivity() {
    lateinit var campo_nombre: EditText
    lateinit var campo_correo: EditText
    lateinit var campo_contra: EditText
    lateinit var campo_verificar_contra: EditText
    lateinit var campo_fecha: EditText
    lateinit var btn_registrarse: Button
    lateinit var btn_ir_login: TextView

    // Fecha de nacimiento elegida en el DatePicker (null si aún no se elige)
    var fecha_nacimiento: Calendar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        campo_nombre = findViewById<EditText>(R.id.campo_nombre)
        campo_correo = findViewById<EditText>(R.id.campo_correo)
        campo_contra = findViewById<EditText>(R.id.campo_contra)
        campo_verificar_contra = findViewById<EditText>(R.id.campo_verificar_contra)
        campo_fecha = findViewById<EditText>(R.id.campo_fecha)
        btn_registrarse = findViewById<Button>(R.id.btn_registrarse)
        btn_ir_login = findViewById<TextView>(R.id.btn_ir_login)

        campo_fecha.setOnClickListener { mostrarCalendario() }

        btn_registrarse.setOnClickListener { registrar() }

        btn_ir_login.setOnClickListener { finish() }
    }

    private fun mostrarCalendario() {
        // Si aún no hay fecha, el calendario abre 18 años atrás para no tener que retroceder tanto
        val inicial = fecha_nacimiento ?: Calendar.getInstance().apply { add(Calendar.YEAR, -18) }
        val dialogo = DatePickerDialog(
            this,
            { _, anio, mes, dia ->
                val seleccionada = Calendar.getInstance()
                seleccionada.set(anio, mes, dia)
                fecha_nacimiento = seleccionada
                campo_fecha.setText(
                    String.format(Locale.getDefault(), "%02d/%02d/%04d", dia, mes + 1, anio)
                )
            },
            inicial.get(Calendar.YEAR),
            inicial.get(Calendar.MONTH),
            inicial.get(Calendar.DAY_OF_MONTH)
        )
        // No se puede nacer en el futuro
        dialogo.datePicker.maxDate = System.currentTimeMillis()
        dialogo.show()
    }

    private fun registrar() {
        val nombre = campo_nombre.text.toString().trim()
        val correo = campo_correo.text.toString().trim()
        val contra = campo_contra.text.toString()
        val verificar = campo_verificar_contra.text.toString()
        val fecha = fecha_nacimiento

        if (nombre.isEmpty() || correo.isEmpty() || contra.isEmpty() ||
            verificar.isEmpty() || fecha == null
        ) {
            mensaje("Todos los campos son obligatorios")
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            mensaje("El correo electrónico no es válido")
            return
        }

        if (contra != verificar) {
            mensaje("Las contraseñas no coinciden")
            return
        }

        if (calcularEdad(fecha) < 18) {
            mensaje("Debes ser mayor de 18 años para registrarte")
            return
        }

        mensaje("¡Registro exitoso! Bienvenido, $nombre")
    }

    private fun calcularEdad(nacimiento: Calendar): Int {
        val hoy = Calendar.getInstance()
        var edad = hoy.get(Calendar.YEAR) - nacimiento.get(Calendar.YEAR)
        // Si aún no llega su cumpleaños este año, se resta uno
        if (hoy.get(Calendar.MONTH) < nacimiento.get(Calendar.MONTH) ||
            (hoy.get(Calendar.MONTH) == nacimiento.get(Calendar.MONTH) &&
                hoy.get(Calendar.DAY_OF_MONTH) < nacimiento.get(Calendar.DAY_OF_MONTH))
        ) {
            edad--
        }
        return edad
    }

    private fun mensaje(texto: String) {
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show()
    }
}
