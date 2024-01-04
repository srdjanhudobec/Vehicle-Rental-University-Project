package klase;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Platforma {

	private ArrayList<Korisnik> korisnici;
	private ArrayList<Vozilo> dostupnaVozila;
	private String naziv;
	
	public Platforma() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Platforma(ArrayList<Korisnik> korisnici, ArrayList<Vozilo> dostupnaVozila, String naziv) {
		super();
		this.korisnici = korisnici;
		this.dostupnaVozila = dostupnaVozila;
		this.naziv = naziv;
	}

	public ArrayList<Korisnik> getKorisnici() {
		return korisnici;
	}

	public void setKorisnici(ArrayList<Korisnik> korisnici) {
		this.korisnici = korisnici;
	}

	public ArrayList<Vozilo> getDostupnaVozila() {
		return dostupnaVozila;
	}

	public void setDostupnaVozila(ArrayList<Vozilo> dostupnaVozila) {
		this.dostupnaVozila = dostupnaVozila;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
	static Scanner sc = new Scanner(System.in);
	static String korisnickoIme;
	static String lozinka;
	
	static public void opcijeVlasnika(Vlasnik v1) {
		System.out.println("Izaberite neku od opcija unosom rednog broja ispred opcije:\n1.Pretraga vozila\n2.Stavljanje vozila van upotrebe\n3.Odjava");
		Scanner sc = new Scanner(System.in);
		String unosKorisnika = sc.next();
		while(!(unosKorisnika.matches("[1-3]"))) {
			System.out.println("Niste uneli odgovarajuci broj,izaberite neku od opcija unosom rednog broja ispred opcije:\n1.Pretraga vozila\n2.Stavljanje vozila van upotrebe\n3.Odjava");
			unosKorisnika = sc.next();
		}
		if(unosKorisnika.strip().equals("1")) {
			v1.pretragaVozila();
		}
		else if(unosKorisnika.strip().equals("2")) {
			v1.stavljanjeVozilaVanUpotrebe();
		}
		else if(unosKorisnika.strip().equals("3")) {
			v1.odjavaKorisnika();
		}
	}
	
	static public void opcijeServisera(Serviser s1) {
		System.out.println("Izaberite neku od opcija unosom rednog broja ispred opcije:\n1.Pretraga vozila\n2.Pregledanje vozila\n3.Popravka vozila\n4.Odjava");
		Scanner sc = new Scanner(System.in);
		String unosKorisnika = sc.next();
		while(!(unosKorisnika.matches("[1-4]"))) {
			System.out.println("Niste uneli odgovarajuci broj,izaberite neku od opcija unosom rednog broja ispred opcije:\n1.Pretraga vozila\n2.Pregledanje vozila\n3.Popravka vozila\n4.Odjava");
			unosKorisnika = sc.next();
		}
		if(unosKorisnika.strip().equals("1")) {
			s1.pretragaVozila();
		}
		else if(unosKorisnika.strip().equals("2")) {
			s1.pregledanjeVozila();
		}
		else if(unosKorisnika.strip().equals("3")) {
			s1.popravkaVozila();
		}
		else if(unosKorisnika.strip().equals("4")) {
			s1.odjavaKorisnika();
		}
	}
	
	static public void opcijeIznajmljivaca(Iznajmljivac iz1) {
		System.out.println("Izaberite neku od opcija unosom rednog broja ispred opcije:\n1.Kreiranje najma\n2.Vracanje vozila\n3.Dopuna novcanih sredstava\n4.Pretraga vozila\n5.Prikaz istorije najma\n6.Odjava");
		Scanner sc = new Scanner(System.in);
		String unosKorisnika = sc.next();
		while(!(unosKorisnika.matches("[1-6]"))) {
			System.out.println("Niste uneli odgovarajuci broj,Izaberite neku od opcija unosom rednog broja ispred opcije:\n1.Kreiranje najma\n2.Vracanje vozila\n3.Dopuna novcanih sredstava\n4.Pretraga vozila\n5.Prikaz istorije najma\n6.Odjava");
			unosKorisnika = sc.next();
		}
		//radjeno sa switch case jer izgleda lepse u odnosu na brdo if else grananja,ipak ovde imamo 6 opcija
		switch(unosKorisnika.strip()) {
			case "1":iz1.kreiranjeNajma();
			break;
			case "2":iz1.vracanjeVozila();
			break;
			case "3":iz1.dopunaSredstava();;
			break;
			case "4":iz1.pretragaVozila();
			break;
			case "5":iz1.prikazIstorijeNajma();
			break;
			case "6":iz1.odjavaKorisnika();;
			break;
		}
	}
	
	//ova metoda je staticka jer se ovde ne odnosi ni na jednu konkretnu klasu
	static public void prijavaKorisnika() {
		ArrayList<String> postojeciKorisnici = Korisnik.ucitajDosadasnjeKorisnike();
		System.out.println("Prijava korisnika.\nUnesite korisnicko ime: ");
		korisnickoIme = sc.nextLine().strip();
		while(!(postojeciKorisnici.contains(korisnickoIme))) {
			System.out.println("Korisnicko ime ne postoji,unesite korisnicko ime: ");
			korisnickoIme = sc.nextLine().strip();
		}
		System.out.println("Unesite lozinku: ");
		lozinka = sc.nextLine().strip();
		boolean ulogovan = false;
		//ucitavanje svega iz csv fajla
		try {
			FileInputStream fis = new FileInputStream("data/korisnici.csv");
			String procitano = new String(fis.readAllBytes(),"utf-8");
			String [] linije = procitano.split("\n");
			for(int i=0;i<linije.length;i++) {
				String [] pojedinacnaPolja = linije[i].split(",");
				if(korisnickoIme.equals(pojedinacnaPolja[1].strip()) && lozinka.equals(pojedinacnaPolja[2].strip())) {
					System.out.println("Uspesno ste prijavljeni " + korisnickoIme);
					ulogovan = true;
					if(pojedinacnaPolja[0].equals("class klase.Vlasnik")){
						if(pojedinacnaPolja.length != 4) {
							System.out.println("Doslo je do greske,probajte opet.");
							prijavaKorisnika();
						}
						Vlasnik v1 = new Vlasnik();
						v1 = v1.kreirajInstancuVlasnika();
						//promenjeno da se novokreirani vlasnik upise na mesto vlasnika u vozilima koje on poseduje
						ArrayList<Vozilo> vozilaVlasnika = v1.getVozila();
						for(int j=0;j<vozilaVlasnika.size();j++) {
							v1.getVozila().get(j).setVlasnik(v1);
						}
						opcijeVlasnika(v1);
						ulogovan = true;
					}
					else if(pojedinacnaPolja[0].equals("class klase.Serviser")){
						if(pojedinacnaPolja.length != 3) {
							System.out.println("Doslo je do greske,probajte opet.");
							prijavaKorisnika();
						}
						Serviser s1 = new Serviser(korisnickoIme,lozinka);
						opcijeServisera(s1);
						ulogovan = true;
					}
					else if(pojedinacnaPolja[0].equals("class klase.Iznajmljivac")) {
						if(pojedinacnaPolja.length != 10) {
							System.out.println("Doslo je do greske,probajte opet.");
							prijavaKorisnika();
						}
						Iznajmljivac iz1 = new Iznajmljivac();
						iz1 = iz1.kreirajInstancuIznaj();
						opcijeIznajmljivaca(iz1);
						ulogovan = true;
					}
				}
			}
			if(ulogovan == false) {
				//ako korisnik nije uspeo da se uloguje,nudi se opet prijava
				System.out.println("Nisu uneti tacni podaci,probajte opet.");
				prijavaKorisnika();
			}
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(IOException e) {
			e.getMessage();
		}
	}

	public static void main(String[] args) {
		System.out.println("Dobrodosli u agenciju NSGOClub.\nNasa agencija se bavi iznajmljivanjem bicikala i trotineta. \nAko imate nalog ulogujte se ili ako nemate nalog registrujte se.");
		Scanner sc = new Scanner(System.in);
		System.out.println("\n1.Prijava korisnika\n2.Registracija korisnika\nUnesite broj ispred opcije koju birate:");
		String izborOpcije = sc.next();
		while(!(izborOpcije.equals("1")) && !(izborOpcije.equals("2"))){
			System.out.println("\n1.Prijava korisnika\n2.Registracija korisnika\nUnesite broj ispred opcije koju birate:");
			izborOpcije = sc.nextLine();
		}
		if(izborOpcije.equals("1")) {
			prijavaKorisnika();
		}
		else {
			System.out.println("Unesite da li hocete da se registrujete kao iznajmljivac ili kao serviser.\n1.Iznajmljivac\n2.Serviser\nUnesite broj ispred zeljene kategorije: ");
			String izborOpcijeRegistracije = sc.nextLine();
			while(!(izborOpcijeRegistracije.equals("1")) && !(izborOpcijeRegistracije.equals("2"))){
				System.out.println("Unesite da li hocete da se registrujete kao iznajmljivac ili kao serviser.\n1.Iznajmljivac\n2.Serviser\nUnesite broj ispred zeljene kategorije: ");
				izborOpcijeRegistracije = sc.nextLine();
			}
			if(izborOpcijeRegistracije.equals("1")) {//registracija iznajmljivaca
				Iznajmljivac iz1 = new Iznajmljivac();
				iz1 = iz1.registrovanje();
				prijavaKorisnika();
			}
			else {
				Serviser s1 = new Serviser();//registracija servisera
				s1 = s1.registrovanje();
				prijavaKorisnika();
			}
		}
	}
	
}
