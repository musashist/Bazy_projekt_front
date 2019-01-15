package sample;

public class Worker {
    private String Imie;
    private String Nazwisko;

    public Worker(String imie, String nazwisko){
        this.Imie = imie;
        this.Nazwisko = nazwisko;
    }

    public String getImie(){
        return Imie;
    }

    public String getNazwisko(){
        return Nazwisko;
    }
}
