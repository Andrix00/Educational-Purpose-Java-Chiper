package example;

import java.util.Scanner;

public class Example {

    //Dichiarazioni variabili globali
    private static char Alphabet[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'K', 'J', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'X', 'Y', 'W', 'Z' };
    
    private static int GlobRotazione;
    
    public static void main(String[] args) {
        
        Scanner mScanner = new Scanner(System.in);
        
        System.out.println("Test del programma");
        System.out.println("Inserisci MSG1 (Per il test scrivi \"COMANDO\")");
        String MSG1 = mScanner.nextLine();
        System.out.println("Inserisci MSG2 (Per il test scrivi \"ATTACCA\")");
        String MSG2 = mScanner.nextLine();
        System.out.println("Inserisci la rotazione (Per il test scrivi \"3\")");
        int Rotazione = mScanner.nextInt();
        
        mScanner.nextLine(); //Per leggere un eventuale prossima riga
        
        String MSG1_PR = Codifica_PR(MSG1);
        System.out.println("MSG1_PR (funzione Codifica_PR(MSG1): " + MSG1_PR);

        String MSG2_CO = Concatena(MSG1_PR, MSG2);
        System.out.println("MSG2_CO (funzione Concatena(MSG1_PR,MSG2): " + MSG2_CO);

        String MSG2_PU = Codifica_PU(MSG2_CO, Rotazione);
        System.out.println("MSG2_PU (funzione Codifica_PU(MSG2_CO,3): " + MSG2_PU);
        
        String MSG2_CO_DEC = Decodifica_PU(MSG2_PU);
        System.out.println("MSG2_CO (funzione Decodifica_PU(MSG2_PU): " + MSG2_CO_DEC);
        
        String MSG2_DEC = Decodifica_PR(MSG2_CO, MSG1_PR);
        System.out.println("MSG2 (funzione Decodifica_PR(MSG2_CO, MSG1_PR): " + MSG2_DEC);
        
        mScanner.close(); //è buona pratica chiudere lo scanner
        
    }
    
    private static String Codifica_PR(String MSG1) {
        MSG1 = MSG1.toUpperCase(); //Lo metto in maiuscolo in quanto il mio alfabeto è maiuscolo
        String MSG1_PR = ""; //Messaggio cifrato da ritornare in output
        for(int i = 0; i < MSG1.length(); i++) {
            //Verifico se l'indice è dispari controllando il resto della divisione Index / 2
            if((i % 2) == 0) {
                //Se non è dispari, quindi è pari la lettera rimane invariata
                MSG1_PR += MSG1.substring(i, (i + 1));
            }
            else {
                //Se è dispari la lettera va sostituita con quella maggiore di una posizione nell'alfabeto
                //Trovo l'Index della lettera e la conseguende lettera da sostituire con un for che cicla l'alfabeto
                for(int c = 0; c < Alphabet.length; c++) {
                    //Controllo se la lettera della parola equivale a quella all'index c dell'alfabeto
                    if(MSG1.charAt(i) == Alphabet[c]) {
                        //Si, allora la sostituisco con quella all'index i + 1
                        MSG1_PR += Alphabet[c + 1];
                    }
                }
            }
        }
        return MSG1_PR;
    }
    
    private static String Concatena(String MSG1_PR, String MSG2) {
        MSG1_PR = MSG1_PR.toUpperCase(); //Lo metto in maiuscolo per sicurezza
        MSG2 = MSG2.toUpperCase(); //Lo metto in maiuscolo per sicurezza
        String MSG2_CO = ""; //Messaggio da ritornare in output
        for(int i = 0; i < (MSG1_PR + MSG2).length(); i++) {
            //Verifico se l'indice è dispari controllando il resto della divisione Index / 2
            if((i % 2) == 0) {
                //Se non è dispari, quindi è pari la lettera va sostituita con MSG2
                MSG2_CO += MSG2.substring((i / 2), ((i / 2) + 1));
            }
            else {
                //Se è dispari la lettera va sostituita con MSG1_PR
               MSG2_CO += MSG1_PR.substring((i / 2), ((i / 2) + 1));
            }
        }
        return MSG2_CO;
    }
    
    private static String Codifica_PU(String MSG2_CO, int Rotazione) {
        GlobRotazione = Rotazione;
        MSG2_CO = MSG2_CO.toUpperCase(); //Lo metto in maiuscolo per sicurezza
        String MSG2_PU = ""; //Messaggio da ritornare in output
        for(int i = 0; i < MSG2_CO.length(); i++) {
            //Ottengo il carattere da cifrare
            char toRotate = MSG2_CO.charAt(i);
            //Ciclo le lettere dell'alfabeto per trovare quella a quale corrisponde ed eseguire la rotazione
            for(int c = 0; c < Alphabet.length; c++) {
                if(toRotate == Alphabet[c]) {
                    //Ho trovato la corrispondenza
                    if((c + Rotazione) < Alphabet.length) {
                        //Ruoto le lettere in maniera lineare
                        MSG2_PU += Alphabet[c + Rotazione];
                    }
                    else {
                        //Ruoto le lettere in maniera circolare
                        int newIndex = (c + Rotazione) - (Alphabet.length - 1);
                        MSG2_PU += Alphabet[newIndex];
                    }
                }
            }
        }
        return MSG2_PU;
    }
    
    private static String Decodifica_PU(String MSG2_PU) {
        MSG2_PU = MSG2_PU.toUpperCase(); //Lo metto in maiuscolo per sicurezza
        String MSG2_CO = ""; //Messaggio da ritornare in output
        for(int i = 0; i < MSG2_PU.length(); i++) {
            //Ottengo il carattere da cifrare
            char toRotate = MSG2_PU.charAt(i);
            //Ciclo le lettere dell'alfabeto per trovare quella a quale corrisponde ed eseguire la rotazione
            for(int c = 0; c < Alphabet.length; c++) {
                if(toRotate == Alphabet[c]) {
                    //Ho trovato la corrispondenza
                    if((c - GlobRotazione) >= 0) {
                        //Ruoto le lettere in maniera lineare
                        MSG2_CO += Alphabet[c - GlobRotazione];
                    }
                    else {
                        //Ruoto le lettere in maniera circolare
                        int newIndex = (Alphabet.length - 1) + (c - GlobRotazione);
                        MSG2_CO += Alphabet[newIndex];
                    }
                }
            }
        }
        return MSG2_CO;
    }
    
    private static String Decodifica_PR(String MSG2_CO, String MSG1_PR) {
        MSG2_CO = MSG2_CO.toUpperCase(); //Lo metto in maiuscolo per sicurezza
        String MSG2 = ""; //Messaggio da ritornare in output
        String MSG1 = "";
        for(int i = 0; i < MSG2_CO.length(); i++) {
            //Verifico se l'indice è dispari controllando il resto della divisione Index / 2
            if((i % 2) == 0) {
                //Se non è dispari, quindi è pari
                MSG2 += MSG2_CO.substring(i, i + 1);
            }
            else {
                //è dispari
               MSG1 += MSG2_CO.substring(i, i + 1);
            }
        }
        return MSG2;
    }

}
 
