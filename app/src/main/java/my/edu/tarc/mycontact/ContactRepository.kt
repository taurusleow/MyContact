package my.edu.tarc.mycontact

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import my.edu.tarc.mycontact.entity.Contact

class ContactRepository(private val contactDao: ContactDao) {
    //TODO: Link to DAO and create a cache copy of UI data
    val allContact: LiveData<List<Contact>> = contactDao.getAll()

    @WorkerThread
    suspend fun insert (contact: Contact){
        contactDao.insert(contact)
    }

    @WorkerThread
    suspend fun delete(contact: Contact){
        contactDao.delete(contact)
    }

    @WorkerThread
    suspend fun update(contact: Contact){
        contactDao.update(contact)
    }
}