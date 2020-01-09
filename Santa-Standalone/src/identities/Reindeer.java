package identities;

import configs.General;
import shareRegions.InfoRepository;
import shareRegions.SantaHouse;
import shareRegions.SouthPacific;
import shareRegions.Stable;
import shareRegions.TripAroundWorld;
import states.ReindeerStates;

/**
 *
 * @author andre cardoso 65069 & joao ribeiro 64649
 */
public class Reindeer extends Thread{
    
    private int idReindeer;                         /* ID do Reindeers */
    private InfoRepository log;                     /* Atributo relativo ao repositorio de informação*/
    private SouthPacific holidays;                  /* Atributo relativo a SouthPacific */
    private Stable stable;                          /* Atributo relativo a Stable */
    private TripAroundWorld trip;                   /* Atributo relativo a TripAroundWorld */
    private SantaHouse house;
    private ReindeerStates state;                   /* Estado das Reindeers */

    
    public Reindeer(int idReindeer, InfoRepository log, SouthPacific holidays, 
            Stable stable,  TripAroundWorld trip, SantaHouse house) {
        this.idReindeer = idReindeer;
        this.log = log;
        this.holidays = holidays;
        this.stable = stable;
        this.trip = trip;
        this.house = house;
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
    
    // Setter States
    public void setReindeerState(ReindeerStates state) {
        this.state = state;
    }
}
