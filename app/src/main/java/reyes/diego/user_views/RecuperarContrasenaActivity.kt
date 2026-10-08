package reyes.diego.user_views

import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RecuperarContrasenaActivity : AppCompatActivity() {
    lateinit var campo_correo: EditText
    lateinit var btn_enviar: Button
    lateinit var btn_cancelar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recuperar_contrasena)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        campo_correo = findViewById<EditText>(R.id.campo_correo)
        btn_enviar = findViewById<Button>(R.id.btn_enviar)
        btn_cancelar = findViewById<Button>(R.id.btn_cancelar)

        btn_enviar.setOnClickListener {
            val correo = campo_correo.text.toString().trim()

            if (correo.isEmpty()) {
                Toast.makeText(this, "Ingresa tu correo electrónico", Toast.LENGTH_SHORT).show()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                Toast.makeText(this, "El correo electrónico no es válido", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    this,
                    "Se envió un enlace de recuperación a $correo",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        btn_cancelar.setOnClickListener { finish() }
    }
}
