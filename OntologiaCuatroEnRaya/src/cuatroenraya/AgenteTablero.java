/**
 *
 * Agente Tablero
 *
 */
package cuatroenraya;

import cuatroenraya.elementos.Ficha;
import cuatroenraya.elementos.Ganador;
import cuatroenraya.elementos.Juego;
import cuatroenraya.elementos.Jugador;
import cuatroenraya.elementos.Jugar;
import cuatroenraya.elementos.Partida;
import cuatroenraya.elementos.PosicionarFicha;
import cuatroenraya.elementos.Tablero;
import cuatroenraya.elementos.Movimiento;
import cuatroenraya.elementos.MovimientoRealizado;
import cuatroenraya.elementos.Posicion;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.BeanOntologyException;
import jade.content.onto.Ontology;
import jade.content.onto.basic.Action;
import jade.content.onto.basic.Done;
import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;
import jade.proto.AchieveREInitiator;
import jade.domain.FIPANames;
import java.util.Date;
import java.util.Vector;
import java.util.Enumeration;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Agente Tablero que se comunicará con los agentes de las páginas amarillas y
 * con dos de ellos iniciará una partida.
 *
 * @author David Abad Vich
 * @author Jesús Alejandro Benitez Pedrero
 * @author Jesús Alberto Salazar Ortega
 */
public class AgenteTablero extends Agent {

    public int nPartida;
    private GuiLink myGui;
    private int searchTime = 1000;
    DFAgentDescription[] results;
    private int nResponders;
    private Vector receptores = new Vector();
    ///Jugadores de la partida activa
    private Jugador[] participantes = new Jugador[2];
    private Tablero tableroActual;
    private Jugador jugadorJugando;
    private Posicion posicionAnterior;
    private int tablero[][];
    private int respuestas = 0;
    private int inicioJuego = 0;
    private boolean restartGame;
    private Ficha fichaRoja = new Ficha(OntologiaCuatroEnRaya.FICHA_ROJA);
    private Ficha fichaAzul = new Ficha(OntologiaCuatroEnRaya.FICHA_AZUL);
    private ContentManager manager = (ContentManager) getContentManager();
    /// El lenguaje utilizado por el agente para la comunicación es SL 
    private Codec codec = new SLCodec();
    /// La ontología que utilizará el agente
    private Ontology ontology;

    /**
     * Función que muestra el tablero por consola
     */
    private void muestraTablero() {
        System.out.println();
        System.out.print("Estado actual del tablero en Tablero");
        for (int i = 0; i < tableroActual.getNumFilas(); ++i) {
            for (int j = 0; j < tableroActual.getNumColumnas(); ++j) {
                if (j % tableroActual.getNumColumnas() == 0) {
                    System.out.println();
                }
                System.out.print(tablero[i][j] + " ");
            }
        }
        System.out.println();
        System.out.println();
    }

    /**
     * Indica si alguno de los jugadores ha hecho cuatro en raya. Devuelve 0 en
     * el caso de que no lo haya, 1 en el caso de que lo haya hecho el jugador
     * 1, y 2 en el caso de que lo haya hecho el jugador 2.
     */
    public int comprobarTablero() {
        int i = tableroActual.getNumFilas() - 1;
        int j;
        boolean encontrado = false;
        int jugador = 0;
        int casilla;
        while (!encontrado && i >= 0) {
            j = tableroActual.getNumColumnas() - 1;
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
                    if (i + 3 < tableroActual.getNumFilas()) {
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
                            if (j + 3 < tableroActual.getNumColumnas()) {
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
     * Indica si el tablero está lleno
     */
    public boolean tableroLleno() {
        boolean lleno = true;
        int i = 0;
        int j;

        while (lleno && i < tableroActual.getNumFilas()) {
            j = 0;
            while (lleno && j < tableroActual.getNumColumnas()) {
                if (tablero[i][j] == 0) {
                    lleno = false;
                } else {
                    j = j + 1;
                }
            }
            i = i + 1;
        }

        return lleno;
    }

    /**
     * Inicialización del agente donde se prepara la ontología y los jugadores,
     * además de inicializar algunas variables, llama a la función principal que
     * busca jugadores cuando haga falta.
     */
    @Override
    protected void setup() {

        ///Llamada a la interfaz
        myGui = new GuiLink(this);
        myGui.setTitle("Tablero: " + getAID().getLocalName());
        myGui.setVisible(true);
        myGui.clearTablero();
        myGui.jugadoresClear();
        try {
            ///Obtenemos la instancia de la ontología y registramos el lenguaje
            ///y la ontología para poder completar el contenido de los mensajes

            ontology = OntologiaCuatroEnRaya.getInstance();
        } catch (BeanOntologyException ex) {
            Logger.getLogger(AgenteTablero.class.getName()).log(Level.SEVERE, null, ex);
        }

        manager.registerLanguage(codec);
        manager.registerOntology(ontology);

        ///Inicializamos las fichas para los jugadores
        participantes[0] = new Jugador();
        participantes[0].setFicha(fichaRoja);

        participantes[1] = new Jugador();
        participantes[1].setFicha(fichaAzul);

        nPartida = 0;
        restartGame = true;
        addBehaviour(new ControlDeJuego(this, searchTime));
    }

    /**
     * Tarea que inicia todo el proceso de juego cuando es necesario. Empezando
     * por la búsqueda de jugadores, estos casos son cuando acaba la partida
     * satisfactoriamente, cuando no se han encontrado agentes suficientes,
     * cuando hay un error en la comunicación, cuando un agente tarda demasiado
     * en contestar, etc
     */
    class ControlDeJuego extends TickerBehaviour {

        public ControlDeJuego(Agent agent, long period) {
            super(agent, period);
        }

        @Override
        public void onTick() {
            if (restartGame == true) {
                addBehaviour(new BusquedaJugadores());
                restartGame = false;
            }
        }
    }

    /**
     * Tarea de un sólo disparo que busca agentes para jugar. En las páginas
     * amarillas de entre todos los que hayan, escoge 6 al azar
     */
    class BusquedaJugadores extends OneShotBehaviour {

        @Override
        public void action() {
            try {
                myGui.setGame(this.myAgent.getLocalName(), nPartida);
                ///Buscamos a los agentes participantes
                DFAgentDescription template = new DFAgentDescription();
                ServiceDescription templateSd = new ServiceDescription();
                templateSd.setType(OntologiaCuatroEnRaya.REGISTRO_JUGADOR);
                template.addServices(templateSd);

                results = DFService.search(this.myAgent, template);

                if (results.length > 1) {
                    ///Cogemos aleatoriamente a 6 agentes como máximo

                    ///Para ello barajamos el vector
                    Random r = new Random();
                    for (int primera = 0; primera < results.length; primera++) {
                        int segunda = r.nextInt(results.length);
                        DFAgentDescription temp = results[primera];
                        results[primera] = results[segunda];
                        results[segunda] = temp;
                    }

                    ///Creamos un vector auxiliar
                    int longitud;
                    if (results.length >= 6) {
                        longitud = 6;
                    } else {
                        longitud = results.length;
                    }

                    DFAgentDescription[] aux = new DFAgentDescription[longitud];
                    System.arraycopy(results, 0, aux, 0, longitud);

                    ///Ahora results tiene 6 agentes elegidos aleatoriamente entre todos los que había.
                    results = aux;


                    ///Añadimos los agentes como receptores del mensaje de propuesta
                    nResponders = results.length;
                    receptores.clear();
                    for (int i = 0; i < results.length; ++i) {
                        DFAgentDescription dfd = results[i];
                        receptores.add(dfd.getName());
                    }

                    /// Creamos el mensaje a enviar en el CFP
                    ACLMessage msg = new ACLMessage(ACLMessage.CFP);
                    Enumeration elm = receptores.elements();
                    while (elm.hasMoreElements()) {
                        msg.addReceiver((AID) elm.nextElement());
                    }

                    msg.setSender(getAID());
                    msg.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
                    /// Esperamos respuesta por 10seg.
                    msg.setReplyByDate(new Date(System.currentTimeMillis() + 10000));

                    ///Creamos el contenido del mensaje inicial
                    Tablero tab = new Tablero(6, 7); /// Tablero 6x7
                    tableroActual = tab;
                    jugadorJugando = null;
                    posicionAnterior = new Posicion(0, 0);
                    Juego juego = new Juego(OntologiaCuatroEnRaya.TIPO_JUEGO); ///Creamos el juego
                    Partida partida = new Partida("Partida de prueba", juego); ///Creamos la partida        
                    Jugar jugar = new Jugar(partida, tab); /// Creamos el cuerpo de la acción

                    ///Inicializamos el tablero personal del AgenteTablero para esta partida
                    tablero = new int[tab.getNumFilas()][tab.getNumColumnas()];
                    for (int i = 0; i < tab.getNumFilas(); ++i) {
                        for (int j = 0; j < tab.getNumColumnas(); ++j) {
                            tablero[i][j] = 0;
                        }
                    }

                    msg.setLanguage(codec.getName());
                    msg.setOntology(ontology.getName());

                    ///Creación de la acción Jugar que se enviará
                    Action a = new Action(getAID(), jugar);
                    manager.fillContent(msg, a);

                    ///Implementación del protocolo ContractNet
                    System.out.println("Empezamos con la prueba ......");
                    myGui.msg(this.myAgent.getLocalName(), "Tablero iniciado, esperando jugadores.");
                    addBehaviour(new IniciarJuego(this.myAgent, msg, partida));
                } else {
                    ///Si no hay suficientes jugadores, se reinicia el juego
                    restartGame = true;
                }
            } catch (Exception ex) {
            }
        }
    }

    /**
     * Tarea llamada cuando en la tarea BussquedaJugadores se encuentran más de
     * dos jugadores esta tarea inicia un protocolo ContractNet con los
     * jugadores encontrados, que pueden ser de 2 a 6.
     *
     * Esta tarea se llama directamente con el mensaje que va a enviar para
     * empezar la comunicación.
     *
     * @see BusquedaJugadores
     * @see ContractNetInitiator
     */
    class IniciarJuego extends ContractNetInitiator {

        private Partida partida; ///Partida que se está jugando

        /**
         * Contructor por defecto
         *
         * @param agent Agente que hace la llamada a esta función
         * @param message Mensaje inicial, que se enviará automaticamente al ser
         * llamado la tarea
         * @param partida La partida actual
         */
        public IniciarJuego(Agent agent, ACLMessage message, Partida partida) {
            super(agent, message);
            this.partida = partida;
            respuestas = 0;
            inicioJuego = 0;
        }

        /**
         * Función del ContractNetInitiator. maneja todos los propose que le
         * llegan y sólo guarda la referencia a los dos primeras respuestas de
         * petición que le llegan.
         */
        @Override
        protected void handlePropose(ACLMessage propose, Vector v) {

            if (respuestas < 2) {
                ///Los dos primeros en responder se almacenan para jugar
                participantes[respuestas].setJugador(propose.getSender());
                respuestas++;
            }
        }

        /**
         * Función del ContractNetInitiator, maneja todos los refuse que le
         * llegan.
         */
        @Override
        protected void handleRefuse(ACLMessage refuse) {
            //System.out.println("El agente " + refuse.getSender().getName() + " reusa");
            nResponders--;
        }

        /**
         * Manejador para cuando no se consigue enviar el mensaje
         * satisfactoriamente.
         */
        @Override
        protected void handleFailure(ACLMessage failure) {
            if (failure.getSender().equals(myAgent.getAMS())) {
                /// Fallo en el envío al no encontrar al agente participante
                System.out.println("No se ha encontrado el agente participante");
                myGui.msg(myAgent.getLocalName(), "No se ha encontrado el agente participante");
            } else {
                System.out.println("El agente " + failure.getSender().getName() + " falla");
                myGui.msg(myAgent.getLocalName(), "El agente " + failure.getSender().getName() + " falla");
            }
            ///Immediate failure --> we will not receive a response from this agent
            nResponders--;
        }

        /**
         * Manejador para cuando han llegado todas las respuestas.
         */
        @Override
        protected void handleAllResponses(Vector responses, Vector acceptances) {

            if (responses.size() < nResponders) {
                ///No todos agentes han contestado en el tiempo
                System.out.println("Timeout agotado: perdidos " + (nResponders - responses.size()) + " participantes");
                myGui.msg(myAgent.getLocalName(), "Timeout agotado: perdidos " + (nResponders - responses.size()) + " participantes");
            }


            ///Aquí separamos los que han aceptado con los que han rechazado
            Vector aceptados = new Vector();
            Vector rechazados = new Vector();
            Enumeration g = responses.elements();
            try {
                while (g.hasMoreElements()) {
                    ACLMessage m = (ACLMessage) g.nextElement();
                    if (m.getPerformative() != ACLMessage.REFUSE) {
                        aceptados.add(m);
                    } else {
                        rechazados.add(m);
                    }
                }
            } catch (Exception ex) {
            }
            System.out.println("Han aceptado " + aceptados.size() + " agentes.");
            System.out.println("Total: " + responses.size() + " aceptados: " + aceptados.size() + " están jugando: " + rechazados.size());
            if (aceptados.size() > 1) {
                myGui.msg(myAgent.getLocalName(), "Encontrados " + responses.size() + " agentes. Han aceptado " + aceptados.size() + " agentes y están jugando: " + rechazados.size());
            } else {
                myGui.msg(myAgent.getLocalName(), "Encontrados " + responses.size() + " agentes. Ha aceptado " + aceptados.size() + " agente y están jugando: " + rechazados.size());
            }
            ///Si 2 o más que han aceptado, podemos jugar, así que escogemos dos
            ///y a los demás los rechazamos.
            ///En el caso que no haya más de dos, rechazamos a todos.            
            if (aceptados.size() >= 2) {
                /// Seleccionamos a los dos primeros que hayan contestado
                int i = 0;
                int j = 1; ///Para localizar al oponente
                Enumeration e = aceptados.elements();
                while (e.hasMoreElements()) {
                    ACLMessage msg = (ACLMessage) e.nextElement();
                    ACLMessage reply = msg.createReply();

                    if ((i < 2)) {
                        try {
                            Action a = (Action) manager.extractContent(msg);
                            Jugar jugar = (Jugar) a.getAction();
                            jugar.setOponente(participantes[j]); ///Incluimos el oponente

                            a.setAction(jugar); ///La incluimos en la acción
                            manager.fillContent(reply, a); /// Y en el mensaja


                            reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                            acceptances.addElement(reply);
                            j--;

                            myGui.jugadoresStart(participantes[0], participantes[1]);
                        } catch (Exception ex) {
                        }

                    } else {
                        ///Rechazamos si ya tenemos a dos
                        reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
                        acceptances.addElement(reply);
                    }

                    i++;
                }
                myGui.msg(this.myAgent.getLocalName(), "Empezamos la partida.");
                nPartida++;
                myGui.setGame(this.myAgent.getLocalName(), nPartida);

            } else {

                ///Si no aceptan más de dos rechazamos a todos
                Enumeration e = aceptados.elements();
                while (e.hasMoreElements()) {
                    ACLMessage msg = (ACLMessage) e.nextElement();
                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
                    acceptances.addElement(reply);
                }
                System.out.println("No han aceptado suficientes agentes, reiniciando juego del tablero: " + this.myAgent.getLocalName());
                myGui.msg(myAgent.getLocalName(), "No han aceptado suficientes agentes, reiniciando juego del tablero: " + this.myAgent.getLocalName() + "\n");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                }

                restartGame = true;
            }
        }

        /**
         * Esta función se encarga de manejar los inform provenientes de los
         * jugadores después de haberles llegado los ACCEPT_PROPOSAL, cuando los
         * dos jugadores nos hayan respondido, llamamos al ManejadorMovimiento
         * que se encarga de eleguir a quien le toca empezar.
         */
        @Override
        protected void handleInform(ACLMessage inform) {

            ///Aquí es donde llega el Done, lo podemos usar para tener la información oficial que se va a usar en la partida.
            try {
                if (++inicioJuego == 2) {
                    addBehaviour(new ManejadorMovimiento(partida));
                }
            } catch (Exception ex) {
            }
        }

        /**
         * Manejador para cuando han llegado todos los Informs
         */
        @Override
        protected void handleAllResultNotifications(Vector resultNotifications) {
            System.out.println("Todas las notificaciones han sido recibidas");
        }
    }

    /**
     * Tarea de un disparo que se encarga de enviarle cada vez que es llamada al
     * agente jugador correspondiente un protocolo del tipo REQUEST para iniciar
     * una conversación del tipo AchieveREInitiator para poner el movimiento.
     *
     * Crea el cuerpo de la acción, teniendo en cuenta si es el primer
     * movimiento o ya ha habido uno.
     */
    class ManejadorMovimiento extends OneShotBehaviour {

        private Partida partida; ///Partida que se está jugando

        public ManejadorMovimiento(Partida partida) {
            this.partida = partida;
        }

        @Override
        public void action() {
            /// Creamos el mensaje a enviar en el REQUEST
            try {
                Ficha ficha;
                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                if (jugadorJugando == participantes[1]) {
                    msg.addReceiver(participantes[0].getJugador());
                    jugadorJugando = participantes[0];
                    ficha = new Ficha(OntologiaCuatroEnRaya.FICHA_AZUL);
                    //System.out.println("Le toca jugar al jugador rojo.");
                } else {
                    if (jugadorJugando == participantes[0]) {
                        msg.addReceiver(participantes[1].getJugador());
                        jugadorJugando = participantes[1];
                        ficha = new Ficha(OntologiaCuatroEnRaya.FICHA_ROJA);
                        //System.out.println("Le toca jugar al jugador azul.");
                    } else {
                        ///Este caso es el primer movimiento
                        ///Eleguir un jugador al azar para que inicie el juego
                        Random generator = new Random();
                        int pos = generator.nextInt(2);
                        jugadorJugando = participantes[pos];
                        msg.addReceiver(jugadorJugando.getJugador());
                        ficha = new Ficha(OntologiaCuatroEnRaya.LIBRE);
                    }
                }



                msg.setSender(getAID());
                msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
                /// Esperamos respuesta por 10seg.
                msg.setReplyByDate(new Date(System.currentTimeMillis() + 10000));

                ///Creamos el contenido del Action particular
                Posicion pos = posicionAnterior; /// Creamos la posición
                Movimiento mov = new Movimiento(ficha, pos); ///Creamos el movimiento
                PosicionarFicha posFicha = new PosicionarFicha(partida, mov); ///Creamos el cuerpo de la acción


                msg.setLanguage(codec.getName());
                msg.setOntology(ontology.getName());

                ///Creación de la acción PosicionarFicha que se enviará
                Action a = new Action(getAID(), posFicha);
                manager.fillContent(msg, a);

                ///Iniciamos el protocolo de comunicación con el Jugador
                addBehaviour(new MovimientoT(this.myAgent, msg, posFicha));

            } catch (Exception ex) {
            }
        }
    }

    class MovimientoT extends AchieveREInitiator {

        private PosicionarFicha partida; ///Partida que se está jugando

        public MovimientoT(Agent agent, ACLMessage message, PosicionarFicha partida) {
            super(agent, message);
            this.partida = partida;
        }

        /**
         * Manejador en caso de fallo, si es así, dejamos el juego en tablas.
         */
        @Override
        protected void handleFailure(ACLMessage failure) {
            if (failure.getSender().equals(myAgent.getAMS())) {
                /// FAILURE notification from the JADE runtime: the receiver does not exist         
                System.out.println("No existe el agente respuesta " + failure.getSender().getLocalName());
                myGui.msg(myAgent.getLocalName(), "No existe el agente respuesta " + failure.getSender().getLocalName());
            } else {
                System.out.println("Agente " + failure.getSender().getName() + " falló al realizar la tarea requerida.");
                myGui.msg(myAgent.getLocalName(), "Agente " + failure.getSender().getName() + " falló al realizar la tarea requerida.");
            }

            ///Dejamos el juego en tablas
            Jugador jugador = new Jugador(myAgent.getAID(), new Ficha(OntologiaCuatroEnRaya.LIBRE));
            addBehaviour(new GanadorJuego(myAgent, new Ganador(partida.getPartida(), jugador)));
        }

        /**
         * Manejador que recibe los Informs, el contenido del mismo es el
         * movimiento que ha realizado el jugador, comprobamos que el movimiento
         * sea correcto, si el tablero se ha llenado, para dejar el juego en
         * tablas y comprobar si ha ganado un jugador.
         *
         * Si no ha ganado ningún jugador, se llama de nuevo a
         * ManejadorMovimiento.
         */
        @Override
        protected void handleInform(ACLMessage inform) {

            ///Recibimos el predicado del Done contenido en el Inform
            try {
                MovimientoRealizado petMov;
                Done a = (Done) manager.extractContent(inform);
                Action b = (Action) a.getAction();
                petMov = (MovimientoRealizado) a.getCondition();

                ///Hacemos comprobaciones primero
                boolean comprobacion = false;
                ///Comprobamos si la posición es válida
                int posFila = petMov.getMovimiento().getPosicion().getFila();
                int posColumna = petMov.getMovimiento().getPosicion().getColumna();

                if (tablero[posFila][posColumna] != 0) {
                    comprobacion = true;
                }

                ///Comprobamos si el jugador es el indicado
                if (jugadorJugando == petMov.getJugador()) {
                    comprobacion = true;
                }

                ///Comprobamos si la ficha del jugador es correcta
                if (jugadorJugando.getFicha() == petMov.getMovimiento().getFicha()) {
                    comprobacion = true;
                }

                if (tableroActual.getNumFilas() < petMov.getMovimiento().getPosicion().getFila() && petMov.getMovimiento().getPosicion().getFila() < 0) {
                    comprobacion = true;
                }
                if (tableroActual.getNumFilas() < petMov.getMovimiento().getPosicion().getColumna() && petMov.getMovimiento().getPosicion().getColumna() < 0) {
                    comprobacion = true;
                }
                if (comprobacion == true) {
                    ///Lanzar un mensaje de ganador a los dos, empate, algo ha salido mal y reiniciar el juego.
                    System.out.println("Error.");
                    myGui.msg(myAgent.getLocalName(), "Error crítico en la partida, se reiniciará en breves.");
                    ///Dejamos el juego en tablas
                    Jugador jugador = new Jugador(myAgent.getAID(), new Ficha(OntologiaCuatroEnRaya.LIBRE));
                    addBehaviour(new GanadorJuego(myAgent, new Ganador(partida.getPartida(), jugador)));
                } else {
                    ///Hacemos definitivo el movimiento
                    tablero[posFila][posColumna] = petMov.getMovimiento().getFicha().getColor();
                    posicionAnterior = new Posicion(posFila, posColumna);
                    //System.out.println("No ha habido ningún error con la acción, el movimiento es correcto.");
                }
            } catch (Exception ex) {
            }

            ///Fin de las comprobaciones de los movimientos, podemos comprobarlo como está el estado del juego.

            int ganadorTablero = comprobarTablero();
            if (ganadorTablero != 0) {
                if (ganadorTablero == participantes[0].getFicha().getColor()) {
                    System.out.println("Ha ganado el jugador " + participantes[0].getJugador().getLocalName());
                    myGui.msg(myAgent.getLocalName(), "Ha ganado el jugador " + participantes[0].getJugador().getLocalName());
                    Ganador ganador = new Ganador(partida.getPartida(), participantes[0]);
                    addBehaviour(new GanadorJuego(myAgent, ganador));
                } else {
                    System.out.println("Ha ganado el jugador " + participantes[1].getJugador().getLocalName());
                    myGui.msg(myAgent.getLocalName(), "Ha ganado el jugador " + participantes[1].getJugador().getLocalName());
                    Ganador ganador = new Ganador(partida.getPartida(), participantes[1]);
                    addBehaviour(new GanadorJuego(myAgent, ganador));
                }
            } else {

                if (tableroLleno() == true) {
                    System.out.println("Tablero lleno, hemos terminado D:");
                    Jugador jugador = new Jugador(myAgent.getAID(), new Ficha(OntologiaCuatroEnRaya.LIBRE));
                    addBehaviour(new GanadorJuego(myAgent, new Ganador(partida.getPartida(), jugador)));
                } else {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                    }
                    ///Sino hay ganador, seguimos jugando
                    addBehaviour(new ManejadorMovimiento(partida.getPartida()));
                }
            }
          

            myGui.actualizarTablero(tablero);
            
        }

        /**
         * Manejador en caso de que el agente jugador refuse a hacer el
         * movimiento
         */
        @Override
        protected void handleRefuse(ACLMessage refuse) {
            ///Dejamos el juego en tablas
            myGui.msg(myAgent.getLocalName(), "El jugador " + refuse.getSender().getLocalName() + " ha rechazado mover, se reiniciará la partida.");
            Jugador jugador = new Jugador(this.myAgent.getAID(), new Ficha(0));
            addBehaviour(new GanadorJuego(myAgent, new Ganador(partida.getPartida(), jugador)));
            nResponders--;


        }
    }

    /**
     * Tarea de un sólo disparo que es llamada cuando un jugador ha ganado, el
     * juego ha quedado en tablas o ha habido un error.
     *
     * El mensaje es enviado del tipo SUBCRIBE.
     *
     * Una vez acabado, reiniciamos el juego.
     */
    class GanadorJuego extends OneShotBehaviour {

        Ganador ganador;

        public GanadorJuego(Agent a, Ganador ganador) {
            super(a);
            this.ganador = ganador;
        }

        @Override
        public void action() {
            try {
                System.out.println("\nGANADOR");

                /// Crear el mensaje
                ACLMessage msg = new ACLMessage(ACLMessage.SUBSCRIBE);

                msg.setSender(getAID());
                msg.addReceiver(participantes[0].getJugador());
                msg.addReceiver(participantes[1].getJugador());
                msg.setLanguage(codec.getName());
                msg.setOntology(ontology.getName());

                manager.fillContent(msg, ganador);

                if (ganador.getJugador().getFicha().getColor() == 0) {
                    System.out.println("El juego ha quedado en tablas.");
                    myGui.msg(myAgent.getLocalName(), "El juego ha quedado en tablas.");
                }

                send(msg);

                ///Dejamos mostrando el tablero unos 3 segundos
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                }

                ///Reiniciamos el juego
                myGui.clearTablero();
                restartGame = true;
            } catch (Exception e) {
            }
        }
    }

    /**
     * Tarea que es llamada cuando el agente termina.
     */
    @Override
    protected void takeDown() {
        System.out.println("\n" + getName() + " Terminadas las tareas ...");
    }
}
