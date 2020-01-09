package shareRegions;

import identities.Gnome;
import states.GnomeStates;
import structures.MemException;
import structures.MemFIFO;
import structures.Semaphore;
import configs.General;
/**
 *
 * @author andre cardoso 65069 & joao ribeiro 64649
 */
public class ToyFactory {
    
    private InfoRepository log;             /* Atributo relativo a InfoRepository */
    private Semaphore mutex;                /* Acesso á regiao critica */
    static MemFIFO<Integer> gnomesToTalk;      /* lista de espera para falar com Santa */
    private Semaphore[] gnomeWait;            /* Garante o numero de gnomos nao é superior a "General.MAX_FIFO" */
    private int nGnomes;                    /* Numero de gnomos que precisam de conselhos */
    private int nGnomeToTalk;               /* Numero de gnomos que precisam de conselhos, mas estao a espera de um grupo */
    
    
    //  Construtor
    public ToyFactory(InfoRepository log) {
        this.log = log;
        mutex = new Semaphore();
        mutex.up();                                                     // verde
        try {
            gnomesToTalk = new MemFIFO<>(new Integer[General.NUM_GNOMES]);
        } catch (MemException ex) {
        }
        gnomeWait = new Semaphore[General.NUM_GNOMES];
        for (int i = 0; i < General.NUM_GNOMES; i++)
            gnomeWait[i] = new Semaphore();
        nGnomes = 0;
        nGnomeToTalk=0;
    }
    
    //  Usado pelo (identities).Gnome
    public void work(){
        try{
            Thread.sleep((long) (100*Math.random()) );
        }catch(InterruptedException e){
        }
    }
    
    //  Usado pelo (identities).Gnome
    public int needAdvice(int idGnome) {
        
        mutex.down();
        /* Se o nGnomes é maior ou igual a "General.MAX_FIFO, 
        espera que o grupo formado anteriormente acabe de falar com o Santa"*/
        if (General.MAX_FIFO <= nGnomes){
            try {
                gnomesToTalk.write(idGnome);
            } catch (MemException ex) {
            }
            nGnomeToTalk++;
            mutex.up();
                gnomeWait[idGnome].down();
            mutex.down();
        }
        
        ((Gnome) Thread.currentThread()).setGnomeState(GnomeStates.WISHINGTTOMEET);
        log.writeGnomeState(idGnome, GnomeStates.WISHINGTTOMEET);
        
        /* Se o nGnomes é menor a "General.MAX_FIFO, entra no grupo */
        if (nGnomes < General.MAX_FIFO){
            nGnomes++;                          // inc. numero de Gnomos
            ((Gnome) Thread.currentThread()).setGnomeState(GnomeStates.JOINGROUP);
            log.writeGnomeOnGroup(idGnome, nGnomes);
        }
        
        /* Ultimo bate á porta & acorda Santa*/
        if (nGnomes == General.MAX_FIFO)
            log.writeGnomeKnoock();
        
        mutex.up();
        return nGnomes;
    }
    
    //  Usado pelo (identities).Gnome
    public void goBackToWork(int idGnome){
        
        mutex.down();
        ((Gnome) Thread.currentThread()).setGnomeState(GnomeStates.WORKING);
        log.writeGnomeState(idGnome, GnomeStates.WORKING);
        
        nGnomes--;
        if (nGnomes == 0){
            for(int i=0; i < nGnomeToTalk; i++){
                try {
                    gnomeWait[ gnomesToTalk.read() ].up();              // gnomos parados por a fila estar cheia, podem avançar
                } catch (MemException ex) {
                }
            }
            nGnomeToTalk=0;                         // reset var
        }
        mutex.up();
    }
}