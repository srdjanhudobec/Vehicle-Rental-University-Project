package klase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import enumi.Stanje;
public class Trotinet extends Vozilo{
	
	private int maksimalnaBrzina;
	private int trajanjeBaterije;
	
	public Trotinet() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Trotinet(String identifikator, Vlasnik vlasnik, int cenaPoSatu, int velicinaTockova, int nosivost,
			Stanje stanje, boolean zauzeto,int maksimalnaBrzina,int trajanjeBaterije) {
		super(identifikator, vlasnik, cenaPoSatu, velicinaTockova, nosivost, stanje, zauzeto);
		// TODO Auto-generated constructor stub
		this.maksimalnaBrzina = maksimalnaBrzina;
		this.trajanjeBaterije = trajanjeBaterije;
	}

	public int getMaksimalnaBrzina() {
		return maksimalnaBrzina;
	}

	public void setMaksimalnaBrzina(int maksimalnaBrzina) {
		this.maksimalnaBrzina = maksimalnaBrzina;
	}

	public int getTrajanjeBaterije() {
		return trajanjeBaterije;
	}

	public void setTrajanjeBaterije(int trajanjeBaterije) {
		this.trajanjeBaterije = trajanjeBaterije;
	}

	

	@Override
	public void popravi() {
		// TODO Auto-generated method stub
		
	}
	
	public String toString() {
		return "Trotinet [identifikator = " + identifikator + ", vlasnik = " + vlasnik + ", cenaPoSatu = " + cenaPoSatu + ", velicinaTockova = " + velicinaTockova + ", nosivost = " + nosivost + ", stanje = " + stanje + ", zauzeto = " + zauzeto + ", maksimalnaBrzina = " + maksimalnaBrzina + ", trajanjeBaterije = " + trajanjeBaterije + "]";
	}
	
	static public void dodajTrotinet() {//ova metoda je sluzila da se doda trotinet u fajl da se ne bi kucao rucno,ali ova metoda vise nema svrhu,ipak je ostavljena cisto da se vidi kako se trotinet kreira
		String id = dodatneKlase.kreiranjeIdentifikatora.generisiID(6);
		ArrayList<Vozilo> vozila = new ArrayList<Vozilo>();
		Vlasnik v1 = new Vlasnik("markomarkovic","markomoj",null);
		int cenaPoSatu = 100;
		int velicinaTockova = 10;
		int nosivost = 150;
		Stanje stanje = enumi.Stanje.BEZ_OSTECENJA;
		boolean zauzeto = false;
		int maksimalnaBrzina = 25;
		int trajanjeBaterije = 40;
		Trotinet t1 = new Trotinet(id,v1,cenaPoSatu,velicinaTockova,nosivost,stanje,zauzeto,maksimalnaBrzina,trajanjeBaterije);
		vozila.add(t1);
		v1.setVozila(vozila);
		String zaUpis = null;//zapis instance trotineta u vozila.csv
		try {
			File vozilaCSV = new File("data/vozila.csv");
			FileWriter f1 = new FileWriter("data/vozila.csv",true);
			if(vozilaCSV.length() > 0) {
				zaUpis = "\n" + "Trotinet," +  id + "," + v1 +"," +  cenaPoSatu +"," +  velicinaTockova +"," +  nosivost +"," +  stanje +"," +  zauzeto +"," +  maksimalnaBrzina +"," +  trajanjeBaterije; 
			}
			else {
				zaUpis = "Trotinet," + id + "," + v1 +"," +  cenaPoSatu +"," +  velicinaTockova +"," +  nosivost +"," +  stanje +"," +  zauzeto +"," +  maksimalnaBrzina +"," +  trajanjeBaterije;
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

	@Override
	public void unajmi(Vozilo izabranoVozilo) {
		// TODO Auto-generated method stub
		
	}
}
