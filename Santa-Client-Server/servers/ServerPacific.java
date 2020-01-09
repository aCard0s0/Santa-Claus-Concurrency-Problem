/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servers;

import client.shareRegions.RepositoryStub;
import configs.Ips;
import configs.Ports;
import genclass.GenericIO;
import interfaces.PacificInterface;
import java.net.SocketTimeoutException;
import proxys.PacificProxy;
import shareRegions.SouthPacific;

/**
 *
 * @author João Gonçalo Ribeiro
 */
public class ServerPacific {
    
    public static boolean waitConnection;                              // sinalização de actividade
    
    public static void main (String [] args) {

        RepositoryStub repo;                            // arg de entrada da Regiao Partilhada
        SouthPacific holiday;                                  // SouthPacific (representa o serviço a ser prestado)
        PacificInterface holidayInter;                    // interface à Stable
        ServerCom scon, sconi;                          // canais de comunicação
        PacificProxy holidayProxy;                        // thread agente prestador do serviço

        /* estabelecimento do servico */
        
        repo = new RepositoryStub(Ips.HOST_REPOSITORY, Ports.PORT_REPOSITORY);    // Instanciação do Repositorio
        holiday = new SouthPacific(repo);                              // activação do serviço
        holidayInter = new PacificInterface(holiday);                // activação do interface com o serviço
        scon = new ServerCom(Ports.PORT_SOUTHPACIFIC);                // criação do canal de escuta e sua associação
        scon.start ();                                          // com o endereço público
        GenericIO.writelnString ("O serviço com SouthPacific foi estabelecido!");
        GenericIO.writelnString ("O servidor esta em escuta...");

        /* processamento de pedidos */

        waitConnection = true;
        while (waitConnection) {
            try { 
                sconi = scon.accept ();                          // entrada em processo de escuta
                holidayProxy = new PacificProxy (sconi, holidayInter);  // lançamento do agente prestador do serviço
                holidayProxy.start ();
            } catch (SocketTimeoutException e){    
                scon.end();                                         // terminação de operações
                GenericIO.writelnString ("O servidor foi desactivado.");
            }
        }
    }
}
