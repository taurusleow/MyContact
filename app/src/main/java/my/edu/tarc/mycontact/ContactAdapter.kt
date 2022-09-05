package my.edu.tarc.mycontact

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import my.edu.tarc.mycontact.entity.Contact

//serve as middleman between ur data source with the recycle view
//it will feed in to the recycle view

//An object linking data source to a RecyclerView
//ViewHolder is an individual layout to show each record
class ContactAdapter : RecyclerView.Adapter<ContactAdapter.ViewHolder>(){
    //Cache copy of data
    private var dataSet = emptyList<Contact>()

    //An inner class representing each individual record
    class ViewHolder (view: View): RecyclerView.ViewHolder(view){
        //view (parameter) refers to the layout hosting each record
        val textViewName:TextView = view.findViewById(R.id.textViewName)
        val textViewPhone:TextView = view.findViewById(R.id.textViewPhone)

        init {
            view.setOnClickListener{
                //TODO: Handle click event

            }
        }
    }

    internal fun setContact(contact: List<Contact>){
        dataSet = contact
        notifyDataSetChanged() //refresh the RecyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //need to tell the system which layout we want
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Insert data from a record to the layout (ViewHolder)
        val contact = dataSet[position] //from the parameter of the class
        holder.textViewName.text = contact.name
        holder.textViewPhone.text = contact.phone
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}