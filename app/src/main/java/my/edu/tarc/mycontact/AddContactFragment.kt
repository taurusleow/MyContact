package my.edu.tarc.mycontact

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import my.edu.tarc.mycontact.databinding.FragmentAddContactBinding
import my.edu.tarc.mycontact.entity.Contact

class AddContactFragment : Fragment() , MenuProvider{
    private var _binding: FragmentAddContactBinding? = null

    //Create a reference to view model
    private val contactViewModel:ContactViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val menuHost : MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        // Inflate the layout for this fragment
        _binding = FragmentAddContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.findItem(R.id.action_save).isVisible = true
        menu.findItem(R.id.action_add).isVisible = false
        menu.findItem(R.id.action_profile).isVisible = false
        menu.findItem(R.id.action_settings).isVisible = false
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId){
            R.id.action_save ->{
               //TODO: Add contact record to a data storage
                val name = binding.editTextPersonName.text.toString()
                val phone = binding.editTextPhone2.text.toString()
                val newContact = Contact(name, phone)

                //refer to the main activity (adding new contact to the array list)
                contactViewModel.insert(newContact)

                Toast.makeText(context, "Profile Saved", Toast.LENGTH_SHORT).show()

                //after save return to the firstFragment
                findNavController().navigateUp()

                true
            }
            android.R.id.home->{
                activity?.onBackPressed()
                return true
            }

        }
        return true
    }

}