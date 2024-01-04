package klase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

import enumi.Stanje;


public class Serviser extends Korisnik{

	public Serviser() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public Serviser(String korisnickoIme, String lozinka) {
		super(korisnickoIme, lozinka);
		// TODO Auto-generated constructor stub
	}
	
	public Serviser registrovanje() {//registracija na osnovu procitanih podataka
		ArrayList<String> listaKorisnickihImena = Korisnik.ucitajDosadasnjeKorisnike();
		System.out.println("Unesite vase korisnicko ime: ");
		String korisnickoImeServisera = sc.nextLine().strip();
		while(listaKorisnickihImena.contains(korisnickoImeServisera)) {
			System.out.println("Uneli ste postojece korisnicko ime,unesite vase korisnicko ime: ");
			korisnickoImeServisera = sc.nextLine().strip();
		}
		
		System.out.println("Unesite vasu lozinku: ");
		String lozinkaServisera = sc.nextLine().strip();
		try {
			File korisniciCSV = new File("data/korisnici.csv");
			FileWriter fw = new FileWriter("data/korisnici.csv",true);
			String zaUpis = null;
			if(korisniciCSV.length() > 0) {
				zaUpis = getClass() + "," +  korisnickoImeServisera + "," + lozinkaServisera +  "\n"; //pogledati jel su potrebni jos neki podaci da se zapisu al msm da nisu
			}
			else {
				zaUpis = getClass() + "," + korisnickoImeServisera + "," + lozinkaServisera;
			}
			fw.write(zaUpis);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Serviser s1 = new Serviser(korisnickoImeServisera,lozinkaServisera);//kreiranje instance servisera
		return s1;
	}

	static public ArrayList<Vozilo> pretragaPoServisiranju(){//pretraga vozila koja nisu servisirana
		ArrayList<Najam> sviNajmovi = Najam.sviNajmovi();
		ArrayList<Vozilo> svaVozila = Vozilo.svaVozila();
		ArrayList<Vozilo> pretrazenaVozila = new ArrayList<Vozilo>();
		for(int i=0;i<svaVozila.size();i++) {
			for(int j=0;j<sviNajmovi.size();j++) {//prolazi kroz sve najmove i ako im je uradjen servis false dodaje se u listu vozila
				if(sviNajmovi.get(j).isUradjenServis() == false && sviNajmovi.get(j).getIznajmljenoVozilo().getIdentifikator().equals(svaVozila.get(i).getIdentifikator())) {
					pretrazenaVozila.add(svaVozila.get(i));
				}
			}
		}
		return pretrazenaVozila;
	}
	
	static public ArrayList<Vozilo> pretragaVozilaZaPopravku() {//pretraga vozila koja imaju malo ili veliko ostecenje
		ArrayList<Vozilo> svaVozila = Vozilo.svaVozila();
		ArrayList<Vozilo> pretrazenaVozila = new ArrayList<Vozilo>();
		for(int i=0;i<svaVozila.size();i++) {//prolazi kroz sva vozila,ako vozilo ima malo ili veliko ostecenje dodaju se u listu vozila
			if(svaVozila.get(i).getStanje().equals(Stanje.MALO_OSTECENJE) || svaVozila.get(i).getStanje().equals(Stanje.VELIKO_OSTECENJE)) {
				pretrazenaVozila.add(svaVozila.get(i));
			}
		}
		return pretrazenaVozila;
	}
	
	public void popravkaVozila() {
		ArrayList<Vozilo> vozila = pretragaVozilaZaPopravku();
		if(vozila.size() <= 0) {//ako su sva vozila bez ostecenja korisniku se nude sve opcije opet
			System.out.println("Nema vozila koja mogu da se poprave.");
			Platforma.opcijeServisera(this);
		}
		else {
			ArrayList<String> indeksi = new ArrayList<String>();
			//ispis vozila sa malim i velikim ostecenjem
			for(int i=0;i<vozila.size();i++) {
				String zaUpis = "";
				Vozilo trenutnoVozilo = vozila.get(i);
				if(trenutnoVozilo.getClass().getName().equals("klase.Bicikl")) {
					Bicikl trenutniBajs = (Bicikl) trenutnoVozilo;
					zaUpis = (i+1) + "- Bicikl," + trenutniBajs.getIdentifikator() + "," + trenutniBajs.getVlasnik().getKorisnickoIme() + "," + trenutniBajs.getCenaPoSatu() + "," + trenutniBajs.getVelicinaTockova() + "," + trenutniBajs.getNosivost() + "," + trenutniBajs.getStanje() + "," + trenutniBajs.isZauzeto() + "," + trenutniBajs.getBrojBrzina() + "," + trenutniBajs.getVisina() ;
				}else if(trenutnoVozilo.getClass().getName().equals("klase.Trotinet")) {
					Trotinet trenutniTrot = (Trotinet) trenutnoVozilo;
					zaUpis = (i+1) + "- Trotinet," + trenutniTrot.getIdentifikator() + "," + trenutniTrot.getVlasnik().getKorisnickoIme() + "," + trenutniTrot.getCenaPoSatu() + "," + trenutniTrot.getVelicinaTockova() + "," + trenutniTrot.getNosivost() + "," + trenutniTrot.getStanje() + "," + trenutniTrot.isZauzeto() + "," + trenutniTrot.getMaksimalnaBrzina() + "," + trenutniTrot.getTrajanjeBaterije() ;
				}
				System.out.println(zaUpis);
				indeksi.add("" + (i+1));
			}
			System.out.println("Unesite broj ispred vozila koje zelite da popravite: ");
			Scanner sc = new Scanner(System.in);
			String unos = sc.next();
			//ovde ide while
			while(!(indeksi.contains(unos))) {
				System.out.println("Niste uneli odgovarajuci broj ispred vozila,unesite broj ispred vozila koje zelite da popravite: ");
				unos = sc.next();
			}
			//stavljanje stanja vozila na bez ostecenja
			Vozilo izabranoVoz = vozila.get(Integer.parseInt(unos)-1);
			if(izabranoVoz.getClass().getName().equals("klase.Bicikl")) {
				Bicikl b1 = (Bicikl) izabranoVoz;
				b1.popravi(b1);//ako je vozilo bicikl,poziva se metoda popravi od klase Bicikl
			}else if(izabranoVoz.getClass().getName().equals("klase.Trotinet")) {
				Trotinet t1 = (Trotinet) izabranoVoz;
				t1.popravi(t1);//ako je vozilo trotinet,poziva se metoda popravi od klase Trotinet
			}
		}
		Platforma.opcijeServisera(this); //poziva se da se program ne bi zavrsio vec da korisnik moze da bira jos neke opcije
	}
	
	public void pregledanjeVozila() {
		ArrayList<Vozilo> vozila = pretragaPoServisiranju();
		if(vozila.size() <= 0) {//ako su sva vozila servisirana,korisniku se opet nude opcije,tako da moze da kreira najam recimo i da nakon toga moze vozilu iz tog najma da uradi servis(tj.pregleda ga)
			System.out.println("Nema vozila koja mogu da se pregledaju,tacnije nema vozila koja nisu servisirana.");
			Platforma.opcijeServisera(this);
		}
		else {
			ArrayList<String> indeksi = new ArrayList<String>();
			//ispis vozila koji nemaju uradjeni servis
			for(int i=0;i<vozila.size();i++) {
				String zaUpis = "";
				Vozilo trenutnoVozilo = vozila.get(i);
				if(trenutnoVozilo.getClass().getName().equals("klase.Bicikl")) {
					Bicikl trenutniBajs = (Bicikl) trenutnoVozilo;
					zaUpis = (i+1) + "- Bicikl," + trenutniBajs.getIdentifikator() + "," + trenutniBajs.getVlasnik().getKorisnickoIme() + "," + trenutniBajs.getCenaPoSatu() + "," + trenutniBajs.getVelicinaTockova() + "," + trenutniBajs.getNosivost() + "," + trenutniBajs.getStanje() + "," + trenutniBajs.isZauzeto() + "," + trenutniBajs.getBrojBrzina() + "," + trenutniBajs.getVisina() ;
				}else if(trenutnoVozilo.getClass().getName().equals("klase.Trotinet")) {
					Trotinet trenutniTrot = (Trotinet) trenutnoVozilo;
					zaUpis = (i+1) + "- Trotinet," + trenutniTrot.getIdentifikator() + "," + trenutniTrot.getVlasnik().getKorisnickoIme() + "," + trenutniTrot.getCenaPoSatu() + "," + trenutniTrot.getVelicinaTockova() + "," + trenutniTrot.getNosivost() + "," + trenutniTrot.getStanje() + "," + trenutniTrot.isZauzeto() + "," + trenutniTrot.getMaksimalnaBrzina() + "," + trenutniTrot.getTrajanjeBaterije() ;
				}
				System.out.println(zaUpis);
				indeksi.add("" + (i+1));
			}
			System.out.println("Unesite broj ispred vozila koje zelite da pregledate: ");
			Scanner sc = new Scanner(System.in);
			String unos = sc.next();
			//ovde ide while
			while(!(indeksi.contains(unos))) {
				System.out.println("Niste uneli odgovarajuci broj ispred vozila,unesite broj ispred vozila koje zelite da pregledate: ");
				unos = sc.next();
			}
			Vozilo izabranoVoz = vozila.get(Integer.parseInt(unos) - 1);
			//korisceno matches cisto radi promene
			System.out.println("Unesite broj ispred stanja na koje zelite da izmenite vozilo: \n1.Bez ostecenja\n2.Malo ostecenje\n3.Veliko ostecenje\n4.Neupotrebljivo");
			String izbor = sc.next();
			while(!(izbor.matches("[1-4]"))) {
				System.out.println("Niste uneli odgovarajuci broj,unesite broj ispred stanja na koje zelite da izmenite vozilo: \n1.Bez ostecenja\n2.Malo ostecenje\n3.Veliko ostecenje\n4.Neupotrebljivo");
				izbor = sc.next();
			}
			switch(izbor.strip()) {
				case "1":izabranoVoz.setStanje(Stanje.BEZ_OSTECENJA);
					break;
				case "2":izabranoVoz.setStanje(Stanje.MALO_OSTECENJE);
					break;
				case "3":izabranoVoz.setStanje(Stanje.VELIKO_OSTECENJE);
					break;
				case "4":izabranoVoz.setStanje(Stanje.NEUPOTREBLJIVO);
					break;
			}
			//sledi upis u fajl
			//iscitavanje iz fajla i menjanje,rezultat smesten u listu
			ArrayList<String> vozilaString = new ArrayList<String>();
			try {
				FileInputStream fis = new FileInputStream("data/vozila.csv");
				String procitano = new String(fis.readAllBytes(),"utf-8");
				String [] linije = procitano.split("\n");
				for(int i=0;i<linije.length;i++) {
					String [] pojedinacnaPolja = linije[i].split(",");
					if(pojedinacnaPolja[1].strip().equals(izabranoVoz.getIdentifikator())) {
						String zaUpis = pojedinacnaPolja[0] + "," + pojedinacnaPolja[1] + "," + pojedinacnaPolja[2] + "," + pojedinacnaPolja[3] + "," + pojedinacnaPolja[4] + "," + pojedinacnaPolja[5] + "," + izabranoVoz.getStanje() + "," + pojedinacnaPolja[7] + "," + pojedinacnaPolja[8] + "," + pojedinacnaPolja[9];
						vozilaString.add(zaUpis);
					}
					else {
						vozilaString.add(linije[i]);
					}
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(IOException e) {
				e.getMessage();
			}
			//zapis te liste u vozila.csv
			try {
				FileWriter fw = new FileWriter("data/vozila.csv");
				for(int i=0;i<vozilaString.size();i++) {
					fw.write(vozilaString.get(i) + "\n");
					fw.flush();
				}
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//podatak o servisiranju u najmovima da se stavi da je uradjen servis i da se to izmeni i u fajlu najmovi.csv
			ArrayList<Najam> sviNajmovi = Najam.sviNajmovi();
			for(int i=0;i<sviNajmovi.size();i++) {
				if(sviNajmovi.get(i).getIznajmljenoVozilo().getIdentifikator().equals(izabranoVoz.getIdentifikator())) {
					sviNajmovi.get(i).setUradjenServis(true);
				}
			}
			//citanje liste najmova i izmena
			ArrayList<String> najmoviString = new ArrayList<String>();
			try {
				FileInputStream fis = new FileInputStream("data/najmovi.csv");
				String procitano = new String(fis.readAllBytes(),"utf-8");
				String [] linije = procitano.split("\n");
				for(int i=0;i<linije.length;i++) {
					String [] pojedinacnaPolja = linije[i].split(",");
					if(pojedinacnaPolja[4].strip().equals(izabranoVoz.getIdentifikator())) {
						String zaUpis = pojedinacnaPolja[0] + "," + pojedinacnaPolja[1] + "," + pojedinacnaPolja[2] + "," + pojedinacnaPolja[3] + "," + pojedinacnaPolja[4] + "," + pojedinacnaPolja[5] + ",true";
						najmoviString.add(zaUpis);
					}
					else {
						najmoviString.add(linije[i]);
					}
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(IOException e) {
				e.getMessage();
			}
			//zapis izmenjene liste najma u najmovi.csv fajl
			try {
				FileWriter fw = new FileWriter("data/najmovi.csv");
				for(int i=0;i<najmoviString.size();i++) {
					fw.write(najmoviString.get(i) + "\n");
					fw.flush();
				}
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Platforma.opcijeServisera(this); //poziva se da se program ne bi zavrsio vec da korisnik moze da bira jos neke opcije
	}
}
