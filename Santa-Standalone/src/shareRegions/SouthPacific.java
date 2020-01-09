/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shareRegions;

import identities.Reindeer;
import states.ReindeerStates;
import structures.Semaphore;

/**
 *
 * @author andre cardoso & joao ribeiro
 */
public class SouthPacific {
    
    private Semaphore mutex;                        /* Accesso á regiao critica */
    private InfoRepository log;                     /* Atributo relativo a InfoRepository */
    
    //  Construtor
    public SouthPacific(InfoRepository log) {
        this.log = log;
        mutex = new Semaphore();                    
        mutex.up();                                 // entrada na regiao critica 
    }
    
    //  Usado pelo (identities).Reindeer
    public void enjoyView (int idReindeer){
        try{
            Thread.sleep((long) (100 * Math.random()));
        }catch (InterruptedException e) {}
    }
    
    //  Usado pelo (identities).Reindeer
    public void leaveHolidays(int idReindeer){
        mutex.down();                                                // vermelho
        ((Reindeer) Thread.currentThread()).setReindeerState( ReindeerStates.HOLIDAYS );
        log.writeReindeerState(idReindeer, ReindeerStates.HOLIDAYS); 
        mutex.up();                                                  // verde
    }
}