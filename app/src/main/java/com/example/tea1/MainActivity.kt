package com.example.tea1


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tea1.ItemToDo
import com.example.tea1.ListeToDo
import com.example.tea1.ProfileToDo


object DataStore {
    val profiles = mutableListOf<ProfileToDo>()
}

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataStore.profiles.clear()
        DataStore.profiles.addAll(DataPersistence.loadProfiles(this))

        val prefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this)
        val pseudo = prefs.getString("pseudo", "Utilisateur")

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.title = "MainActivity"

        val dictionnaire : MutableMap<String, MutableList<MutableList<String>>> = mutableMapOf()


        val editTextPseudo = findViewById<EditText>(R.id.pseudo)
        val buttonOk = findViewById<Button>(R.id.buttonOk)
        val textTest = findViewById<TextView>(R.id.test)
        //val settings=findViewById<ViewGroup>(R.id.settings)

        //settings.setOnClickListener{}
        editTextPseudo.hint= pseudo
        // Gestion du clic sur le bouton
        buttonOk.setOnClickListener {
            val pseudo = editTextPseudo.text.toString()
            textTest.text = pseudo
            editTextPseudo.hint=pseudo
            val intent = Intent(this, ChoixListActivity::class.java)
            intent.putExtra("pseudo_from_main_activity", pseudo)
            //intent.putExtra("PSEUDO_KEY", pseudo)
            startActivity(intent)

        }



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_settings, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.preferences -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



}