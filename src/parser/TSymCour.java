public class TSymCour {

    private String nom;
    private CODES_LEX code;
    private CODES_LEX last_code ;

    

    public TSymCour() {
    }

    public TSymCour(CODES_LEX code, String nom) {
        this.code = code;
        this.nom = nom;
    }

    public CODES_LEX getCode() {
        return code;
    }

    public CODES_LEX getLastCode() {
        return last_code;
    }

    public void setCode(CODES_LEX code) {
        this.last_code = this.code ;
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setSymbole(String nom, CODES_LEX code){
        this.code = code;
        this.nom = nom;
    }
}
