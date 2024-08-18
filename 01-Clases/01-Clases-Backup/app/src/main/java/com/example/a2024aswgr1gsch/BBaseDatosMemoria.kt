package com.example.a2024aswgr1gsch

class BBaseDatosMemoria {
    companion object{
        val arregloBEntrenador = arrayListOf<BEntrenador>()

        init {
            arregloBEntrenador
                .add(BEntrenador(1, "Gilson", "asa"))
            arregloBEntrenador
                .add(BEntrenador(2, "Saul", "assa"))
            arregloBEntrenador
                .add(BEntrenador(3, "Drogon", "asasasa"))
        }
    }
}