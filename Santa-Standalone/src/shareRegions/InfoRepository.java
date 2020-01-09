package shareRegions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import states.GnomeStates;
import states.ReindeerStates;
import states.SantaStates;
import structures.Semaphore;
import configs.General;

/**
 *
 * @author andre cardoso 65069 & joao peixe 64649
 */
public class InfoRepository {
    final Scanner sca = new Scanner(System.in);

    private int nReindeers;           /* numero total de renas */
    private int nGnome;               /* numero total de gnomos */
    private int groupSize;            /* tamanho do grupo que fala com o Santa */
    // States
    private String santaState;        /* Estado do Santa */        
    private String[] gnomeState;      /* indexado pelo id dos Gnomos, guarda os estados */
    private String[] reindeersState;  /* indexado pelo id dos Renas, guarda os estados */
    
    private int reindeersAtStable;    /* numero de Renas no estabulo */
    private int nElvesInGroup;        /* numero de Gnomos no grupo */          
    private boolean reindeerReady;    /* V- as renas estao todas no estabulo e prontas para viagem. F- caso contrario */
    private boolean elvesKnocked;     /* V- grupo de Gnomos formado, e pronto para falar com o Santa. F- caso contario */     
    private String [] queue;          /* contem os ids dos Gnomos que estao á porta */             
    private int queueSize;            /* informação do tamanho da estrutura de dados referida anteriormente */
    
    private int nYears;               /* Variavel para contar o numero de anos*/
    private Semaphore mutex;          /* Acesso regiao critica */
    private int nSantaConsul;         /* NumConsutas feitas do ultimo ano pelo Santa */
    private boolean[] lastGnomeConsul;/* Para verificar se todos os gnomes tiveram a sua ultima consulta*/
    
    private PrintWriter printw;       /* para escrever a informação no ficheiro */
    
    public InfoRepository() {
        nGnome = General.NUM_GNOMES;
        nReindeers = General.NUM_REINDEER;
        groupSize = General.MAX_FIFO;
        // States
        santaState = SantaStates.RESTING.toString();
        gnomeState = new String[nGnome];
        for(int g=0; g < nGnome; g++)
            this.gnomeState[g] = GnomeStates.WORKING.toString();
        this.reindeersState = new String[nReindeers];
        for(int r=0; r < nReindeers; r++)
            this.reindeersState[r] = ReindeerStates.HOLIDAYS.toString();
        // General Info
        reindeersAtStable = 0;
        nElvesInGroup = 0;
        reindeerReady = false;
        elvesKnocked = false;
        queue = new String[General.MAX_FIFO];
        queueSize = 0;
        // Condiçoes de paragem
        nYears = 0;
        mutex = new Semaphore();
        mutex.up();                                         // verde
        nSantaConsul = 0;
        lastGnomeConsul = new boolean[General.NUM_GNOMES];
        /* Cria o log, se existir substitui & esreve primeira linha com estados inicais */
        createLog();
        header();
        write();
    }
    
    /** Condiçoes de paragem. 
     */
    /* Usado em (identiteis).Santa , 1 ano passa quando os presentes sao distribuidos */
    public void incYears() {
        mutex.down();
        nYears++;
        mutex.up();
    }
    
    /* Usado em (shareRegions).SantaHouse.listenGnomes */
    public void incConsul(){  
        /* func auxiliar á função endSanta, para verificar 
           se todos os gnomes foram ouvidos antes de morrem */
        mutex.down();
        if(General.N_TRIPS == nYears)
            nSantaConsul++;           
        mutex.up();
    }
    
    /* Usado em (identities).Santa.run  */
    public boolean endSanta(){
        boolean stat;           /* Variavel que irá decidir se encontram as condiçoes de paragem */
        
        mutex.down();
        if( nYears == General.N_TRIPS && 
                (nSantaConsul == General.NUM_GNOMES/ General.MAX_FIFO))
            stat = true;
        else
            stat = false;
        mutex.up();
        return stat;
    }
    
    /* Usado em (identities).Reindeer.run  */
    public boolean endReindeer(){
        boolean stat;     /* Variavel que irá decidir se encontram as condiçoes de paragem */
        
        mutex.down();
        if(nYears == General.N_TRIPS)
            stat = true;
        else
            stat = false;
        mutex.up();
        return stat;
    }
    
    /* Usado em (shareRegions).SantaHouse.talk  */
    public void setLastConsul(int idGnome) {
        mutex.down();
        if( nYears == General.N_TRIPS)
            lastGnomeConsul[idGnome] = true;    /* Gnomo teve a sua ultima consulta*/
        mutex.up();
    }
    
    /* Usado em (indentities).Gnomes.run  */
    public boolean endGnome(int idGnome){
        boolean stat;     /* Variavel que irá decidir se encontram as condiçoes de paragem*/
        mutex.down();
        if(nYears == General.N_TRIPS && lastGnomeConsul[idGnome])
            stat = true;
        else
            stat = false;
        mutex.up();
        return stat;
    }
    // ____________________________________
    
    /** Para escrever no Ficheiro. */
    //  Santa
    public void writeSantaState(SantaStates state){
        santaState = state.toString();
        write();
    }
    //  Gnomos
    public void writeMeetGnomes(){
        santaState = SantaStates.MEETINGELVES.toString();
        elvesKnocked = false;
        write();
    }
    //  Gnomos
    public void writeGnomeKnoock() {
        elvesKnocked = true;
    }
    //  Gnome
    public void writeGnomeState(int id, GnomeStates state){
        gnomeState[id] = state.toString();
        write();
    }
    //  Gnome
    public void writeGnomeOnGroup(int id, int nGnomes){
        gnomeState[id] = GnomeStates.JOINGROUP.toString();
        nElvesInGroup = nGnomes;
        queue[queueSize] = ""+id;
        queueSize++;
        write();
    }
    //  Gnome
    public void writeGnomeWithSanta(int id) {
        gnomeState[id] = GnomeStates.CONSULTINGSANTA.toString();
        queueSize--;
        queue[queueSize] = "-";
        nElvesInGroup--;
        write();
    }
    //  Reindeer
    public void writeReindeerState(int id, ReindeerStates state){
        reindeersState[id] = state.toString();
        write();
    }
    //  Reindeer
    public void writeReindeerOnSledge(int id){
        reindeersState[id] = ReindeerStates.PULLINGSLEDGE.toString();
        reindeersAtStable--;
        reindeerReady = false;
        write();
    }
    //  Reindeer
    public void writeReinderKnoock(){
        reindeerReady = true;
    }
    //  Reindeer
    public void writeReinOnStable(int id, int nReindeer){
        reindeersState[id] = ReindeerStates.ATSTABLE.toString();
        reindeersAtStable = nReindeer+1; // compor variavel
        write();
    }
    //_____________________________________
    
    /** 
     * Criar ficheiro ou substituir ficheiro.
     */
    private void createLog(){
        
        String fileName;                        /* Nome do ficheiro de saida */
        
        System.out.println("Qual o nome do Logger?");
        fileName = sca.nextLine();
        File file = new File(fileName);
        
        if (file.exists()){
            System.out.println("Ficheiro existente, deseja subtituir? (s/n)");
            String rsp = sca.nextLine().toUpperCase();
            if ("S".equals(rsp))
                try {
                    printw = new PrintWriter(file);
            } catch (FileNotFoundException ex) {
                // mensagem de erro
            }
            else{
                System.err.println("Operação cancelada.");
                System.exit(1);
            }
        } else {
            try {
                printw = new PrintWriter(file);
            } catch (FileNotFoundException ex) {
                // mensagem de erro
            }
        }
    }
    
    /**
     * Writes the header of the log file to the printw PrintWriter.
     */
    private void header() {
        printw.format("SCENES ON THE LIFE OF SANTA CLAUS - Description of the internal state of the problem\n");
        printw.format("\n");
        
        printw.format("%5s","SANTA");
        for(int e=0;e<nGnome;e++) {
            printw.format(" ELF %d",e);
        }
        printw.format(" %3s %2s","NEG", "SG");
        for(int q=0;q<groupSize;q++) {
            printw.format(" Q%d",q);
        }

        for(int r=0;r<nReindeers;r++) {
            printw.format(" REIND %d",r);
        }
        printw.format(" %3s %2s","NRD", "SR");

        printw.format("\n");

        printw.flush();
    }
    
    /**
     * Writes a new line with all the information to the printw PrintWriter.
     */
    private synchronized void write() {
        printw.format("%5s", santaState);
        
        for(int e=0; e < nGnome; e++)
            printw.format("%6s", gnomeState[e]);
        
        printw.format("%3s ", nElvesInGroup);           // NEG
        printw.format("%3s", (elvesKnocked ? "T" : "F"));

        for(int q=0; q < queueSize; q++) 
            printw.format("%3s", queue[q]);
        
        for(int q=queueSize; q < groupSize; q++) 
            printw.format("%3s","-");
        
        for(int r=0; r < nReindeers; r++) 
            printw.format("%8s", reindeersState[r]);
        
        printw.format("%3d ", reindeersAtStable);
        printw.format("%3s ", (reindeerReady ? "T" : "F"));
        printw.format("\n");
        
        printw.flush();
    }
}