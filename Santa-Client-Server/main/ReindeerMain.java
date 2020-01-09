/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import client.shareRegions.RepositoryStub;
import client.shareRegions.SantaHouseStub;
import client.shareRegions.SouthPacificStub;
import client.shareRegions.StableStub;
import client.shareRegions.TripAroundStub;
import configs.General;
import configs.Ips;
import configs.Ports;
import identities.Reindeer;

/**
 *
 * @author acardoso
 */
public class ReindeerMain {

    /**
     *  Lançamento das Threads da Rena
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        /**     Instanciar Regiões partilhadas que esta entidade utiliza
        *       Start & Join da Thread do Rena (Reindeer)
        */        
        RepositoryStub logStub = new RepositoryStub(Ips.HOST_REPOSITORY, Ports.PORT_REPOSITORY);
        TripAroundStub tripStub = new TripAroundStub(Ips.HOST_TRIPAROUND, Ports.PORT_TRIPARROUND);
        StableStub stableStub = new StableStub(Ips.HOST_STABLE, Ports.PORT_STABLE);
        SouthPacificStub southPacificStub = new SouthPacificStub(Ips.HOST_SOUTHPACIFIC, Ports.PORT_SOUTHPACIFIC);
        SantaHouseStub santaHouseStub = new SantaHouseStub(Ips.HOST_SANTAHOUSE, Ports.PORT_SANTAHOUSE);
        
        /**    Create & Start threads.     */
        Reindeer[]  thdReindeer = new Reindeer[General.NUM_REINDEER];
        for(int i=0; i< General.NUM_REINDEER; i++) {
            thdReindeer[i] = new Reindeer(i, logStub, southPacificStub, stableStub, tripStub, santaHouseStub);
            thdReindeer[i].start();
            System.out.println("Thread-"+i+" Rena, lançada.");
        }
        
        /**      Wait for Threads.       */
        for (int i = 0; i < General.NUM_REINDEER; i++) {
            try {
                thdReindeer[i].join();
                System.out.println("Thread-"+i+" Rena, terminada.");
            } catch(Exception e) {
            System.err.println("Erro apanhar Thread-Reindeer ");
            System.err.println("Caught Exception: " + e.getMessage());
            System.exit(1);
            }
        }
        
        stableStub.shutDown();
        southPacificStub.shutDown();
        
    }
    
}
