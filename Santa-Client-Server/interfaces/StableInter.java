/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import configs.General;
import proxys.StableProxy;
import servers.ServerStable;
import shareRegions.Stable;
import states.ReindeerStates;
import structures.Messages.Message;
import structures.Messages.MessageException;
import structures.Messages.MsgIndex;

/**
 *
 * @author acardoso
 */
public class StableInter {

    private final Stable stable;                 // Instaciamento da regiao partilhada
    
    public StableInter(Stable stable) {
        this.stable = stable;
    }
    
    public Message processAndReply(Message inMsg) throws MessageException {
        
        Message reply = null;                   // Mensagem de resposta
        ReindeerStates rState = null;            // Estado enviado na resposta
        int rsp;                                // Resposta de quantas renas estao no estabulo
        
        /* Validação & Processamento da mensagem recebida */
        switch(inMsg.getIdMethod()){
            
            default: 
                System.err.println("Mensagem desconhecida!");
                System.exit(1);
            
            case MsgIndex.RS_goBackToStable:
                if (inMsg.getIdSender() < 0 || inMsg.getIdSender() >= General.NUM_REINDEER)
                    throw new MessageException("Id da Rena inválido!", inMsg);
                if (inMsg.getState() != ReindeerStates.HOLIDAYS)
                    throw new MessageException("Estado da Rena "+ inMsg.getIdSender() +" inválido!", inMsg);
                
                rsp = stable.goBackToStable(inMsg.getIdSender());     // processamento
                
                rState = ((StableProxy) Thread.currentThread()).getReindeerState();
                reply = new Message(MsgIndex.AS_goBackToStable, rsp, rState);  // gerar resposta
                break;
                
            case MsgIndex.RS_shutDown:                             // shutdown do servidor
                (((StableProxy) (Thread.currentThread ())).getScon ()).setTimeout (10);
                ServerStable.waitConnection = false;

                reply = new Message (MsgIndex.AS_shutDown);            // gerar confirmação
                break;
        }
        return reply;
    }
}
