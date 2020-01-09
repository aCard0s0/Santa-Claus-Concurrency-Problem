package states;

/**
 *
 * @author andre cardoso 65069 & joao ribeiro 64649
 */
public enum SantaStates implements States {
    
    RESTING{
        @Override
        public int getIdState(){  return 0;       }
        
        @Override
        public String toString(){ return " REST"; }

    },
    DECIDING{
        @Override
        public int getIdState(){  return 1;       }
        
        @Override
        public String toString(){ return "DECID"; }
    },
    MEETINGELVES{
        @Override
        public int getIdState(){  return 2;       }
        
        @Override
        public String toString(){ return "MEETG"; }
    },
    DISTRIBUTINGGIFTS{
        @Override
        public int getIdState(){  return 3;       }
        
        @Override
        public String toString(){ return "GITFS"; }
    };
    
    public static String getSantaEnum(String s){
        switch (s) {
            default: 
                System.err.println("Erro a identificar o enum das Renas");
                System.exit(1);
            case " REST":
                return "RESTING";
            case "DECID":
                return "DECIDING";
            case "MEETG":
                return "MEETINGELVES";
            case "GITFS":
                return "DISTRIBUTINGGIFTS";
        }
    }
}
