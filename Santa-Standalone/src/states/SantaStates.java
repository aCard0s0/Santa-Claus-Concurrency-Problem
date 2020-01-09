package states;

/**
 *
 * @author andre cardoso 65069 & joao ribeiro 64649
 */
public enum SantaStates {
    
    RESTING{
        @Override
        public String toString(){ return " REST"; }
    },
    DECIDING{
        @Override
        public String toString(){ return "DECID"; }
    },
    MEETINGELVES{
        @Override
        public String toString(){ return "MEETG"; }
    },
    DISTRIBUTINGGIFTS{
        @Override
        public String toString(){ return "GITFS"; }
    }
}
