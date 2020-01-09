package identities;

import client.shareRegions.RepositoryStub;
import client.shareRegions.SantaHouseStub;
import client.shareRegions.TripAroundStub;
import states.SantaStates;
import states.States;

/**
 *
 * @author andre cardoso 65069 & joao ribeiro 64649
 */
public class Santa extends Thread {

    private States state;              /* Estado do Santa */
    private final RepositoryStub log;             /* Atributo relativo ao repositorio de informação*/
    private final SantaHouseStub house;               /* Atributo relativo a SantaHouse */
    private final TripAroundStub trip;           /* Atributo relativo a TripAroundWorld */
    
    public Santa(RepositoryStub log, SantaHouseStub house, TripAroundStub trip) {
        this.log = log;
        this.house = house;
        this.trip = trip;
        state = SantaStates.RESTING;        /* Santa iniciado a dormir */
    }
    
    @Override
    public void run() {
        /* É o Santa que faz a contagem das N iterações (anos) com func. "log.incYears()" */
        while(!log.endSanta()){
            house.goToSleepSanta();
            if (house.openDoor() == 'G'){       /* São Gnomos */
                house.inviteIn();
                house.listenGnomes();
                house.sayGoodbye();
            }else{                              /* São Renas */
                trip.harnessReindeers();
                trip.travelAround();
                trip.goHome();
                log.incYears();                  /* 1 ano passa quando os presentes sao distribuidos */ 
            }
        }
    }
    
    //  Getter & Setter States
    public States getSantaState() {
        return state;
    }
    public void setSantaState(States state) {
        this.state = state;
    }
}