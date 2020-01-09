package main;

import identities.Gnome;
import identities.Reindeer;
import identities.Santa;
import shareRegions.InfoRepository;
import shareRegions.SantaHouse;
import shareRegions.SouthPacific;
import shareRegions.Stable;
import shareRegions.ToyFactory;
import shareRegions.TripAroundWorld;
import configs.General;

/**
 *
 * @author andre cardoso 65069 & joao peixe 64649
 */
public class SantaClaus {

    /**
     *  Main Function.
     *  Instanciação de todas as classes (shareRegions)
     *  Instanciação e lançaento das Threads (identities)
     * @param args
     */
    public static void main(String args[]) {
        
        /** Instanciar regiões partilhadas. */
        InfoRepository  log         = new InfoRepository();  /* Repositorio de info */
        SantaHouse      house       = new SantaHouse(log);
        Stable          stable      = new Stable(log);
        SouthPacific    holidays    = new SouthPacific(log);
        ToyFactory      factory     = new ToyFactory(log);
        TripAroundWorld trip        = new TripAroundWorld(log);
        
        /** Instanciar identidades. */
        Santa       thdSanta    = new Santa(log, house, trip);
        Gnome[]     thdGnomes   = new Gnome[General.NUM_GNOMES];         
        Reindeer[]  thdReindeer = new Reindeer[General.NUM_GNOMES];
        
        /**    Start & Create threads.     */
        //Santa
        thdSanta.start();
        //  Gnomes
        for(int i=0; i< General.NUM_GNOMES; i++) {
            thdGnomes[i] = new Gnome(i, log, factory, house);
            thdGnomes[i].start();
        }
        //  Reindeers
        for(int i=0; i< General.NUM_REINDEER; i++) {
            thdReindeer[i] = new Reindeer(i, log, holidays, stable, trip, house);
            thdReindeer[i].start();
        }
        
        /**      Wait for Threads.       */
        // Santa
        try {
            thdSanta.join();
        } catch (InterruptedException e) {
            System.err.println("Caught Exception: " + e.getMessage());
            System.exit(1);
        }
        //  Gnomes
        for(int t=0; t < General.NUM_GNOMES; t++) {
            try {
                thdGnomes[t].join();
            } catch(Exception e) {
                System.err.println("Caught Exception: " + e.getMessage());
                System.exit(1);
            }
        }
        //  Reindeers
        for(int t=0; t < General.NUM_REINDEER; t++) {
            try {
                thdReindeer[t].join();
            } catch(Exception e) {
                System.err.println("Caught Exception: " + e.getMessage());
                System.exit(1);
            }
        }
    }
}