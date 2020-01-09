package states;

/**
 *
 * @author Andre Cardoso 65069 & Joao Ribeiro 64649
 */
public enum ReindeerStates implements States{
    
    HOLIDAYS{
        @Override
        public String toString(){ return "HOLID"; }

        @Override
        public int getIdState() {
            return 0;
        }
    },
    ATSTABLE{
    @Override
        public String toString(){ return "ATSTA"; }

        @Override
        public int getIdState() {
            return 1;
        }
    },
    PULLINGSLEDGE{
      @Override
        public String toString(){ return "PULL"; }

        @Override
        public int getIdState() {
            return 2;
        }
    };
    
    public static String getReindeerEnum(String s){
        switch (s) {
            default: 
                System.err.println("Erro a identificar o enum das Renas");
                System.exit(1);
            case "HOLID":
                return "HOLIDAYS";
            case "ATSTA":
                return "ATSTABLE";
            case "PULL":
                return "PULLINGSLEDGE";
        }
    }
}