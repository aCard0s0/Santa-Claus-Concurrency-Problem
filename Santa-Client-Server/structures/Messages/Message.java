/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures.Messages;

import genclass.GenericIO;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import states.GnomeStates;
import static states.GnomeStates.getGnomeEnum;
import states.ReindeerStates;
import static states.ReindeerStates.getReindeerEnum;
import states.SantaStates;
import static states.SantaStates.getSantaEnum;
import states.States;

/**
 *
 * @author acardoso
 */
public class Message implements Serializable{
    
    private static final long serialVersionUID = 1001L;
    
    private int idSender;  // id da mensagem
    private String idMethod; // metodo invocado
    private States state;
    //private SantaStates santaState; // estado do Santa
    //private GnomeStates gnomeState; // estado do Santa
    //private ReindeerStates reindeerState; // estado do Santa
    private int answer;                 // resposta dos metodos invocados (numero de gnomes ou reindeer)
    
    // Geral
    public Message(String idMethod){
        this.idMethod = idMethod;
    }
    
    // Geral
    public Message(String idMethod, int id) {
        this.idMethod = idMethod;
        idSender = id;
    }
    
    // Santa   Gnomes   Renas
    public Message(String idMethod, States state) {
        this.idMethod = idMethod;
        this.state = state;
    }
    
    // Gnomes  Renas
    public Message(String idMethod, int idIdentitie, States state) {
        this.idMethod = idMethod;
        idSender = idIdentitie;
        this.state = state;
    }
    
    // Log
    public Message(String idMethod, int id, int num){
        this.idMethod = idMethod;
        this.idSender = id;
        answer = num;
    }
    
    /**
   *  Instanciação de uma mensagem (forma 5).
   *
   *    @param stringXML mensagem em formato XML
   */
    public Message (String stringXML, boolean isXML) {
        
        InputSource in = new InputSource (new StringReader (stringXML));
        SAXParserFactory spf;
        SAXParser saxParser = null;

        spf = SAXParserFactory.newInstance ();
        spf.setNamespaceAware (false);
        spf.setValidating (false);
        try { 
            saxParser = spf.newSAXParser();
            saxParser.parse(in, new HandlerXML ());         // fazer analise do programa
        
        } catch (ParserConfigurationException e){ 
            GenericIO.writelnString ("Erro na instanciação do parser (configuração): " + e.getMessage () + "!");
            System.exit (1);
        
        } catch (SAXException e) { 
            GenericIO.writelnString ("Erro na instanciação do parser (SAX): " + e.getMessage () + "!");
            System.exit (1);
        
        } catch (IOException e) { 
            GenericIO.writelnString ("Erro na execução do parser (SAX): " + e.getMessage () + "!");
            System.exit (1);
        }
   }
    
    public String getIdMethod() {
        return idMethod;
    }
    
    public States getState(){
        return state;
    }
    
    /*public SantaStates getSantaState(){
        return santaState;
    }
    
    public GnomeStates getGnomeState(){
        return gnomeState;
    }
    
    public ReindeerStates getReindeerState(){
        return reindeerState;
    }*/
    
    public int getIdSender() {
        return idSender;
    }

    public int getAnswer(){
        return answer;
    }
    
    @Override
    public String toString() {
        return  "Metodo= "+ getIdMethod() +"\n"+
                "Sender= "+ getIdSender() +"\n"+
                "S State= "+ getState() +"\n";
    }
    
    /**
   *  Conversão para um string XML.
   *
   *    @return string contendo a descrição XML da mensagem
   */

   public String toXMLString () {
       
      return ("<Mensagem>" +
                "<IdMetodo>" + idMethod + "</IdMetodo>" +
                "<IdSender>" + idSender + "</IdSender>" +
                "<Estado>" + getState() + "</Estado>" +
              "</Mensagem>");
   }
    
    /**
   *        Este tipo de dados define o handler que gere os acontecimentos que 
   *    ocorrem durante o parsing de um string XML.
   *
   */

    private class HandlerXML extends DefaultHandler {

        /**
         *  Parsing do string XML em curso
         *    @serialField parsing
         */
        private boolean parsing;

        /**
         *  Parsing de um elemento em curso
         *    @serialField element
         */
        private boolean element;

        /**
         *  Nome do elemento em processamento
         *    @serialField elemName
         */
        private String elemName;

        /**
         *  Início do processamento do string XML.
         *
         */
        @Override
        public void startDocument () throws SAXException{
            
            idMethod = null;                // id do metodo invocado
            idSender = -1;                   // id de quem envia a mensagem
            state = null;                   // estado da identidade
            //santaState = null;              // estado do Santa
            //gnomeState = null;              // estado do Santa
            //reindeerState = null;           // estado do Santa
            answer = -1; 
    
            parsing = true;
        }


        /**
         *  Fim do processamento do string XML.
         *
         */
        @Override
        public void endDocument () throws SAXException {
            
            parsing = false;
        }

        /**
         *  Início do processamento de um elemento do string XML.
         *
         */
        @Override
        public void startElement(String namespaceURI, String localName,
                                  String qName, Attributes atts) throws SAXException {
            element = parsing;
            if (parsing) elemName = qName;
        }

        /**
         *  Fim do processamento de um elemento do string XML.
         *
         */
        @Override
        public void endElement(String namespaceURI, String localName, String qName) 
                throws SAXException {
            element = false;
            elemName = null;
        }

        /**
         *  Processamento do conteúdo de um elemento do string XML.
         *
         */
        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String elem;

            elem = new String (ch, start, length);
            if (parsing && element) { 
                if (elemName.equals ("IdMetodo")) {
                    idMethod = elem; 
                    return;
                }
                if (elemName.equals ("IdSender")) {
                    idSender = new Integer (elem); 
                    return;
                }
                if (elemName.equals ("Estado") && !elem.equals("null")) {
                    try{
                        // Santa
                        if (elem.equals(" REST") || elem.equals("DECID") ||
                                elem.equals("MEETG") || elem.equals("GITFS")){
                            state = Enum.valueOf(SantaStates.class, getSantaEnum(elem));
                            return;
                        }

                        // Rena
                        if (elem.equals("HOLID") || elem.equals("ATSTA") ||
                                elem.equals("PULL")) {
                            state = Enum.valueOf(ReindeerStates.class, getReindeerEnum(elem));
                            return;
                        }

                        // Gnome
                        if (elem.equals(" WORK") || elem.equals("WMEET") ||
                                elem.equals("JOING") || elem.equals("CSANT")) {
                            state = Enum.valueOf(GnomeStates.class, getGnomeEnum(elem));
                            return;
                        }
                    
                    } catch(IllegalArgumentException | NullPointerException e){
                        System.err.println("Erro a converter estado string para enum");
                        System.err.println(e.getMessage());
                        System.exit(1);
                    
                    }
                    
                }
            }
        }
    }

     /**
      *         Este tipo de dados define o handler que gere os acontecimentos que 
      *     ocorrem durante o parsing de um string XML.
      *
      *     Main para testes
      */
    /*public static void main (String [] args) {
        
        Message msg1 = new Message (MsgIndex.AS_enjoyView, true);
        String str = msg1.toXMLString ();
        Message msg2 = new Message (str);
        System.out.println ("Mensagem" + msg2.toString ());
    }*/
}