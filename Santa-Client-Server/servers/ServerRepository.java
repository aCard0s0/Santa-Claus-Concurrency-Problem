/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servers;

import configs.Ports;
import genclass.GenericIO;
import interfaces.RepositoryInter;
import java.net.SocketTimeoutException;
import proxys.RepositoryProxy;
import shareRegions.InfoRepository;

/**
 *
 * @author João Gonçalo Ribeiro
 */
public class ServerRepository {
    
    public static boolean waitConnection;                              // sinalização de actividade
    
    public static void main (String [] args) {

        InfoRepository repo;                            // arg de entrada da Regiao Partilhada
        RepositoryInter repoInter;
        ServerCom scon, sconi;                          // canais de comunicação
        RepositoryProxy repoProx;
        /* estabelecimento do servico */
        
        repo = new InfoRepository();    // Instanciação do Repositorio
        repoInter = new RepositoryInter(repo);
        scon = new ServerCom(Ports.PORT_REPOSITORY);                // criação do canal de escuta e sua associação
        scon.start();                                          // com o endereço público
        GenericIO.writelnString ("O serviço com Repositorio foi estabelecido!");
        GenericIO.writelnString ("O servidor esta em escuta...");

        /* processamento de pedidos */

        waitConnection = true;
        while (waitConnection) {
            try { 
                sconi = scon.accept ();                          // entrada em processo de escuta
                repoProx = new RepositoryProxy (sconi, repoInter);  // lançamento do agente prestador do serviço
                repoProx.start ();
            } catch (SocketTimeoutException e) {    
                scon.end();                                         // terminação de operações
                GenericIO.writelnString ("O servidor foi desactivado.");
            }
        }
        
        
    }
}
