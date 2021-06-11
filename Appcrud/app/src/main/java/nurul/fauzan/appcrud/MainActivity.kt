package nurul.fauzan.appcrud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var add: Button
    private lateinit var view: Button
    private lateinit var update: Button

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: NameAdapter? = null
    private var nme: NameModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecyclerView()
        sqLiteHelper = SQLiteHelper(this)

        add.setOnClickListener { addName() }
        view.setOnClickListener { viewName() }
        update.setOnClickListener { updateName() }

        adapter?.setOnClickItem {
            Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show()

            name.setText(it.name)
            email.setText(it.email)
            nme = it
        }
        adapter?.setOnClickDeleteItem {
            deleteName(it.id)
        }
    }

    private fun updateName() {
        val name = name.text.toString()
        val email = email.text.toString()

        if (name == nme?.name && email == nme?.email){
            Toast.makeText(this,"Record not changed", Toast.LENGTH_SHORT).show()
            return
        }
        if (nme == null) return

        val nme = NameModel(id = nme!!.id, name = name, email = email)
        val status = sqLiteHelper.updateName(nme)
        if (status > -1) {
            clearEditText()
            viewName()
        }else{
            Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun viewName() {
        val nmeList = sqLiteHelper.getAllName()
        Log.e("Test", "${nmeList.size}")

        adapter?.addItems(nmeList)
    }

    private fun addName() {
        val name = name.text.toString()
        val email = email.text.toString()

        if (name.isEmpty() || email.isEmpty()){
            Toast.makeText(this, "Please Enter Required Field", Toast.LENGTH_SHORT).show()
        }else{
            val nme = NameModel(name = name, email = email)
            val status = sqLiteHelper.insertName(nme)

            if (status > -1){
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                clearEditText()
                viewName()
            }else{
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteName(id: Int){
        if (id == null) return

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Delete Item?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes"){ dialog, _ ->
            sqLiteHelper.deleteNameById(id)
            viewName()
            dialog.dismiss()
        }
        builder.setNegativeButton("No"){dialog, _ ->

            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()
    }

    private fun clearEditText() {
        name.setText("")
        email.setText("")
        name.requestFocus()
    }

    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = NameAdapter()
        recyclerView.adapter = adapter
    }

    private fun initView() {
        name = findViewById(R.id.name)
        email = findViewById(R.id.email)
        add = findViewById(R.id.add)
        view = findViewById(R.id.view)
        update = findViewById(R.id.update)
        recyclerView = findViewById(R.id.recyclerView)
    }
}