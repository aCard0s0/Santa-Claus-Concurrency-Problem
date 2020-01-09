/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import configs.General;
import proxys.FactoryProxy;
import servers.ServerFactory;
import shareRegions.ToyFactory;
import states.GnomeStates;
import structures.Messages.Message;
import structures.Messages.MessageException;
import structures.Messages.MsgIndex;

/**
 *
 * @author João Gonçalo Ribeiro
 */
public class FactoryInter {

    private final ToyFactory factory;           // Instaciamento da regiao partilhada
    
    public FactoryInter(ToyFactory factory) {
        this.factory = factory;
    }
    
    public Message processAndReply(Message inMsg) throws MessageException {
        
        Message reply = null;               // Mensagem de resposta
        GnomeStates state = null;           // Estado enviado na resposta
        int rsp;                            // resposta do numero de Gnomes na fila
            
        /* Validação & Processamento da mensagem recebida */
        switch(inMsg.getIdMethod()){
            
            default: 
                System.err.println("Mensagem desconhecida!");
                System.exit(1);
            
            case MsgIndex.RS_work:
                factory.work();                         // processamento
                reply = new Message(MsgIndex.AS_work);  // gerar resposta
                break;
                
            case MsgIndex.RS_needAdvice:
                if (inMsg.getIdSender() < 0 || inMsg.getIdSender() >= General.NUM_GNOMES)
                    throw new MessageException("Id do Gnome inválido!", inMsg);
                
                rsp = factory.needAdvice(inMsg.getIdSender());
                
                reply = new Message(MsgIndex.AS_needAdvice, rsp);  // gerar resposta
                break;
                
            case MsgIndex.RS_goBackToWork:
                if (inMsg.getIdSender() < 0 || inMsg.getIdSender() >= General.NUM_GNOMES)
                    throw new MessageException("Id do Gnome inválido!", inMsg);
                if (inMsg.getState() != GnomeStates.CONSULTINGSANTA)
                    throw new MessageException("Estado da Rena inválido!", inMsg);
                
                factory.goBackToWork(inMsg.getIdSender());      // processamento
                
                state = ((FactoryProxy) Thread.currentThread()).getGnomeState();
                reply = new Message(MsgIndex.AS_goBackToWork, state);  // gerar resposta
                break;
            
            case MsgIndex.RS_shutDown:                                                        // shutdown do servidor
                
                (((FactoryProxy) (Thread.currentThread ())).getScon ()).setTimeout (10);
                ServerFactory.waitConnection = false;

                reply = new Message (MsgIndex.AS_shutDown);            // gerar resposta
                break;
        }
        
        return reply;
    }
}
