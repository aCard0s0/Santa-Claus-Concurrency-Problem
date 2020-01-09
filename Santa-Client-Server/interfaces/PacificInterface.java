/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import configs.General;
import proxys.PacificProxy;
import servers.ServerPacific;
import shareRegions.SouthPacific;
import states.ReindeerStates;
import structures.Messages.Message;
import structures.Messages.MessageException;
import structures.Messages.MsgIndex;

/**
 *
 * @author João Gonçalo Ribeiro
 */
public class PacificInterface {

    private final SouthPacific holiday;               // Instaciamento da regiao partilhada
    
    public PacificInterface(SouthPacific holiday) {
        this.holiday = holiday;
    }
    
    public Message processAndReply(Message inMsg) throws MessageException {
        
        Message reply = null;                   // Mensagem de resposta
        ReindeerStates state = null;            // Estado enviado na resposta
        
        /* Validação & Processamento da mensagem recebida */
        switch(inMsg.getIdMethod()){
            
            default: 
                System.err.println("Mensagem desconhecida!");
                System.exit(1);
            
            case MsgIndex.RS_enjoyView:
                if (inMsg.getIdSender() < 0 || inMsg.getIdSender() >= General.NUM_REINDEER)
                    throw new MessageException("Id da Rena inválido!", inMsg);
                
                holiday.enjoyView(inMsg.getIdSender());     // processamento
                
                reply = new Message(MsgIndex.AS_enjoyView);  // gerar resposta
                break;
                
            case MsgIndex.RS_leaveHolidays:
                if (inMsg.getIdSender() < 0 || inMsg.getIdSender() >= General.NUM_REINDEER)
                    throw new MessageException("Id da Rena inválido!", inMsg);
                if (inMsg.getState() != ReindeerStates.PULLINGSLEDGE)
                    throw new MessageException("Estado da Rena inválido!", inMsg);
                
                holiday.leaveHolidays(inMsg.getIdSender());     // processamento
                
                state = ((PacificProxy) Thread.currentThread()).getReindeerState();
                reply = new Message(MsgIndex.AS_leaveHolidays, state);  // gerar resposta
                break;
                
            case MsgIndex.RS_shutDown:                                  // shutdown do servidor
                
                (((PacificProxy) (Thread.currentThread ())).getScon ()).setTimeout (10);
                ServerPacific.waitConnection = false;

                reply = new Message (MsgIndex.AS_shutDown);            // gerar resposta
                break;
        }
        
        return reply;
    }
}

