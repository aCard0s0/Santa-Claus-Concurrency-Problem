/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import configs.General;
import proxys.RepositoryProxy;
import servers.ServerRepository;
import shareRegions.InfoRepository;
import structures.Messages.Message;
import structures.Messages.MessageException;
import structures.Messages.MsgIndex;

/**
 *
 * @author João Gonçalo Ribeiro
 */
public class RepositoryInter {

    private final InfoRepository log;               // Instaciamento da regiao partilhada
    //
    
    public RepositoryInter(InfoRepository log) {
        this.log = log;
    }
    
    public Message processAndReply(Message inMsg) throws MessageException {
        
        Message reply = null;                   // Mensagem de resposta
        boolean rsp;      // Resposta que identifica se o ciclo de vida de uma identidade acaba

        /* Validação & Processamento da mensagem recebida */
        switch(inMsg.getIdMethod()){
            
            default: 
                System.err.println("Mensagem desconhecida!");
                System.exit(1);
            
            case MsgIndex.RS_incYears:
                log.incYears();                             // processamento
                reply = new Message(MsgIndex.AS_incYears);  // gerar resposta
                break;
                
            case MsgIndex.RS_incConsul:
                log.incConsul();                             // processamento
                reply = new Message(MsgIndex.AS_incConsul);  // gerar resposta
                break;
                
            case MsgIndex.RS_endSanta:
                rsp = log.endSanta();                   // processamento
                if(rsp)
                    reply = new Message(MsgIndex.AS_endSantaT);  // gerar resposta (true)
                else
                    reply = new Message(MsgIndex.AS_endSantaF);  // gerar resposta (false)
                break;
                
            case MsgIndex.RS_endReindeer:
                rsp = log.endReindeer();                    // processamento
                if(rsp)
                    reply = new Message(MsgIndex.AS_endReindeerT);  // gerar resposta (true)
                else
                    reply = new Message(MsgIndex.AS_endReindeerF);  // gerar resposta (false)
                break;
            
            case MsgIndex.RS_endGnome:
                if (inMsg.getIdSender() < 0 || inMsg.getIdSender() >= General.NUM_GNOMES)
                    throw new MessageException("Id do Gnome inválido!", inMsg);
                
                rsp = log.endGnome(inMsg.getIdSender());            // processamento
                
                if(rsp)
                    reply = new Message(MsgIndex.AS_endGnomeT);  // gerar resposta (true)
                else
                    reply = new Message(MsgIndex.AS_endGnomeF);  // gerar resposta (false)
                break;
                
            case MsgIndex.RS_setLastConsul:
                if (inMsg.getIdSender() < 0 || inMsg.getIdSender() >= General.NUM_GNOMES)
                    throw new MessageException("Id do Gnome inválido!", inMsg);
                
                log.setLastConsul(inMsg.getIdSender());         // processamento

                reply = new Message(MsgIndex.AS_setLastConsul);  // gerar resposta
                break;
                
            case MsgIndex.RS_writeSantaState:
                log.writeSantaState(inMsg.getState());     // processamento
                reply = new Message(MsgIndex.AS_writeSantaState);  // gerar resposta
                break;
                
            case MsgIndex.RS_writeMeetGnomes:
                log.writeMeetGnomes();                          // processamento
                
                reply = new Message(MsgIndex.AS_writeMeetGnomes);  // gerar resposta
                break;
                
            case MsgIndex.RS_writeGnomeKnoock:
                log.writeGnomeKnoock();                         // processamento
                
                reply = new Message(MsgIndex.AS_writeGnomeKnoock);  // gerar resposta
                break;
                
            case MsgIndex.RS_writeGnomeState:
                if (inMsg.getIdSender() < 0 || inMsg.getIdSender() >= General.NUM_GNOMES)
                    throw new MessageException("Id do Gnome inválido!", inMsg);
                
                log.writeGnomeState(inMsg.getIdSender(), inMsg.getState()); // processamento

                reply = new Message(MsgIndex.AS_writeGnomeState);  // gerar resposta
                break;
                
            case MsgIndex.RS_writeGnomeOnGroup:
                if (inMsg.getIdSender() < 0 || inMsg.getIdSender() >= General.NUM_GNOMES)
                    throw new MessageException("Id do Gnome inválido!", inMsg);
                
                log.writeGnomeOnGroup(inMsg.getIdSender(), inMsg.getAnswer());// processamento
                
                reply = new Message(MsgIndex.AS_writeGnomeOnGroup);  // gerar resposta
                break;
                
            case MsgIndex.RS_writeGnomeWithSanta:
                if (inMsg.getIdSender() < 0 || inMsg.getIdSender() >= General.NUM_GNOMES)
                    throw new MessageException("Id do Gnome inválido!", inMsg);
                
                log.writeGnomeWithSanta(inMsg.getIdSender());       // processamento
                
                reply = new Message(MsgIndex.AS_writeGnomeWithSanta);  // gerar resposta
                break;
                
            case MsgIndex.RS_writeReindeerState:
                if (inMsg.getIdSender() < 0 || inMsg.getIdSender() >= General.NUM_REINDEER)
                    throw new MessageException("Id da Rena inválido!", inMsg);
                
                log.writeReindeerState(inMsg.getIdSender(), inMsg.getState()); // processamento
                
                reply = new Message(MsgIndex.AS_writeReindeerState);  // gerar resposta
                break;
                
            case MsgIndex.RS_writeReindeerOnSledge:
                if (inMsg.getIdSender() < 0 || inMsg.getIdSender() >= General.NUM_REINDEER)
                    throw new MessageException("Id da Rena inválido!", inMsg);
                
                log.writeReindeerOnSledge(inMsg.getIdSender());     // processamento

                reply = new Message(MsgIndex.AS_writeReindeerOnSledge);  // gerar resposta
                break;
            
            case MsgIndex.RS_writeReinderKnoock:
                log.writeReinderKnoock();                       // processamento
                
                reply = new Message(MsgIndex.AS_writeReinderKnoock);  // gerar resposta
                break;
                
            case MsgIndex.RS_writeReinOnStable:
                if (inMsg.getIdSender() < 0 || inMsg.getIdSender() >= General.NUM_REINDEER)
                    throw new MessageException("Id da Rena inválido!", inMsg);
                
                log.writeReinOnStable(inMsg.getIdSender(), inMsg.getAnswer());  // processamento

                reply = new Message(MsgIndex.AS_writeReinOnStable);  // gerar resposta
                break;
                
            case MsgIndex.RS_shutDown:                               // shutdown do servidor
                
                (((RepositoryProxy) (Thread.currentThread ())).getScon ()).setTimeout (10);
                ServerRepository.waitConnection = false;

                reply = new Message (MsgIndex.AS_shutDown);            // gerar resposta
                break;
        }
        
        return reply;
    }
    
}
