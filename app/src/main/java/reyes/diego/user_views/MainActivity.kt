package reyes.diego.user_views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var btn_registrar: Button
    lateinit var btn_ingresar: Button
    lateinit var btn_contrasena: TextView
    lateinit var campo_correo: EditText
    lateinit var campo_contra: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btn_registrar = findViewById<Button>(R.id.btn_registrar)
        btn_ingresar = findViewById<Button>(R.id.btn_ingresar)
        btn_contrasena = findViewById<TextView>(R.id.btn_contrasena)
        campo_correo = findViewById<EditText>(R.id.campo_correo)
        campo_contra = findViewById<EditText>(R.id.campo_contra)

        btn_registrar.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        btn_contrasena.setOnClickListener {
            val intent = Intent(this, RecuperarContrasenaActivity::class.java)
            startActivity(intent)
        }

        btn_ingresar.setOnClickListener {
            val usuario = campo_correo.text.toString()
            val contra = campo_contra.text.toString()

            if (usuario == "admin" && contra == "admin") {
                Toast.makeText(this, "Ingresó", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Datos incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
