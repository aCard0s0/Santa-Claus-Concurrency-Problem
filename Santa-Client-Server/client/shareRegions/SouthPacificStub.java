/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.shareRegions;

import client.ClientCom;
import genclass.GenericIO;
import identities.Reindeer;
import states.States;
import structures.Messages.Message;
import structures.Messages.MsgIndex;

/**
 *
 * @author João Gonçalo Ribeiro
 */
public class SouthPacificStub{
    private final String serverHostName;
    private final int serverPortNumb;

    public SouthPacificStub(String hostName, int portNumber) {
        serverHostName = hostName;
        serverPortNumb = portNumber;
    }
        
    public void enjoyView (int idReindeer){

        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;                                            // Mensagem enviada (send) Mensagem recevida (reply)
        String inString, outString;                                     // mensagem sao convertidas em XML recevidas e enviadas
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgIndex.RS_enjoyView, idReindeer);           // cria mensagem
        outString= send.toXMLString();        
        con.writeObject (outString);                                         // escrever mensagem
        inString= (String) con.readObject();
        reply= new Message(inString, isXML());                             // le resposta
        
        if (!reply.getIdMethod().equals(MsgIndex.AS_enjoyView)) {         // verificar resposta
            GenericIO.writelnString("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            GenericIO.writelnString(reply.toString ());
            System.exit(1);
        }
        con.close();                                                     // fecha coneção
    }
    
    public void leaveHolidays(int idReindeer){

        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;                                            // Mensagem enviada (send) Mensagem recevida (reply)
        States state;                                           // Estado da Thread da Rena
        String inString, outString;                                     // mensagem sao convertidas em XML recevidas e enviadas
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        state = ((Reindeer) Thread.currentThread()).getReindeerState();           // estado a ser enviado
        send = new Message(MsgIndex.RS_leaveHolidays, idReindeer, state);                 // cria mensagem
        outString= send.toXMLString();        
        con.writeObject (outString);                                         // escrever mensagem
        inString= (String) con.readObject();
        reply= new Message(inString, isXML());                            // le resposta
        
        if (!reply.getIdMethod().equals(MsgIndex.AS_leaveHolidays)) {         // verificar resposta
            GenericIO.writelnString("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            GenericIO.writelnString(reply.toString ());
            System.exit(1);
        }
        con.close();                                                    // fecha conexão
        ((Reindeer) Thread.currentThread()).setReindeerState(reply.getState());  // actualiza estado da Thread Santa
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