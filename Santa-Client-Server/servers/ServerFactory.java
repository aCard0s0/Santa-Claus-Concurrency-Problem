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
import interfaces.FactoryInter;
import java.net.SocketTimeoutException;
import proxys.FactoryProxy;
import shareRegions.ToyFactory;

/**
 *
 * @author João Gonçalo Ribeiro
 */
public class ServerFactory {
   
    public static boolean waitConnection;                              // sinalização de actividade
    
    public static void main (String [] args) {

        RepositoryStub repo;                            // arg de entrada da Regiao Partilhada
        ToyFactory factory;                                  // ToyFactory (representa o serviço a ser prestado)
        FactoryInter factoryInter;                    // interface à Stable
        ServerCom scon, sconi;                          // canais de comunicação
        FactoryProxy toyFactProxy;                        // thread agente prestador do serviço

        /* estabelecimento do servico */
        
        repo = new RepositoryStub(Ips.HOST_REPOSITORY, Ports.PORT_REPOSITORY);    // Instanciação do Repositorio
        factory = new ToyFactory(repo);                              // activação do serviço
        factoryInter = new FactoryInter(factory);                // activação do interface com o serviço
        scon = new ServerCom(Ports.PORT_TOYFACTORY);                // criação do canal de escuta e sua associação
        scon.start ();                                          // com o endereço público
        GenericIO.writelnString ("O serviço com ToyFactory foi estabelecido!");
        GenericIO.writelnString ("O servidor esta em escuta...");

        /* processamento de pedidos */

        waitConnection = true;
        while (waitConnection) {
            try { 
                sconi = scon.accept ();                          // entrada em processo de escuta
                toyFactProxy = new FactoryProxy (sconi, factoryInter);  // lançamento do agente prestador do serviço
                toyFactProxy.start ();
            } catch (SocketTimeoutException e){    
                scon.end();                                         // terminação de operações
                GenericIO.writelnString ("O servidor foi desactivado.");
            }
            
        }
    }
}
