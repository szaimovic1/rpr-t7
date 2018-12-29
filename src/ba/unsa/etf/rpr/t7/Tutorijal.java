package ba.unsa.etf.rpr.t7;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Tutorijal {

    public static void main(String[] args) {

    }
    public static ArrayList<Grad> ucitajGradove(){
        ArrayList<Grad> gradovi = new ArrayList<>();
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileReader("mjerenja.txt.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Datoteka ulaz.txt ne postoji ili se ne može otvoriti");
        }
        try{
            while(ulaz.hasNext()) {
                String[] mjerenja = ulaz.nextLine().split(",");
                Grad grad = new Grad();
                grad.setNaziv(mjerenja[0]);
                double[] niz;
                niz = new double[mjerenja.length-1];
                for(int i = 0; i<mjerenja.length; i++) {
                    if(i==1000) break;
                    niz[i] = Double.valueOf(mjerenja[i+1]);
                }
                grad.setTemperature(niz);
                gradovi.add(grad);
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            try{
                ulaz.close();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return gradovi;
    }
    public static UN ucitajXml(ArrayList<Grad> gradovi) {
        UN un = new UN();
        ArrayList<Drzava> drzave = new ArrayList<Drzava>();
        Drzava drzava = new Drzava();
        Grad grad = new Grad();
        Document xmldoc = null;
        try {
            DocumentBuilder docReader = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            xmldoc = docReader.parse(new File("drzave.xml"));
        } catch (Exception e) {
            System.out.println("drzave.xml nije validan XML dokument");
        }
        Element korijen = xmldoc.getDocumentElement();
        NodeList nodeList = korijen.getChildNodes();
        for(int i=0;i<nodeList.getLength();i++) {
            Node node = nodeList.item(i);
            if(node instanceof Element) {
                Element element = (Element) node;
                String naziv = element.getElementsByTagName("naziv").item(0).getTextContent();
                int brojStanovnika = Integer.parseInt(element.getAttribute("stanovnika"));
                String jedinica = element.getElementsByTagName("povrsina").item(0).getAttributes().item(0).getTextContent();
                int povrsina = Integer.parseInt(element.getElementsByTagName("povrsina").item(0).getTextContent());
                Element glavniGrad = (Element) element.getElementsByTagName("glavnigrad").item(0);
                String glavniGradIme = glavniGrad.getElementsByTagName("naziv").item(0).getTextContent();
                int glavniStanovnika = Integer.parseInt(glavniGrad.getAttribute("stanovnika"));
                for(int j = 0; j < gradovi.size(); j++) {
                    if(glavniGradIme.equals(gradovi.get(j).getNaziv())) {
                        Grad gradNovi = new Grad();
                        gradNovi.setNaziv(glavniGradIme);
                        gradNovi.setBrojStanovnika(glavniStanovnika);
                        gradNovi.setTemperature(gradovi.get(j).getTemperature());
                        Drzava drzavaNova = new Drzava();
                        drzavaNova.setBrojStanovnika(brojStanovnika);
                        drzavaNova.setGlavniGrad(gradNovi);
                        drzavaNova.setJedinicaZaPovrsinu(jedinica);
                        drzavaNova.setNaziv(naziv);
                        drzavaNova.setPovrsina(povrsina);
                        drzave.add(drzavaNova);
                    }
                }
            }
        }
        un.setDrzave(drzave);
        return un;
    }
}
