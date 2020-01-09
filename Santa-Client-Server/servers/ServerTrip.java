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
import interfaces.TripInter;
import java.net.SocketTimeoutException;
import proxys.TripProxy;
import shareRegions.TripAroundWorld;

/**
 *
 * @author João Gonçalo Ribeiro
 */
public class ServerTrip {
    
    public static boolean waitConnection;                              // sinalização de actividade
    
    public static void main (String [] args) {

        RepositoryStub repo;                            // arg de entrada da Regiao Partilhada
        TripAroundWorld trip;                                  // TripAroundWorld (representa o serviço a ser prestado)
        TripInter tripInter;                    // interface à Stable
        ServerCom scon, sconi;                          // canais de comunicação
        TripProxy holidayProxy;                        // thread agente prestador do serviço

        /* estabelecimento do servico */
        
        repo = new RepositoryStub(Ips.HOST_REPOSITORY, Ports.PORT_REPOSITORY);    // Instanciação do Repositorio
        trip = new TripAroundWorld(repo);                              // activação do serviço
        tripInter = new TripInter(trip);                        // activação do interface com o serviço
        scon = new ServerCom(Ports.PORT_TRIPARROUND);                // criação do canal de escuta e sua associação
        scon.start ();                                          // com o endereço público
        GenericIO.writelnString ("O serviço TripAroundWold foi estabelecido!");
        GenericIO.writelnString ("O servidor esta em escuta...");

        /* processamento de pedidos */

        waitConnection = true;
        while (waitConnection) {
            try { 
                sconi = scon.accept ();                          // entrada em processo de escuta
                holidayProxy = new TripProxy (sconi, tripInter);  // lançamento do agente prestador do serviço
                holidayProxy.start ();
            } catch (SocketTimeoutException e){
                scon.end();                                         // terminação de operações
                GenericIO.writelnString ("O servidor foi desactivado.");
            }
        }
    }
}
