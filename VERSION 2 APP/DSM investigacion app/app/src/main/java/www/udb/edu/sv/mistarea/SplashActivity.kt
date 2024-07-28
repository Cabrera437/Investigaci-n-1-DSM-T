package www.udb.edu.sv.mistarea
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)


        val splashScreenDuration: Long = 2500

        android.os.Handler().postDelayed({
            // Iniciar la actividad principal
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // para que no vulva a repetir si se le da boton hacia atras-por seguridad
        }, splashScreenDuration)
    }
}
