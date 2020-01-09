package shareRegions;

import identities.Gnome;
import identities.Santa;
import states.GnomeStates;
import states.SantaStates;
import structures.MemException;
import structures.MemFIFO;
import structures.Semaphore;
import configs.General;

/**
 *
 * @author andre cardoso 650659 & joao ribeiro 64649
 */
public class SantaHouse {
    
    private InfoRepository log;             /* Atributo relativo ao repositorio de informação*/
    private boolean gnomeAtDoor;            /* V- se sao gnomos na porta. F- caso contario */
    private boolean reindeerAtDoor;         /* V- se sao renas na porta. F- caso contario */
    private Semaphore mutex;                /* Acesso á região critica*/
    private Semaphore sleep;                /* Semaforo relativo ao Santa */
    private Semaphore greet;                /* Cumprimenta gnomos, um de cada vez, quando entram na casa */
    private Semaphore[] wait;               /* espera enquanto gnome entra, pela ordem do Santa */
    private Semaphore gnomeWaitForSanta;    
    static MemFIFO<Integer> gnomesInHouse;  /* guarda id dos Gnomes que estao na casa do Santa */
    private int nGnomes;                    /* */

    //  Construtor
    public SantaHouse(InfoRepository log) {
        this.log = log;
        gnomeAtDoor= false;
        reindeerAtDoor= false;
        mutex = new Semaphore();
        mutex.up();                                                     // verde
        gnomeWaitForSanta = new Semaphore();
        sleep = new Semaphore();
        greet = new Semaphore();                    // para compprimentar o gnomes
        wait = new Semaphore[General.NUM_GNOMES];
        for(int i=0; i < General.NUM_GNOMES; i++)
            wait[i] = new Semaphore();
        try {
            gnomesInHouse = new MemFIFO<>(new Integer[General.MAX_FIFO]);
        } catch (MemException ex) {
        }
        nGnomes = 0;
    }
    
    //  Usado pelo (identities).Santa
    public void goToSleepSanta(){
        sleep.down();
    }
    
    //  Usado pelo (identities).Santa
    public char openDoor(){
        
        char decicao = 0;
                
        mutex.down();                                                   // verde
        ((Santa) Thread.currentThread()).setSantaState(SantaStates.DECIDING);
        log.writeSantaState(SantaStates.DECIDING);
        
        if(reindeerAtDoor){
            decicao = 'R';
            reindeerAtDoor = false;
        }else if (gnomeAtDoor) {
            decicao = 'G';
            gnomeAtDoor = false;
        }

        mutex.up();                     // sai da regiao critica
        return decicao;
    }
    
    //Gnomes
    public void enterHouse(int idGnome){
        
        mutex.down();
        try {
            gnomesInHouse.write(idGnome);
        } catch (MemException ex) {
        }
        mutex.up();
        
        gnomeWaitForSanta.down();
        
        mutex.down();
        ((Gnome) Thread.currentThread()).setGnomeState(GnomeStates.CONSULTINGSANTA);
        log.writeGnomeWithSanta(idGnome);
        nGnomes++;

        if(nGnomes == General.MAX_FIFO){
            sleep.up();                                     // acorda Santa
        }
        mutex.up();                                     // sai da região critica
        
        wait[idGnome].down();           // espera para ser comprimentado pelo Santa
        
        mutex.down();
        greet.up();                             // comprimenta e entra o proximo
        mutex.up();

    }
    
    //  Usado pelo (identities).Santa
    public void inviteIn(){
        
        int id = 0;             /* */
        
        mutex.down();
        ((Santa)Thread.currentThread()).setSantaState(SantaStates.MEETINGELVES);
        log.writeMeetGnomes();
        for(int i=0; i < General.MAX_FIFO; i++){
            try {
                id = gnomesInHouse.read(); 
                
            } catch (MemException ex) {
            }
            gnomeWaitForSanta.up();
            wait[id].up();          
            mutex.up();
            
            greet.down();       // para o Santa bloquear passagem de mais gnomos
            
            mutex.down();
        }
        mutex.up();
    }
    
    //  Usado pelo (identities).Santa
    public void listenGnomes(){
        
        try{
            Thread.sleep((long) (100 * Math.random()));
        }catch(InterruptedException e){
            /* print de erro */
        }
        mutex.down();
        /*  para no ultimo ano contar as vezes que atendeu os gnomos
            para acabarem assim que consultam o santa */
        log.incConsul();
        mutex.up();
    }
    
    //Gnomes
    public void talk(int idGnome){
        
        mutex.down();
        /* Caso seja o ultimo ano, o gnome vai estar marcado, significando que 
            nao volta a falar com o Santa outra vez */
        log.setLastConsul(idGnome);
        try {
            gnomesInHouse.write(idGnome);   // volta a escrever lo na fila para o Santa dizer adeus
        } catch (MemException ex) {
        }
        mutex.up();
        wait[idGnome].down();
    }
    
    //  Usado pelo (identities).Santa
    public void sayGoodbye(){
        
        mutex.down();                                    // entra regiao critica
        ((Santa) Thread.currentThread()).setSantaState(SantaStates.RESTING);
        log.writeSantaState(SantaStates.RESTING);
        nGnomes = 0;                // reset
        for(int i=0; i < General.MAX_FIFO; i++){
            try {
                wait[ gnomesInHouse.read() ].up();     //para saber qual o gnome a dizer adeus
            } catch (MemException ex) {
            }
        }
        sleep.down();               // adormecer santa
        mutex.up();                                        // sai regiao critica

    }

    //  Usado em (identities).Gnome
    public void isGnomesAtDoor() {
        mutex.down();
        gnomeAtDoor = true;
        sleep.up();         // acorda santa
        mutex.up();
    }
    
    //  Usado em (identities).Reindeer
    public void isReindeerAtDoor() {
        mutex.down();
        reindeerAtDoor = true;
        sleep.up();         // acorda santa
        mutex.up();
    }
}