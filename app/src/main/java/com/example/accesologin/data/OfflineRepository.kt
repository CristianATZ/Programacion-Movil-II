package com.example.accesologin.data

import com.example.accesologin.model.Acceso_Entity

interface RepositoryDB {
    suspend fun getAccesDB(matricula: String, password: String):  Acceso_Entity?
}

class OfflineRepository(
    private val accesoDao: AccesoDao
): RepositoryDB {
    override suspend fun getAccesDB(matricula: String, contrasenia: String): Acceso_Entity ?= accesoDao.getAccess(matricula, contrasenia)
}