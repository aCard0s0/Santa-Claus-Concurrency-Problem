package identities;

import shareRegions.InfoRepository;
import shareRegions.SantaHouse;
import shareRegions.TripAroundWorld;
import states.SantaStates;

/**
 *
 * @author andre cardoso 65069 & joao ribeiro 64649
 */
public class Santa extends Thread {

    private SantaStates state;              /* Estado do Santa */
    private InfoRepository log;             /* Atributo relativo ao repositorio de informação*/
    private SantaHouse house;               /* Atributo relativo a SantaHouse */
    private TripAroundWorld trip;           /* Atributo relativo a TripAroundWorld */
    
    public Santa(InfoRepository log, SantaHouse house, TripAroundWorld trip) {
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
    
    //Setter States
    public void setSantaState(SantaStates state) {
        this.state = state;
    }
}