package com.example.tea1

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text



class ShowListActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_show_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.showList)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.title = "ShowListActivity"

        val listOfItems = findViewById<LinearLayout>(R.id.list_items)

        val intent = intent
        val pseudoRecu = intent.getStringExtra("pseudo")
        val listeRecu = intent.getStringExtra("liste_name")
        val profile = DataStore.profiles.find { it.getLogin() == pseudoRecu }
        val liste = profile?.getMesListeToDo()?.find { it.getTitreListeToDo() == listeRecu }

        if (liste != null) {
            for (item in liste.getLesItems()) {
                val itemView = layoutInflater.inflate(R.layout.item_view, listOfItems, false)
                val itemCheckBox = itemView.findViewById<CheckBox>(R.id.itemCheckBox)
                itemCheckBox.text = item.getDescription()
                itemCheckBox.isChecked = item.getFait()

                itemCheckBox.setOnCheckedChangeListener { _, isChecked ->
                    itemCheckBox.isChecked = isChecked
                }

                listOfItems.addView(itemView)
            }
        }



        val buttonOk = findViewById<Button>(R.id.buttonOk)
        val EditNewItem = findViewById<EditText>(R.id.nom_item)



        buttonOk.setOnClickListener {
            val name_new_item = EditNewItem.text.toString()
            val profile = DataStore.profiles.find { it.getLogin() == pseudoRecu }
            val liste = profile?.getMesListeToDo()?.find { it.getTitreListeToDo() == listeRecu }

            if (liste != null) {
                val newItem = ItemToDo(name_new_item)
                liste.getLesItems().add(newItem)

                val itemView = layoutInflater.inflate(R.layout.item_view, listOfItems, false)
                val itemCheckBox = itemView.findViewById<CheckBox>(R.id.itemCheckBox)
                itemCheckBox.text = newItem.getDescription()

                itemCheckBox.setOnCheckedChangeListener { _, isChecked ->
                    itemCheckBox.isChecked = isChecked
                    newItem.setFait(true)
                    DataPersistence.saveProfiles(this, DataStore.profiles)
                }

                listOfItems.addView(itemView)
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