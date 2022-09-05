package my.edu.tarc.mycontact

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import my.edu.tarc.mycontact.databinding.ActivityMainBinding
import my.edu.tarc.mycontact.databinding.FragmentSecondBinding
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.OutputStream

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(), MenuProvider {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //Get content from another app component
    //similar to implicit intent
    private val getProfilePic = registerForActivityResult(ActivityResultContracts.GetContent()){ uri->
        if(uri!== null){
            binding.imageViewPicture.setImageURI(uri)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val menuHost : MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Retrieve data from preferences
        val preferences = activity?.getPreferences(Context.MODE_PRIVATE)
        if (preferences != null) {
            if(preferences.contains(getString(R.string.name))){
                binding.editTextTextName.setText(preferences.getString(getString(R.string.name), ""))
            }
            if(preferences.contains(getString(R.string.phone))){
                binding.editTextPhone.setText(
                    preferences.getString(getString(R.string.phone), "")
                )
            }
        }
        binding.imageViewPicture.setOnClickListener{
            getProfilePic.launch("image/*")
        }
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
                //Create an instance of Preferences
                val preferences= activity?.getPreferences(Context.MODE_PRIVATE)
                val name = binding.editTextTextName.text.toString()
                val phone = binding.editTextPhone.text.toString()
                if(preferences !=null){
                    with(preferences?.edit()){
                        putString(getString(R.string.name), name)
                        putString(getString(R.string.phone), phone)
                        apply() //save to the preference file
                    }
                }
                saveProfilePicture()
                Toast.makeText(context, "Profile Saved", Toast.LENGTH_SHORT).show()

                //after save return to the firstFragment
                val navController = activity?.findNavController(R.id.nav_host_fragment_content_main)
                navController?.navigateUp()
            }
            android.R.id.home->{
                activity?.onBackPressed()
                return true
            }
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun saveProfilePicture() {
        val filename = "profile.png"
        val file = File(this.context?.filesDir, filename)

        val bd = binding.imageViewPicture.getDrawable() as BitmapDrawable
        val bitmap = bd.bitmap
        val outputStream: OutputStream

        try{
            outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, outputStream)
            outputStream.flush()
            outputStream.close()
        }catch (e: FileNotFoundException){
            e.printStackTrace()
        }
    }

    private fun readProfilePicture(){
        val filename = "profile.png"
        val file = File(this.context?.filesDir, filename)

        try{
            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
            binding.imageViewPicture.setImageBitmap(bitmap)
        }catch (e: FileNotFoundException){
            e.printStackTrace()
        }
    }


}