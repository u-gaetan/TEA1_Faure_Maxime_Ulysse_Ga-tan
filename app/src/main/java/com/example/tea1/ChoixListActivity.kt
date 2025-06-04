package com.example.tea1

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText

import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat





class ChoixListActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_choix_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.choixList)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.title = "ChoixListActivity"

        val listOfLists = findViewById<LinearLayout>(R.id.list_lists)

        val intent = intent
        val pseudoRecu = intent.getStringExtra("pseudo_from_main_activity")


        val profile = DataStore.profiles.find { it.getLogin() == pseudoRecu }

        if (profile != null) {
            for (liste in profile.getMesListeToDo()) {
                val listView = layoutInflater.inflate(R.layout.list_item_view, listOfLists, false)
                val EditNameListItem = listView.findViewById<TextView>(R.id.liste)
                EditNameListItem.text = liste.getTitreListeToDo()
                EditNameListItem.setOnClickListener {
                    val intent = Intent(this, ShowListActivity::class.java)
                    intent.putExtra("pseudo", pseudoRecu)
                    intent.putExtra("liste_name", liste.getTitreListeToDo())
                    startActivity(intent) //on lance l'autre activité
                }
                listOfLists.addView(listView) 
            }
        }



        val buttonOk = findViewById<Button>(R.id.buttonOk)
        val EditNewListe = findViewById<EditText>(R.id.nom_liste)



        buttonOk.setOnClickListener {
            val name_new_liste = EditNewListe.text.toString()

            val profile = DataStore.profiles.find { it.getLogin() == pseudoRecu }
                ?: ProfileToDo(pseudoRecu!!).also { DataStore.profiles.add(it) }

            if (profile.getMesListeToDo().any { it.getTitreListeToDo() == name_new_liste }) {
                Toast.makeText(this, "Nom de liste déjà utilisé", Toast.LENGTH_SHORT).show()
            } else {
                val newList = ListeToDo(name_new_liste)
                profile.getMesListeToDo().add(newList)

                val listView = layoutInflater.inflate(R.layout.list_item_view, listOfLists, false)
                val EditNameListItem = listView.findViewById<TextView>(R.id.liste)
                EditNameListItem.text = name_new_liste
                listOfLists.addView(listView)
                EditNameListItem.setOnClickListener {
                    val intent = Intent(this, ShowListActivity::class.java)
                    intent.putExtra("pseudo", pseudoRecu)
                    intent.putExtra("liste_name", name_new_liste)
                    startActivity(intent)
                }
                DataPersistence.saveProfiles(this, DataStore.profiles)

            }
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