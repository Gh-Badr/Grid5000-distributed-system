import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MakefileParser {
    private TSymCour SymboleCourant = new TSymCour();
    private BufferedReader reader;
    private String currentLine;
    private int currentIndex;

    public static void main(String[] args) {
        MakefileParser parser = new MakefileParser(); // Création d'une instance de MakefileParser
        parser.processFile("MakeFile.txt");
        System.out.println("Fin du makefile.");
    }

    public void processFile(String filePath) {
        try  {

            reader = new BufferedReader(new FileReader(filePath));
            currentLine = reader.readLine();
            currentIndex = 0;

            while (currentLine != null) {
                while (currentIndex <= currentLine.length()) {
                    PROGRAM();
                    if (currentLine == null) { break ;}
                }
                //currentLine = reader.readLine();
                //currentIndex = 0;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void PROGRAM(){
        nextToken();

    }    

    private void nextToken() {
    try {
        if (currentIndex >= currentLine.length()) {
            currentLine = reader.readLine();
            currentIndex = 0;
            if (currentLine == null) {
                return; 
            }
        }

        char character = currentLine.charAt(currentIndex);
        while (character == ' ') {
            currentIndex++;
            if (currentIndex >= currentLine.length()) {
                return;
            }
            character = currentLine.charAt(currentIndex);
        }

        Result result = processToken(character, currentLine, currentIndex);
        System.out.println(SymboleCourant.getCode());

        //System.out.println("result : "+result.nextLine);
        //System.out.println("The line here : "+currentLine);


       
        if (result.nextLine) {
            String currentLine2 = reader.readLine();
            if (currentLine2 != null) {
               // System.out.println("saut de ligne");
                currentLine = currentLine2;
                currentIndex = 0;
            } else {
                currentLine = null ;
            }
        } else {
            //System.out.println("Pas de saut de ligne");
            if(result.changeI != 0){ 
                currentIndex = result.changeI;
            } else {
                currentIndex ++ ;
            }
        }
                





        //System.out.println("je comprend pas");

    } catch (IOException e) {
        e.printStackTrace();
    }
}


    private Result processToken(char character, String line, int index) {
        switch (character) {
            case '#':
                //System.out.println(character + " = Commentaire");
                SymboleCourant.setCode(CODES_LEX.TOKEN_COMMENT);
                //index-- ;
                return new Result(true, true, index);
            case ':':
                //System.out.println(character + " = COLON");
                SymboleCourant.setCode(CODES_LEX.TOKEN_COLON);
                break;
            case ',':
                //System.out.println(character + " = Comma");
                SymboleCourant.setCode(CODES_LEX.TOKEN_COMMA);
                break;
            case ';':
                //System.out.println(character + " = SemiColon");
                SymboleCourant.setCode(CODES_LEX.TOKEN_SMCOLON);
                break;
            case '\t':
                //System.out.println(character + " = Tabulation");
                SymboleCourant.setCode(CODES_LEX.TOKEN_TAB);
                break;
            default:
                String variable = "" ;
                while (character != ':' && character != ' ' && character != ','   ) {                        
                    variable = String.format("%s%s", variable, character);
                    index++; 
                    if (index >= line.length()) {
                        break; 
                    }
                    character = line.charAt(index);
                }

                //System.out.println("variable : " + variable);

   
                //Si le dernier symbole est une tabulation alors il s'agit d'une commande
                if (SymboleCourant.getCode() == CODES_LEX.TOKEN_TAB) {
                    SymboleCourant.setCode(CODES_LEX.TOKEN_COMMAND);
                    return new Result(true, true, index);
                } else { 
                    //il s'agit donc soit d'une cible ou d'une dependance
                    //si le symbole précédant est un COLON (:) alors c'est une dépendance
                    if (SymboleCourant.getCode() == CODES_LEX.TOKEN_COLON ||
                     (SymboleCourant.getCode() == CODES_LEX.TOKEN_COMMA && SymboleCourant.getLastCode() == CODES_LEX.TOKEN_DEPENDENCIES) ||
                     SymboleCourant.getCode() == CODES_LEX.TOKEN_DEPENDENCIES
                     ) {           
                        SymboleCourant.setCode(CODES_LEX.TOKEN_DEPENDENCIES);
                    } 
                    else {
                        //si le symbole suivant est un COLON (:) alors il s'agit d'une cible 
                        int index2 = index ;
                        boolean isColon = false ;
                        while (character != ':') {  
                        //while (character != ':' && character != ' ') {                        
                            index2++; 
                            if (index2 >= line.length()) {
                                break; 
                            }
                            character = line.charAt(index2);
                        }
                        if (index2 < line.length()) {
                            isColon = true ;
                        }

                        if(isColon){
                            if(variable.equals("all")){
                                SymboleCourant.setCode(CODES_LEX.TOKEN_ALL);
                            } else {
                                SymboleCourant.setCode(CODES_LEX.TOKEN_TARGET);
                            }
                        } else {
                            SymboleCourant.setCode(CODES_LEX.TOKEN_VAR);
                        }
                    }

                    
                    //character = line.charAt(index);
                    return new Result(true, false, index);
                }
        }
        return new Result(false, false, 0);
    }

    // Classe pour gérer les résultats du traitement de caractère
    private static class Result {
        boolean shouldSkip;
        boolean nextLine;
        int changeI;

        Result(boolean shouldSkip, boolean nextLine, int changeI) {
            this.shouldSkip = shouldSkip;
            this.nextLine = nextLine;
            this.changeI = changeI;
        }
    }
}
