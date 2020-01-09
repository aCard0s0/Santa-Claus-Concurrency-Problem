/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import client.shareRegions.RepositoryStub;
import client.shareRegions.SantaHouseStub;
import client.shareRegions.ToyFactoryStub;
import configs.General;
import configs.Ips;
import configs.Ports;
import identities.Gnome;

/**
 *
 * @author João Gonçalo Ribeiro
 */
public class GnomeMain {
    /**
     *  Lançamento das Threads do Gnome
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        /**     Instanciar Regiões partilhadas que esta entidade utiliza
        *       Start & Join da Thread do Gnomo (Gnome)
        */    
        
        RepositoryStub logStub = new RepositoryStub(Ips.HOST_REPOSITORY, Ports.PORT_REPOSITORY);
        SantaHouseStub santaHouseStub = new SantaHouseStub(Ips.HOST_SANTAHOUSE, Ports.PORT_SANTAHOUSE);
        ToyFactoryStub toyFactoryStub = new ToyFactoryStub(Ips.HOST_TOYFACTORY, Ports.PORT_TOYFACTORY);

        
        /**    Create & Start threads.     */
        Gnome[]  thdGnome = new Gnome[General.NUM_GNOMES];
        for(int i=0; i< General.NUM_REINDEER; i++) {
            thdGnome[i] = new Gnome(i, logStub, toyFactoryStub, santaHouseStub);
            thdGnome[i].start();
            System.out.println("Thread-"+i+" Gnome, lançada.");
        }
        
        /**      Wait for Threads.       */
        for(int t=0; t < General.NUM_GNOMES; t++) {
            try {
                thdGnome[t].join();
                System.out.println("Thread-"+t+" Gnome, terminada.");
            } catch(Exception e) {
                System.err.println("Erro apanhar Thread-Rena "+ t);
                System.err.println("Caught Exception: " + e.getMessage());
                System.exit(1);
            }
        }
        
        
        
    }
    
}
