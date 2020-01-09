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
import interfaces.HouseInter;
import java.net.SocketTimeoutException;
import proxys.HouseProxy;
import shareRegions.SantaHouse;

/**
 *
 * @author João Gonçalo Ribeiro
 */
public class ServerHouse {
    
    public static boolean waitConnection;                              // sinalização de actividade
    
    public static void main (String [] args) {

        RepositoryStub repo;                            // arg de entrada da Regiao Partilhada
        SantaHouse house;                                  // Stable (representa o serviço a ser prestado)
        HouseInter houseInter;                    // interface à SantaHouse
        ServerCom scon, sconi;                          // canais de comunicação
        HouseProxy houseProxy;                        // thread agente prestador do serviço

        /* estabelecimento do servico */
        
        repo = new RepositoryStub(Ips.HOST_REPOSITORY, Ports.PORT_REPOSITORY);    // Instanciação do Repositorio
        house = new SantaHouse(repo);                              // activação do serviço
        houseInter = new HouseInter(house);                // activação do interface com o serviço
        scon = new ServerCom(Ports.PORT_SANTAHOUSE);                // criação do canal de escuta e sua associação
        scon.start ();                                          // com o endereço público
        GenericIO.writelnString ("O serviço com SantaHouse foi estabelecido!");
        GenericIO.writelnString ("O servidor esta em escuta...");

        /* processamento de pedidos */

        waitConnection = true;
        while (waitConnection) {
            try { 
                sconi = scon.accept ();                          // entrada em processo de escuta
                houseProxy = new HouseProxy (sconi, houseInter);  // lançamento do agente prestador do serviço
                houseProxy.start();
            } catch (SocketTimeoutException e){    
                scon.end();                                         // terminação de operações
                GenericIO.writelnString ("O servidor foi desactivado.");
            }
            
        }
    }
}
