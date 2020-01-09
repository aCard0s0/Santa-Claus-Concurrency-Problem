/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxys;

import genclass.GenericIO;
import interfaces.TripInter;
import servers.ServerCom;
import states.ReindeerStates;
import states.SantaStates;
import structures.Messages.Message;
import structures.Messages.MessageException;

/**
 *
 * @author João Gonçalo Ribeiro
 */
public class TripProxy extends Thread{

    private static int nProxy = 0;           // Contador de threads lançados
    private final ServerCom sconi;                // Canal de comunicação
    private final TripInter tripInter;    // Interface à Fabrica
    private ReindeerStates rState;
    private SantaStates sState;
    
    public TripProxy(ServerCom sconi, TripInter tripInter) {
        //super("TripAroundProxy_"+ TripAroundProxy.getProxyId());
        this.sconi = sconi;
        this.tripInter = tripInter;
    }
    
    @Override
    public void run(){
        Message inMsg = null, outMsg = null;  // mensagem de saida e de entrada
        String inString, outString;          // string XML de entrada e saida
        
        inString = (String) sconi.readObject();   // ler pedido do cliente em XML
        inMsg =  new Message(inString, isXML());    // transformá-lo para o formato de mensagem
        
        try { 
            outMsg = tripInter.processAndReply(inMsg);         // processá-lo
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
    public ReindeerStates getReindeerState() {
        return rState; //To change body of generated methods, choose Tools | Templates.
    }
    
    public void setReindeerState(ReindeerStates state) {
        this.rState = state;
    }
    
    public SantaStates getSantaState() {
        return sState; //To change body of generated methods, choose Tools | Templates.
    }
    
    public void setSantaState(SantaStates state) {
        this.sState = state;
    }
    
    /* Geração do identificador da instanciação. */
    private static int getProxyId () {
        Class<?> cl = null;                     // representação do tipo de dados ClientProxy na máquina
                                                //   virtual de Java
        int proxyId;                            // identificador da instanciação

        try {
            cl = Class.forName ("servers.TripAroundProxy");
        
        } catch (ClassNotFoundException e) { 
            GenericIO.writelnString ("O tipo de dados TripAroundProxy não foi encontrado!");
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
