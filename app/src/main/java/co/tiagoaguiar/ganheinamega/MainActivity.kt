package co.tiagoaguiar.ganheinamega

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.Random

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Referência dos objetos
        val editText: EditText = findViewById(R.id.edit_number)
        val txtResult: TextView = findViewById(R.id.txtresult)
        val btnGeneration: Button = findViewById(R.id.btn_generate)

        //Salva os dados da ultima aposta
        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val result = prefs.getString("result", null)

        result?.let {
            txtResult.text = "Ultima aposta: $it"
        }

        btnGeneration.setOnClickListener {
            val text = editText.text.toString()

            numberGenerator(text, txtResult)
        }
    }

    private fun numberGenerator(text: String, txtResult: TextView) {

        //Validar quando o campo é vazio
        if (text.isEmpty()) {
            Toast.makeText(this, "Informe um numerto entre 6 e 15", Toast.LENGTH_LONG).show()
            return
        }

        //Verifica se o numero inserido está entre 6 e 15
        val qtdNumber = text.toInt()

        if (qtdNumber < 6 || qtdNumber > 15) {
            Toast.makeText(this, "Informe um numerto entre 6 e 15", Toast.LENGTH_LONG).show()
            return
        }

        //Gera numeros aleatorios
        val numbers = mutableSetOf<Int>()
        val random = Random()

        while (true) {
            val number = random.nextInt(60)
            numbers.add(number + 1)

            if (numbers.size == qtdNumber) break
        }

        txtResult.text = numbers.joinToString(" - ")

        val editor = prefs.edit()
        editor.putString("result", txtResult.text.toString())
        editor.apply()

    }
}