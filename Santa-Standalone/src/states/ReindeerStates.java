package states;

/**
 *
 * @author Andre Cardoso 65069 & Joao Ribeiro 64649
 */
public enum ReindeerStates {
    
    HOLIDAYS{
        @Override
        public String toString(){ return "HOLID"; }
    },
    ATSTABLE{
    @Override
        public String toString(){ return "ATSTA"; }
    },
    PULLINGSLEDGE{
      @Override
        public String toString(){ return "PULL"; }
    }
}