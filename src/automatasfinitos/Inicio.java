/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatasfinitos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.util.HashMap;

/**
 *
 * @author Pedro_Acevedo
 */
public class Inicio extends javax.swing.JFrame {

    public static final ArrayList<String> OP = new ArrayList<>();
    public static ArrayList<String> Alfabeto;
    private HashMap<String,Integer> hs = new HashMap();
    String[] Abecedario = {"A","B","C","D","E","F","G","H","I","J","K","L","M","Ñ","O","P"};
    public ArrayList<String> Conjuntos;
    String[][] Thompson,AFDOP,AFDNOP;
    JTable table;
    String[][]MAT;
    static int latestState = 0;
    int Parentesis;
    /**
     * Creates new form Inicio
     */
    public Inicio() {
        initComponents();
        OP.add("?");
        OP.add(")");
        OP.add("(");
        OP.add("*");
        OP.add("|");
        OP.add("+");
        jTableAFD.setEnabled(false);
        jTableSubConjuntos.setEnabled(false);
        jTableThompson.setEnabled(false);
        jTextAlfa.setEnabled(false);
        jTextConjuntos.setEnabled(false);
        jTextMueveOP.setEnabled(false);
        jTextSignificativos.setEnabled(false);
        jTextField2.setEnabled(false);
        jTextMueveNOP.setEnabled(false);
        jButton2.setEnabled(false);      
        setLocationRelativeTo(null);
        this.setResizable(false);
    }
    
    private void Thompson(String ER){
        if(true){
            Stack<Integer> Pars = new Stack();
            ArrayList<Pair> Pairs = new ArrayList();
            String[] ERs = ER.split("");
            for(int i=0;i < ERs.length;i++){
                if(ERs[i].equals("(")){
                    Pars.add(i);
                }else if(ERs[i].equals(")")) {
                    Pairs.add(new Pair(Pars.pop(),i));
                }
            }
            hs = Columns(Alfabeto());
            MAT = new String[50][hs.size()];
            RideER(ER,0, Pairs);
            /*ArrayList<String> Segments = Segment_OP(ER);
            //System.out.println("no");
            Segments.forEach((p) -> {
                System.out.println(p);
            });*/
        }
    }
    private HashMap<String,Integer> Columns(String alp){
        HashMap<String,Integer> h = new HashMap();
        String[] alpha = alp.substring(2,alp.length()-2).split(", ");
        for(int i=0;i < alpha.length ; i++){
            h.put(alpha[i], i);
        }
        h.put("ε",h.size());
        return h;
    }
    
    
    private void RideER(String ER,int i, ArrayList<Pair> Pars){
        if(i == ER.length()){
            return;
        }else if(Character.isLetter(ER.charAt(i))){
            if((i+1 < ER.length() && Character.isLetter(ER.charAt(i+1))) | i+1 == ER.length()
                    | (i+1 < ER.length() && ER.substring(i+1,i+2).equals("(")) ){
                //MAT[latestState][hs.get(Character.toString(s))] = latestState + 1;
                basic(latestState,ER.substring(i, i+1));
                latestState++;
                RideER(ER,i+1, Pars);
            }else if((i+1 < ER.length() && ER.substring(i+1,i+2).equals("*"))){
                int tmpStart = latestState;
                epsilon(latestState);
                latestState++;
                RideER(ER.substring(i,i+1),0,Pars);
                epsilon(latestState,tmpStart);
                epsilon(latestState);
                epsilon(tmpStart,latestState);
                latestState++;
                RideER(ER,i+2,Pars);
            }else if(i+1 < ER.length() && ER.substring(i+1,i+2).equals("+")){
                int tmpStart = latestState;
                epsilon(latestState);
                latestState++;
                RideER(ER.substring(i,i+1),0,Pars);
                epsilon(latestState,tmpStart);
                epsilon(latestState);
                //epsilon(tmpStart,latestState);
                latestState++;
                RideER(ER,i+2,Pars);
            }else if(i+1 < ER.length() && ER.substring(i+1,i+2).equals("?")){
                int tmpStart = latestState;
                epsilon(latestState);
                latestState++;
                RideER(ER.substring(i,i+1),0,Pars);
                epsilon(latestState);
                epsilon(tmpStart,latestState);
                latestState++;
                RideER(ER,i+2,Pars);
            }
        //WITH PARENTESIS    
        }else if(ER.substring(i,i+1).equals("(")){
            int j = couple(Pars,i);
            if((j+1 < ER.length() &&  Character.isLetter(ER.charAt(j+1))) | j+1 == ER.length()
                    | (j+1 < ER.length() && ER.substring(j+1,j+2).equals("("))){
                if(ER.substring(i, j).contains("|")){
                    //System.out.println("loop");
                    String[] operands = ER.substring(i+1, j).replace("|", "-").split("-");
                    int tmpStart = latestState;
                    epsilon(latestState);
                    latestState++;
                    RideER(operands[0],0,Pars);
                    int tmpa = latestState;
                    epsilon(tmpStart,latestState);
                    latestState++;
                    RideER(operands[1],0,Pars);
                    epsilon(latestState);
                    epsilon(tmpa,latestState);
                    latestState++;
                    RideER(ER,j+1,Pars);
                }
            //OPTIONAL
            }else if(j+1 < ER.length() && ER.substring(j+1,j+2).equals("?")){
                if(!ER.substring(i+1, j).contains("|")){
                    int tmpStart = latestState;
                    epsilon(latestState);
                    latestState++;
                    RideER(ER.substring(i+1,j),0,Pars);
                    epsilon(latestState);
                    epsilon(tmpStart,latestState);
                    latestState++;
                    RideER(ER,j+2,Pars);
                }else {
                    int tmpStart = latestState;
                    epsilon(latestState);
                    latestState++;
                    
                    String[] operands = ER.substring(i+1, j).replace("|", "-").split("-");
                    int tmpStart1 = latestState;
                    epsilon(latestState);
                    latestState++;
                    RideER(operands[0],0,Pars);
                    int tmpa1 = latestState;
                    epsilon(tmpStart1,latestState);
                    latestState++;
                    RideER(operands[1],0,Pars);
                    epsilon(latestState);
                    epsilon(tmpa1,latestState);
                    latestState++;
                    
                    epsilon(latestState);
                    epsilon(tmpStart,latestState);
                    latestState++;
                    RideER(ER,j+2,Pars);
                }
            //AND
            }else if(j+1 < ER.length() && ER.substring(j+1,j+2).equals("*")){
                if(!ER.substring(i+1, j).contains("|")){
                    int tmpStart = latestState;
                    epsilon(latestState);
                    latestState++;
                    RideER(ER.substring(i+1,j),0,Pars);
                    epsilon(latestState,tmpStart);
                    epsilon(latestState);
                    epsilon(tmpStart,latestState);
                    latestState++;
                    RideER(ER,j+2,Pars);
                }else{
                    int tmpStart = latestState;
                    epsilon(latestState);
                    latestState++;
                    
                    String[] operands = ER.substring(i+1, j).replace("|", "-").split("-");
                    int tmpStart1 = latestState;
                    epsilon(latestState);
                    latestState++;
                    RideER(operands[0],0,Pars);
                    int tmpa1 = latestState;
                    epsilon(tmpStart1,latestState);
                    latestState++;
                    RideER(operands[1],0,Pars);
                    epsilon(latestState);
                    epsilon(tmpa1,latestState);
                    latestState++;
                    
                    epsilon(latestState,tmpStart);
                    epsilon(latestState);
                    epsilon(tmpStart,latestState);
                    latestState++;
                    RideER(ER,j+2,Pars);
                }
            //OR
            }else if(j+1 < ER.length() && ER.substring(j+1,j+2).equals("+")){
                if(!ER.substring(i+1, j).contains("|")){
                    int tmpStart = latestState;
                    epsilon(latestState);
                    latestState++;
                    RideER(ER.substring(i+1,j),0,Pars);
                    epsilon(latestState,tmpStart);
                    epsilon(latestState);
                    //epsilon(tmpStart,latestState);
                    latestState++;
                    RideER(ER,j+2,Pars);
                }else{
                    int tmpStart = latestState;
                    epsilon(latestState);
                    latestState++;
                    
                    String[] operands = ER.substring(i+1, j).replace("|", "-").split("-");
                    int tmpStart1 = latestState;
                    epsilon(latestState);
                    latestState++;
                    RideER(operands[0],0,Pars);
                    int tmpa1 = latestState;
                    epsilon(tmpStart1,latestState);
                    latestState++;
                    RideER(operands[1],0,Pars);
                    epsilon(latestState);
                    epsilon(tmpa1,latestState);
                    latestState++;
                    
                    epsilon(latestState,tmpStart);
                    epsilon(latestState);
                    //epsilon(tmpStart,latestState);
                    latestState++;
                    RideER(ER,j+2,Pars);
                }
            }
        }
    }
    private int couple(ArrayList<Pair> pars, int a){
        int b = 0;
        for(Pair p : pars){
            if(Integer.parseInt(p.a.toString()) == a){
                b = Integer.parseInt(p.b.toString());
                break;
            }
        }
        return b;
    }
    
    private void epsilon(int latestState){
        if(MAT[latestState][hs.get("ε")] == null){
            MAT[latestState][hs.get("ε")] =(latestState + 1) + "";
        }else{
            MAT[latestState][hs.get("ε")] = MAT[latestState][hs.get("ε")] + ","  + (latestState + 1);
        }
        
    }
    private void epsilon(int i, int latestState){
        if(MAT[i][hs.get("ε")] == null){
            MAT[i][hs.get("ε")] = (latestState+1)+"";
        }else{
            MAT[i][hs.get("ε")] = MAT[i][hs.get("ε")] + "," + (latestState +1);
        }
        
    }
    
    /*private void RideER(String ER){
        int i=0;
        int latestState = 0;
        String[] ERs = ER.split("");
        ArrayList<String> Pars = new ArrayList();
        hs = Columns(Alfabeto());
        MAT = new int[50][hs.size()];
        Pars.add("(");
        Pars.add(")");
        while(i < ERs.length){
            char s = ERs[i].charAt(0);
            //if(Character.isLetter(s)){
                if(Character.isLetter(s) && i+1 < ERs.length && !Character.isLetter(ER.charAt(i+1)) && 
                        !Pars.contains(ERs[i+1])){
                    Segments.add(ERs[i]+ERs[i+1]);
                    i+=2;
                }else if(Character.isLetter(s) && !Pars.contains(ERs[i+1])){
                    Segments.add(ERs[i]);
                    i++;
                }else{
                
                }
            //}
            if(Character.isLetter(s)){
                if((i+1 < ERs.length && Character.isLetter(ERs[i+1].charAt(0))) | i+1 == ERs.length){
                    MAT[latestState][hs.get(Character.toString(s))] = latestState + 1;
                    latestState++;
                    i++;
                }else{
                
                }
            }else{
                
            }
        }
        //System.out.println("no");
    }*/
    
    private void basic(int latestState, String ER){
        if(MAT[latestState][hs.get(ER)] == null){
            MAT[latestState][hs.get(ER)] = (latestState + 1) + "";
        }else{
            MAT[latestState][hs.get(ER)] = MAT[latestState][hs.get(ER)] + "," +  (latestState + 1);
        }
    }
    private boolean isAtomic(String ER){
        return !((ER.contains("|") | (ER.contains("?"))) |
                (ER.contains("+")) | (ER.contains("*")));
    }
    
    private void printMAT(int latestState){
        System.out.print("  ");
        hs.keySet().forEach((k) -> {
            System.out.print(k + " ");
        });
        System.out.println();
        for(int j = 0; j <= latestState;j++){
            System.out.print(j + " ");
            for(int k = 0; k < hs.size();k++){
                if(MAT[j][k] == null){
                    System.out.print(0 + " ");
                }else{
                    System.out.print(MAT[j][k] + " ");
                }
                
            }
            System.out.println();
        }
    }
    
    //
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableThompson = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAlfa = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableSubConjuntos = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextConjuntos = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableAFD = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextSignificativos = new javax.swing.JTextArea();
        jTextField2 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabelSINO = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTextMueveOP = new javax.swing.JTextArea();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTextMueveNOP = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Automatas Finitos");
        setMaximumSize(new java.awt.Dimension(1280, 720));
        setMinimumSize(new java.awt.Dimension(1280, 720));
        setPreferredSize(new java.awt.Dimension(600, 600));

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("AUTOMATAS FINITOS");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("EXPRESION REGULAR:");

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton1.setText("CONSTRUIR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTableThompson.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTableThompson.setEditingColumn(0);
        jTableThompson.setEditingRow(0);
        jTableThompson.setRowSelectionAllowed(false);
        jScrollPane1.setViewportView(jTableThompson);

        jTextAlfa.setEditable(false);
        jTextAlfa.setColumns(1);
        jTextAlfa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextAlfa.setLineWrap(true);
        jTextAlfa.setRows(1);
        jTextAlfa.setTabSize(1);
        jTextAlfa.setMinimumSize(new java.awt.Dimension(433, 2331));
        jScrollPane2.setViewportView(jTextAlfa);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("AFN Transiciones:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Alfabeto:");

        jTableSubConjuntos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTableSubConjuntos.setRowSelectionAllowed(false);
        jScrollPane3.setViewportView(jTableSubConjuntos);

        jTextConjuntos.setEditable(false);
        jTextConjuntos.setColumns(1);
        jTextConjuntos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextConjuntos.setLineWrap(true);
        jTextConjuntos.setRows(1);
        jTextConjuntos.setTabSize(1);
        jTextConjuntos.setMinimumSize(new java.awt.Dimension(433, 2331));
        jScrollPane4.setViewportView(jTextConjuntos);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("AFD No Optimo:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Conjuntos:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("AFD TransD:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Estados Significativos:");

        jTableAFD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTableAFD.setEditingColumn(0);
        jTableAFD.setEditingRow(0);
        jTableAFD.setRowSelectionAllowed(false);
        jScrollPane5.setViewportView(jTableAFD);

        jTextSignificativos.setEditable(false);
        jTextSignificativos.setColumns(1);
        jTextSignificativos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextSignificativos.setLineWrap(true);
        jTextSignificativos.setRows(1);
        jTextSignificativos.setTabSize(1);
        jTextSignificativos.setMinimumSize(new java.awt.Dimension(433, 2331));
        jScrollPane6.setViewportView(jTextSignificativos);

        jTextField2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField2KeyTyped(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("RECONOCIMIENTO:");

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton2.setText("COMPROBAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabelSINO.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelSINO.setText("--");

        jTextMueveOP.setEditable(false);
        jTextMueveOP.setColumns(1);
        jTextMueveOP.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextMueveOP.setRows(1);
        jTextMueveOP.setTabSize(1);
        jTextMueveOP.setMinimumSize(new java.awt.Dimension(433, 2331));
        jScrollPane7.setViewportView(jTextMueveOP);

        jTextMueveNOP.setEditable(false);
        jTextMueveNOP.setColumns(1);
        jTextMueveNOP.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextMueveNOP.setRows(1);
        jTextMueveNOP.setTabSize(1);
        jTextMueveNOP.setMinimumSize(new java.awt.Dimension(433, 2331));
        jScrollPane8.setViewportView(jTextMueveNOP);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("CON AFD NO OPTIMO:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("CON AFD OPTIMO:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextField1)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel9)
                                .addComponent(jTextField2)
                                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addComponent(jLabelSINO)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(181, 181, 181))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGap(285, 285, 285)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 641, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelSINO)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(46, 46, 46))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        // TODO add your handling code here:
        if (!Character.isLetter(evt.getKeyChar()) && !Character.isDigit(evt.getKeyChar()) && !OP.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_jTextField1KeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (!jTextField1.getText().equals("")) {
            Alfabeto = new ArrayList<>();
            if (ComprobarEXP()) {
                Reinicio();
                jTableAFD.setEnabled(true);
                jTableSubConjuntos.setEnabled(true);
                jTableThompson.setEnabled(true);
                jTextAlfa.setEnabled(true);
                jTextConjuntos.setEnabled(true);
                jTextMueveOP.setEnabled(true);
                jTextSignificativos.setEnabled(true);
                jTextField2.setEnabled(true);
                jTextMueveNOP.setEnabled(true);
                jButton2.setEnabled(true);              
                jTextAlfa.setText(Alfabeto());
                Thompson(jTextField1.getText());
                printMAT(latestState);
                latestState = 0;
                if (Alfabeto.size() > 7) {
                    jTableAFD.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                    jTableSubConjuntos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                    jTableThompson.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                }else{
                    jTableAFD.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
                    jTableSubConjuntos.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
                    jTableThompson.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
                }
                AFDNOP = SubConjuntos();
                estSig(AFDNOP);
                //Thompson(jTextField1.getText());
            }    
        }else{
            JOptionPane.showMessageDialog(null, "Llene el campo respectivo.", "Automatas finitos", JOptionPane.WARNING_MESSAGE);
    
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2KeyTyped

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (!jTextField2.getText().equals("")) {
            //SubConjuntos();
            if (Reconocimiento(AFDOP,jTextField2.getText())) {
                jLabelSINO.setText("SI");
            }else{
                jLabelSINO.setText("NO");
            }
            jTextMueveOP.setText(TM);
            TM="";
            if (Reconocimiento(AFDNOP,jTextField2.getText())) {
                jLabelSINO.setText("SI");
            }else{
                jLabelSINO.setText("NO");
            }
            jTextMueveNOP.setText(TM);
        }else{
            JOptionPane.showMessageDialog(null, "Llene el campo respectivo.", "Automatas finitos", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton2ActionPerformed
    
    public void Reinicio(){
        jTextAlfa.setText("");
        jTextConjuntos.setText("");
        jTextMueveOP.setText("");
        jTextSignificativos.setText("");
        jTextField2.setText("");
        jTextMueveNOP.setText("");
        jLabelSINO.setText("--");
    }
       
    public boolean ComprobarEXP(){
        boolean sw = true;
        int i = 0;
        String EXP = jTextField1.getText();
        Parentesis = 0;
        while(i < EXP.length() && sw) {
            if (!OP.contains(EXP.substring(i, i+1))) {
                if (!Alfabeto.contains(EXP.substring(i, i+1))) {
                    Alfabeto.add(EXP.substring(i, i+1));   
                }
            }else{
                boolean error = false;
                if (i<(EXP.length()-1)) {
                    switch(EXP.substring(i, i+1)){
                        case "|":
                            if (OP.contains(EXP.substring(i+1, i+2)) && !EXP.substring(i+1, i+2).equals("(")) {
                                error = true;
                            }
                        break;
                        case "?":
                            if (OP.contains(EXP.substring(i+1, i+2)) && !EXP.substring(i+1, i+2).equals("(") && !EXP.substring(i+1, i+2).equals(")")) {
                                error = true;
                            }
                        break;
                        case "*":
                            if (EXP.substring(i+1, i+2).equals("+") | EXP.substring(i+1, i+2).equals("?") | EXP.substring(i+1, i+2).equals("*")) {
                                error = true;
                            }
                        break;
                        case "(":
                            Parentesis++;
                            if (OP.contains(EXP.substring(i+1, i+2)) && !EXP.substring(i+1, i+2).equals("(")) {
                                error = true;
                            }                        
                        break;
                        case ")":            
                            Parentesis++;           
                        break;
                        case "+":
                            if (EXP.substring(i+1, i+2).equals("+") | EXP.substring(i+1, i+2).equals("*") | EXP.substring(i+1, i+2).equals("?")) {
                                error = true;
                            }                         
                        break;
                    }
                }else{
                    if (EXP.substring(i, i+1).equals(")") | EXP.substring(i, i+1).equals("(")) {
                        Parentesis++;
                    }
                    if (EXP.substring(i, i+1).equals("|") | EXP.substring(i, i+1).equals("(")) {
                        error=true;
                    }
                    if (Parentesis%2 != 0) {
                        error=true;
                    }
                }
                if (error) {
                    JOptionPane.showMessageDialog(null, "La expresion regular tiene errores.", "Automatas finitos", JOptionPane.WARNING_MESSAGE);
                    sw = false;
                }
            }
            i++;
        }
        return sw;
    }
    
    String Alfabeto(){
        String A = "{";
        Collections.sort(Alfabeto);
        A = A + " " + Alfabeto.get(0);
        for (int i = 1; i < Alfabeto.size(); i++) {
            A = A + ", " + Alfabeto.get(i);
        }
        A = A + " }";
        return A;
    }
    
    
    // METODO DE SUBCONJUNTOS.
        
    String TransEpsilon(String[][] T,int Pos,ArrayList<String> C){
        String TE = "" +  T[Pos][0];
        if (!C.contains(T[Pos][0])) {
            C.add(T[Pos][0]);   
        }
        //System.out.println(TE + "Actual");
        //System.out.println(T[Pos][T[0].length-1] + "Trans EP - " + Pos);
        if (!T[Pos][T[0].length-1].equals("-")) {
            String[] E = T[Pos][T[0].length-1].split(",");
            for (int i = 0; i < E.length; i++) {
                if (!C.contains(E[i])){
                     TE = TE + "," + TransEpsilon(T,Integer.parseInt(E[i]),C);    
                }             
            }
        }
        return TE;
    }
    
    String MueveC(String[][] T,String CTE,int Sim,ArrayList<String> C){
        String MC = "";
            String[] CE = CTE.split(",");
            for (int i = 0; i < CE.length; i++){
                String c = T[Integer.parseInt(CE[i])][Sim];
                if (c.length()>1){
                    String[] s = c.split(",");
                    for (int j = 0; j < s.length; j++) {
                        if (!C.contains(s[j])) {
                           C.add(s[j]);
                        }
                   }
                }else{
                    if (!c.equals("-")) {
                        if (!C.contains(c)) {
                           C.add(c);
                        }   
                    }
                }
            }
        if (!C.isEmpty()) {
            ArrayList<String> EP = new ArrayList<>();
           for (String c : C) {
               TransEpsilon(T,Integer.parseInt(c),EP);
           }
           MC = MC + EP.get(0);
           for (int i = 1; i < EP.size(); i++) {
               MC = MC + ","  + EP.get(i);
           }
        }    
        return MC;
    }
    
    public String[][] SubConjuntos(){
        String[][] T = {{"0","-","-","1,7"},{"1","-","-","2,4"},{"2","3","-","-"},{"3","-","-","6"},{"4","-","5","-"},{"5","-","-","6"},{"6","-","-","1,7"},{"7","8","-","-"},{"8","-","9","-"},{"9","-","10","-"},{"10","-","-","-"}};
        Conjuntos = new ArrayList<>();
        Conjuntos.add(TransEpsilon(T,0,new ArrayList<>()));
        ArrayList<String> Trans = new ArrayList<>();
        Trans.add("");
        int a = 0;
        while(a < Conjuntos.size()){
            for (int i = 0; i < Alfabeto.size(); i++) {
                String Ti = MueveC(T,Conjuntos.get(a),(i+1),new ArrayList<>());
                if (!Conjuntos.contains(Ti)) {
                    Conjuntos.add(Ti);
                    Trans.add("");
                    Trans.set(a,Trans.get(a) + (Conjuntos.size()-1) + "-");
                }else{
                    Trans.set(a,Trans.get(a) + (Conjuntos.indexOf(Ti)) + "-");
                }
            }
            a++;
        }
        String[][] Datos = new String[Conjuntos.size()][Alfabeto.size()+1];
        for (int i = 0; i < Conjuntos.size(); i++) {
            Datos[i][0] = Abecedario[i];
        }
        for (int i = 0; i < Trans.size(); i++) {
            String[] c = Trans.get(i).split("-");
            for (int j = 0; j < c.length; j++) {
              Datos[i][(j+1)] = Abecedario[Integer.parseInt(c[j])];
            }
            jTextConjuntos.setText( jTextConjuntos.getText() + Abecedario[i] + " --> {" + Conjuntos.get(i) + "}" + "\n" );   
        }
        DefTable(jTableSubConjuntos,Datos);
        return Datos;
    }

    // METODO DE ESTADOS SIGNIFICATIVOS
    
    public void estSig(String[][] mat) {
        jTextSignificativos.setText("");
        String[][] T = {{"0", "-", "-", "1,7"}, {"1", "-", "-", "2,4"}, {"2", "3", "-", "-"}, {"3", "-", "-", "6"}, {"4", "-", "5", "-"}, {"5", "-", "-", "6"}, {"6", "-", "-", "1,7"}, {"7", "8", "-", "-"}, {"8", "-", "9", "-"}, {"9", "-", "10", "-"}, {"10", "-", "1", "-"}};
        ArrayList<String> est_sig = new ArrayList<>();
        String[][] datos;
        String[][] aux3 = new String[Conjuntos.size()][Alfabeto.size() + 1];
        for (int i = 0; i < Conjuntos.size(); i++) {
            System.arraycopy(mat[i], 0, aux3[i], 0, Alfabeto.size() + 1);
        }
        String[] aux2 = new String[Abecedario.length];
        int cont = 0;
        for (int i = 0; i < Conjuntos.size(); i++) {
            String aux[] = Conjuntos.get(i).split(",");
            String add = "";
            for (String aux1 : aux) {
                if (!T[Integer.parseInt(aux1)][1].equals("-") | !T[Integer.parseInt(aux1)][2].equals("-")) {
                    if (add.equals("")) {
                        add = add + aux1;
                    } else {
                        add = add + "," + aux1;
                    }
                }
            }
            if (exist(est_sig, add, aux3, Conjuntos.size(), Alfabeto.size() + 1, i)) {
                est_sig.add("Est_sig(" + Abecedario[i] + ")={" + add + "}-> eliminar");
                jTextSignificativos.setText(jTextSignificativos.getText() + est_sig.get(est_sig.size() - 1) + "\n");
            } else {
                est_sig.add(add);
                aux2[cont] = Abecedario[i];
                cont++;
                jTextSignificativos.setText(jTextSignificativos.getText() + "Est_sig(" + Abecedario[i] + ")= {" + add + "}" + "\n");
            }
        }
        AFDOP = new String[cont][Alfabeto.size() + 1];
        for (int i = 0; i < cont; i++) {
            AFDOP[i][0] = aux2[i];
            for (int j = 0; j < Conjuntos.size(); j++) {
                if (aux3[j][0].equals(aux2[i])) {
                    for (int k = 0; k < Alfabeto.size(); k++) {
                        AFDOP[i][k + 1] = aux3[j][k + 1];
                    }
                }
            }
        }        
        DefTable(jTableAFD, AFDOP);
    }

    boolean exist(ArrayList<String> a, String b, String[][] c, int fil, int col, int d) {
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).equals(b)) {
                for (int j = 0; j < fil; j++) {
                    for (int k = 1; k < col; k++) {
                        if (c[j][k].equals(Abecedario[d])) {
                            c[j][k] = Abecedario[i];
                            System.out.println(Abecedario[d] + "->" + Abecedario[i]);
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    //DEFINICION DE TABLA.
    
    public JTable DefTable(JTable T,String[][] Mat){
        String[] Columnas = new String[Alfabeto.size()+1];
        Columnas[0] = " ";
        for (int i = 0; i < Alfabeto.size(); i++) {
           Columnas[i+1] = Alfabeto.get(i);
        }
        JTable M = new JTable(Mat, Columnas);
        T.setModel(M.getModel());
        return T;
    }
    String TM = "";
    

// RECONOCIMIENTO.
    
    int Mueve(int Nodo, String Letra,String[][] Mat){
        int Sig = -1;
        String M = Mat[Nodo][Alfabeto.indexOf(Letra)+1];
        if (!M.equals("-")) {
            int i = 0;
            TM = TM + "--" + Letra +  "-> " + M +  " ";
            while(i < Mat.length) {
                if (Mat[i][0].equals(M)) {
                    Sig = i;
                    i = Mat.length;
                }else{
                    i++;
                }
            }
        }
        return Sig;
    }
    
    public boolean Reconocimiento(String[][] Mat,String Cadena){
        boolean sw = true;
        int j = 0;
        while(sw && j < Cadena.length()){
            if (!Alfabeto.contains(Cadena.substring(j, j+1))) {
                sw = false;
            }else{
                j = j+1;
            }
        }
        if (sw) {
            int a = Mueve(0,Cadena.substring(0,1),Mat);
            int i = 0;
            TM = Mat[0][0] + "";
            while (i < Cadena.length() && sw) {
                if (a!=-1) {
                    a = Mueve(a,Cadena.substring(i, i+1),Mat);
                    i++;
                }else{
                    sw = false;
                }
            }    
            if ((i != Cadena.length() && sw)){
                 sw = false;
            }else{
                if (a != (Mat.length-1)) {
                    sw = false;
                    TM = "No llega al estado de finalización.";
                }
            }
        }else{
            TM = "Esta cadena no pertenece al alfabeto.";
        }
        return sw;
    } 
    
    
    
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
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Inicio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelSINO;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTable jTableAFD;
    private javax.swing.JTable jTableSubConjuntos;
    private javax.swing.JTable jTableThompson;
    private javax.swing.JTextArea jTextAlfa;
    private javax.swing.JTextArea jTextConjuntos;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextArea jTextMueveNOP;
    private javax.swing.JTextArea jTextMueveOP;
    private javax.swing.JTextArea jTextSignificativos;
    // End of variables declaration//GEN-END:variables
}
