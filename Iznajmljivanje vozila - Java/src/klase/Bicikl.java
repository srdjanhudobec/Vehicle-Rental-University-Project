package klase;
import enumi.Stanje;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import enumi.Stanje;

public class Bicikl extends Vozilo{
	

	private int brojBrzina;
	private int visina;
	
	public Bicikl() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Bicikl(String identifikator, Vlasnik vlasnik, int cenaPoSatu, int velicinaTockova, int nosivost,
			Stanje stanje, boolean zauzeto,int brojBrzina,int visina) {
		super(identifikator, vlasnik, cenaPoSatu, velicinaTockova, nosivost, stanje, zauzeto);
		// TODO Auto-generated constructor stub
		this.brojBrzina = brojBrzina;
		this.visina = visina;
	}

	public int getBrojBrzina() {
		return brojBrzina;
	}

	public void setBrojBrzina(int brojBrzina) {
		this.brojBrzina = brojBrzina;
	}

	public int getVisina() {
		return visina;
	}

	public void setVisina(int visina) {
		this.visina = visina;
	}
	
	@Override
	public String toString() {
		return "Bicikl [identifikator = " + identifikator + ", vlasnik = " + vlasnik + ", cenaPoSatu = " + cenaPoSatu + ", velicinaTockova = " + velicinaTockova + ", nosivost = " + nosivost + ", stanje = " + stanje + ", zauzeto = " + zauzeto + ", brojBrzina = " + brojBrzina + ", visina = " + visina + "]";
	}
	
	static public void dodajBicikl() {//ova metoda je sluzila da se doda bicikl u fajl da se ne bi kucao rucno,ali ova metoda vise nema svrhu,ipak je ostavljena cisto da se vidi kako se biciklo kreira
		String id = dodatneKlase.kreiranjeIdentifikatora.generisiID(6);
		ArrayList<Vozilo> vozila = new ArrayList<Vozilo>();
		Vlasnik v1 = new Vlasnik("markomarkovic","markomoj",null);
		int cenaPoSatu = 100;
		int velicinaTockova = 10;
		int nosivost = 150;
		Stanje stanje = enumi.Stanje.BEZ_OSTECENJA;
		boolean zauzeto = false;
		int brojBrzina = 6;
		int visina = 100;
		Bicikl b1 = new Bicikl(id,v1,cenaPoSatu,velicinaTockova,nosivost,stanje,zauzeto,brojBrzina,visina);
		vozila.add(b1);
		v1.setVozila(vozila);
		String zaUpis = null;//zapisivanje instance u vozila
		try {
			File vozilaCSV = new File("data/vozila.csv");
			FileWriter f1 = new FileWriter("data/vozila.csv",true);
			if(vozilaCSV.length() > 0) {
				zaUpis = "\n" + "Bicikl," +  id + "," + v1 +"," +  cenaPoSatu +"," +  velicinaTockova +"," +  nosivost +"," +  stanje +"," +  zauzeto +"," +  brojBrzina +"," +  visina; 
			}
			else {
				zaUpis = "Bicikl," + id + "," + v1 +"," +  cenaPoSatu +"," +  velicinaTockova +"," +  nosivost +"," +  stanje +"," +  zauzeto +"," +  brojBrzina +"," +  visina;
			}
			f1.write(zaUpis);
			f1.flush();
			f1.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IOException e) {
			e.getMessage();
		}
	}
	
	//nasledjene metode interfejsa koje implementira vozilo
	@Override
	public void popravi() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unajmi(Vozilo izabranoVozilo) {
		// TODO Auto-generated method stub
		
	}
}
