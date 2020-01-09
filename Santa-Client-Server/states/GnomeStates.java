package states;

/**
 *
 * @author andre cardoso 65069 & joao ribeiro 64649
 */
public enum GnomeStates implements States{
    
    WORKING{
        @Override
        public String toString(){ return " WORK"; }

        @Override
        public int getIdState() {
            return 0;
        }
    },
    WISHINGTTOMEET{
        @Override
        public String toString(){ return "WMEET"; }

        @Override
        public int getIdState() {
            return 1;
        }
    },
    JOINGROUP{
        @Override
        public String toString(){ return "JOING"; }

        @Override
        public int getIdState() {
            return 2;
        }
    },
    CONSULTINGSANTA{
        @Override
        public String toString(){ return "CSANT"; }

        @Override
        public int getIdState() {
            return 3;
        }
    };
    
    public static String getGnomeEnum(String s){
        switch (s) {
            default: 
                System.err.println("Erro a identificar o enum do gnome");
                System.exit(1);
            case " WORK":
                return "WORKING";
            case "WMEET":
                return "WISHINGTTOMEET";
            case "JOING":
                return "JOINGROUP";
            case "CSANT":
                return "CONSULTINGSANTA";
        }
    }
}