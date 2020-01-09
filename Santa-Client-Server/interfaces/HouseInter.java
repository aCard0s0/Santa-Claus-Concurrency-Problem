/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import configs.General;
import proxys.HouseProxy;
import servers.ServerHouse;
import shareRegions.SantaHouse;
import states.GnomeStates;
import states.SantaStates;
import structures.Messages.Message;
import structures.Messages.MessageException;
import structures.Messages.MsgIndex;

/**
 *
 * @author João Gonçalo Ribeiro
 */
public class HouseInter {

    private final SantaHouse house;              // Instaciamento da regiao partilhada
    
    public HouseInter(SantaHouse house) {
        this.house = house;
    }
    
    public Message processAndReply(Message inMsg) throws MessageException {
        
        Message reply = null;                   // Mensagem de resposta
        char rsp;                               // Resposta para saber a identidade que bater a porta
        SantaStates sState = null;               // Estado enviado na resposta
        GnomeStates gState = null;               // Estado enviado na resposta
                
        /* Validação & Processamento da mensagem recebida */
        switch(inMsg.getIdMethod()){
            
            default: 
                System.err.println("Mensagem desconhecida!");
                System.exit(1);
            
            case MsgIndex.RS_goToSleepSanta:
                house.goToSleepSanta();                 // processamento
                
                reply = new Message(MsgIndex.AS_goToSleepSanta);  // gerar resposta
                break;
                
            case MsgIndex.RS_openDoor:
                rsp = house.openDoor();     // processamento

                sState = ((HouseProxy) Thread.currentThread()).getSantaState();
                if(rsp == 'G')
                    reply = new Message(MsgIndex.AS_openDoor_G, sState);  // gerar resposta (Gnomes)
                else if (rsp == 'R')
                    reply = new Message(MsgIndex.AS_openDoor_R, sState);  // gerar resposta (Renas)
                break;
                
            case MsgIndex.RS_enterHouse:
                if (inMsg.getIdSender() < 0 || inMsg.getIdSender() >= General.NUM_GNOMES)
                    throw new MessageException("Id do Gnome inválido!", inMsg);
                
                house.enterHouse(inMsg.getIdSender());          // processamento
                
                gState = ((HouseProxy) Thread.currentThread()).getGnomeState();
                reply = new Message(MsgIndex.AS_enterHouse, gState);    // gerar resposta
                break;
                
            case MsgIndex.RS_inviteIn:
                house.inviteIn();                           // processamento
                
                sState = ((HouseProxy) Thread.currentThread()).getSantaState();
                reply = new Message(MsgIndex.AS_inviteIn, sState);    // gerar resposta
                break;
                
            case MsgIndex.RS_listenGnomes:
                
                house.listenGnomes();                           // processamento
                reply = new Message(MsgIndex.AS_listenGnomes);    // gerar resposta
                break;
                
            case MsgIndex.RS_talk:
                if (inMsg.getIdSender() < 0 || inMsg.getIdSender() >= General.NUM_GNOMES)
                    throw new MessageException("Id do Gnome inválido!", inMsg);
                
                house.talk(inMsg.getIdSender());          // processamento
                reply = new Message(MsgIndex.AS_talk);    // gerar resposta
                break;
                
            case MsgIndex.RS_sayGoodbye:
                if (inMsg.getState()!= SantaStates.MEETINGELVES)
                    throw new MessageException("Estado do Santa inválido!", inMsg);
                
                house.sayGoodbye();                 // processamento
                
                sState = ((HouseProxy) Thread.currentThread()).getSantaState();
                reply = new Message(MsgIndex.AS_sayGoodbye, sState);    // gerar resposta
                break;
                
            case MsgIndex.RS_isGnomesAtDoor:
                house.isGnomesAtDoor();             // processamento
                reply = new Message(MsgIndex.AS_isGnomesAtDoor);    // gerar resposta
                break;
            
            case MsgIndex.RS_isReindeerAtDoor:
                house.isReindeerAtDoor();                   // processamento
                reply = new Message(MsgIndex.AS_isReindeerAtDoor);    // gerar resposta
                break;
            
            case MsgIndex.RS_shutDown:                     // shutdown do servidor
                (((HouseProxy) (Thread.currentThread ())).getScon ()).setTimeout (10);
                ServerHouse.waitConnection = false;

                reply = new Message (MsgIndex.AS_shutDown);            // gerar confirmação
                break;
        }
        
        return reply;
    }
}
