package states;

/**
 *
 * @author andre cardoso 65069 & joao ribeiro 64649
 */
public enum GnomeStates {
    
    WORKING{
        @Override
        public String toString(){ return " WORK"; }
    },
    WISHINGTTOMEET{
        @Override
        public String toString(){ return "WMEET"; }
    },
    JOINGROUP{
        @Override
        public String toString(){ return "JOING"; }
    },
    CONSULTINGSANTA{
        @Override
        public String toString(){ return "CSANT"; }
    }
}