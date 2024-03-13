package com.example.accesologin.data

import com.example.accesologin.model.Acceso_Entity
import com.example.accesologin.model.Alumno_Entity
import com.example.accesologin.model.CalifFinal_Entity
import com.example.accesologin.model.CalifUnidad_Entity
import com.example.accesologin.model.CardexProm_Entity
import com.example.accesologin.model.Cardex_Entity
import com.example.accesologin.model.Carga_Entity

interface RepositoryDB {
    suspend fun getAccesDB(matricula: String, password: String):  Acceso_Entity
    suspend fun getAlumnoDB(): Alumno_Entity
    suspend fun getCargaDB(): List<Carga_Entity>
    suspend fun getCalifUnidadDB(): List<CalifUnidad_Entity>
    suspend fun getCalifFinalDB(): List<CalifFinal_Entity>
    suspend fun getCardexDB(): List<Cardex_Entity>
    suspend fun getCardexPromDB(): CardexProm_Entity
}

class OfflineRepository(
    private val accesoDao: AccesoDao,
    private val alumnoDao: AlumnoDao,
    private val cargaDao: CargaDao,
    private val califUnidadDao: CalifUnidadDao,
    private val califFinalDao: CalifFinalDao,
    private val cardexDao: CardexDao,
    private val cardexPromDao: CardexPromDao
): RepositoryDB {
    //override suspend fun getAccesDB(matricula: String, contrasenia: String): Acceso_Entity = accesoDao.getAccess(matricula, contrasenia)
    override suspend fun getAccesDB(matricula: String, password: String): Acceso_Entity {
        return accesoDao.getAccess(matricula, password)
    }

    override suspend fun getAlumnoDB(): Alumno_Entity {
        return alumnoDao.getAlumno()
    }

    override suspend fun getCargaDB(): List<Carga_Entity> {
        return cargaDao.getCarga()
    }

    override suspend fun getCalifUnidadDB(): List<CalifUnidad_Entity> {
        return califUnidadDao.getCalifsUnidad()
    }

    override suspend fun getCalifFinalDB(): List<CalifFinal_Entity> {
        return califFinalDao.getCalifsFinal()
    }

    override suspend fun getCardexDB(): List<Cardex_Entity> {
        return cardexDao.getCardex()
    }

    override suspend fun getCardexPromDB(): CardexProm_Entity {
        return cardexPromDao.getCardexProm()
    }
}