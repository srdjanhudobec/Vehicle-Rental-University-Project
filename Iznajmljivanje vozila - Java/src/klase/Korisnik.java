package klase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Korisnik{
	
	static public Scanner sc = new Scanner(System.in);
	
	protected String korisnickoIme;
	protected String lozinka;
	
	
	public Korisnik() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Korisnik(String korisnickoIme, String lozinka) {
		super();
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
	}


	public String getKorisnickoIme() {
		return korisnickoIme;
	}


	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}


	public String getLozinka() {
		return lozinka;
	}


	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}
	
	public void odjavaKorisnika() {
		System.out.println("Uspesno ste se odjavili.Unesite broj ispred opcije koju birate.\n1.Prijava korisnika\n2.Izlazak iz aplikacije");
		Scanner sc = new Scanner(System.in);
		String izabrano = sc.next().strip();
		while(!(izabrano.equals("1")) && !(izabrano.equals("2"))) {//unos se ponavlja dok se ne izabere 1 ili 2
			System.out.println("Niste uneli odgovarajuci broj ispred opcije.Unesite broj ispred opcije koju birate.\n1.Prijava korisnika\n2.Izlazak iz aplikacije");
			izabrano = sc.next();
		}
		if(izabrano.equals("1")) {
			Platforma.prijavaKorisnika();//prijava korisnika
		}
		else if(izabrano.equals("2")) {
			System.exit(0);//izlaz iz aplikacije
		}
	}
	
	static public ArrayList<String> ucitajDosadasnjeKorisnike() {//ucitava sve korisnike i dodaje korisnicko ime svakog korisnika u jednu arraylistu
		ArrayList<String> listaKorisnickihImena = new ArrayList<String>();
		try {
			FileInputStream fis = new FileInputStream("data/korisnici.csv");
			String procitano = new String(fis.readAllBytes(),"utf-8"); 
			String [] linije = procitano.split("\n");
			for(int i=0;i<linije.length;i++) {
				String [] podelaPoZarezu = linije[i].split(",");
				listaKorisnickihImena.add(podelaPoZarezu[1]);
			}
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(IOException e) {
			e.getMessage();
			
		}
		return listaKorisnickihImena;
	}
	
	
	public ArrayList<Vozilo> pretragaVozila(){
		ArrayList<Vozilo> pretrazenaVozila = new ArrayList<Vozilo>();//vozila koja ce proci pretragu
		ArrayList<Vozilo> svaVozila = Vozilo.svaVozila();//sva vozila
		ArrayList<Najam> sviNajmovi = Najam.sviNajmovi();//svi najmovi
		System.out.println("Unesite prema cemu zelite da pretrazujute vozila unosom rednog broja ispred opcije:\n1.Po tipu vozila\n2.Po zauzetosti(slobodna ili zauzeta)\n3.Po servisiranju(servisirano ili ne)");
		String unesenaOpcija = sc.next().strip();
		while(!(unesenaOpcija.equals("1")) && !(unesenaOpcija.equals("2")) && !(unesenaOpcija.equals("3"))) {
			System.out.println("Niste uneli odgovarajucu opciju,unesite prema cemu zelite da pretrazujute vozila unosom rednog broja ispred opcije:\\n1.Po tipu vozila\\n2.Po zauzetosti(slobodna ili zauzeta)\\3.Po servisiranju(servisirano ili ne)");
			unesenaOpcija = sc.next().strip();
		}
		if(unesenaOpcija.equals("1")) {//pretraga po tipu vozila
			System.out.println("Unesite broj ispred tipa vozila po kom hocete da pretrazite vozila.\n1.Bicikl\n2.Trotinet");
			String tipVozila = sc.next().strip();
			while(!(tipVozila.equals("1")) && !(tipVozila.equals("2"))) {
				System.out.println("Niste uneli odgovarajucu opciju,unesite broj ispred tipa vozila po kom hocete da pretrazite vozila.\n1.Bicikl\n2.Trotinet");
				tipVozila = sc.next().strip();
			}
			if(tipVozila.equals("1")) {//pretraga bicikala
				for(int i=0;i<svaVozila.size();i++) {
					if(svaVozila.get(i).getClass().getName().strip().equals("klase.Bicikl")) {
						pretrazenaVozila.add(svaVozila.get(i));
					}
				}
			}
			else if(tipVozila.equals("2")) {//pretraga trotineta
				for(int i=0;i<svaVozila.size();i++) {
					if(svaVozila.get(i).getClass().getName().strip().equals("klase.Trotinet")) {
						pretrazenaVozila.add(svaVozila.get(i));
					}
				}
			}
		}else if(unesenaOpcija.equals("2")) {//pretraga po zauzetosti(slobodna ili zauzeta)
			System.out.println("Unesite broj ispred opcije zauzetosti po kojoj hocete da pretrazujete vozila.\n1.Slobodna vozila\n2.Zauzeta vozila");
			String zauzetost = sc.next().strip();
			while(!(zauzetost.equals("1")) && !(zauzetost.equals("2"))) {
				System.out.println("Niste uneli odgovarajucu opciju,unesite broj ispred opcije zauzetosti po kojoj hocete da pretrazujete vozila.\n1.Slobodna vozila\n2.Zauzeta vozila");
				zauzetost = sc.next().strip();
			}
			if(zauzetost.equals("1")) {//pretraga slobodnih
				for(int i=0;i<svaVozila.size();i++) {
					if(svaVozila.get(i).isZauzeto() == false) {
						pretrazenaVozila.add(svaVozila.get(i));
					}
				}
			}
			else if(zauzetost.equals("2")) {//pretraga zauzetih
				for(int i=0;i<svaVozila.size();i++) {
					if(svaVozila.get(i).isZauzeto() == true) {
						pretrazenaVozila.add(svaVozila.get(i));
					}
				}
			}
		}else if(unesenaOpcija.equals("3")) {//pretraga po tome da li je uradjen servis ili ne
			System.out.println("Unesite broj ispred opcije po kojoj hocete da pretrazujete vozila.\n1.Servisirana\n2.Nisu servisirana");
			String servisirana = sc.next().strip();
			while(!(servisirana.equals("1")) && !(servisirana.equals("2"))) {
				System.out.println("Niste uneli odgovarajucu opciju,unesite broj ispred opcije po kojoj hocete da pretrazujete vozila.\n1.Servisirana\n2.Nisu servisirana");
				servisirana = sc.next().strip();
			}
			if(servisirana.equals("1")) {//pretraga servisiranih
				for(int i=0;i<svaVozila.size();i++) {
					for(int j=0;j<sviNajmovi.size();j++) {
						if(sviNajmovi.get(j).isUradjenServis() == true && sviNajmovi.get(j).getIznajmljenoVozilo().getIdentifikator().equals(svaVozila.get(i).getIdentifikator())) {
							pretrazenaVozila.add(svaVozila.get(i));
						}
					}
				}
			}
			else if(servisirana.equals("2")) {//pretraga neservisiranih
				pretrazenaVozila = Serviser.pretragaPoServisiranju();
			}
		}
		if(pretrazenaVozila.size()> 0) {
			//sta god da se dodalo u ovu listu pretrazenih vozila,ovde se radi ispis te liste pre nego sto se vrati,u formatu kao sto je zapisano u vozila.csv fajlu
			for(int i=0;i<pretrazenaVozila.size();i++) {
				String zaIspis = "";
				Vozilo trenVozilo = pretrazenaVozila.get(i);
				if(trenVozilo.getClass().getName().strip().equals("klase.Bicikl")) {
					Bicikl b1 = (Bicikl) trenVozilo;
					zaIspis = "Bicikl," + b1.getIdentifikator() + "," + b1.getVlasnik().getKorisnickoIme() + "," + b1.getCenaPoSatu() + "," + b1.getVelicinaTockova() + "," + b1.getNosivost() + "," + b1.getStanje() + "," + b1.isZauzeto() + "," + b1.getBrojBrzina() + "," + b1.getVisina();
				}
				else if(trenVozilo.getClass().getName().strip().equals("klase.Trotinet")) {
					Trotinet t1 = (Trotinet) trenVozilo;
					zaIspis = "Trotinet," + t1.getIdentifikator() + "," + t1.getVlasnik().getKorisnickoIme() + "," + t1.getCenaPoSatu() + "," + t1.getVelicinaTockova() + "," + t1.getNosivost() + "," + t1.getStanje() + "," + t1.isZauzeto() + "," + t1.getMaksimalnaBrzina() + "," + t1.getTrajanjeBaterije();
				}
				System.out.println(zaIspis);
			}
		}
		else {
			System.out.println("Nema vozila pretrazenog po unetim filterima,probajte opet.");
			//ako nema vozila po tim filterima,korisniku se nudi mogucnost da opet pretrazi ili izabere neku drugu opciju,zavisno od toga koja je klasa
		}//na osnovu toga da li je korisnik iznajmljivac,serviser ili vlasnik,kreira se instanca te klase i pozivaju se njegove opcije koje mu se nude
		if(this.getClass().getName().strip().equals("klase.Iznajmljivac")){
			Iznajmljivac iz1 = (Iznajmljivac) this;
			Platforma.opcijeIznajmljivaca(iz1);
		}else if(this.getClass().getName().strip().equals("klase.Serviser")){
			Serviser s1 = (Serviser) this;
			Platforma.opcijeServisera(s1);
		}else if(this.getClass().getName().strip().equals("klase.Vlasnik")){
			Vlasnik v1 = (Vlasnik) this;
			Platforma.opcijeVlasnika(v1);
		}
		return pretrazenaVozila;
	}
}

