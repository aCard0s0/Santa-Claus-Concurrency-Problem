/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.shareRegions;

import client.ClientCom;
import genclass.GenericIO;
import states.States;
import structures.Messages.Message;
import structures.Messages.MsgIndex;

/**
 *
 * @author João Gonçalo Ribeiro
 */
public class RepositoryStub {
    
    private final String serverHostName;
    private final int serverPortNumb;

    public RepositoryStub(String hostName, int portNumber) {
        serverHostName = hostName;
        serverPortNumb = portNumber;
    }
    
    public void incYears(){
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;                                            // Mensagem enviada (send) Mensagem recevida (reply)
        String inString, outString;                                     // mensagem sao convertidas em XML recevidas e enviadas

        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgIndex.RS_incYears);                        // cria mensagem
        outString= send.toXMLString();
        con.writeObject (outString);                                         // escrever mensagem
        inString= (String) con.readObject();
        reply= new Message(inString, isXML());
        
        if (!reply.getIdMethod().equals(MsgIndex.AS_incYears)) {         // verificar resposta
            GenericIO.writelnString("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            GenericIO.writelnString(reply.toString ());
            System.exit(1);
        }
        con.close();                                                     // fecha coneção
    }
    
    public void incConsul(){  
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;                                            // Mensagem enviada (send) Mensagem recevida (reply)
        String inString, outString;                                     // mensagem sao convertidas em XML recevidas e enviadas
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }

        send = new Message(MsgIndex.RS_incConsul);                 // cria mensagem
        outString= send.toXMLString();        
        con.writeObject (outString);                                         // escrever mensagem
        inString= (String) con.readObject();
        reply= new Message(inString, isXML());

        if (!reply.getIdMethod().equals(MsgIndex.AS_incConsul)) {         // verificar resposta
            GenericIO.writelnString("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            GenericIO.writelnString(reply.toString ());
            System.exit(1);
        }
        con.close();                                                     // fecha coneção
    }
    
    public boolean endSanta(){
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;                                            // Mensagem enviada (send) Mensagem recevida (reply)
        String inString, outString;                                     // mensagem sao convertidas em XML recevidas e enviadas
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgIndex.RS_endSanta);                 // cria mensagem
        outString= send.toXMLString();        
        con.writeObject (outString);                                         // escrever mensagem
        inString= (String) con.readObject();
        reply= new Message(inString, isXML());
        
        if (!reply.getIdMethod().equals(MsgIndex.AS_endSantaT) &&
                !reply.getIdMethod().equals(MsgIndex.AS_endSantaF)) {         // verificar resposta
            GenericIO.writelnString("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            GenericIO.writelnString(reply.toString ());
            System.exit(1);
        }
        con.close();                                                     // fecha coneção
        
        if (reply.getIdMethod().equals(MsgIndex.AS_endSantaT))
            return true;
        else
            return false;
    }
    
    public boolean endReindeer(){
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;                                            // Mensagem enviada (send) Mensagem recevida (reply)
        String inString, outString;                                     // mensagem sao convertidas em XML recevidas e enviadas

        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgIndex.RS_endReindeer);                 // cria mensagem
        outString= send.toXMLString();        
        con.writeObject (outString);                                         // escrever mensagem
        inString= (String) con.readObject();
        reply= new Message(inString, isXML());
        
        if (!reply.getIdMethod().equals(MsgIndex.AS_endReindeerT) &&
                !reply.getIdMethod().equals(MsgIndex.AS_endReindeerF)) {         // verificar resposta
            GenericIO.writelnString("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            GenericIO.writelnString(reply.toString ());
            System.exit(1);
        }
        con.close();                                                     // fecha coneção
        
        if (reply.getIdMethod().equals(MsgIndex.AS_endReindeerT))
            return true;
        else
            return false;
    }
    
    public void setLastConsul(int idGnome) {
       ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;                                            // Mensagem enviada (send) Mensagem recevida (reply)
        String inString, outString;                                     // mensagem sao convertidas em XML recevidas e enviadas
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgIndex.RS_setLastConsul, idGnome);                 // cria mensagem
        outString= send.toXMLString();        
        con.writeObject (outString);                                         // escrever mensagem
        inString= (String) con.readObject();
        reply= new Message(inString, isXML());
        
        if (!reply.getIdMethod().equals(MsgIndex.AS_setLastConsul)) {         // verificar resposta
            GenericIO.writelnString("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            GenericIO.writelnString(reply.toString ());
            System.exit(1);
        }
        con.close();                                                     // fecha coneção
    }
    
    public boolean endGnome(int idGnome){
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;                                            // Mensagem enviada (send) Mensagem recevida (reply)
        String inString, outString;                                     // mensagem sao convertidas em XML recevidas e enviadas
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgIndex.RS_endGnome, idGnome);                 // cria mensagem
        outString= send.toXMLString();        
        con.writeObject (outString);                                         // escrever mensagem
        inString= (String) con.readObject();
        reply= new Message(inString, isXML());
        
        if (!reply.getIdMethod().equals(MsgIndex.AS_endGnomeT) &&
                !reply.getIdMethod().equals(MsgIndex.AS_endGnomeF)) {         // verificar resposta
            GenericIO.writelnString("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            GenericIO.writelnString(reply.toString ());
            System.exit(1);
        }
        con.close();                                                     // fecha coneção
        
        if (reply.getIdMethod().equals(MsgIndex.AS_endGnomeT))
            return true;
        else
            return false;
    }
    // ____________________________________
    
    /** Para escrever no Ficheiro. */
    //  Santa
    public void writeSantaState(States state){
         ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;                                            // Mensagem enviada (send) Mensagem recevida (reply)
        String inString, outString;                                     // mensagem sao convertidas em XML recevidas e enviadas
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgIndex.RS_writeSantaState, state);                 // cria mensagem
        outString= send.toXMLString();        
        con.writeObject (outString);                                         // escrever mensagem
        inString= (String) con.readObject();
        reply= new Message(inString, isXML());
        
        if (!reply.getIdMethod().equals(MsgIndex.AS_writeSantaState)) {         // verificar resposta
            GenericIO.writelnString("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            GenericIO.writelnString(reply.toString ());
            System.exit(1);
        }
        con.close();                                                     // fecha coneção
    }
    //  Gnomos
    public void writeMeetGnomes(){
         ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;                                            // Mensagem enviada (send) Mensagem recevida (reply)
        String inString, outString;                                     // mensagem sao convertidas em XML recevidas e enviadas
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgIndex.RS_writeMeetGnomes);                 // cria mensagem
        outString= send.toXMLString();        
        con.writeObject (outString);                                         // escrever mensagem
        inString= (String) con.readObject();
        reply= new Message(inString, isXML());                           // le resposta
        
        if (!reply.getIdMethod().equals(MsgIndex.AS_writeMeetGnomes)) {         // verificar resposta
            GenericIO.writelnString("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            GenericIO.writelnString(reply.toString ());
            System.exit(1);
        }
        con.close();                                                     // fecha coneção
    }
    //  Gnomos
    public void writeGnomeKnoock() {
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;                                            // Mensagem enviada (send) Mensagem recevida (reply)
        String inString, outString;                                     // mensagem sao convertidas em XML recevidas e enviadas
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgIndex.RS_writeGnomeKnoock);                 // cria mensagem
        outString= send.toXMLString();        
        con.writeObject (outString);                                         // escrever mensagem
        inString= (String) con.readObject();
        reply= new Message(inString, isXML());                             // le resposta
        
        if (!reply.getIdMethod().equals(MsgIndex.AS_writeGnomeKnoock)) {         // verificar resposta
            GenericIO.writelnString("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            GenericIO.writelnString(reply.toString ());
            System.exit(1);
        }
        con.close();                                                     // fecha coneção
    }
    //  Gnome
    public void writeGnomeState(int id, States state){
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;                                            // Mensagem enviada (send) Mensagem recevida (reply)
        String inString, outString;                                     // mensagem sao convertidas em XML recevidas e enviadas
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgIndex.RS_writeGnomeState, id, state);                 // cria mensagem
        outString= send.toXMLString();        
        con.writeObject (outString);                                         // escrever mensagem
        inString= (String) con.readObject();
        reply= new Message(inString, isXML());                            // le resposta
        
        if (!reply.getIdMethod().equals(MsgIndex.AS_writeGnomeState)) {         // verificar resposta
            GenericIO.writelnString("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            GenericIO.writelnString(reply.toString ());
            System.exit(1);
        }
        con.close();                                                     // fecha coneção
    }
    //  Gnome
    public void writeGnomeOnGroup(int id, int nGnomes){
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;                                            // Mensagem enviada (send) Mensagem recevida (reply)
        String inString, outString;                                     // mensagem sao convertidas em XML recevidas e enviadas
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgIndex.RS_writeGnomeOnGroup, id, nGnomes);                 // cria mensagem
        outString= send.toXMLString();        
        con.writeObject (outString);                                         // escrever mensagem
        inString= (String) con.readObject();
        reply= new Message(inString, isXML());                             // le resposta
        
        if (!reply.getIdMethod().equals(MsgIndex.AS_writeGnomeOnGroup)) {         // verificar resposta
            GenericIO.writelnString("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            GenericIO.writelnString(reply.toString ());
            System.exit(1);
        }
        con.close();                                                     // fecha coneção
    }
    //  Gnome
    public void writeGnomeWithSanta(int id) {
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;                                            // Mensagem enviada (send) Mensagem recevida (reply)
        String inString, outString;                                     // mensagem sao convertidas em XML recevidas e enviadas
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgIndex.RS_writeGnomeWithSanta, id);                 // cria mensagem
        outString= send.toXMLString();        
        con.writeObject (outString);                                         // escrever mensagem
        inString= (String) con.readObject();
        reply= new Message(inString, isXML());                             // le resposta
        
        if (!reply.getIdMethod().equals(MsgIndex.AS_writeGnomeWithSanta)) {         // verificar resposta
            GenericIO.writelnString("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            GenericIO.writelnString(reply.toString ());
            System.exit(1);
        }
        con.close();                                                     // fecha coneção
    }
    
    //  Reindeer
    public void writeReindeerState(int id, States state){
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;                                            // Mensagem enviada (send) Mensagem recevida (reply)
        String inString, outString;                                     // mensagem sao convertidas em XML recevidas e enviadas
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgIndex.RS_writeReindeerState, id, state);                 // cria mensagem
        outString= send.toXMLString();        
        con.writeObject (outString);                                         // escrever mensagem
        inString= (String) con.readObject();
        reply= new Message(inString, isXML());                             // le resposta
        
        if (!reply.getIdMethod().equals(MsgIndex.AS_writeReindeerState)) {         // verificar resposta
            GenericIO.writelnString("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            GenericIO.writelnString(reply.toString ());
            System.exit(1);
        }
        con.close();                                                     // fecha coneção
    }
    //  Reindeer
    public void writeReindeerOnSledge(int id){
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;                                            // Mensagem enviada (send) Mensagem recevida (reply)
        String inString, outString;                                     // mensagem sao convertidas em XML recevidas e enviadas
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgIndex.RS_writeReindeerOnSledge, id);                 // cria mensagem
        outString= send.toXMLString();        
        con.writeObject (outString);                                         // escrever mensagem
        inString= (String) con.readObject();
        reply= new Message(inString, isXML());                            // le resposta
        
        if (!reply.getIdMethod().equals(MsgIndex.AS_writeReindeerOnSledge)) {         // verificar resposta
            GenericIO.writelnString("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            GenericIO.writelnString(reply.toString ());
            System.exit(1);
        }
        con.close();                                                     // fecha coneção
    }
    //  Reindeer
    public void writeReinderKnoock(){
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;                                            // Mensagem enviada (send) Mensagem recevida (reply)
        String inString, outString;                                     // mensagem sao convertidas em XML recevidas e enviadas
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgIndex.RS_writeReinderKnoock);                 // cria mensagem
        outString= send.toXMLString();        
        con.writeObject (outString);                                         // escrever mensagem
        inString= (String) con.readObject();
        reply= new Message(inString, isXML());                             // le resposta
        
        if (!reply.getIdMethod().equals(MsgIndex.AS_writeReinderKnoock)) {         // verificar resposta
            GenericIO.writelnString("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            GenericIO.writelnString(reply.toString ());
            System.exit(1);
        }
        con.close();                                                     // fecha coneção
    }
    //  Reindeer
    public void writeReinOnStable(int id, int nReindeer){
        ClientCom con = new ClientCom (serverHostName, serverPortNumb); // Instanciação comunicação com servidor
        Message send, reply;                                            // Mensagem enviada (send) Mensagem recevida (reply)
        String inString, outString;                                     // mensagem sao convertidas em XML recevidas e enviadas
        
        while (!con.open ()){                                           // aguarda ligação
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        send = new Message(MsgIndex.RS_writeReinOnStable, id, nReindeer);                 // cria mensagem
        outString= send.toXMLString();        
        con.writeObject (outString);                                         // escrever mensagem
        inString= (String) con.readObject();
        reply= new Message(inString, isXML());                             // le resposta
        
        if (!reply.getIdMethod().equals(MsgIndex.AS_writeReinOnStable)) {         // verificar resposta
            GenericIO.writelnString("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
            GenericIO.writelnString(reply.toString ());
            System.exit(1);
        }
        con.close();                                                     // fecha coneção
    }
    //_____________________________________

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