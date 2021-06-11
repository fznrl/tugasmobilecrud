package nurul.fauzan.appcrud

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.RecyclerView

class NameAdapter : RecyclerView.Adapter<NameAdapter.NameViewHolder>() {
    private var nmeList: ArrayList<NameModel> = ArrayList()
    private var onClickItem: ((NameModel) -> Unit)? = null
    private var onClickDeleteItem: ((NameModel) -> Unit)? = null

    fun addItems(items: ArrayList<NameModel>) {
        this.nmeList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (NameModel) -> Unit){
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem(callback: (NameModel) -> Unit){
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NameViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_items_nme,parent,false)
    )

    override fun onBindViewHolder(holder: NameViewHolder, position: Int) {
        val nme = nmeList[position]
        holder.bindView(nme)
        holder.itemView.setOnClickListener{ onClickItem?.invoke(nme) }
        holder.delete.setOnClickListener { onClickDeleteItem?.invoke(nme)}
    }

    override fun getItemCount(): Int {
       return nmeList.size
    }

    class NameViewHolder(var view: View): RecyclerView.ViewHolder(view){
        private var id = view.findViewById<TextView>(R.id.tvid)
        private var name = view.findViewById<TextView>(R.id.tvname)
        private var email = view.findViewById<TextView>(R.id.tvemail)
        var delete = view.findViewById<TextView>(R.id.del)

        fun bindView(nme:NameModel){
            id.text = nme.id.toString()
            name.text = nme.name
            email.text = nme.email
        }
    }
}