package identities;

import client.shareRegions.RepositoryStub;
import client.shareRegions.SantaHouseStub;
import client.shareRegions.ToyFactoryStub;
import configs.General;
import states.GnomeStates;
import states.States;

/**
 *
 * @author andre cardoso 65069 & joao ribeiro 64649
 */
public class Gnome extends Thread {
    
    private final int idGnome;                        /* Numero de identificação do Gnomo*/
    private final RepositoryStub log;                 /* Atributo relativo ao repositorio de informação*/
    private States gstate;                               /* Estado dos Gnomos (enum) */
    private final ToyFactoryStub factory;                 /* Atributo relativo a ToyFactory */
    private final SantaHouseStub house;                   /* Atributo relativo a SantaHouse */

    public Gnome(int idGnome, RepositoryStub logStub, ToyFactoryStub toyFactoryStub, SantaHouseStub santaHouseStub) {
        this.idGnome = idGnome;
        this.log = logStub;
        this.factory = toyFactoryStub;
        this.house = santaHouseStub;
        this.gstate = GnomeStates.WORKING;                 /* Gnomos iniciado a trabalhar*/
    }

    @Override
    public void run() {
        int nGomes = 0;
        /* Gnomes morrem depois de N iterações (anos) 
        e depois de todos serem atendidos pelo Santa no ultimo ano*/
        while(!log.endGnome(idGnome)){
            factory.work();
            nGomes = factory.needAdvice(idGnome);
            if (nGomes == General.MAX_FIFO)
                house.isGnomesAtDoor();
            house.enterHouse(idGnome);                     /* entram "General.MAX_FIFO" de cada vez*/
            house.talk(idGnome);
            factory.goBackToWork(idGnome);
        }
    }
    
    //Getter & Setter States
    public States getGnomeState(){
        return gstate;
    }
    public void setGnomeState(States state) {
        this.gstate = state;
    }
}