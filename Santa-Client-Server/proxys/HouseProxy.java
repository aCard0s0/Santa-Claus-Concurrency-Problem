/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxys;

import genclass.GenericIO;
import interfaces.HouseInter;
import servers.ServerCom;
import states.GnomeStates;
import states.SantaStates;
import structures.Messages.Message;
import structures.Messages.MessageException;

/**
 *
 * @author João Gonçalo Ribeiro
 */
public class HouseProxy extends Thread{
    
    private static int nProxy = 0;           // Contador de threads lançados
    private final ServerCom sconi;                // Canal de comunicação
    private final HouseInter houseInter;    // Interface à House
    private SantaStates stateS;
    private GnomeStates stateG;
    
    public HouseProxy(ServerCom sconi, HouseInter houseInter) {
        //super("ProxyHouse_"+ HouseProxy.getProxyId());
        this.sconi = sconi;
        this.houseInter = houseInter;
    }
    
    @Override
    public void run(){
        Message inMsg = null, outMsg = null;  // mensagem de saida e de entrada
        String inString, outString;          // string XML de entrada e saida
        
        inString = (String) sconi.readObject();   // ler pedido do cliente em XML
        inMsg =  new Message(inString, isXML());    // transformá-lo para o formato de mensagem
        try { 
            outMsg = houseInter.processAndReply(inMsg);         // processá-lo
        } catch (MessageException e) { 
            GenericIO.writelnString ("Thread " + getName () + ": " + e.getMessage () + "!");
            GenericIO.writelnString (e.getMessageVal ().toString ());
            System.exit (1);
        }
        outString = outMsg.toXMLString();                    // converter resposta para XML
        sconi.writeObject(outString);                                // enviar resposta ao cliente
        sconi.close ();                                                // fechar canal de comunicação
    }
    
    // Getter & Setter State
    public SantaStates getSantaState() {
        return stateS; //To change body of generated methods, choose Tools | Templates.
    }
    public GnomeStates getGnomeState() {
        return stateG; //To change body of generated methods, choose Tools | Templates.
    }
    
    public void setSantaState(SantaStates state) {
        this.stateS = state;
    }
    
    public void setGnomeState(GnomeStates state) {
        this.stateG = state;
    }
    
    /* Geração do identificador da instanciação. */
    private static int getProxyId () {
        Class<?> cl = null;                     // representação do tipo de dados ClientProxy na máquina
                                                //   virtual de Java
        int proxyId;                            // identificador da instanciação

        try {
            cl = Class.forName ("servers.HouseProxy");
        
        } catch (ClassNotFoundException e) { 
            GenericIO.writelnString ("O tipo de dados HouseProxy não foi encontrado!");
            e.printStackTrace ();
            System.exit (1);
        }

        synchronized (cl) { 
            proxyId = nProxy;
            nProxy += 1;
        }
        return proxyId;
    }
    
    public ServerCom getScon(){
      return sconi;
    }
    
    public boolean isXML(){
        return true;
    }
}