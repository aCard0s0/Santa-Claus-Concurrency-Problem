package shareRegions;

import client.shareRegions.RepositoryStub;
import states.ReindeerStates;
import states.SantaStates;
import structures.Mem.MemException;
import structures.Mem.MemFIFO;
import structures.Semaphore.Semaphore;
import configs.General;
import proxys.TripProxy;

/**
 *
 * @author andre cardoso 65069 & joao ribeiro 64649
 */
public class TripAroundWorld {
    
    private RepositoryStub log;                 /* Atributo relativo a InfoRepository */
    private MemFIFO<Integer> harnessRein;       /* Grupo de renas prontas para a viagem */
    private int nHarnessReindeer;               /* Para contar as renas na viagem */
    private int nReinOnSlege;                   /* Para contar as renas no treno */
    private Semaphore mutex;                    /* Semaforo para a regiao critica */
    private Semaphore santaWaitReindeers;       /* Semaforo para o Santa esperar pelas renas para serem amarradas*/
    private Semaphore santaWait;                /* Semaforo para Santa espera pela ultima rena chegue ao treno */
    private Semaphore[] waitForTrip;            /* Semaforo para as renas esperarem pelo Santa ir a Trip */
    private Semaphore[] readyForHarness;        /* Semaforo de renas prontas para Trip */
 
    //  Construtor
    public TripAroundWorld(RepositoryStub log) {
        this.log = log;
        try {
            harnessRein = new MemFIFO<>(new Integer[General.NUM_REINDEER]);
        } catch (MemException ex) {
        }
        nHarnessReindeer = 0;
        nReinOnSlege = 0;
        mutex = new Semaphore();
        mutex.up();                                                     // verde
        santaWaitReindeers = new Semaphore();
        santaWait = new Semaphore();
        waitForTrip = new Semaphore[General.NUM_REINDEER];
        for(int i=0; i < waitForTrip.length; i++)
            waitForTrip[i] = new Semaphore();
        readyForHarness = new Semaphore[General.NUM_REINDEER];
        for(int i=0; i < General.NUM_REINDEER; i++)
           readyForHarness[i] = new Semaphore();
    }
    
    //  Usado pelo (identities).Santa
    public void harnessReindeers() {
        
        mutex.down();
        ((TripProxy) Thread.currentThread()).setSantaState(SantaStates.DISTRIBUTINGGIFTS);
        log.writeSantaState(SantaStates.DISTRIBUTINGGIFTS); 
        
        mutex.up();
        
        nReinOnSlege= 0;
        while(nReinOnSlege < General.NUM_REINDEER){
            santaWaitReindeers.down();              // espera pelas renas, para as amarrar
            mutex.down();
            try {
                readyForHarness[ harnessRein.read() ].up();               // rena foi amarrada, vai para o treno

            } catch (MemException ex) {
            }
            mutex.up();
            nReinOnSlege++;                         // inc. renas no treno
        }
        santaWait.down();                           // Santa espera por todas as renas no treno
    }
    
    //  Usado pelo (identities).Reindeer
    public void groupAtSledge(int idReindeer){
        
        mutex.down();
        try {
            harnessRein.write(idReindeer);          // é adicionado uma rena quando Santa as chama. ( stable.callReindeer() )
        } catch (MemException ex) {
        }
        mutex.up();
        
        santaWaitReindeers.up();                // rena preparada para ser amarrada.
        readyForHarness[idReindeer].down();     // renas bloqueiada, porque ainda nao foi amarrada.
        
        mutex.down();
        ((TripProxy) Thread.currentThread()).setReindeerState(ReindeerStates.PULLINGSLEDGE);
        log.writeReindeerOnSledge(idReindeer);
        
        nHarnessReindeer++;                     // inc. renas amarradas
        
        if(nHarnessReindeer == General.NUM_REINDEER){   // ultima rena chega ao treno
            santaWait.up();                             // Santa já tem todas as renas
            nHarnessReindeer = 0;                       // reset
        }
        mutex.up();
    }
    
    //  Usado pelo (identities).Reindeer
    public void followSanta(int idReindeer){
        waitForTrip[idReindeer].down(); // renas esperam pela ordem do Santa
    }
    
    //  Usado pelo (identities).Santa
    public void travelAround(){
        try {
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException ex) {
        }
    }
    
    //  Usado pelo (identities).Santa
    public void goHome(){
        
        mutex.down();
        // Santa vai para casa
        ((TripProxy) Thread.currentThread()).setSantaState(SantaStates.RESTING);
        log.writeSantaState(SantaStates.RESTING);
        
        // Santa dá ordem as renas para seguirem para ferias
        for(int i=0; i < General.NUM_REINDEER; i++)
            waitForTrip[i].up();

        mutex.up();
    }
}