package cuatroenraya;

import java.util.Random;

/**
 *
 * @author Jesús Albero Salazar Ortega
 */
public class IAA {

    private int tiradas = 0;

    /**
     * Árboles para los movimientos.
     */
    class NodoG {

        ///Mejor movimiento.
        int mejorMovimiento;
        ///Nodos hijos.
        NodoG nodos[];
        ///Tablero del juego.
        public int tablero[];
        ///Turno de la computadora.
        boolean miTurno = false;
        ///Indice de la pocision.
        int indice;
        ///Ganador.
        int ganador = 0;

        /**
         * Constructor.
         */
        NodoG() {
        }
    }
    ///Raíz del árbol.
    NodoG arbol = new NodoG();
    ///Atributos.
    int[] tablero;
    int filas, columnas;
    int tama;
    int miFicha, fichaOponente;
    int profundidad = 0;

    /**
     * Método que nos devuelve los espacios disponibles.
     *
     * @param tablero El tablero a comprobar.
     * @return Número de casillas disponibles.
     */
    public int movDisponibles(int[] tablero) {
        int mov = 0;

        for (int i = 0; i < tama; i++) {
            if (tablero[i] == 0) {
                mov++;
            }
        }
        return mov;
    }

    /**
     * Método que nos devuelve los índices del tablero de las pocisiones vacías.
     *
     * @param tablero El tablero que vamos a analizar.
     * @return Nos devuelve los índices de las posiciones vacías
     */
    public int[] posVacias(int[] tablero) {
        ///Creamos el vector con los índices.
        int[] indices = new int[movDisponibles(tablero)];
        int indice = 0;
        ///Recorremos y guardamos los indices.
        for (int i = 0; i < tama; i++) {
            if (tablero[i] == 0) {
                indices[indice] = i;
                indice++;
            }
        }
        return indices;
    }

    /**
     * Clase que en invocada al principio, que se encarga de crear el árbol raíz
     * inicial.
     *
     * @param tablero tablero a la que se le va a analizar la jugada
     * @param filas Número de filas del tablero
     * @param columnas Número de columnas de tablero
     * @param ficha La ficha del jugador
     * @return Devuelve el índice de la mejor posición
     */
    public int movimiento(int[] tablero, int filas, int columnas, int ficha) {

        /*Asignamos el tablero.*/
        this.tablero = tablero;
        this.tama = filas * columnas;
        this.filas = filas;
        this.columnas = columnas;
        miFicha = ficha;
        if (ficha == 1) {
            fichaOponente = 2;
        } else {
            fichaOponente = 1;
        }

        int posi[] = new int[6];
        posi[0] = 16;
        posi[1] = 17;
        posi[2] = 18;
        posi[3] = 23;
        posi[4] = 24;
        posi[5] = 25;
        Random ran = new Random();
        if (tiradas++ < 2) {
            int rand;
            boolean encontrado = false;
            do {
                rand = ran.nextInt(6);
                if (this.tablero[posi[rand]] == 0) {
                    return posi[rand];
                }

            } while (encontrado = false);
        }
    
    /*Copiamos el tablero a nuestro nodo ra�z.*/
    this.arbol.tablero  = new int[tama];
    for (int i = 0;
    i< tama ;
    i

    
        ++) {
            this.arbol.tablero[i] = this.tablero[i];
    }

    /*Calculamos el mejor movimiento del �rbol, desde las hojas hasta la raiz.*/
    movComputadora(arbol, profundidad);
    /*Devolvemos el mejor movimiento.*/
    return arbol.mejorMovimiento ;
}
/**
 * Método recursivo, que genera los nodos con los movimientos.
 *
 * @param raiz El nodo que se va a comprobar
 * @param profundidad La profundidad actual del árbol
 */
public void movComputadora(NodoG raiz, int profundidad) {
        /*N�mero de movimientos disponibles y sus indices en el tablero.*/
        int movimientos = movDisponibles(raiz.tablero);
        int indices[] = posVacias(raiz.tablero);
        int prof = profundidad;
        prof++;

        /*Inicializamos el nodo.*/
        raiz.nodos = new NodoG[movimientos];

        /*Verificamos si hay un ganador.*/

        raiz.ganador = evaluar2(raiz.tablero, miFicha);
        if (prof <= 2) {
            ///raiz.ganador = evaluar2(raiz.tablero, miFicha);
                /*Creamos los datos de cada hijo.*/
            for (int i = 0; i < movimientos; i++) {

                /*Inicializamos los nodos hijos del �rbol.*/
                raiz.nodos[i] = new NodoG();
                raiz.nodos[i].tablero = new int[tama];
                /*Les pasamos su tablero.*/
                for (int j = 0; j < tama; j++) {
                    raiz.nodos[i].tablero[j] = raiz.tablero[j];
                }
                /*Creamos los diferentes movimientos posibles.*/
                if (raiz.miTurno) {
                    raiz.nodos[i].tablero[indices[i]] = fichaOponente;
                } else {
                    raiz.nodos[i].tablero[indices[i]] = miFicha;
                }

                /*Cambiamos el turno de los hijos*/
                raiz.nodos[i].miTurno = !raiz.miTurno;


                /*Guardamos el indice de su movimiento.*/
                raiz.nodos[i].indice = indices[i];

                movComputadora(raiz.nodos[i], prof);

            }

            /*Minimax*/
            if (!raiz.miTurno) {
                raiz.ganador = Max(raiz);
            } else {
                raiz.ganador = Min(raiz);
            }

        }

    }

    /**
     * Método que calcula el MÁXIMO de los nodos hijos de MIN
     *
     * @param raiz El nodo a analizar
     * @return El mejor de los hijos del nodo pasado por parámetros
     */
    public int Max(NodoG raiz) {
        int Max = Integer.MIN_VALUE;
        /*M�ximo para la computadora, buscamos el valor donde gane.*/
        for (int i = 0; i < raiz.nodos.length; i++) {
            /*Preguntamos por un nodo con un valor alto MAX*/
            if (raiz.nodos[i].ganador > Max) {
                /*Lo asignamos y pasamos el mejor movimiento a la ra�z.*/
                Max = raiz.nodos[i].ganador;
                raiz.mejorMovimiento = raiz.nodos[i].indice;
                /*Terminamos de buscar.*/

            }
        }

        /*Borramos los nodos.*/
        raiz.nodos = null;

        return Max;
    }

    /**
     * Método que calcula el MÍNIMO de los nodos hijos de MAX
     *
     * @param raiz El nodo a analizar
     * @return El mejor de los hijos del nodo pasado por parámetros
     */
    public int Min(NodoG raiz) {
        int Min = Integer.MAX_VALUE;
        /*M�nimo para el jugador*/
        for (int i = 0; i < raiz.nodos.length; i++) {
            if (raiz.nodos[i].ganador < Min) {
                Min = raiz.nodos[i].ganador;
                raiz.mejorMovimiento = raiz.nodos[i].indice;

            }
        }
        /*Borramos los nodos.*/
        raiz.nodos = null;
        return Min;
    }

    /**
     * Indica si alguno de los jugadores ha hecho cuatro en raya. Devuelve 0 en
     * el caso de que no lo haya, 1 en el caso de que lo haya hecho el jugador
     * 1, y 2 en el caso de que lo haya hecho el jugador 2.
     *
     * @param tableroArbol El tablero a analizar si ha terminado
     * @return 0 en caso de que no haya ganador y 1 o 2 dependiendo del jugador
     * que haya ganado
     */
    public int terminado(int[] tableroArbol) {

        int[][] tablero = new int[filas][columnas];
        int indice = 0;
        for (int i = 0; i < filas; ++i) {
            for (int j = 0; j < columnas; ++j) {
                tablero[i][j] = tableroArbol[indice++];
            }
        }

        int i = filas - 1;
        int j;
        boolean encontrado = false;
        int jugador = 0;
        int casilla;
        while (!encontrado && i >= 0) {
            j = columnas - 1;
            while (!encontrado && j >= 0) {
                casilla = tablero[i][j];
                if (casilla != 0) {
                    /// Busqueda horizontal
                    if (j - 3 >= 0) {
                        if (tablero[i][j - 1] == casilla
                                && tablero[i][j - 2] == casilla
                                && tablero[i][j - 3] == casilla) {
                            encontrado = true;
                            jugador = casilla;
                        }
                    }
                    /// Busqueda vertical
                    if (i + 3 < filas) {
                        if (tablero[i + 1][j] == casilla
                                && tablero[i + 2][j] == casilla
                                && tablero[i + 3][j] == casilla) {
                            encontrado = true;
                            jugador = casilla;
                        } else {
                            /// Busqueda diagonal 1
                            if (j - 3 >= 0) {
                                if (tablero[i + 1][j - 1] == casilla
                                        && tablero[i + 2][j - 2] == casilla
                                        && tablero[i + 3][j - 3] == casilla) {
                                    encontrado = true;
                                    jugador = casilla;
                                }
                            }
                            /// Busqueda diagonal 2
                            if (j + 3 < columnas) {
                                if (tablero[i + 1][j + 1] == casilla
                                        && tablero[i + 2][j + 2] == casilla
                                        && tablero[i + 3][j + 3] == casilla) {
                                    encontrado = true;
                                    jugador = casilla;
                                }
                            }
                        }
                    }
                }
                j = j - 1;
            }
            i = i - 1;
        }
        return jugador;
    }

    /**
     * Función para conocer la cantidad de veces que aparece una subcadena en
     * una cadena
     *
     * @param cadena La cadena a analizar
     * @param patron La subcadena
     * @return El número de ocurrencias encontradas
     */
    public static int cantidadOcurrencias(String cadena, String patron) {
        int cant = 0;
        while (cadena.indexOf(patron) > -1) {
            cadena = cadena.substring(cadena.indexOf(patron) + patron.length(), cadena.length());
            cant++;
        }
        return cant;
    }

    /**
     * Función heurística que analiza en tablero y nos da un valor alto si las
     * fichas has sido bien colocada a nuestro favor o valor bajo si lo hace en
     * nuestra contra
     *
     * @param tableroArbol El tablero a analizar
     * @param miFicha Mi ficha
     * @return la piuntuación obtenida por el tablero
     */
    public int evaluar2(int[] tableroArbol, int miFicha) {

        int filas = 6;
        int columnas = 7;

        int puntuacion = 0;

        String Sfilas[] = new String[filas];
        String Scolumnas[] = new String[columnas];
        String Sdiagonales[] = new String[6];
        String Sdiagonales1[] = new String[6];
        for (int i = 0; i < filas; ++i) {
            Sfilas[i] = "";
        }
        for (int i = 0; i < columnas; ++i) {
            Scolumnas[i] = "";
        }
        for (int i = 0; i < 6; ++i) {
            Sdiagonales[i] = "";
        }
        for (int i = 0; i < 6; ++i) {
            Sdiagonales1[i] = "";
        }

        ///Para las horizontales
        int index = 0;
        for (int i = 0; i < filas; ++i) {
            for (int j = 0; j < columnas; ++j) {
                Sfilas[i] += tableroArbol[index++];
            }
        }

        ///Para las verticales
        for (int i = 0; i < columnas; ++i) {
            for (int j = 0; j < filas; ++j) {
                Scolumnas[i] += tableroArbol[i + (j * columnas)];
            }
        }

        ///Para diagonales 1
        index = 0;
        int x = 3;
        for (int i = 0; i < 4; i++) {
            Sdiagonales[0] += tableroArbol[x + (i * columnas)];
            x--;
        }
        x = 4;
        for (int i = 0; i < 5; i++) {
            Sdiagonales[1] += tableroArbol[x + (i * columnas)];
            x--;
        }
        x = 5;
        for (int i = 0; i < 6; i++) {
            Sdiagonales[2] += tableroArbol[x + (i * columnas)];
            x--;
        }
        x = 6;
        for (int i = 0; i < 6; i++) {
            Sdiagonales[3] += tableroArbol[x + (i * columnas)];
            x--;
        }
        x = 5;
        for (int i = 2; i < 7; i++) {
            Sdiagonales[4] += tableroArbol[i + (x * columnas)];
            x--;
        }
        x = 5;
        for (int i = 3; i < 7; i++) {
            Sdiagonales[5] += tableroArbol[i + (x * columnas)];
            x--;
        }

        ///Para diagonales 2
        x = 2;
        for (int i = 0; i < 4; i++) {
            Sdiagonales1[0] += tableroArbol[i + (x * columnas)];
            x++;
        }
        x = 1;
        for (int i = 0; i < 5; i++) {
            Sdiagonales1[1] += tableroArbol[i + (x * columnas)];
            x++;
        }
        x = 0;
        for (int i = 0; i < 6; i++) {
            Sdiagonales1[2] += tableroArbol[i + (x * columnas)];
            x++;
        }
        x = 0;
        for (int i = 1; i < 7; i++) {
            Sdiagonales1[3] += tableroArbol[i + (x * columnas)];
            x++;
        }
        x = 0;
        for (int i = 2; i < 7; i++) {
            Sdiagonales1[4] += tableroArbol[i + (x * columnas)];
            x++;
        }
        x = 0;
        for (int i = 3; i < 7; i++) {
            Sdiagonales1[5] += tableroArbol[i + (x * columnas)];
            x++;
        }


        String pos1, pos2, pos3, pos4, pos5, pos6, pos7;
        String pos8, pos9, pos10, pos11, pos12, pos13, pos14;
        String pos15, pos16, pos17, pos18, pos19, pos20;
        if (miFicha == 1) {
            pos1 = "110";
            pos2 = "011";
            pos3 = "220";
            pos4 = "022";
            pos5 = "0110";
            pos6 = "0220";
            pos7 = "1010";
            pos8 = "0101";
            pos9 = "2020";
            pos10 = "0202";
            pos11 = "1110";
            pos12 = "0111";
            pos13 = "2220";
            pos14 = "0222";
            pos15 = "1111";
            pos16 = "2222";
            pos17 = "1101";
            pos18 = "1011";
            pos19 = "2202";
            pos20 = "2022";
        } else {
            pos1 = "220";
            pos2 = "022";
            pos3 = "110";
            pos4 = "011";
            pos5 = "0220";
            pos6 = "0110";
            pos7 = "2020";
            pos8 = "0202";
            pos9 = "0110";
            pos10 = "0101";
            pos11 = "2220";
            pos12 = "0222";
            pos13 = "1110";
            pos14 = "0111";
            pos15 = "2222";
            pos16 = "1111";
            pos17 = "2202";
            pos18 = "2022";
            pos19 = "1101";
            pos20 = "1011";
        }
        for (int i = 0; i < filas; i++) {
            ///Para las horizontales
            for (int n = 0; n < cantidadOcurrencias(Sfilas[i], pos1); ++n) {
                puntuacion += 20;
            }
            for (int n = 0; n < cantidadOcurrencias(Sfilas[i], pos2); ++n) {
                puntuacion += 20;
            }
            for (int n = 0; n < cantidadOcurrencias(Sfilas[i], pos3); ++n) {
                puntuacion -= 20;
            }
            for (int n = 0; n < cantidadOcurrencias(Sfilas[i], pos4); ++n) {
                puntuacion -= 20;
            }
            for (int n = 0; n < cantidadOcurrencias(Sfilas[i], pos5); ++n) {
                puntuacion += 20;
            }
            for (int n = 0; n < cantidadOcurrencias(Sfilas[i], pos6); ++n) {
                puntuacion -= 20;
            }
            for (int n = 0; n < cantidadOcurrencias(Sfilas[i], pos7); ++n) {
                puntuacion += 100;
            }
            for (int n = 0; n < cantidadOcurrencias(Sfilas[i], pos8); ++n) {
                puntuacion += 100;
            }
            for (int n = 0; n < cantidadOcurrencias(Sfilas[i], pos9); ++n) {
                puntuacion -= 100;
            }
            for (int n = 0; n < cantidadOcurrencias(Sfilas[i], pos10); ++n) {
                puntuacion -= 100;
            }
            for (int n = 0; n < cantidadOcurrencias(Sfilas[i], pos11); ++n) {
                puntuacion += 10000;
            }
            for (int n = 0; n < cantidadOcurrencias(Sfilas[i], pos12); ++n) {
                puntuacion += 10000;
            }
            for (int n = 0; n < cantidadOcurrencias(Sfilas[i], pos13); ++n) {
                puntuacion -= 10000;
            }
            for (int n = 0; n < cantidadOcurrencias(Sfilas[i], pos14); ++n) {
                puntuacion -= 10000;
            }
            for (int n = 0; n < cantidadOcurrencias(Sfilas[i], pos15); ++n) {
                puntuacion += 100000;
            }
            for (int n = 0; n < cantidadOcurrencias(Sfilas[i], pos16); ++n) {
                puntuacion -= 100000;
            }
            for (int n = 0; n < cantidadOcurrencias(Sfilas[i], pos17); ++n) {
                puntuacion += 10000;
            }
            for (int n = 0; n < cantidadOcurrencias(Sfilas[i], pos18); ++n) {
                puntuacion += 10000;
            }
            for (int n = 0; n < cantidadOcurrencias(Sfilas[i], pos19); ++n) {
                puntuacion -= 10000;
            }
            for (int n = 0; n < cantidadOcurrencias(Sfilas[i], pos20); ++n) {
                puntuacion -= 10000;
            }
        }

        ///Para las verticales
        for (int i = 0; i < columnas; i++) {
            for (int n = 0; n < cantidadOcurrencias(Scolumnas[i], pos1); ++n) {
                puntuacion += 20;
            }
            for (int n = 0; n < cantidadOcurrencias(Scolumnas[i], pos2); ++n) {
                puntuacion += 20;
            }
            for (int n = 0; n < cantidadOcurrencias(Scolumnas[i], pos3); ++n) {
                puntuacion -= 20;
            }
            for (int n = 0; n < cantidadOcurrencias(Scolumnas[i], pos4); ++n) {
                puntuacion -= 20;
            }
            for (int n = 0; n < cantidadOcurrencias(Scolumnas[i], pos5); ++n) {
                puntuacion += 20;
            }
            for (int n = 0; n < cantidadOcurrencias(Scolumnas[i], pos6); ++n) {
                puntuacion -= 20;
            }
            for (int n = 0; n < cantidadOcurrencias(Scolumnas[i], pos7); ++n) {
                puntuacion += 100;
            }
            for (int n = 0; n < cantidadOcurrencias(Scolumnas[i], pos8); ++n) {
                puntuacion += 100;
            }
            for (int n = 0; n < cantidadOcurrencias(Scolumnas[i], pos9); ++n) {
                puntuacion -= 100;
            }
            for (int n = 0; n < cantidadOcurrencias(Scolumnas[i], pos10); ++n) {
                puntuacion -= 100;
            }
            for (int n = 0; n < cantidadOcurrencias(Scolumnas[i], pos11); ++n) {
                puntuacion += 10000;
            }
            for (int n = 0; n < cantidadOcurrencias(Scolumnas[i], pos12); ++n) {
                puntuacion += 10000;
            }
            for (int n = 0; n < cantidadOcurrencias(Scolumnas[i], pos13); ++n) {
                puntuacion -= 10000;
            }
            for (int n = 0; n < cantidadOcurrencias(Scolumnas[i], pos14); ++n) {
                puntuacion -= 10000;
            }
            for (int n = 0; n < cantidadOcurrencias(Scolumnas[i], pos15); ++n) {
                puntuacion += 100000;
            }
            for (int n = 0; n < cantidadOcurrencias(Scolumnas[i], pos16); ++n) {
                puntuacion -= 100000;
            }
            for (int n = 0; n < cantidadOcurrencias(Scolumnas[i], pos17); ++n) {
                puntuacion += 10000;
            }
            for (int n = 0; n < cantidadOcurrencias(Scolumnas[i], pos18); ++n) {
                puntuacion += 10000;
            }
            for (int n = 0; n < cantidadOcurrencias(Scolumnas[i], pos19); ++n) {
                puntuacion -= 10000;
            }
            for (int n = 0; n < cantidadOcurrencias(Scolumnas[i], pos20); ++n) {
                puntuacion -= 10000;
            }
        }

        ///Para las diagonales 1
        for (int i = 0; i < 6; i++) {
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales[i], pos1); ++n) {
                puntuacion += 20;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales[i], pos2); ++n) {
                puntuacion += 20;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales[i], pos3); ++n) {
                puntuacion -= 20;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales[i], pos4); ++n) {
                puntuacion -= 20;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales[i], pos5); ++n) {
                puntuacion += 20;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales[i], pos6); ++n) {
                puntuacion -= 20;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales[i], pos7); ++n) {
                puntuacion += 100;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales[i], pos8); ++n) {
                puntuacion += 100;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales[i], pos9); ++n) {
                puntuacion -= 100;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales[i], pos10); ++n) {
                puntuacion -= 100;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales[i], pos11); ++n) {
                puntuacion += 10000;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales[i], pos12); ++n) {
                puntuacion += 10000;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales[i], pos13); ++n) {
                puntuacion -= 10000;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales[i], pos14); ++n) {
                puntuacion -= 10000;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales[i], pos15); ++n) {
                puntuacion += 100000;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales[i], pos16); ++n) {
                puntuacion -= 100000;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales[i], pos17); ++n) {
                puntuacion += 10000;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales[i], pos18); ++n) {
                puntuacion += 10000;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales[i], pos19); ++n) {
                puntuacion -= 10000;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales[i], pos20); ++n) {
                puntuacion -= 10000;
            }
        }

        ///Para las diagonales 2
        for (int i = 0; i < 6; i++) {
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales1[i], pos1); ++n) {
                puntuacion += 20;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales1[i], pos2); ++n) {
                puntuacion += 20;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales1[i], pos3); ++n) {
                puntuacion -= 20;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales1[i], pos4); ++n) {
                puntuacion -= 20;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales1[i], pos5); ++n) {
                puntuacion += 20;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales1[i], pos6); ++n) {
                puntuacion -= 20;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales1[i], pos7); ++n) {
                puntuacion += 100;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales1[i], pos8); ++n) {
                puntuacion += 100;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales1[i], pos9); ++n) {
                puntuacion -= 100;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales1[i], pos10); ++n) {
                puntuacion -= 100;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales1[i], pos11); ++n) {
                puntuacion += 10000;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales1[i], pos12); ++n) {
                puntuacion += 10000;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales1[i], pos13); ++n) {
                puntuacion -= 10000;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales1[i], pos14); ++n) {
                puntuacion -= 10000;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales1[i], pos15); ++n) {
                puntuacion += 100000;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales1[i], pos16); ++n) {
                puntuacion -= 100000;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales1[i], pos17); ++n) {
                puntuacion += 10000;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales1[i], pos18); ++n) {
                puntuacion += 10000;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales1[i], pos19); ++n) {
                puntuacion -= 10000;
            }
            for (int n = 0; n < cantidadOcurrencias(Sdiagonales1[i], pos20); ++n) {
                puntuacion -= 10000;
            }
        }
        return puntuacion;
    } 
}
