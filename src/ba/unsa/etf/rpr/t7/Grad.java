package ba.unsa.etf.rpr.t7;

public class Grad {
    private String naziv;
    private int brojStanovnika;
    private double[] temperature;

    public Grad(){
        naziv = "";
        brojStanovnika = 0;
        temperature = new double[1000];
    }

    public int getBrojStanovnika() {
        return brojStanovnika;
    }

    public void setBrojStanovnika(int brojStanovnika) {
        this.brojStanovnika = brojStanovnika;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public double[] getTemperature() {
        return temperature;
    }

    public void setTemperature(double[] temperature) {
        this.temperature = temperature;
    }
}
