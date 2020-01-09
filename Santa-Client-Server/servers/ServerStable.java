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
import interfaces.StableInter;
import java.net.SocketTimeoutException;
import proxys.StableProxy;
import shareRegions.Stable;

/**
 *
 * @author acardoso
 */
public class ServerStable {
    
    public static boolean waitConnection;                              // sinalização de actividade
    
    /**
    *   Programa principal.
     * @param args
    */
    public static void main (String [] args) {
        
        RepositoryStub repo;                            // arg de entrada da Regiao Partilhada
        Stable stable;                                  // Stable (representa o serviço a ser prestado)
        StableInter stableInter;                    // interface à Stable
        ServerCom scon, sconi;                          // canais de comunicação
        StableProxy stableProxy;                        // thread agente prestador do serviço

        /* estabelecimento do servico */
        
        repo = new RepositoryStub(Ips.HOST_REPOSITORY, Ports.PORT_REPOSITORY);    // Instanciação do Repositorio
        stable = new Stable(repo);                                      // Instanciação do serviço
        stableInter = new StableInter(stable);                // activação do interface com o serviço
        scon = new ServerCom(Ports.PORT_STABLE);                // criação do canal de escuta e sua associação
        scon.start ();                                          // com o endereço público
        GenericIO.writelnString ("O serviço com Stable foi estabelecido!");
        GenericIO.writelnString ("O servidor esta em escuta...");

        /* processamento de pedidos */

        waitConnection = true;
        while (waitConnection) {
            try { 
                sconi = scon.accept ();                          // entrada em processo de escuta
                stableProxy = new StableProxy (sconi, stableInter);  // lançamento do agente prestador do serviço
                stableProxy.start();
            } catch (SocketTimeoutException e){    
                scon.end();                                         // terminação de operações
                GenericIO.writelnString ("O servidor foi desactivado.");
            }
        }
    }
}
