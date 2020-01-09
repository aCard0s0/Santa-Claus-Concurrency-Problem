package identities;

import configs.General;
import shareRegions.InfoRepository;
import shareRegions.ToyFactory;
import shareRegions.SantaHouse;
import states.GnomeStates;

/**
 *
 * @author andre cardoso 65069 & joao ribeiro 64649
 */
public class Gnome extends Thread {
    
    private int idGnome;                        /* Numero de identificação do Gnomo*/
    private InfoRepository log;                 /* Atributo relativo ao repositorio de informação*/
    private GnomeStates gstate;                 /* Estado dos Gnomos (enum) */
    private ToyFactory factory;                 /* Atributo relativo a ToyFactory */
    private SantaHouse house;                   /* Atributo relativo a SantaHouse */
    
    public Gnome(int idGnome, InfoRepository log, ToyFactory factory, SantaHouse house) {
        this.idGnome = idGnome;
        this.log = log;
        this.factory = factory;
        this.house = house;
        this.gstate = GnomeStates.WORKING;          /* Gnomos iniciado a trabalhar*/
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
            house.enterHouse(idGnome);          /* entram "General.MAX_FIFO" de cada vez*/
            house.talk(idGnome);
            factory.goBackToWork(idGnome);
        }
    }
    
    // Setter States
    public void setGnomeState(GnomeStates state) {
        this.gstate = state;
    }
}