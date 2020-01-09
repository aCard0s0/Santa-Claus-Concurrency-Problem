package identities;

import client.shareRegions.RepositoryStub;
import client.shareRegions.SantaHouseStub;
import client.shareRegions.SouthPacificStub;
import client.shareRegions.StableStub;
import client.shareRegions.TripAroundStub;
import configs.General;
import states.ReindeerStates;
import states.States;

/**
 *
 * @author andre cardoso 65069 & joao ribeiro 64649
 */
public class Reindeer extends Thread{
    
    private final int idReindeer;                         /* ID do Reindeers */
    private final RepositoryStub log;                     /* Atributo relativo ao repositorio de informação*/
    private final SouthPacificStub holidays;                  /* Atributo relativo a SouthPacific */
    private final StableStub stable;                          /* Atributo relativo a Stable */
    private final TripAroundStub trip;                   /* Atributo relativo a TripAroundWorld */
    private final SantaHouseStub house;
    private States state;                   /* Estado das Reindeers */

    public Reindeer(int i, RepositoryStub logStub, SouthPacificStub southPacificStub, StableStub stableStub, TripAroundStub tripStub, SantaHouseStub santaHouseStub) {
        this.idReindeer = i;
        this.log = logStub;
        this.holidays = southPacificStub;
        this.stable = stableStub;
        this.trip = tripStub;
        this.house = santaHouseStub;
        state = ReindeerStates.HOLIDAYS;            /* Renas iniciado em ferias */
    }
    
    @Override
    public void run() {
        int nReindeer = 0;
        /* Renas morrem depois de N iterações (anos) */
        while(!log.endReindeer()){
            holidays.enjoyView(idReindeer);
            nReindeer = stable.goBackToStable(idReindeer);
            if(nReindeer == General.NUM_REINDEER)
                house.isReindeerAtDoor();
            trip.groupAtSledge(idReindeer);
            trip.followSanta(idReindeer);
            holidays.leaveHolidays(idReindeer);
        }
    }
    
    // Getter & Setter States
    public States getReindeerState(){
        return state;
    }
    public void setReindeerState(States state) {
        this.state = state;
    }
}
