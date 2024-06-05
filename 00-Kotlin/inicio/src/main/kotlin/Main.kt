import java.util.*

fun main() {
    print ("Hola mundo")

    //Immutable
    val inmutable: String = "Adrian"
    //inmutable = "Sebastian" //ERROR

    //Mutable
    var mutable: String = "Kevin"
    mutable = "Adrian" //OK

    //VAL > VAR

    //DUCK TYPING
    val ejemploVariable = " Adrian Eguez "
    ejemploVariable.trim()

    //Variables Primitivas
    val edadEjemplo: Int = 12
    val nombreProfesor: String = "Adrian Eguez"
    val sueldo: Double=1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean=true
    //Clases de Java
    val fechaNacimiento: Date = Date()

    //When (Switch)

    val estadoCivilWhen = "C"
    when (estadoCivilWhen){
        ("C") -> {
            println("Casado")
        }
        ("S") -> {
            println("Soltero")
        }
        else -> {
            println("No sabemos")
        }
    }

    val esSoltero = (estadoCivilWhen == "S")
    val coqueteo = if (esSoltero) "Si" else "No" // if else chiquito

    calcularSueldo(10.00)
    calcularSueldo(10.00,15.00,20.00)

    //NAMED PARAMETERS
    //calcularSueldo(sueldo, tasa, bonoEspecial)
    calcularSueldo(10.00, bonoEspecial = 20.00)
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00)

    //Uso de Clases
    val sumaUno =  Suma(1,1)
    val sumaDos = Suma(null,1)
    val sumaTres = Suma(1,null)
    val sumaCuatro = Suma(null,null)
    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)

    //Arreglos

    //Estatico No se puede modificar contenido
    val arregloEstatico: Array<Int> = arrayOf<Int>(1,2,3)
    println(arregloEstatico)
    //Dinamico
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(1,2,3,4,5,6,7,8,9,10)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    //For each
    val respuestaFroEach: Unit = arregloDinamico
        .forEach{ valorActual: Int ->
            println("Valor Actual: ${valorActual}")

    }

    //it Elemento a iterar
    arregloDinamico.forEach{println("Valor Actual (it): ${it}")}

    //MAP -> Muta(modifica cambio) el arreglo
    //1) Enviamos el nuevo valor de la iteracion
    //2) Nos devuelve un NUEVO ARREGLO con valores
    //de las iteraciones

    val respuestaMap: List<Double> = arregloDinamico
        .map{ valorActual: Int ->
            return@map valorActual.toDouble() + 100.00
        }
    println(respuestaMap)
    val respuestaMapDos = arregloDinamico.map { it + 15 }
    println(respuestaMapDos)

    //Filter -> Filtrar el arreglo
    //1) Devolver una expresion (TRUE O FALSE)
    //2) Nuevo arreglo
    val respuestaFilter: List<Int> = arregloDinamico
        .filter{ valorActual: Int ->
            //Expresion o Condicion
            val mayoresACinco: Boolean = valorActual > 5
            return@filter mayoresACinco
        }
    val respuestaFilterDos = arregloDinamico.filter { it <=5 }
    println(respuestaFilter)
    println(respuestaFilterDos)

    // OR AND
    // OR -> ANY (Alguno Cumple)
    // AND -> (Todos Cumplen)
    val respuestaAny: Boolean = arregloDinamico
        .any{ valorActual: Int ->
            return@any(valorActual>5)
        }
    println(respuestaAny) //True
    val respuestaAll: Boolean = arregloDinamico
        .all { valorActual: Int ->
            return@all(valorActual >5)
        }
    println(respuestaAll) //False


    //REDUCE -> Valor Acumulado
    //Valor Acumulado = 0 (Siempre empieza en 0 en Kotlin)
    //[1,2,3,4,5] -> Acumular "SUMAR" estos valores del arreglo
    //valorIteracion1 = valorEmpieza + 1 = 0 +1 = 1 -> Iteracion1
    //valorIteracion2 = valorAcumuladoIteracion1 + 2 = 1 +2 = 3 -> Iteracion2
    //valorIteracion3 = valorAcumuladoIteracion2 + 3 = 3 + 3 = 6 3 -> Iteracion3
    //valorIteracion4 = valorAcumuladoIteracion3 + 4 = 6 + 4 = 10 3 -> Iteracion4
    //valorIteracion5 = valorAcumuladoIteracion4 + 5 = 10 + 5 = 15 3 -> Iteracion5

    val respuestaReduce: Int = arregloDinamico
        .reduce{ acumulado:Int, valorActual:Int ->
            return@reduce(acumulado + valorActual) //-> Cambiar o usar la logica del negocio
        }
    println(respuestaReduce)
    //return@reduce acumulado + (itemCarrito.cantidad + itemCarrito.precio)
    









}//Termina Funcion Main

fun imprimirNombre(nombre:String):Unit{
    println("Nombre: ${nombre}") //Templete String
}

fun calcularSueldo(
    sueldo:Double, //Requerido: Necesita un identificador de variable
    tasa: Double = 12.00, //Opcional (defecto)
    bonoEspecial:Double? = null // Opcional (nullable)
): Double{
    //Int -> Int? (nullable)
    //String -> String? (nula=lable)
    //Date -> Date? (nullable)
    if(bonoEspecial == null){
        return sueldo * (100/tasa)
    }else{
        return sueldo * (100/tasa) * bonoEspecial
    }
}
//Clase Java
abstract class NumerosJava{
    protected val numeroUno:Int
    protected val numeroDos: Int

    constructor(
        uno:Int,
        dos:Int
    ){
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}

//Clase Kotlin

abstract class Numeros( //Constructor Primario
    // Caso 1 Parametro Normal
    // uno: Int , (parametro(sin modificador de acceso))

    //caso 2 Parametro y propiedad (atributo)(protected)
    //private   var uno: Int(propiedad "instancia.uno")

    protected val numeroUno: Int,
    protected val numeroDos: Int
){
    init { //bloque constructor primario OPCIONAL
        this.numeroUno
        this.numeroDos
        println("Inicializando")

    }
}

class Suma( //Constructor Primario
    unoParametro: Int, //Parametro
    dosParametro: Int, //Parametro
): Numeros (
    unoParametro,
    dosParametro
){
    public val soyPublicoExplicito:String = "Explicito" //Explicito
    val soyPublicoImplicito: String = "Implicito" //Publics (propiedades, metodos)
    init{ //Bloque Codigo Constructor Primario
        this.numeroUno
        this.numeroDos
        numeroUno //this. OPCIONAL (PROPIEDADES, METODOS)
        numeroDos
        this.soyPublicoExplicito
        soyPublicoImplicito
    }
    constructor( // Constructor Secundario
        uno: Int?,
        dos: Int
    ):this(
        if(uno == null) 0 else uno,
        dos
    )

    constructor( // Constructor Tercero
        uno: Int,
        dos: Int?
    ):this(
        if(dos == null) 0 else dos,
        uno
    )

    constructor( //constructor cuarto
        uno: Int?,
        dos: Int?
    ):this(
        if (uno == null) 0 else uno,
        if (dos == null) 0 else dos,
    )

    fun sumar(): Int{
        val total = numeroUno + numeroDos
        //Suma.agregarHistorial(total)
        //("Suma. o "NombreClase." es Opcional)
        agregarHistorial(total)
        return total
    }

    companion object{
        //similar al Static
        //funciones y variables
        val pi = 3.14 //Suma.pi

        fun elevarAlCuadrado(num:Int):Int{
            return num *num
        }
        val historialSumas = arrayListOf<Int>() //Suma.historial

        fun agregarHistorial(valorTotalSuma:Int){
            historialSumas.add(valorTotalSuma)
        }
    }

}













