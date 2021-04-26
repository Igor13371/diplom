package com.example.marketing.database

class Repository(private val dao: AppDao) {
    val offers = dao.getAllOffers()

    suspend fun insertOffer(offer: Offer) {
        dao.insert(offer)
    }
    suspend fun nukeTable(){
        dao.nukeTable()
    }
    suspend fun updateOffer(newName: String, newDescription: String, searchId: Int){
        dao.updateOffer(newName,newDescription,searchId)
    }
}