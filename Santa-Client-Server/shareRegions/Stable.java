package shareRegions;

import client.shareRegions.RepositoryStub;
import states.ReindeerStates;
import structures.Semaphore.Semaphore;
import configs.General;
import proxys.StableProxy;

/**
 *
 * @author andre cardoso 65069 & joao ribeiro 64649
 */
public class Stable {
    
    private RepositoryStub log;             /* Atributo relativo a InfoRepository */
    private int nReindeers;                 /* numero de renas no estabulo */
    private Semaphore mutex;                /* Accesso á regiao critica */
    private Semaphore wait;                 /* Semaforo para as renas esperarem para ir para a Trips*/

    public Stable(RepositoryStub log) {
        this.log = log;
        nReindeers = 0;
        mutex = new Semaphore();                    
        mutex.up();                                 // entrada na regiao critica
        wait = new Semaphore();
    }
    
    //  Usado pelo (identities).Reindeer
    public int goBackToStable(int idReindeer){
        
        if (nReindeers == General.NUM_REINDEER)
            nReindeers = 0;                     // reset variavel da ultima vez que tiveram aqui
        
        mutex.down();                                                   // verde
        ((StableProxy) Thread.currentThread()).setReindeerState(ReindeerStates.ATSTABLE);
        log.writeReinOnStable(idReindeer, nReindeers);
        
        nReindeers++;                               // inc. numero de renas
        if (nReindeers == General.NUM_REINDEER){        
            log.writeReinderKnoock();
        }
        
        mutex.up();                                                  // vermelho
        return nReindeers;
    }
}