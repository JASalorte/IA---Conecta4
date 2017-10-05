package cuatroenraya;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import cuatroenraya.elementos.Jugador;
import java.awt.Font;

/**
 *
 * @author Jesús Alberto Salazar Ortega
 * @author David Abad Vich
 * @author Jesús Alejandro Benitez Pedrero
 *
 *
 * Interfaz que se va a usar para mostrar el tablero, los mensajes y los
 * jugadores.
 *
 */
public class GuiLink extends javax.swing.JFrame {

    /**
     * Constructor por defecto
     */
    public GuiLink() {
        initComponents();
        inicio();
        v = new javax.swing.JButton[42];
        v[0] = J1;
        v[1] = J2;
        v[2] = J3;
        v[3] = J4;
        v[4] = J5;
        v[5] = J6;
        v[6] = J7;
        v[7] = J8;
        v[8] = J9;
        v[9] = J10;
        v[10] = J11;
        v[11] = J12;
        v[12] = J13;
        v[13] = J14;
        v[14] = J15;
        v[15] = J16;
        v[16] = J17;
        v[17] = J18;
        v[18] = J19;
        v[19] = J20;
        v[20] = J21;
        v[21] = J22;
        v[22] = J23;
        v[23] = J24;
        v[24] = J25;
        v[25] = J26;
        v[26] = J27;
        v[27] = J28;
        v[28] = J29;
        v[29] = J30;
        v[30] = J31;
        v[31] = J32;
        v[32] = J33;
        v[33] = J34;
        v[34] = J35;
        v[35] = J36;
        v[36] = J37;
        v[37] = J38;
        v[38] = J39;
        v[39] = J40;
        v[40] = J41;
        v[41] = J42;
        int index = 0;
        for (int i = 0; i < 6; ++i) {
            for (int j = 0; j < 7; ++j) {
                v[index++].setIcon(null);
            }
        }
    }

    /**
     * Constructor con referencia a su propio tablero.
     */
    public GuiLink(AgenteTablero a) {
        initComponents();
        inicio();
        myBoard = a;
        v = new javax.swing.JButton[42];
        v[0] = J1;
        v[1] = J2;
        v[2] = J3;
        v[3] = J4;
        v[4] = J5;
        v[5] = J6;
        v[6] = J7;
        v[7] = J8;
        v[8] = J9;
        v[9] = J10;
        v[10] = J11;
        v[11] = J12;
        v[12] = J13;
        v[13] = J14;
        v[14] = J15;
        v[15] = J16;
        v[16] = J17;
        v[17] = J18;
        v[18] = J19;
        v[19] = J20;
        v[20] = J21;
        v[21] = J22;
        v[22] = J23;
        v[23] = J24;
        v[24] = J25;
        v[25] = J26;
        v[26] = J27;
        v[27] = J28;
        v[28] = J29;
        v[29] = J30;
        v[30] = J31;
        v[31] = J32;
        v[32] = J33;
        v[33] = J34;
        v[34] = J35;
        v[35] = J36;
        v[36] = J37;
        v[37] = J38;
        v[38] = J39;
        v[39] = J40;
        v[40] = J41;
        v[41] = J42;
   
    }

    /**
     * Función que muestra los mensajes de la consola
     */
    void msg(String name, String ms) {
        if (tx1.getText().equals("")) {
            tx1.setText(ms);
        } else {
            tx1.setText(ms + "\n" + tx1.getText());
        }
    }

    /**
     * Función que actualiza las fichas del tablero
     */
    void actualizarTablero(int[][] tablero) {
        int index = 0;
        for (int i = 0; i < 6; ++i) {
            for (int j = 0; j < 7; ++j) {
                if (tablero[i][j] == 0) {
//                    System.out.println();

                    v[index].setIcon(null);
                }
                if (tablero[i][j] == 1) {
//                    System.out.println();
                    v[index].setIcon(new ImageIcon("src/cuatroenraya/imagenes/x.png"));
                }
                if (tablero[i][j] == 2) {
                    v[index].setIcon(new ImageIcon("src/cuatroenraya/imagenes/cir.png"));
                }
                index++;
//                System.out.print(tablero[i][j] + " ");
            }
        }

    }

    /**
     * Limpia el tablero, inicializándolo
     */
    void clearTablero() {
        int index = 0;
        for (int i = 0; i < 6; ++i) {
            for (int j = 0; j < 7; ++j) {
                v[index++].setIcon(null);
            }
        }
        msg(null, "");
    }

    /**
     * Inicia los nombres de los jugadores y el icono de su ficha
     */
    void jugadoresStart(Jugador j1, Jugador j2) {
        LPlayer1.setText("Jugador " + j1.getJugador().getLocalName());
        LPlayer2.setText("Jugador " +j2.getJugador().getLocalName());
        if (j1.getFicha().getColor() == 1) {
            Player1.setIcon(new ImageIcon("src/cuatroenraya/imagenes/x.png"));
            Player2.setIcon(new ImageIcon("src/cuatroenraya/imagenes/cir.png"));
        } else {
            Player2.setIcon(new ImageIcon("src/cuatroenraya/imagenes/x.png"));
            Player1.setIcon(new ImageIcon("src/cuatroenraya/imagenes/cir.png"));
        }
    }

    /**
     * Limpia los jugadores, para cuando acaba la partida
     */
    void jugadoresClear() {
        LPlayer1.setText("Sin jugador");
        LPlayer2.setText("Sin jugador");
    }

    /**
     * Función de inicio para asignar los colores
     */
    void inicio() {
        Color color1 = Color.BLACK;
        Color color2 = Color.GREEN;
        Color color3 = Color.GREEN;
        /*
         * Labels
         */
        tx1.setForeground(color3);
        jLabel1.setForeground(color3);
        LPlayer1.setForeground(color3);
        LPlayer2.setForeground(color3);
        tx1.setFont(new Font("Tahoma", Font.BOLD, 16));


        /*
         * Fondos
         */
        jPanelFondo.setBackground(color1);
        jPanelTablero.setBackground(color1);
        J1.setBackground(color1);
        J2.setBackground(color1);
        J3.setBackground(color1);
        J4.setBackground(color1);
        J5.setBackground(color1);
        J6.setBackground(color1);
        J7.setBackground(color1);
        J8.setBackground(color1);
        J9.setBackground(color1);
        J10.setBackground(color1);
        J11.setBackground(color1);
        J12.setBackground(color1);
        J13.setBackground(color1);
        J14.setBackground(color1);
        J15.setBackground(color1);
        J16.setBackground(color1);
        J17.setBackground(color1);
        J18.setBackground(color1);
        J19.setBackground(color1);
        J20.setBackground(color1);
        J21.setBackground(color1);
        J22.setBackground(color1);
        J23.setBackground(color1);
        J24.setBackground(color1);
        J25.setBackground(color1);
        J26.setBackground(color1);
        J27.setBackground(color1);
        J28.setBackground(color1);
        J29.setBackground(color1);
        J30.setBackground(color1);
        J31.setBackground(color1);
        J32.setBackground(color1);
        J33.setBackground(color1);
        J34.setBackground(color1);
        J35.setBackground(color1);
        J36.setBackground(color1);
        J37.setBackground(color1);
        J38.setBackground(color1);
        J39.setBackground(color1);
        J40.setBackground(color1);
        J41.setBackground(color1);
        J42.setBackground(color1);
        Player1.setBackground(color1);
        Player2.setBackground(color1);

        /*
         * Bordes
         */

        jPanelTablero.setBorder(javax.swing.BorderFactory.createLineBorder(color2, 2));
       

        /*
         * Consola
         */
        tx1.setBackground(color1);
        tx1.setBorder(javax.swing.BorderFactory.createLineBorder(color2, 2));

    }

    /**
     * Función que actualiza los datos de estas dos variables, usadas cuando se
     * va a cerrar el tablero.
     *
     * @param b El nombre del tablero
     * @param ID El número de partidas jugadas
     */
    void setGame(String b, int ID) {
        board = b;
        IDgame = ID;
    }

    /*void takeDownBoard(String board, String ID) {
     JOptionPane.showMessageDialog(this, "Tablero " + board + " ha terminado con " + ID + " partidas realizadas.", "Salir", JOptionPane.OK_OPTION);
     this.setVisible(false);
     this.dispose();
     }*/
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jPanel1 = new javax.swing.JPanel();
        jPanelFondo = new javax.swing.JPanel();
        jPanelTablero = new javax.swing.JPanel();
        J23 = new javax.swing.JButton();
        J37 = new javax.swing.JButton();
        J7 = new javax.swing.JButton();
        J14 = new javax.swing.JButton();
        J31 = new javax.swing.JButton();
        J6 = new javax.swing.JButton();
        J22 = new javax.swing.JButton();
        J20 = new javax.swing.JButton();
        J33 = new javax.swing.JButton();
        J5 = new javax.swing.JButton();
        J32 = new javax.swing.JButton();
        J19 = new javax.swing.JButton();
        J13 = new javax.swing.JButton();
        J39 = new javax.swing.JButton();
        J4 = new javax.swing.JButton();
        J34 = new javax.swing.JButton();
        J25 = new javax.swing.JButton();
        J41 = new javax.swing.JButton();
        J27 = new javax.swing.JButton();
        J12 = new javax.swing.JButton();
        J11 = new javax.swing.JButton();
        J21 = new javax.swing.JButton();
        J15 = new javax.swing.JButton();
        J24 = new javax.swing.JButton();
        J3 = new javax.swing.JButton();
        J42 = new javax.swing.JButton();
        J9 = new javax.swing.JButton();
        J36 = new javax.swing.JButton();
        J8 = new javax.swing.JButton();
        J35 = new javax.swing.JButton();
        J10 = new javax.swing.JButton();
        J28 = new javax.swing.JButton();
        J40 = new javax.swing.JButton();
        J16 = new javax.swing.JButton();
        J26 = new javax.swing.JButton();
        J2 = new javax.swing.JButton();
        J17 = new javax.swing.JButton();
        J1 = new javax.swing.JButton();
        J18 = new javax.swing.JButton();
        J30 = new javax.swing.JButton();
        J38 = new javax.swing.JButton();
        J29 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tx1 = new javax.swing.JTextArea();
        LaberReinicio = new javax.swing.JLabel();
        Player2 = new javax.swing.JButton();
        LPlayer2 = new javax.swing.JLabel();
        Player1 = new javax.swing.JButton();
        LPlayer1 = new javax.swing.JLabel();

        jScrollPane2.setViewportView(jEditorPane1);

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("4 en Raya ");
        setBackground(new java.awt.Color(0, 0, 255));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                MensajeSalida(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                Cierre(evt);
            }
        });

        jPanelFondo.setBackground(new java.awt.Color(255, 255, 255));

        jPanelTablero.setBackground(new java.awt.Color(255, 255, 255));
        jPanelTablero.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        J23.setBackground(new java.awt.Color(255, 255, 255));
        J23.setBorderPainted(false);

        J37.setBackground(new java.awt.Color(255, 255, 255));
        J37.setBorderPainted(false);

        J7.setBackground(new java.awt.Color(255, 255, 255));
        J7.setBorderPainted(false);

        J14.setBackground(new java.awt.Color(255, 255, 255));
        J14.setBorderPainted(false);

        J31.setBackground(new java.awt.Color(255, 255, 255));
        J31.setBorderPainted(false);

        J6.setBackground(new java.awt.Color(255, 255, 255));
        J6.setBorderPainted(false);

        J22.setBackground(new java.awt.Color(255, 255, 255));
        J22.setBorderPainted(false);

        J20.setBackground(new java.awt.Color(255, 255, 255));
        J20.setBorderPainted(false);

        J33.setBackground(new java.awt.Color(255, 255, 255));
        J33.setBorderPainted(false);

        J5.setBackground(new java.awt.Color(255, 255, 255));
        J5.setBorderPainted(false);

        J32.setBackground(new java.awt.Color(255, 255, 255));
        J32.setBorderPainted(false);

        J19.setBackground(new java.awt.Color(255, 255, 255));
        J19.setBorderPainted(false);

        J13.setBackground(new java.awt.Color(255, 255, 255));
        J13.setBorderPainted(false);

        J39.setBackground(new java.awt.Color(255, 255, 255));
        J39.setBorderPainted(false);

        J4.setBackground(new java.awt.Color(255, 255, 255));
        J4.setBorderPainted(false);

        J34.setBackground(new java.awt.Color(255, 255, 255));
        J34.setBorderPainted(false);

        J25.setBackground(new java.awt.Color(255, 255, 255));
        J25.setBorderPainted(false);

        J41.setBackground(new java.awt.Color(255, 255, 255));
        J41.setBorderPainted(false);

        J27.setBackground(new java.awt.Color(255, 255, 255));
        J27.setBorderPainted(false);

        J12.setBackground(new java.awt.Color(255, 255, 255));
        J12.setBorderPainted(false);

        J11.setBackground(new java.awt.Color(255, 255, 255));
        J11.setBorderPainted(false);

        J21.setBackground(new java.awt.Color(255, 255, 255));
        J21.setBorderPainted(false);

        J15.setBackground(new java.awt.Color(255, 255, 255));
        J15.setBorderPainted(false);

        J24.setBackground(new java.awt.Color(255, 255, 255));
        J24.setBorderPainted(false);

        J3.setBackground(new java.awt.Color(255, 255, 255));
        J3.setBorderPainted(false);

        J42.setBackground(new java.awt.Color(255, 255, 255));
        J42.setBorderPainted(false);

        J9.setBackground(new java.awt.Color(255, 255, 255));
        J9.setBorderPainted(false);

        J36.setBackground(new java.awt.Color(255, 255, 255));
        J36.setBorderPainted(false);

        J8.setBackground(new java.awt.Color(255, 255, 255));
        J8.setBorderPainted(false);

        J35.setBackground(new java.awt.Color(255, 255, 255));
        J35.setBorderPainted(false);

        J10.setBackground(new java.awt.Color(255, 255, 255));
        J10.setBorderPainted(false);

        J28.setBackground(new java.awt.Color(255, 255, 255));
        J28.setBorderPainted(false);

        J40.setBackground(new java.awt.Color(255, 255, 255));
        J40.setBorderPainted(false);

        J16.setBackground(new java.awt.Color(255, 255, 255));
        J16.setBorderPainted(false);

        J26.setBackground(new java.awt.Color(255, 255, 255));
        J26.setBorderPainted(false);

        J2.setBackground(new java.awt.Color(255, 255, 255));
        J2.setBorder(null);

        J17.setBackground(new java.awt.Color(255, 255, 255));
        J17.setBorderPainted(false);

        J1.setBackground(new java.awt.Color(255, 255, 255));
        J1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        J1.setBorderPainted(false);

        J18.setBackground(new java.awt.Color(255, 255, 255));
        J18.setBorderPainted(false);

        J30.setBackground(new java.awt.Color(255, 255, 255));
        J30.setBorderPainted(false);

        J38.setBackground(new java.awt.Color(255, 255, 255));
        J38.setBorderPainted(false);

        J29.setBackground(new java.awt.Color(255, 255, 255));
        J29.setBorderPainted(false);

        org.jdesktop.layout.GroupLayout jPanelTableroLayout = new org.jdesktop.layout.GroupLayout(jPanelTablero);
        jPanelTablero.setLayout(jPanelTableroLayout);
        jPanelTableroLayout.setHorizontalGroup(
            jPanelTableroLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelTableroLayout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jPanelTableroLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanelTableroLayout.createSequentialGroup()
                        .add(jPanelTableroLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanelTableroLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                .add(J29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(J36, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, J22, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanelTableroLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanelTableroLayout.createSequentialGroup()
                                .add(jPanelTableroLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(J30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(J37, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jPanelTableroLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jPanelTableroLayout.createSequentialGroup()
                                        .add(J38, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(J39, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(J40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                    .add(jPanelTableroLayout.createSequentialGroup()
                                        .add(J31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(J32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(J33, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jPanelTableroLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jPanelTableroLayout.createSequentialGroup()
                                        .add(J34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(J35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                    .add(jPanelTableroLayout.createSequentialGroup()
                                        .add(J41, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(J42, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                            .add(jPanelTableroLayout.createSequentialGroup()
                                .add(J23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(J24, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(J25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(J26, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(J27, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(J28, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                    .add(jPanelTableroLayout.createSequentialGroup()
                        .add(jPanelTableroLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(J8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(J15, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(J1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanelTableroLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanelTableroLayout.createSequentialGroup()
                                .add(J16, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(J17, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(J18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(J19, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(J20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(J21, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jPanelTableroLayout.createSequentialGroup()
                                .add(J9, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(J10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(J11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(J12, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(J13, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(J14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jPanelTableroLayout.createSequentialGroup()
                                .add(J2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(J3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(J4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(J5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(J6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(J7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanelTableroLayout.setVerticalGroup(
            jPanelTableroLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelTableroLayout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jPanelTableroLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanelTableroLayout.createSequentialGroup()
                        .add(jPanelTableroLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(J6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(J7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanelTableroLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(J13, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(J14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(jPanelTableroLayout.createSequentialGroup()
                        .add(jPanelTableroLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanelTableroLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(J3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelTableroLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                    .add(J2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(org.jdesktop.layout.GroupLayout.LEADING, J4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                            .add(J5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanelTableroLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(J12, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(J11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(J10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(jPanelTableroLayout.createSequentialGroup()
                        .add(J1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanelTableroLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(J9, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(J8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelTableroLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(J16, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(J15, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(J17, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(J18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(J19, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(J20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(J21, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelTableroLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanelTableroLayout.createSequentialGroup()
                        .add(J22, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(J29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanelTableroLayout.createSequentialGroup()
                        .add(jPanelTableroLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(J23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(J24, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(J25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(6, 6, 6)
                        .add(jPanelTableroLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(J31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(J30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(J32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(J33, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(J34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(J35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(J26, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(J28, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(J27, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelTableroLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(J40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(J38, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(J37, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(J36, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(J39, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(J41, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(J42, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 51, 0));
        jLabel1.setText("4 en Raya");

        tx1.setEditable(false);
        tx1.setColumns(20);
        tx1.setRows(5);
        jScrollPane1.setViewportView(tx1);

        LaberReinicio.setForeground(new java.awt.Color(255, 0, 51));

        Player2.setBackground(new java.awt.Color(255, 255, 255));
        Player2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cuatroenraya/imagenes/x.png"))); // NOI18N
        Player2.setBorderPainted(false);

        LPlayer2.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        LPlayer2.setForeground(new java.awt.Color(204, 51, 0));
        LPlayer2.setText("Sin jugador");
        LPlayer2.setMaximumSize(new java.awt.Dimension(82, 17));

        Player1.setBackground(new java.awt.Color(255, 255, 255));
        Player1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cuatroenraya/imagenes/cir.png"))); // NOI18N
        Player1.setBorderPainted(false);

        LPlayer1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        LPlayer1.setForeground(new java.awt.Color(204, 51, 0));
        LPlayer1.setText("Sin jugador");
        LPlayer1.setMaximumSize(new java.awt.Dimension(82, 17));

        org.jdesktop.layout.GroupLayout jPanelFondoLayout = new org.jdesktop.layout.GroupLayout(jPanelFondo);
        jPanelFondo.setLayout(jPanelFondoLayout);
        jPanelFondoLayout.setHorizontalGroup(
            jPanelFondoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelFondoLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelFondoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanelFondoLayout.createSequentialGroup()
                        .add(jPanelFondoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanelFondoLayout.createSequentialGroup()
                                .add(LPlayer1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(139, 139, 139))
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelFondoLayout.createSequentialGroup()
                                .add(Player1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(46, 46, 46)))
                        .add(jPanelTablero, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jPanelFondoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanelFondoLayout.createSequentialGroup()
                                .add(31, 31, 31)
                                .add(LPlayer2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jPanelFondoLayout.createSequentialGroup()
                                .add(146, 146, 146)
                                .add(Player2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 974, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(LaberReinicio, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 114, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .add(jPanelFondoLayout.createSequentialGroup()
                .add(455, 455, 455)
                .add(jLabel1)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelFondoLayout.setVerticalGroup(
            jPanelFondoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelFondoLayout.createSequentialGroup()
                .add(31, 31, 31)
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanelFondoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanelFondoLayout.createSequentialGroup()
                        .add(LPlayer1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(10, 10, 10)
                        .add(Player1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanelTablero, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanelFondoLayout.createSequentialGroup()
                        .add(LPlayer2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(26, 26, 26)
                        .add(Player2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(jPanelFondoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanelFondoLayout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(LaberReinicio)
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelFondoLayout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 155, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jPanelFondo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 999, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 10, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelFondo, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Función para monstrar un mensaje de confirmación cuando cerramos el
     * tablero.
     */
    private void finAplicacion() {
        int respuesta;
        respuesta = javax.swing.JOptionPane.showConfirmDialog(rootPane,
                "Tablero " + board + " va a finalizar con " + IDgame + " partidas jugadas.",
                "¿Seguro que desea salir?",
                javax.swing.JOptionPane.OK_CANCEL_OPTION,
                javax.swing.JOptionPane.QUESTION_MESSAGE);
        if (respuesta == javax.swing.JOptionPane.OK_OPTION) {
            myBoard.doDelete();
            this.setVisible(false);
            this.dispose();
        }
    }

    private void MensajeSalida(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_MensajeSalida
        // TODO add your handling code here:
        finAplicacion();
    }//GEN-LAST:event_MensajeSalida

    private void Cierre(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_Cierre
        // TODO add your handling code here:
    }//GEN-LAST:event_Cierre

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GuiLink.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GuiLink.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GuiLink.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GuiLink.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GuiLink().setVisible(true);
            }
        });
    }
    private javax.swing.JButton[] v;
    private String board;
    private int IDgame;
    private AgenteTablero myBoard;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton J1;
    private javax.swing.JButton J10;
    private javax.swing.JButton J11;
    private javax.swing.JButton J12;
    private javax.swing.JButton J13;
    private javax.swing.JButton J14;
    private javax.swing.JButton J15;
    private javax.swing.JButton J16;
    private javax.swing.JButton J17;
    private javax.swing.JButton J18;
    private javax.swing.JButton J19;
    private javax.swing.JButton J2;
    private javax.swing.JButton J20;
    private javax.swing.JButton J21;
    private javax.swing.JButton J22;
    private javax.swing.JButton J23;
    private javax.swing.JButton J24;
    private javax.swing.JButton J25;
    private javax.swing.JButton J26;
    private javax.swing.JButton J27;
    private javax.swing.JButton J28;
    private javax.swing.JButton J29;
    private javax.swing.JButton J3;
    private javax.swing.JButton J30;
    private javax.swing.JButton J31;
    private javax.swing.JButton J32;
    private javax.swing.JButton J33;
    private javax.swing.JButton J34;
    private javax.swing.JButton J35;
    private javax.swing.JButton J36;
    private javax.swing.JButton J37;
    private javax.swing.JButton J38;
    private javax.swing.JButton J39;
    private javax.swing.JButton J4;
    private javax.swing.JButton J40;
    private javax.swing.JButton J41;
    private javax.swing.JButton J42;
    private javax.swing.JButton J5;
    private javax.swing.JButton J6;
    private javax.swing.JButton J7;
    private javax.swing.JButton J8;
    private javax.swing.JButton J9;
    private javax.swing.JLabel LPlayer1;
    private javax.swing.JLabel LPlayer2;
    private javax.swing.JLabel LaberReinicio;
    private javax.swing.JButton Player1;
    private javax.swing.JButton Player2;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelFondo;
    private javax.swing.JPanel jPanelTablero;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea tx1;
    // End of variables declaration//GEN-END:variables
}
