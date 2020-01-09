/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures.Messages;

/**
 *
 * @author acardoso
 */
public class MsgIndex {
    
    /** ShareRegions defenition. */
    public static final String ACK = "ack";
    public static final String REINDEER = "Rena";
    public static final String GNOME = "Gnome";
    public static final String SANTA = "Santa";
    
    // para terminar os server
    public static final String RS_shutDown = "RS_shutDown";
    public static final String AS_shutDown = "AS_shutDown";
    
    public static final String SANTAHOUSE = "SantaHouse";
        //  Santa
        public static final String RS_goToSleepSanta = "RS_goBackToSleepSanta";
        public static final String AS_goToSleepSanta = "AS_goBackToSleepSanta";

        public static final String RS_openDoor = "RS_openDoor";
        public static final String AS_openDoor_R = "AS_openDoor_R";
        public static final String AS_openDoor_G = "AS_openDoor_G";

        public static final String RS_inviteIn = "RS_inviteIn";
        public static final String AS_inviteIn = "AS_inviteIn";

        public static final String RS_listenGnomes = "RS_listenGnomes";
        public static final String AS_listenGnomes = "AS_listenGnomes";

        public static final String RS_sayGoodbye = "RS_SayGoodBye";
        public static final String AS_sayGoodbye = "AS_SayGoodBye";

        //  Reindeer
        public static final String RS_isReindeerAtDoor = "RS_isReindeerAtDoor";
        public static final String AS_isReindeerAtDoor = "AS_isReindeerAtDoor";

        //  Gnome
        public static final String RS_talk = "RS_talk";
        public static final String AS_talk = "AS_talk";
        
        public static final String RS_enterHouse = "RS_enterHouse";
        public static final String AS_enterHouse = "AS_enterHouse";
        
        public static final String RS_isGnomesAtDoor = "RS_isGnomeAtDoor";
        public static final String AS_isGnomesAtDoor = "AS_isGnomeAtDoor";
        
        
    public static final String SOUTHPACIFIC = "SouthPacific";
        public static final String RS_enjoyView = "RS_enjoyView";
        public static final String AS_enjoyView = "AS_enjoyView";

        public static final String RS_leaveHolidays = "RS_leaveHolidays";
        public static final String AS_leaveHolidays = "AS_leaveHolidays";

        
    public static final String STABLE = "Stable";
        public static final String RS_goBackToStable = "RS_goBackToStable";
        public static final String AS_goBackToStable = "AS_goBackToStable";
        
        
    public static final String TOYFACTORY = "ToyFactory";
        public static final String RS_work = "RS_work";
        public static final String AS_work = "AS_work";
        
        public static final String RS_needAdvice = "RS_needAdvice"; /////////////////////
        public static final String AS_needAdvice = "AS_needAdvice";
        
        public static final String RS_goBackToWork = "RS_goBackToWork";
        public static final String AS_goBackToWork = "AS_goBackToWork";

    
    public static final String TRIPAROUNDWORLD = "TripAroundWorld";
        public static final String RS_harnessReindeers = "RS_harnessReindeers";
        public static final String AS_harnessReindeers = "AS_harnessReindeers";
        
        public static final String RS_travelAround = "RS_travelArround";
        public static final String AS_travelAround = "AS_travelArround";
        
        public static final String RS_goHome = "RS_goHome";
        public static final String AS_goHome = "AS_goHome";
        
        public static final String RS_groupAtSledge = "RS_groupAtSledge";
        public static final String AS_groupAtSledge = "AS_groupAtSledge";
        
        public static final String RS_followSanta = "RS_followSanta";
        public static final String AS_followSanta = "AS_followSanta";
        
    
    // InfoRepository    
    public static final String REPOSITORY = "InfoRepository";
        //  Reindeer
        public static final String RS_endReindeer = "RS_endReindeer";
        public static final String AS_endReindeerT = "AS_endReindeerT";
        public static final String AS_endReindeerF = "AS_endReindeerF";
        
        public static final String RS_writeReindeerOnSledge = "RS_writeReindeerOnSledge";
        public static final String AS_writeReindeerOnSledge = "AS_writeReindeerOnSledge";
        
        public static final String RS_writeReindeerState = "RS_writeReindeerState";
        public static final String AS_writeReindeerState = "AS_writeReindeerState";
        
        public static final String RS_writeReinOnStable = "RS_writeReinOnStable";
        public static final String AS_writeReinOnStable = "AS_writeReinOnStable";
        
        public static final String RS_writeReinderKnoock = "RS_writeReinderKnoock";
        public static final String AS_writeReinderKnoock = "AS_writeReinderKnoock";
        
        public static final String RS_writeReinOnSledge = "RS_writeReinOnSledge";
        public static final String AS_writeReinOnSledge = "AS_writeReinOnSledge";
        
        //  Santa
        public static final String RS_endSanta = "RS_endSanta";
        public static final String AS_endSantaT = "AS_endSantaT";
        public static final String AS_endSantaF = "AS_endSantaF";
        
        public static final String RS_incYears = "RS_incYears";
        public static final String AS_incYears = "AS_incYears";
        
        public static final String RS_writeSantaState = "RS_writeSantaState";
        public static final String AS_writeSantaState = "AS_writeSantaState";
        
        public static final String RS_writeGnomeWithSanta = "RS_writeGnomeWithSanta";
        public static final String AS_writeGnomeWithSanta = "AS_writeGnomeWithSanta";
        
        public static final String RS_writeMeetGnomes = "RS_writeMeetGnomes";
        public static final String AS_writeMeetGnomes = "AS_writeMeetGnomes";
        
        public static final String RS_incConsul = "RS_incConsul";
        public static final String AS_incConsul = "AS_incConsul";
        
        public static final String RS_setLastConsul = "RS_setLastConsul";
        public static final String AS_setLastConsul = "AS_setLastConsul";
        
        //  Gnome
        public static final String RS_endGnome = "RS_endGnome";
        public static final String AS_endGnomeT = "AS_endGnomeT";
        public static final String AS_endGnomeF = "AS_endGnomeF";
        
        public static final String RS_writeGnomeState = "RS_writeGnomeState";
        public static final String AS_writeGnomeState = "AS_writeGnomeState";
        
        public static final String RS_writeGnomeOnGroup = "RS_writeGnomeOnGroup";
        public static final String AS_writeGnomeOnGroup = "AS_writeGnomeOnGroup";
        
        public static final String RS_writeGnomeKnoock = "RS_writeGnomeKnoock";
        public static final String AS_writeGnomeKnoock = "AS_writeGnomeKnoock";
        
}
