/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.shareRegions;

import client.ClientCom;
import genclass.GenericIO;
import identities.Gnome;
import states.States;
import structures.Messages.Message;
import structures.Messages.MsgIndex;

/**
 *
 * @author João Gonçalo Ribeiro
 */
public class ToyFactoryStub {
    
    private final String serverHostName;
    private final int serverPortNumb;

    public ToyFactoryStub(String hostName, int portNumber) {
        serverHostName = hostName;
        serverPortNumb = portNumber;
    }
    
    public void work(){
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;                                            // Mensagem enviada (send) Mensagem recevida (reply)
        States state;          // Estado da Thread do Gnome
        String inString, outString;                                     // mensagem sao convertidas em XML recevidas e enviadas
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        state = ((Gnome) Thread.currentThread()).getGnomeState();
        send = new Message(MsgIndex.RS_work, state);                 // cria mensagem
        outString= send.toXMLString();        
        con.writeObject (outString);                                         // escrever mensagem
        inString= (String) con.readObject();
        reply= new Message(inString, isXML());                             // le resposta

        if (!reply.getIdMethod().equals(MsgIndex.AS_work)) {         // verificar resposta
            GenericIO.writelnString("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            GenericIO.writelnString(reply.toString ());
            System.exit(1);
        }
        con.close();                                                     // fecha coneção
        
        ((Gnome) Thread.currentThread()).setGnomeState(reply.getState()); // Actualiza estado da thread do Gnome
    }
    
    
    public int needAdvice(int idGnome) {
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;                                            // Mensagem enviada (send) Mensagem recevida (reply)
        States state;          // Estado da Thread do Gnome
        String inString, outString;                                     // mensagem sao convertidas em XML recevidas e enviadas
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        state = ((Gnome) Thread.currentThread()).getGnomeState();
        
        send = new Message(MsgIndex.RS_needAdvice, idGnome, state);                 // cria mensagem
        outString= send.toXMLString();        
        con.writeObject (outString);                                         // escrever mensagem
        inString= (String) con.readObject();
        reply= new Message(inString, isXML());                             // le resposta
        
        if (!reply.getIdMethod().equals(MsgIndex.AS_needAdvice)) {         // verificar resposta
            GenericIO.writelnString("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            GenericIO.writelnString(reply.toString ());
            System.exit(1);
        }
        con.close();                                                     // fecha coneção
        
        //((Gnome) Thread.currentThread()).setGnomeState(reply.getGnomeState()); // Actualiza estado da thread do Gnome
        
        return reply.getIdSender();                 // retorna numero de gnome em queue
    }

    public void goBackToWork(int idGnome) {
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;                                            // Mensagem enviada (send) Mensagem recevida (reply)
        States state;          // Estado da Thread do Gnome
        String inString, outString;                                     // mensagem sao convertidas em XML recevidas e enviadas
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        state = ((Gnome) Thread.currentThread()).getGnomeState();
        send = new Message(MsgIndex.RS_goBackToWork, idGnome, state);                 // cria mensagem
        outString= send.toXMLString();        
        con.writeObject (outString);                                         // escrever mensagem
        inString= (String) con.readObject();
        reply= new Message(inString, isXML());                             // le resposta
        
        if (!reply.getIdMethod().equals(MsgIndex.AS_goBackToWork)) {         // verificar resposta
            GenericIO.writelnString("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            GenericIO.writelnString(reply.toString ());
            System.exit(1);
        }
        con.close();                                                     // fecha coneção
        ((Gnome) Thread.currentThread()).setGnomeState(reply.getState()); // Actualiza estado da thread do Gnome
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