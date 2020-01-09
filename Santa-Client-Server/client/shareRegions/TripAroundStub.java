/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.shareRegions;

import client.ClientCom;
import genclass.GenericIO;
import identities.Reindeer;
import identities.Santa;
import states.States;
import structures.Messages.Message;
import structures.Messages.MsgIndex;

/**
 *
 * @author João Gonçalo Ribeiro
 */
public class TripAroundStub {
    
    private final String serverHostName;
    private final int serverPortNumb;

    public TripAroundStub(String hostName, int portNumber) {
        serverHostName = hostName;
        serverPortNumb = portNumber;
    }
    
    public void harnessReindeers(){
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;                                            // Mensagem enviada (send) Mensagem recevida (reply)
        States state;           // Estado da Thread do Santa
        String inString, outString;                                     // mensagem sao convertidas em XML recevidas e enviadas
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        state = ((Santa) Thread.currentThread()).getSantaState();
        send = new Message(MsgIndex.RS_harnessReindeers, state);                 // cria mensagem
        outString= send.toXMLString();        
        con.writeObject (outString);                                         // escrever mensagem
        inString= (String) con.readObject();
        reply= new Message(inString, isXML());                             // le resposta
        
        if (!reply.getIdMethod().equals(MsgIndex.AS_harnessReindeers)) {         // verificar resposta
            GenericIO.writelnString("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            GenericIO.writelnString(reply.toString ());
            System.exit(1);
        }
        con.close();                                                     // fecha conexão
    
        ((Santa) Thread.currentThread()).setSantaState(reply.getState()); // actualiza estado da Thread Santa
    }
    
    public void groupAtSledge(int idReindeer){
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;                                            // Mensagem enviada (send) Mensagem recevida (reply)
        States state;            // Estado da Thread do Reindeer
        String inString, outString;                                     // mensagem sao convertidas em XML recevidas e enviadas
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        state = ((Reindeer) Thread.currentThread()).getReindeerState();
        send = new Message(MsgIndex.RS_groupAtSledge, idReindeer, state);                 // cria mensagem
        outString= send.toXMLString();        
        con.writeObject (outString);                                         // escrever mensagem
        inString= (String) con.readObject();
        reply= new Message(inString, isXML());                             // le resposta
        
        if (!reply.getIdMethod().equals(MsgIndex.AS_groupAtSledge)) {         // verificar resposta
            GenericIO.writelnString("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            GenericIO.writelnString(reply.toString ());
            System.exit(1);
        }
        con.close();                                                // fecha conexão
    
        ((Reindeer) Thread.currentThread()).setReindeerState(reply.getState()); // actualiza estado da Thread Reindeer
    }
    
    public void followSanta(int idReindeer){
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;                                            // Mensagem enviada (send) Mensagem recevida (reply)
        String inString, outString;                                     // mensagem sao convertidas em XML recevidas e enviadas
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgIndex.RS_followSanta, idReindeer);                 // cria mensagem
        outString= send.toXMLString();        
        con.writeObject (outString);                                         // escrever mensagem
        inString= (String) con.readObject();
        reply= new Message(inString, isXML());                             // le resposta
        
        if (!reply.getIdMethod().equals(MsgIndex.AS_followSanta)) {         // verificar resposta
            GenericIO.writelnString("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            GenericIO.writelnString(reply.toString ());
            System.exit(1);
        }
        con.close();                                                // fecha coneção
    }
    
    public void travelAround(){
       ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;                                            // Mensagem enviada (send) Mensagem recevida (reply)
        String inString, outString;                                     // mensagem sao convertidas em XML recevidas e enviadas
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgIndex.RS_travelAround);                 // cria mensagem
        outString= send.toXMLString();        
        con.writeObject (outString);                                         // escrever mensagem
        inString= (String) con.readObject();
        reply= new Message(inString, isXML());                            // le resposta
        
        if (!reply.getIdMethod().equals(MsgIndex.AS_travelAround)) {         // verificar resposta
            GenericIO.writelnString("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            GenericIO.writelnString(reply.toString ());
            System.exit(1);
        }
        con.close();                                            // fecha coneção
    }
    
    public void goHome(){
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;                                            // Mensagem enviada (send) Mensagem recevida (reply)
        States state;          // Estado da Thread do Santa
        String inString, outString;                                     // mensagem sao convertidas em XML recevidas e enviadas
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        state = ((Santa) Thread.currentThread()).getSantaState();
        send = new Message(MsgIndex.RS_goHome, state);                 // cria mensagem
        outString= send.toXMLString();        
        con.writeObject (outString);                                         // escrever mensagem
        inString= (String) con.readObject();
        reply= new Message(inString, isXML());                             // le resposta
        
        if (!reply.getIdMethod().equals(MsgIndex.AS_goHome)) {         // verificar resposta
            GenericIO.writelnString("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            GenericIO.writelnString(reply.toString ());
            System.exit(1);
        }
        con.close();                                                // fecha coneção
    
        ((Santa) Thread.currentThread()).setSantaState(reply.getState()); // actualiza estado da Thread Santa
    }
    
    /**
   *  Fazer o shutdown do servidor (solicitação do serviço).
   */

    public void shutDown () {
        ClientCom con = new ClientCom (serverHostName, serverPortNumb);
        Message send, reply;
        String inString, outString;                                     // mensagem sao convertidas em XML recevidas e enviadas
        
        while (!con.open()) {                                                // aguarda ligação
            try { 
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        send = new Message (MsgIndex.RS_shutDown);
        outString= send.toXMLString();        
        con.writeObject (outString);                                         // escrever mensagem
        inString= (String) con.readObject();
        reply= new Message(inString, isXML());
      
        if (!reply.getIdMethod().equals(MsgIndex.AS_shutDown)) { 
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Tipo inválido!");
            GenericIO.writelnString (send.toString ());
            System.exit (1);
        }
        con.close ();
    }
    
    public boolean isXML(){
        return true;
    }
}