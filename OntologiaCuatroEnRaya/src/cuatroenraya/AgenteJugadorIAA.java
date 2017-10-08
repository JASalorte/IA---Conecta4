/**
 * Agente jugador
 */
package cuatroenraya;

import cuatroenraya.elementos.Ganador;
import cuatroenraya.elementos.Jugador;
import cuatroenraya.elementos.Jugar;
import cuatroenraya.elementos.PosicionarFicha;
import cuatroenraya.elementos.Ficha;
import cuatroenraya.elementos.Movimiento;
import cuatroenraya.elementos.MovimientoRealizado;
import cuatroenraya.elementos.Posicion;
import cuatroenraya.elementos.Partida;
import cuatroenraya.elementos.Tablero;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.BeanOntologyException;
import jade.content.onto.Ontology;
import jade.content.onto.basic.Action;
import jade.content.onto.basic.Done;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;
import jade.proto.AchieveREResponder;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Agente Jugador que recibirá peticiones de juego de un Agente Tablero que esté
 * en el sistema y entablará una comunicación para jugar al juego de 4 en raya
 *
 * @author Jesús Alberto Salazar Ortega
 */
public class AgenteJugadorIAA extends Agent {

    private ContentManager manager = (ContentManager) getContentManager();
    /// El lenguaje utilizado por el agente para la comunicación es SL 
    private Codec codec = new SLCodec();
    /// La ontología que utilizará el agente
    private Ontology ontology;
    private Ficha fichaActual;
    private Tablero tableroActual;
    private Partida partidaActual;
    private Jugador oponenteActual;
    private Date ultimoRecibo;
    private boolean jugando;
    private int tablero[][];
    IAA inteligencia;

    /**
     * Función que muestra el tablero por consola
     */
    private void muestraTablero() {
        System.out.println();
        System.out.print("Estado actual del tablero en Jugador " + this.getLocalName());
        for (int i = 0; i < tableroActual.getNumFilas(); ++i) {
            for (int j = 0; j < tableroActual.getNumColumnas(); ++j) {
                if (j % tableroActual.getNumColumnas() == 0) {
                    System.out.println();
                }
                if (tablero[i][j] == 0) {
                    System.out.print("  ");
                } else if (tablero[i][j] == 1) {
                    System.out.print("X ");
                } else {
                    System.out.print("O ");
                }

                //System.out.print(tablero[i][j] + " ");
            }
        }
        System.out.println();
        System.out.println();
    }

    /**
     * Inicialización del agente donde se prepara la ontología y se registra en
     * las páguinas amarillas, lanza la Tarea de GanadorJuego para saber cuando
     * ha ganado, también lanza una tarea encargada de reiniciar el
     * AgenteJugador en caso de que esté mucho tiempo esperando respuesta.
     * Después de que todo esto este hecho, llama a la tarea IniciarJuego
     *
     */
    @Override
    protected void setup() {

        ///Obtenemos la instancia de la ontología y registramos el lenguaje
        ///y la ontología para poder completar el contenido de los mensajes
        try {
            ontology = OntologiaCuatroEnRaya.getInstance();
        } catch (BeanOntologyException ex) {
            Logger.getLogger(AgenteJugadorRandom.class.getName()).log(Level.SEVERE, null, ex);
        }

        manager.registerLanguage(codec);
        manager.registerOntology(ontology);

        System.out.println("El agente " + getName() + " esperando para CFP...");
        ///Registro del agente en las páginas amarillas
        try {
            DFAgentDescription dfd = new DFAgentDescription();
            dfd.setName(getAID());
            ServiceDescription sd = new ServiceDescription();
            sd.setName(getLocalName());
            sd.setType(OntologiaCuatroEnRaya.REGISTRO_JUGADOR);
            /// Agents that want to use this service need to "know" the weather-forecast-ontology
            sd.addOntologies(OntologiaCuatroEnRaya.ONTOLOGY_NAME);
            /// Agents that want to use this service need to "speak" the FIPA-SL language
            sd.addLanguages(FIPANames.ContentLanguage.FIPA_SL);
            dfd.addServices(sd);

            DFService.register(this, dfd);
        } catch (FIPAException fe) {
        }

        jugando = false;
        ultimoRecibo = new Date(System.currentTimeMillis());
        ///Tarea que nos informa el ganador
        addBehaviour(new GanadorJuego(this));
        addBehaviour(new Desconexion(this, 10000));

        ///Plantilla la exploración del mensaje para el protocolo ContractNet
        MessageTemplate template = MessageTemplate.and(
                MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET),
                MessageTemplate.MatchPerformative(ACLMessage.CFP));
        addBehaviour(new IniciarJuego(this, template));
    }

    /**
     * Tarea llama con un template, que hace que reciba sólo mensaje del tipo
     * CFP a través del protocolo FIPA, recibe este tipo de mensajes de un
     * agenteTablero que quiera proponer a los jugadores de las páguinas
     * amarillas jugar.
     *
     * @see ContratNetResponder
     */
    class IniciarJuego extends ContractNetResponder {

        /**
         * Contructor por defecto
         *
         * @param agent Agente que hace la llamada a esta función
         * @param template Define los mensaje que recibirá el
         * ContractNetResponder
         */
        public IniciarJuego(Agent agent, MessageTemplate template) {
            super(agent, template);
        }

        /**
         * Manejador de los mensaje de tipo cfp, los cuales aceptará sino está
         * jugando y rechazará si lo está haciendo.
         *
         * @param cfp mensaje llegado del tablero
         * @return devuelve la proposición al tablero que le ha enviado el cfp
         * @throws NotUnderstoodException
         * @throws RefuseException
         */
        @Override
        protected ACLMessage handleCfp(ACLMessage cfp) throws NotUnderstoodException, RefuseException {

            ///Creamos un mensaje de respuesta al mismo tablero que nos ha enviado la información.
            ACLMessage propose = cfp.createReply();

            try {
                ///Desempaquetamos la acción
                Action a = (Action) manager.extractContent(cfp);
                Jugar jugar = (Jugar) a.getAction();
                Jugador jugador = new Jugador();
                jugador.setJugador(propose.getSender());
                jugar.setOponente(jugador);

                ///Rellenamos el campo oponente con el agente
                ///que acepta la propuesta de juego
                a.setAction(jugar); ///La incluimos en la acción
                manager.fillContent(propose, a); /// Y en el mensaje

            } catch (Exception ex) {
            }
            if (jugando != true) {
                ///Aceptamos si no estamos jugando.
                ultimoRecibo = new Date(System.currentTimeMillis() + 10000);
                propose.setPerformative(ACLMessage.PROPOSE);
                return propose;
            } else {
                ///Si estamos jugando, rechazamos
                propose.setPerformative(ACLMessage.REFUSE);
                return propose;
            }
        }

        /**
         * Si aceptamos el cfp, esperamos a que el tablero acepte nuestra
         * proposición, esta función maneja los mensajes cuando el tablero ha
         * aceptado.
         *
         * Podemos considerar que ya estamos jugando.
         *
         * Cuando termine llamamos al manejador de movimientos.
         *
         * @param cfp
         * @param propose
         * @param accept Mensaje con el contenido de la acción con la que se va
         * a jugar
         * @return devuelve la proposición al tablero que le ha enviado el cfp
         * @throws FailureException
         */
        @Override
        protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose, ACLMessage accept) throws FailureException {

            ///Consideramos que ya estamos jugando y no nos pueden volver a invitar.
            jugando = true;

            ///Creamos un mensaje de respuesta, para informar al tablero que vamos a jugar.
            ACLMessage inform = accept.createReply();

            try {
                ///Inicializamos las variables para esta partida
                Action a = (Action) manager.extractContent(accept);
                Jugar jugar = (Jugar) a.getAction();
                ultimoRecibo = new Date(System.currentTimeMillis() + 10000);
                ///Iniciamos todo lo perteneciente a este juego
                fichaActual = jugar.getOponente().getFicha().getColor() == OntologiaCuatroEnRaya.FICHA_AZUL ? new Ficha(OntologiaCuatroEnRaya.FICHA_ROJA) : new Ficha(OntologiaCuatroEnRaya.FICHA_AZUL);
                tableroActual = jugar.getTablero();
                partidaActual = jugar.getPartida();
                oponenteActual = jugar.getOponente();
                inteligencia = new IAA();
                tablero = new int[tableroActual.getNumFilas()][tableroActual.getNumColumnas()];
                for (int i = 0; i < tableroActual.getNumFilas(); ++i) {
                    for (int j = 0; j < tableroActual.getNumColumnas(); ++j) {
                        tablero[i][j] = 0;
                    }
                }

                ///Informamos que estamos conformes en realizar la acción
                Done d = new Done(jugar);
                manager.fillContent(inform, d);

                MessageTemplate template = MessageTemplate.and(
                        MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST),
                        MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
                addBehaviour(new MovimientoJ(this.myAgent, template));

            } catch (Exception ex) {
            }
            inform.setPerformative(ACLMessage.INFORM);
            //System.out.println(inform);

            return inform;
        }

        /**
         * En caso de que nuestra proposición de juego sea rechazada, el tablero
         * enviará un mensaje que recogerá esta función
         *
         * @param cfp
         * @param propose
         * @param reject
         */
        @Override
        protected void handleRejectProposal(ACLMessage cfp, ACLMessage propose, ACLMessage reject) {
            ///Consideramos que no estamos jugando y aceptamos nuevas proposiciones.
            jugando = false;

        }
    }

    /**
     * Una vez que el CFP ha sido entrablado satisfactoriamente, se llama a esta
     * tarea, se encarga de manejar las peticiones de movimiento que enviará el
     * agente Tablero con el que hemos entrablado el CFP, para llevar el juego.
     *
     * Recibe los mensajes de tipo REQUEST del protocolo FIPA.
     */
    class MovimientoJ extends AchieveREResponder {

        /**
         * Contructor por defecto
         *
         * @param agent Agente que llamó a esta función
         * @param template Template de los tipos de mensaje que recibirá esta
         * tarea
         */
        public MovimientoJ(Agent agent, MessageTemplate template) {
            super(agent, template);
        }

        /**
         * Se encarga de recibir la petición, ver si es correcta, y si es así,
         * informar de que la va a hacer.
         *
         * @param request mensaje del tablero que envia una petición de
         * movimiento y datos sobre el último movimiento
         * @return Devuelve la respuesta para el tablero
         * @throws NotUnderstoodException
         * @throws RefuseException
         */
        @Override
        protected ACLMessage handleRequest(ACLMessage request) throws NotUnderstoodException, RefuseException {
            //System.out.println("Agent " + getLocalName() + ": REQUEST received from " + request.getSender().getName() + ". Action is " + request.getContent());
            PosicionarFicha petMov = null;

            try {
                Action a = (Action) manager.extractContent(request);
                petMov = (PosicionarFicha) a.getAction();
                ultimoRecibo = new Date(System.currentTimeMillis() + 10000);
            } catch (Exception ex) {
            }

            if (checkAction(petMov)) {
                /// We agree to perform the action. Note that in the FIPA-Request
                /// protocol the AGREE message is optional. Return null if you
                /// don't want to send it.
                ACLMessage agree = request.createReply();
                agree.setPerformative(ACLMessage.AGREE);
                return agree;
            } else {
                /// We refuse to perform the action, it is not a valid action
                ACLMessage refuse = request.createReply();
                refuse.setPerformative(ACLMessage.REFUSE);
                return refuse;
            }
        }

        /**
         * Función para comprobar si el movimiento enviado por el tablero es
         * correcto
         *
         * @param petMov Contiene los datos del movimiento anterior
         * @return true si el movimiento parece legítimo.
         */
        private boolean checkAction(PosicionarFicha petMov) {
            int fila = petMov.getAnterior().getPosicion().getFila();
            int columna = petMov.getAnterior().getPosicion().getColumna();

            if (petMov.getPartida().getId().equals(partidaActual.getId())
                    && ((fila >= 0) && (fila <= tableroActual.getNumFilas()))
                    && ((columna >= 0) && (columna <= tableroActual.getNumColumnas()))
                    && (oponenteActual.getFicha() != fichaActual)
                    && (tablero[fila][columna] == 0)) {
                return true;
            } else {
                return false;
            }
        }

        /**
         * Función que crea posiciona la ficha en el tablero y se la envía al
         * tablero.
         *
         * Aquí reside la inteligencia del jugador
         *
         * @param request
         * @param response
         * @return Devuelve el mensaje al Tablero
         * @throws FailureException
         */
        @Override
        protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) throws FailureException {
            ///Desempaquetamos la acción
            PosicionarFicha petMov = null;
            try {
                Action a = (Action) manager.extractContent(request);
                petMov = (PosicionarFicha) a.getAction();
                ultimoRecibo = new Date(System.currentTimeMillis() + 10000);
            } catch (Exception ex) {
            }

            ///Colocamos la ficha anterior en el tablero
            int fila = petMov.getAnterior().getPosicion().getFila();
            int columna = petMov.getAnterior().getPosicion().getColumna();
            tablero[fila][columna] = petMov.getAnterior().getFicha().getColor();

            //muestraTablero();

            /**
             * A partir de aquí empieza la IA para colocar la ficha en el tablero.
             */

            ///Inicio de inteligencia 
            int tama = tableroActual.getNumFilas() * tableroActual.getNumColumnas();
            int[] tableroInteligente = new int[tama];
            int indice = 0;

            for (int i = 0; i < tableroActual.getNumFilas(); i++) {
                for (int j = 0; j < tableroActual.getNumColumnas(); j++) {
                    tableroInteligente[indice++] = tablero[i][j];
                }
            }


            ///Movimiento Inteligente
            int pos = inteligencia.movimiento(tableroInteligente, tableroActual.getNumFilas(), tableroActual.getNumColumnas(), fichaActual.getColor());
            int pos1 = (int) (pos / tableroActual.getNumColumnas());
            int pos2 = pos % (tableroActual.getNumFilas() + 1);

           
            tablero[pos1][pos2] = fichaActual.getColor();

            /**
             * Aquí acaba el algoritmo IA
             */

            ///Creamos el cuerpo de la acción
            Jugador jugador = new Jugador(this.myAgent.getAID(), fichaActual);
            Posicion posicion = new Posicion(pos1, pos2);
            Movimiento movimiento = new Movimiento(fichaActual, posicion);
            MovimientoRealizado mov = new MovimientoRealizado(jugador, movimiento);

                ///Enviamos el mensaje si había alguna posición disponible, sino había, el tablero no nos envió un tablero válido.
                System.out.println("El agente " + getLocalName() + " le envía la acción al tablero: " + request.getSender().getLocalName());
                ACLMessage inform = request.createReply();
                try {
                    Action act = new Action(getAID(), petMov);
                    Done d = new Done(act);
                    d.setCondition(mov);
                    manager.fillContent(inform, d);
                } catch (Exception ex) {
                }
                inform.setPerformative(ACLMessage.INFORM);
                return inform;
            
        }
    }

    /**
     * Tarea cíclica que recibe mensajes de tipo SUBSCRIBE del tablero para
     * saber quien ha ganado la partida actual y el agente deja de jugar.
     */
    class GanadorJuego extends CyclicBehaviour {

        public GanadorJuego(Agent a) {
            super(a);
        }

        @Override
        public void action() {
            ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.SUBSCRIBE));
            if (msg != null) {
                try {
                    Ganador ganador = (Ganador) manager.extractContent(msg);

                    if (ganador.getJugador().getFicha().getColor()!=0) {
                        System.out.println("El juego ha quedado en tablas, el tablero se ha llenado y no hay ganador");
                    } else {
                        System.out.println("El jugador ganador es: " + ganador.getJugador().getJugador().getLocalName());
                    }
                    ///Hemos terminado de jugar
                    jugando = false;

                } catch (Exception e) {
                }
            } else {
                block();
            }
        }
    }

    /**
     * Tarea que comprueba cuando fue mandado el último mensaje, si lleva mucho
     * tiempo sin contestar el tablero, decidimos que el tablero se ha caido y
     * dejamos de jugar.
     */
    class Desconexion extends TickerBehaviour {

        public Desconexion(Agent agent, long period) {
            super(agent, period);
        }

        @Override
        protected void onTick() {
            Date currentDate = new Date(System.currentTimeMillis());
            if (currentDate.compareTo(ultimoRecibo) >= 0) {
                jugando = false;
            }
        }
    }

    /**
     * Tarea que es llamada cuando el agente termina, se desregistra de las
     * páguinas amarillas.
     */
    @Override
    protected void takeDown() {
        System.out.println("\n" + getName() + " Terminadas las tareas ...");
        try {
            DFService.deregister(this);
        } ///Eliminar el registro de las páginas amarillas
        catch (Exception e) {
        }
    }
}
