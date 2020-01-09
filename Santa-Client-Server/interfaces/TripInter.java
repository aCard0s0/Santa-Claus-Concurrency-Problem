/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import configs.General;
import proxys.TripProxy;
import servers.ServerTrip;
import shareRegions.TripAroundWorld;
import states.ReindeerStates;
import states.SantaStates;
import structures.Messages.Message;
import structures.Messages.MessageException;
import structures.Messages.MsgIndex;

/**
 *
 * @author João Gonçalo Ribeiro
 */
public class TripInter {

   
    private final TripAroundWorld trip;           // Instaciamento da regiao partilhada
    
    public TripInter(TripAroundWorld trip) {
        this.trip = trip;
    }
    
    public Message processAndReply(Message inMsg) throws MessageException {
        
        Message reply = null;                       // Mensagem de resposta
        SantaStates sState = null;                  // Estado enviado na resposta
        ReindeerStates rState = null;            // Estado enviado na resposta
        
        /* Validação & Processamento da mensagem recebida */
        switch(inMsg.getIdMethod()){
            
            default: 
                System.err.println("Mensagem desconhecida!");
                System.exit(1);
            
            case MsgIndex.RS_harnessReindeers:
                if (inMsg.getState()!= SantaStates.DECIDING)
                    throw new MessageException("Estado do Santa inválido!", inMsg);
                
                trip.harnessReindeers();     // processamento

                sState = ((TripProxy) Thread.currentThread()).getSantaState();
                reply = new Message(MsgIndex.AS_harnessReindeers, sState);  // gerar resposta
                break;
                
            case MsgIndex.RS_groupAtSledge:
                if (inMsg.getIdSender() < 0 || inMsg.getIdSender() >= General.NUM_REINDEER)
                    throw new MessageException("Id da Rena inválido!", inMsg);
                if (inMsg.getState() != ReindeerStates.ATSTABLE)
                    throw new MessageException("Estado da Rena inválido!", inMsg);
                
                trip.groupAtSledge(inMsg.getIdSender());     // processamento
                
                rState = ((TripProxy) Thread.currentThread()).getReindeerState();
                reply = new Message(MsgIndex.AS_groupAtSledge, rState);  // gerar resposta
                break;
            
            case MsgIndex.RS_followSanta:
                if (inMsg.getIdSender() < 0 || inMsg.getIdSender() >= General.NUM_REINDEER)
                    throw new MessageException("Id da Rena inválido!", inMsg);
                
                trip.followSanta(inMsg.getIdSender());     // processamento

                reply = new Message(MsgIndex.AS_followSanta);  // gerar resposta
                break;
                
            case MsgIndex.RS_travelAround:
                trip.travelAround();     // processamento
                
                reply = new Message(MsgIndex.AS_travelAround);  // gerar resposta
                break;
                
            case MsgIndex.RS_goHome:
                if (inMsg.getState() != SantaStates.DISTRIBUTINGGIFTS)
                    throw new MessageException("Estado do Santa inválido!", inMsg);
                
                trip.goHome();                          // processamento
                
                sState = ((TripProxy) Thread.currentThread()).getSantaState();
                reply = new Message(MsgIndex.AS_goHome, sState);  // gerar resposta
                break;
            
            case MsgIndex.RS_shutDown:                       // shutdown do servidor
                (((TripProxy) (Thread.currentThread ())).getScon ()).setTimeout (10);
                ServerTrip.waitConnection = false;

                reply = new Message (MsgIndex.AS_shutDown);            // gerar confirmação
                break;
        }
        
        return reply;
    }
}
