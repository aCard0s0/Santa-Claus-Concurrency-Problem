/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import client.shareRegions.RepositoryStub;
import client.shareRegions.SantaHouseStub;
import client.shareRegions.ToyFactoryStub;
import client.shareRegions.TripAroundStub;
import configs.Ips;
import configs.Ports;
import identities.Santa;

/**
 *
 * @author acardoso
 */
public class SantaMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        /**     Instanciar Regiões partilhadas que esta entidade utiliza
        *   Start & Join da Thread do Pai Natal (Santa)
        */        
        RepositoryStub logStub = new RepositoryStub(Ips.HOST_REPOSITORY, Ports.PORT_REPOSITORY);
        SantaHouseStub houseStub = new SantaHouseStub(Ips.HOST_SANTAHOUSE, Ports.PORT_SANTAHOUSE);
        TripAroundStub tripStub = new TripAroundStub(Ips.HOST_TRIPAROUND, Ports.PORT_TRIPARROUND);
        ToyFactoryStub toyFactoryStub = new ToyFactoryStub(Ips.HOST_TOYFACTORY, Ports.PORT_TOYFACTORY);
        
        /**    Start & Create threads.     */
        Santa thdSanta = new Santa(logStub, houseStub, tripStub);
        thdSanta.start();
        System.out.println("Thread Santa lançada.");
        
        /**      Wait for Threads.       */
        try {
            thdSanta.join();
            System.out.println("Thread Santa terminada.");
        } catch(Exception e) {
            System.err.println("Erro apanhar Thread-Santa ");
            System.err.println("Caught Exception: " + e.getMessage());
            System.exit(1);
        }
        
        tripStub.shutDown();
        toyFactoryStub.shutDown();
        houseStub.shutDown();
        logStub.shutDown();
    }
}
