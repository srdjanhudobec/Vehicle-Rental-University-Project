package klase;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.spi.FileSystemProvider;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

import enumi.DodatnaOprema;
import enumi.Stanje;

public class Iznajmljivac extends Korisnik{
	
	private Kartica nsgoKartica;
	
	public Iznajmljivac() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Iznajmljivac(String korisnickoIme,String lozinka,Kartica nsgoKartica) {
		super(korisnickoIme,lozinka);
		this.nsgoKartica = nsgoKartica;
	}

	public Kartica getNSGOKartica() {
		return nsgoKartica;
	}

	public void setNSGOKartica(Kartica nSGOKartica) {
		nsgoKartica = nSGOKartica;
	}
	
	public Iznajmljivac kreirajInstancuIznaj() {
		Kartica kartica = new Kartica();
		try {
			//iscitava iz korisnika sve podatke
			FileInputStream fis = new FileInputStream("data/korisnici.csv");
			String procitano = new String(fis.readAllBytes(),"utf-8");
			String [] linije = procitano.split("\n");
			for(int i=0;i<linije.length;i++) {
				String [] pojedinacnaPolja = linije[i].split(",");//ako je korisnik iznajmljivac,iscitamo njegove podatke i na osnovu njih kreiramo instancu iznajmljivaca
				if(pojedinacnaPolja[0].equals("class klase.Iznajmljivac") && pojedinacnaPolja[1].equals(Platforma.korisnickoIme)) {
					kartica.setIdentifikator(pojedinacnaPolja[3]);
					kartica.setIznajmljivac(this);
					kartica.setDatumOd(LocalDate.parse(pojedinacnaPolja[5]));
					kartica.setDatumDo(LocalDate.parse(pojedinacnaPolja[6]));
					kartica.setRaspolozivaSredstva(Integer.parseInt(pojedinacnaPolja[7]));
					Vozilo trenutnoAktivnoVozilo = null;
					if(!(pojedinacnaPolja[8].equals("null"))) {
						String [] trenutnoAktVoz = pojedinacnaPolja[8].split("-");
						for(int j=0;j<trenutnoAktVoz.length;j++) {//ako trenutno aktivno vozilo nije null,onda ga izdvajamo iz liste svih vozila
							ArrayList<Vozilo> svaVozila = Vozilo.svaVozila();
							//moram citati iz vozila da bih dobio sve podatke i kreirao instancu
							for(int k=0;k<svaVozila.size();k++) {
								if(trenutnoAktVoz[j].equals(svaVozila.get(k).getIdentifikator())) {//ako im se poklapaju id-jevi,to vozilo je trenutno aktivno
									trenutnoAktivnoVozilo = (svaVozila.get(k));
								}
							}
						}
						kartica.setTrenutnoAktivnoVozilo(trenutnoAktivnoVozilo);
					}
					else {
						kartica.setTrenutnoAktivnoVozilo(null);
					}
					kartica.setIstorijaIznajmljivanja(null);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IOException e) {
			e.getMessage();
		}
		
		Iznajmljivac iz1 = new Iznajmljivac(Platforma.korisnickoIme,Platforma.lozinka,kartica);//kreiranje instance iznajmljivaca
		return iz1;
	} 
	
	public Iznajmljivac registrovanje() {
		ArrayList<String> listaKorisnickihImena = Korisnik.ucitajDosadasnjeKorisnike();//uzimamo sva korisnicka imena koja se nalaze u fajlu korisnici.csv
		System.out.println("Unesite vase korisnicko ime: ");
		String korisnickoImeIznajmljivaca = sc.nextLine().strip();
		while(listaKorisnickihImena.contains(korisnickoImeIznajmljivaca)) {
			System.out.println("Uneli ste postojece korisnicko ime,unesite vase korisnicko ime: ");//ako tog imena nema u bazi,ponavljamo unos
			korisnickoImeIznajmljivaca = sc.nextLine().strip();
		}
		
		System.out.println("Unesite vasu lozinku: ");
		String lozinkaIznajmljivaca = sc.nextLine().strip();

		System.out.println("Da biste se registrovali kao iznajmljivac neophodno je da vam napravimo NSGO karticu.");
		Kartica kar1 = new Kartica();
		//generisanje random identifikatora
		String identifikator = dodatneKlase.kreiranjeIdentifikatora.generisiID(6);
		kar1.setIdentifikator(identifikator);
		kar1.setIznajmljivac(this);
		//kreiranje i ubacivanje datuma
		kar1.setDatumOd(LocalDate.now());
		kar1.setDatumDo(LocalDate.now().plusYears(1));
		//ubaceni datumi
		kar1.setRaspolozivaSredstva(0);
		kar1.setTrenutnoAktivnoVozilo(null);
		kar1.setIstorijaIznajmljivanja(null);
		System.out.println("Uspesno je kreiran iznajmljivac sa njegovom NSGO karticom.");
		Iznajmljivac iz1 = new Iznajmljivac(korisnickoImeIznajmljivaca,lozinkaIznajmljivaca,kar1);//pogledati sta da radim sa ovim instancama
		//upis u iznajmljivaci.csv
		String zaUpisUIznaj = null;
		try {
			File korisniciCSV = new File("data/korisnici.csv");
			FileWriter fw2 = new FileWriter("data/korisnici.csv",true);
			if(korisniciCSV.length() > 0) {
				zaUpisUIznaj = getClass() + "," + korisnickoImeIznajmljivaca + "," + lozinkaIznajmljivaca + "," + identifikator + "," + korisnickoImeIznajmljivaca + "," + LocalDate.now() + "," + LocalDate.now().plusYears(1) + "," + "0" + "," + null + "," + null + "\n"; 
			}
			else {
				zaUpisUIznaj = getClass() + "," + korisnickoImeIznajmljivaca + "," + lozinkaIznajmljivaca + "," + identifikator + "," + korisnickoImeIznajmljivaca + "," + LocalDate.now() + "," + LocalDate.now().plusYears(1) + "," + "0" + "," + null + "," + null;
			}
			fw2.write(zaUpisUIznaj);
			fw2.flush();
			fw2.close();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(kar1.getIznajmljivac().getKorisnickoIme());
		return iz1;
	}
	
	public void dopunaSredstava() {
		
		int novcanaSredstva = this.getNSGOKartica().getRaspolozivaSredstva();
		System.out.println("Unesite koliko hocete da dopunite sredstava na racun: ");
		String dopuna = sc.next();
		//metoda matches pokriva da je u pitanju broj!
		while(!(dopuna.matches("[0-9]+")) || dopuna.contains("-") || dopuna.equals("0")) {
			System.out.println("Niste uneli odgovarajucu vrednost,unesite koliko hocete da dopunite sredstava na racun: ");
			dopuna = sc.next();
		}
		this.nsgoKartica.setRaspolozivaSredstva(novcanaSredstva + Integer.parseInt(dopuna));
		System.out.println("Trenutno stanje kartice je: " + this.nsgoKartica.getRaspolozivaSredstva());
		String korisnickoIme = this.getKorisnickoIme();
		String noviRedIznaj = getClass() + "," + korisnickoIme + "," + this.getLozinka() + "," + this.nsgoKartica.getIdentifikator() + "," + this.getKorisnickoIme() + "," + this.nsgoKartica.getDatumOd() + "," + this.nsgoKartica.getDatumDo() + "," + this.nsgoKartica.getRaspolozivaSredstva() + "," + this.nsgoKartica.getTrenutnoAktivnoVozilo() + "," + this.nsgoKartica.getIstorijaIznajmljivanja();
		//zapis tog reda u listu procitanih iznajmljivaca
		ArrayList<String> iznajmljivaciNiz = new ArrayList<String>();
		try {
			FileInputStream fis = new FileInputStream("data/korisnici.csv");
			String iznajmljivaci = new String(fis.readAllBytes(),"utf-8");
			String [] linije = iznajmljivaci.split("\n");
			for(int i=0;i<linije.length;i++) {
				String[] poljaUFajlu = linije[i].split(",");
				if(korisnickoIme.equals(poljaUFajlu[1])) {
					iznajmljivaciNiz.add(noviRedIznaj);
				}
				else {
					iznajmljivaciNiz.add(linije[i]);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IOException e) {
			e.getMessage();
		}
//		zapis izmenjenog iznajmljivaca sa azuriranim stanjem u fajl
		try {
			FileWriter fw = new FileWriter("data/korisnici.csv");
			for(int i=0;i<iznajmljivaciNiz.size();i++) {
				fw.write(iznajmljivaciNiz.get(i) + "\n");
				fw.flush();
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Platforma.opcijeIznajmljivaca(this);//poziva se da se program ne bi zavrsio vec da korisnik moze da bira jos neke opcije
	}

	static public ArrayList<DodatnaOprema> dodajDodatnuOpremu() {
		ArrayList<DodatnaOprema> dodatna = new ArrayList<DodatnaOprema>();
		System.out.println("Unesite kolicinu dodatne opreme koju hocete da iznajmite: ");
		String kolicina = sc.next().strip();
		//metoda matches kontrolise da ako se ne unese broj od 1-7 da ponavlja unos
		while(!(kolicina.matches("[1-7]"))) {
			System.out.println("Uneli ste nepostojeci broj,unesite kolicinu dodatne opreme koju hocete da iznajmite:");
			kolicina = sc.next();
		}//for petlja se ponavlja onoliko puta koliko je korisnik uneo da ce kolicinski iznajmiti opreme
		for(int i=0;i<Integer.parseInt(kolicina);i++) {
			System.out.println("Unesite koju " + (i+1) + ". dodatnu opremu hocete da iznajmite uz vozilo:\n1.Kaciga\n2.Reflektujuci prsluk\n3.Svetlo prednje\n4.Svetlo zadnje\n5.Pomocni tockici\n6.Korpa\n7.Decije sediste\nUnesite broj ispred opreme koju birate:");
			String unos = sc.next();
			while(!(unos.matches("[1-7]"))) {
				System.out.println("Uneli ste nepostojeci broj,unesite koju dodatnu opremu hocete da iznajmite uz vozilo:\n1.Kaciga\n2.Reflektujuci prsluk\n3.Svetlo prednje\n4.Svetlo zadnje\n5.Pomocni tockici\n6.Korpa\n7.Decije sediste\nUnesite broj ispred opreme koju birate:");
				unos = sc.next();
			}
			switch(unos) {
				case "1":
					dodatna.add(DodatnaOprema.KACIGA);
					break;
				case "2":
					dodatna.add(DodatnaOprema.REFLEKTUJUCI_PRSLUK);
					break;
				case "3":
					dodatna.add(DodatnaOprema.SVETLO_PREDNJE);
					break;
				case "4":
					dodatna.add(DodatnaOprema.SVETLO_ZADNJE);
					break;
				case "5":
					dodatna.add(DodatnaOprema.POMOCNI_TOCKICI);
					break;
				case "6":
					dodatna.add(DodatnaOprema.KORPA);
					break;
				case "7":
					dodatna.add(DodatnaOprema.DECIJE_SEDISTE);
					break;
			}
		}
		return dodatna;
	}//staticka metoda jer je samo deo koji se ponavlja i nije deo klase
	
	public Najam kreiranjeNajma() {
		Najam n1 = new Najam();
		boolean bezuspesno = false;
		if(this.nsgoKartica.getRaspolozivaSredstva() <= 0) {//ako nema dovoljno sredstava,zavrsava se metoda
			System.out.println("Nemate sredstava,bezuspesno kreiranje najma!");
			bezuspesno = true;
		}
		if(LocalDate.now().isAfter(this.getNSGOKartica().getDatumDo())){//takodje,ako mu je kartica istekla zavrsava se metoda
			System.out.println("Istekla vam je kartica,bezuspesno kreiranje najma!");
			bezuspesno = true;
		}
		if(bezuspesno == false) {
			//vrsi se pretraga vozila i dalje ide izbor i izmena!
			ArrayList<Vozilo> listaSvihVoz = Vozilo.svaVozila();
			ArrayList<Vozilo> listaSlobodnih = Vozilo.pretragaPoZauzetosti();
			ArrayList<String> slobodniIndeksi = new ArrayList<String>();
			//izvlacenje mesta tako sto imamo listu svih vozila i ako se id od svih vozila poklapa sa id u ovim slobodnim njegov (i+1) se dodaje u listu slobodnih indeksa i nakon toga se radi ispis koje sve izbore ima korisnik
			for(int i=0;i<listaSvihVoz.size();i++) {
				for(int j=0;j<listaSlobodnih.size();j++) {
					if(listaSvihVoz.get(i).getIdentifikator().strip().equals(listaSlobodnih.get(j).getIdentifikator().strip())){
						System.out.println((i+1) + "-" + listaSvihVoz.get(i).getClass().getName() +  "," + listaSvihVoz.get(i).getIdentifikator() + "," + listaSvihVoz.get(i).getVlasnik().getKorisnickoIme() + "," + listaSvihVoz.get(i).getCenaPoSatu() + "," + listaSvihVoz.get(i).getVelicinaTockova() + "," + listaSvihVoz.get(i).getNosivost() + "," + listaSvihVoz.get(i).getStanje() + "," + listaSvihVoz.get(i).isZauzeto());
						slobodniIndeksi.add("" + (i+1));
					}
					else {
						continue;
					}
				}
			}
			System.out.println("Unesite broj ispred vozila koje zelite da unajmite: ");
			String uneseniIndeks = sc.next();
			while(!(slobodniIndeksi.contains(uneseniIndeks))) {
				System.out.println("Niste uneli postojecu opciju,unesite broj ispred vozila koje zelite da unajmite: ");
				uneseniIndeks = sc.next();
			}
			Vozilo izabranoVozilo = null;
			for(int i=0;i<listaSvihVoz.size();i++) {
				if((Integer.parseInt(uneseniIndeks)-1)==i) {
					izabranoVozilo = listaSvihVoz.get(i);//ovde se na izabrano vozilo dodeljuje ono vozilo koje je korisnik izabrao
				}
			}
			//vozilo se aktivira na kartici tako sto se na aktivno vozilo smesta izabrano
			this.getNSGOKartica().setTrenutnoAktivnoVozilo(izabranoVozilo);
			//zapis aktivnog vozila u vozila.csv fajlu(tj. samo njegov id),sada prvo pravimo arraylistu sa promenjenim podacima
			ArrayList<String> listaPromenjenihVoz = new ArrayList<String>();
			String zaUpis = "";
			try {
				FileInputStream fis = new FileInputStream("data/korisnici.csv");
				String procitano = new String(fis.readAllBytes(),"utf-8");
				String [] linije = procitano.split("\n");
				for(int i=0;i<linije.length;i++) {
					String [] pojedinacnaPolja = linije[i].split(",");
					if(pojedinacnaPolja[0].strip().equals("class klase.Iznajmljivac")&& pojedinacnaPolja[1].strip().equals(this.getKorisnickoIme())) {
						zaUpis = pojedinacnaPolja[0] + "," + pojedinacnaPolja[1] + "," + pojedinacnaPolja[2] + "," + pojedinacnaPolja[3] + "," + pojedinacnaPolja[4]+ "," + pojedinacnaPolja[5] + "," + pojedinacnaPolja[6] + "," + pojedinacnaPolja[7] + "," + this.getNSGOKartica().getTrenutnoAktivnoVozilo().getIdentifikator() + "," + pojedinacnaPolja[9];
					}
					else {
						zaUpis = linije[i];
					}
					listaPromenjenihVoz.add(zaUpis);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(IOException e) {
				e.getMessage();
			}
			//zapis nove liste u fajl korisnici.csv
			try {
				FileWriter fw = new FileWriter("data/korisnici.csv");
				for(int i=0;i<listaPromenjenihVoz.size();i++) {
					fw.write(listaPromenjenihVoz.get(i) + "\n");
				}
				fw.flush();
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//kreiranje novog najma,zapisivanje takvog najma u fajl najmovi.csv i vracanje te instance kao povratne vrednosti metode
			n1.setIdentifikator(dodatneKlase.kreiranjeIdentifikatora.generisiID(6));
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			String vremeSad = LocalDateTime.now().format(format);
			n1.setPocetak(LocalDateTime.parse(vremeSad, format));
			n1.setKraj(null);
			n1.setIznajmljivac(this);
			n1.setIznajmljenoVozilo(this.getNSGOKartica().getTrenutnoAktivnoVozilo());
			//ovde ide metoda za dodatnu opremu
			n1.setDodatnaOprema(dodajDodatnuOpremu());
			n1.setUradjenServis(false);
			String dodatnaOprema = "";
			for(int i=0;i<n1.getDodatnaOprema().size();i++) {
				dodatnaOprema += n1.getDodatnaOprema().get(i) + "-";
			}
			//zapis ovakvog najma u fajl najmovi.csv!
			try {
				File najamCSV = new File("data/najmovi.csv");
				FileWriter fwNajma = new FileWriter("data/najmovi.csv",true);
				if(najamCSV.length()>0) {
					zaUpis = n1.getIdentifikator() + "," + n1.getPocetak().format(format) + "," + n1.getKraj() + "," + n1.getIznajmljivac().getKorisnickoIme() + "," + n1.getIznajmljenoVozilo().getIdentifikator() + "," + dodatnaOprema + "," + n1.isUradjenServis() + "\n";
				}else {
					zaUpis = n1.getIdentifikator() + "," + n1.getPocetak().format(format) + "," + n1.getKraj() + "," + n1.getIznajmljivac().getKorisnickoIme() + "," + n1.getIznajmljenoVozilo().getIdentifikator() + "," + dodatnaOprema + "," + n1.isUradjenServis();
				}
				fwNajma.write(zaUpis);
				fwNajma.flush();
				fwNajma.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//poziv metode unajmi iz vozila
			if(izabranoVozilo.getClass().getName().strip().equals("klase.Bicikl")) {
				Bicikl b1 = new Bicikl();//ako je u pitanju bicikl,poziva se metoda unajmi od klase Bicikl
				b1.unajmi(izabranoVozilo);
			}
			else if(izabranoVozilo.getClass().getName().strip().equals("klase.Trotinet")) {
				Trotinet t1 = new Trotinet();//ako je u pitanju trotinet,poziva se metoda unajmi od klase Trotinet
				t1.unajmi(izabranoVozilo);
			}
		}
		Platforma.opcijeIznajmljivaca(this);//poziva se da se program ne bi zavrsio vec da korisnik moze da bira jos neke opcije
		return n1;
	}

	public Najam vracanjeVozila() {
		Najam n1 = new Najam();
		//pretraga svih najmova koje iznajmljivac nije vratio
		//proci kroz sva vozila koja nije vratio kroz fajl 
		ArrayList<Vozilo> nevracena = Vozilo.nevracenaVozila(this);//dobija sva nevracena vozila od iznajmljivaca
		if(nevracena.size()<= 0) {
			System.out.println("Ulogovani iznajmljivac nema vozila da vrati,morate prvo da iznajmite vozilo da bi mogli da ga vratite.");
			Platforma.opcijeIznajmljivaca(this);//ako nema vozila koje moze da vrati,ponuditi korisniku opcije opet
		}
		else {
			ArrayList<String> indeksiNevracenih = new ArrayList<String>();
			//ponuda korisniku da bira koje vozilo zeli da vrati!
			for(int i=0;i<nevracena.size();i++) {
				String zaIspis = "";
				if(nevracena.get(i).getClass().getName().strip().equals("klase.Bicikl")) {
					Bicikl b1 = (Bicikl) nevracena.get(i);
					zaIspis = (i+1) + " - " + b1.getClass().getName() + "," + b1.getIdentifikator() + "," + b1.getVlasnik().getKorisnickoIme() + "," + b1.getCenaPoSatu() + "," + b1.getVelicinaTockova() + "," + b1.getNosivost() + "," + b1.getStanje() + "," + b1.isZauzeto() + "," + b1.getBrojBrzina() + "," + b1.getVisina();
					indeksiNevracenih.add("" + (i+1));
					System.out.println(zaIspis);
				}
				else if(nevracena.get(i).getClass().getName().strip().equals("klase.Trotinet")) {
					Trotinet t1 = (Trotinet) nevracena.get(i);
					zaIspis = (i+1) + " - " + t1.getClass().getName() + "," + t1.getIdentifikator() + "," + t1.getVlasnik().getKorisnickoIme() + "," + t1.getCenaPoSatu() + "," + t1.getVelicinaTockova() + "," + t1.getNosivost() + "," + t1.getStanje() + "," + t1.isZauzeto() + "," + t1.getMaksimalnaBrzina() + "," + t1.getTrajanjeBaterije();
					indeksiNevracenih.add("" + (i+1));
					System.out.println(zaIspis);
				}
			}
			System.out.println("Unesite broj ispred vozila koje zelite da vratite: ");
			String uneseniIndeks = sc.next().strip();
			while(!(indeksiNevracenih.contains(uneseniIndeks))) {
				System.out.println("Niste uneli odgovarajuci broj,unesite broj ispred vozila koje zelite da vratite: ");
				uneseniIndeks = sc.next().strip();//ako indeks ne postoji u listi indeksa ponavlja se unos
			}
			Vozilo v1 = nevracena.get(Integer.parseInt(uneseniIndeks)-1);
			//vadjenje pocetka najma iz metode svi najmovi,ako se poklapa id vozila sa id vozila u najmu to vreme se uzima
			ArrayList<Najam> sviNajmovi = Najam.sviNajmovi(this);
			ArrayList<Najam> generalnoSviNajmovi = Najam.sviNajmovi();//generalno svi najmovi iz fajla najmovi.csv
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			String datumPocetka = "";
			for(int i=0;i<sviNajmovi.size();i++) {
				if(sviNajmovi.get(i).getIznajmljenoVozilo().getIdentifikator().strip().equals(v1.getIdentifikator())) {
					datumPocetka = sviNajmovi.get(i).getPocetak().format(format);
				}
			}
			LocalDateTime parsiraniPocetak = LocalDateTime.parse(datumPocetka,format);
			//izracunavanje cene najma na osnovu vremena kad je vraceno vozilo,racuna se duzina najma puta cenaPoSatu
			String krajNajma = LocalDateTime.now().format(format);
			LocalDateTime parsiraniKraj = LocalDateTime.parse(krajNajma,format);
			Duration trajanje = Duration.between(parsiraniPocetak, parsiraniKraj);
			int trajanjeSati = trajanje.toHoursPart();
			int ukupnaCena = trajanjeSati * v1.getCenaPoSatu();
			System.out.println("Ukupna cena za iznajmljivanje vozila je: " + ukupnaCena);
			//umanjenje raspolozivih sredstava iznajmljivaca za dati iznos najma,i ukoliko je iznos negativan ispisati trenutno stanje i da je potrebna dopuna sredstava,inace nova iznajmljivanja nece biti moguca
			this.nsgoKartica.setRaspolozivaSredstva(this.nsgoKartica.getRaspolozivaSredstva() - ukupnaCena);
			if(this.nsgoKartica.getRaspolozivaSredstva() < 0) {
				System.out.println("Kartica je zaduzena,potrebna je dopuna sredstava,inace dalja iznajmljivanja nece biti moguca.");
			}
			System.out.println("Trenutno stanje kartice je: " + this.nsgoKartica.getRaspolozivaSredstva());
			//trenutno vozilo iznajmljivaca je sad null jer je vozilo vraceno
			this.getNSGOKartica().setTrenutnoAktivnoVozilo(null);
			//lista koja cita sve iz korisnika i menja trenutno aktivno vozilo iznajmljvaca na null
			ArrayList<String> korisniciDoSad = new ArrayList<String>();
			try {
				FileInputStream fisKorisnici = new FileInputStream("data/korisnici.csv");
				String procitano = new String(fisKorisnici.readAllBytes(),"utf-8");
				String [] linije = procitano.split("\n");
				for(int i=0;i<linije.length;i++) {
					String [] pojedinacnaPolja = linije[i].split(",");
					String zaUpis = "";
					if(pojedinacnaPolja[0].strip().equals("class klase.Iznajmljivac") && pojedinacnaPolja[1].strip().equals(this.getKorisnickoIme())) {
						zaUpis = pojedinacnaPolja[0] + "," + pojedinacnaPolja[1] + "," + pojedinacnaPolja[2] + "," + pojedinacnaPolja[3] + "," + pojedinacnaPolja[4]+ "," + pojedinacnaPolja[5] + "," + pojedinacnaPolja[6] + "," + this.getNSGOKartica().getRaspolozivaSredstva() + "," + "null," + pojedinacnaPolja[9];
					}
					else {
						zaUpis = linije[i];
					}
					korisniciDoSad.add(zaUpis);
				}			
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(IOException e) {
				e.getMessage();
			}
			//upis u fajl korisnici.csv
			try {
				FileWriter fw = new FileWriter("data/korisnici.csv");
				for(int i=0;i<korisniciDoSad.size();i++) {
					fw.write(korisniciDoSad.get(i) + "\n");
					fw.flush();
				}
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//kad naidje na najam u svim najmovima kojem je korisnik upisao vreme vracanja vozila,stavlja mu vrednost kraja,tj. vreme kad je vozilo vraceno
			for(int i=0;i<generalnoSviNajmovi.size();i++) {
				if(generalnoSviNajmovi.get(i).getIznajmljenoVozilo().getIdentifikator().strip().equals(v1.getIdentifikator())) {
					generalnoSviNajmovi.get(i).setKraj(parsiraniKraj);
					n1 = generalnoSviNajmovi.get(i);
				}
			}
			//zapis najma sa vremenom kraja u najmovi.csv
			try {
				FileWriter fwNajma = new FileWriter("data/najmovi.csv");
				for(int i=0;i<generalnoSviNajmovi.size();i++) {
					Najam trenutniNajam = generalnoSviNajmovi.get(i);
					//pravljenje odgovarajuceg upisa u fajl
					String dodatnaOprema = "";
					for(int j=0;j<trenutniNajam.getDodatnaOprema().size();j++) {
						dodatnaOprema += trenutniNajam.getDodatnaOprema().get(j) + "-"; 
					}
					String zaUpis = trenutniNajam.getIdentifikator() + "," + trenutniNajam.getPocetak().format(format) + "," + trenutniNajam.getKraj().format(format) + "," + trenutniNajam.getIznajmljivac().getKorisnickoIme() + "," + trenutniNajam.getIznajmljenoVozilo().getIdentifikator() + "," + dodatnaOprema + "," + trenutniNajam.isUradjenServis() + "\n";
					fwNajma.write(zaUpis);
				}
				fwNajma.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//poziv metode vrati iz vozila
			if(v1.getClass().getName().strip().equals("klase.Bicikl")) {
				Bicikl b1 = new Bicikl();
				b1.vrati(v1);
			}
			else if(v1.getClass().getName().strip().equals("klase.Trotinet")) {
				Trotinet t1 = new Trotinet();
				t1.vrati(v1);
			}
		}
		Platforma.opcijeIznajmljivaca(this);//poziva se da se program ne bi zavrsio vec da korisnik moze da bira jos neke opcije
		return n1;
	}	
	
	public ArrayList<Najam> prikazIstorijeNajma() {
		ArrayList<Najam> listaNajmova = new ArrayList<Najam>();
		if(Najam.sviNajmovi(this).size()<=0) {//ako iznajmljivac nije kreirao nikakve najmove,onda se iznajmljivacu nude opet sve opcije
			System.out.println("Korisnik nema u istoriji nikakve najmove,pokusajte opet.");
			Platforma.opcijeIznajmljivaca(this);
		}else {
			Najam n1 = null;
			try {
				Scanner sc = new Scanner(System.in);
				DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
				boolean uspesanUnosDatuma = false;
				String unosOd = "";
				String unosDo = "";
				while (uspesanUnosDatuma == false) {//ova petlja proverava da li korisnik uspeo da unese datum u trazenom formatu ili je uneo -1 i 1
					System.out.println("Unesite od kog datuma i vremena zelite da se prikaze istorija najma: u formatu-(yyyy-MM-dd HH:mm)");
					unosOd = sc.nextLine().strip();
					System.out.println("Unesite do kog datuma i vremena zelite da se prikaze istorija najma: u formatu-(yyyy-MM-dd HH:mm)");
					unosDo = sc.nextLine().strip();
			        
			        if (unosOd.equals("-1") && unosDo.equals("1")) {//ako je korisnik uneo -1 i 1 to je validan unos i pusta ga da izadje iz petlje
			            uspesanUnosDatuma = true;
			        }

			        try {
			        	LocalDateTime unosDoKonv = LocalDateTime.parse(unosDo,format);//ovo ovde sluzi cisto da ako ne dodje do erora prilikom parsiranja,da se moze koristiti uneseni datum i da se parsira kad bude trebalo
						LocalDateTime unosOdKonv = LocalDateTime.parse(unosOd,format);
			            uspesanUnosDatuma = true;
			        } catch (DateTimeParseException e) {
			        	if(uspesanUnosDatuma == false) {
				            System.out.println("Uneli ste pogrešan format datuma.Pokušajte ponovo.");
			        	}
			        }
			    }
				if(unosOd.equals("-1") && unosDo.equals("1")) {
					//ovde idu svi najmovi
					FileInputStream fis = new FileInputStream("data/najmovi.csv");
					String procitano = new String(fis.readAllBytes(),"utf-8");
					String [] linije = procitano.split("\n");
					for(int i=0;i<linije.length;i++) {
						String [] pojedinacnaPolja = linije[i].split(",");
						if(pojedinacnaPolja[2].strip().equals("null") && pojedinacnaPolja[3].strip().equals(this.getKorisnickoIme())) {
							int cenaPoSatu = 0;//ako je vreme vracanja null,radi se ispis sa / za vreme trajanja najma i za ukupnu cenu najma
							ArrayList<Vozilo> svaVozila = Vozilo.svaVozila();
							for(int j=0;j<svaVozila.size();j++) {
								String identifikatorVozila = svaVozila.get(j).getIdentifikator().strip();
								if(identifikatorVozila.equals(pojedinacnaPolja[4].strip())) {
									cenaPoSatu = svaVozila.get(j).getCenaPoSatu();
									String redIspisa = pojedinacnaPolja[0] + "," + pojedinacnaPolja[1] + "," + pojedinacnaPolja[2] + "," + "/" + "," + cenaPoSatu + "," + "/";
									System.out.println(redIspisa);
								}
								else {
									continue;
								}
							}
						}
						else if(pojedinacnaPolja[3].strip().equals(this.getKorisnickoIme())){
							Duration trajanje = Duration.between(LocalDateTime.parse(pojedinacnaPolja[1],format), LocalDateTime.parse(pojedinacnaPolja[2],format));
							int trajanjeSati = trajanje.toHoursPart();
							int cenaPoSatu,ukupnaCenaNajma = 0;
							ArrayList<Vozilo> svaVozila = Vozilo.svaVozila();
							for(int j=0;j<svaVozila.size();j++) {
								String identifikatorVozila = svaVozila.get(j).getIdentifikator().strip();
								if(identifikatorVozila.equals(pojedinacnaPolja[4].strip())) {
									cenaPoSatu = svaVozila.get(j).getCenaPoSatu();
									ukupnaCenaNajma = trajanjeSati * cenaPoSatu;//ispis ukupne cene najma i trajanja koji su prethodno izracunati
									String redIspisa = pojedinacnaPolja[0] + "," + pojedinacnaPolja[1] + "," + pojedinacnaPolja[2] + "," + trajanjeSati + "," + cenaPoSatu + "," + ukupnaCenaNajma;
									System.out.println(redIspisa);
								}
								else {
									continue;
								}
							}
							//ovde ide dodavanje instance najma u listu koja se kasnije dodaje u istoriju najma
							n1 = new Najam();
							n1.setIdentifikator(pojedinacnaPolja[0]);
							n1.setPocetak(LocalDateTime.parse(pojedinacnaPolja[1],format));
							n1.setKraj(LocalDateTime.parse(pojedinacnaPolja[2],format));
							n1.setIznajmljivac(Najam.konvertujUIznajmljivaca(pojedinacnaPolja[3]));
							//ovde na osnovu id proci kroz sva vozila,i ako se tekst pojedinacnaPolja poklapa tu smestiti to vozilo!
							Vozilo iznajmljenoVozilo = null;
							for(int k=0;k<svaVozila.size();k++) {
								if(svaVozila.get(k).getIdentifikator().equals(pojedinacnaPolja[4].strip())) {
									iznajmljenoVozilo = svaVozila.get(k);
								}
							}
							n1.setIznajmljenoVozilo(iznajmljenoVozilo);
							//kreiranje liste dodatne opreme
							ArrayList<DodatnaOprema> dodatna = Najam.citajDodatnuOpremu(pojedinacnaPolja[5].strip());
							n1.setDodatnaOprema(dodatna);
							n1.setUradjenServis(Boolean.parseBoolean(pojedinacnaPolja[6].strip()));
							listaNajmova.add(n1);
						}
					}
					
				}
				else {//ako korisnik unese datum od do
					LocalDateTime unosDoKonv = LocalDateTime.parse(unosDo,format);
					LocalDateTime unosOdKonv = LocalDateTime.parse(unosOd,format);
					//petlja koja lista sve najmove u tom periodu
					FileInputStream fis = new FileInputStream("data/najmovi.csv");
					String procitano = new String(fis.readAllBytes(),"utf-8");
					String [] linije = procitano.split("\n");
					for(int i=0;i<linije.length;i++) {
						String [] pojedinacnaPolja = linije[i].split(",");
						//ovde se ne ispisuju oni sa crticom(koji nisu vraceni),jer ne mogu da ispunjavaju uslov od,do jer ne mogu biti vraceni do nekog vremena kad je vreme vracanja null
						if(!(pojedinacnaPolja[2].strip().equals("null")) && pojedinacnaPolja[3].strip().equals(this.getKorisnickoIme())) {
							if(unosOdKonv.isBefore(LocalDateTime.parse(pojedinacnaPolja[1],format)) && unosDoKonv.isAfter(LocalDateTime.parse(pojedinacnaPolja[2],format))) {
								Duration trajanje = Duration.between(LocalDateTime.parse(pojedinacnaPolja[1],format), LocalDateTime.parse(pojedinacnaPolja[2],format));
								int trajanjeSati = trajanje.toHoursPart();
								int cenaPoSatu = 0;
								ArrayList<Vozilo> svaVozila = Vozilo.svaVozila();
								for(int j=0;j<svaVozila.size();j++) {
									if(svaVozila.get(j).getIdentifikator().equals(pojedinacnaPolja[4])) {
										cenaPoSatu = svaVozila.get(j).getCenaPoSatu();
									}
								}
								int ukupnaCenaNajma = trajanjeSati * cenaPoSatu;
								String redIspisa = pojedinacnaPolja[0] + "," + pojedinacnaPolja[1] + "," + pojedinacnaPolja[2] + "," + trajanjeSati + "," + cenaPoSatu + "," + ukupnaCenaNajma;
								System.out.println(redIspisa);
								//kreiranje instance najma i dodavanje u listu pretrazenih najmova
								n1 = new Najam();
								n1.setIdentifikator(pojedinacnaPolja[0]);
								n1.setPocetak(LocalDateTime.parse(pojedinacnaPolja[1],format));
								n1.setKraj(LocalDateTime.parse(pojedinacnaPolja[2],format));
								n1.setIznajmljivac(Najam.konvertujUIznajmljivaca(pojedinacnaPolja[3]));
								//ovde na osnovu id proci kroz sva vozila,i ako se tekst pojedinacnaPolja poklapa tu smestiti to vozilo!
								Vozilo iznajmljenoVozilo = null;
								for(int k=0;k<svaVozila.size();k++) {
									if(svaVozila.get(k).getIdentifikator().equals(pojedinacnaPolja[4].strip())) {
										iznajmljenoVozilo = svaVozila.get(k);
									}
								}
								n1.setIznajmljenoVozilo(iznajmljenoVozilo);
								//kreiranje liste dodatne opreme
								ArrayList<DodatnaOprema> dodatna = Najam.citajDodatnuOpremu(pojedinacnaPolja[5].strip());
								n1.setDodatnaOprema(dodatna);
								n1.setUradjenServis(Boolean.parseBoolean(pojedinacnaPolja[6].strip()));
								listaNajmova.add(n1);
							}
						}
						else {//ako je taj bez vremena vracanja da se preskoci!
							continue;
						}
					}
				} 
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
			if(listaNajmova.size() <=0) {//ako nije pronadjen ni jedan najam sa datim parametrima ispisuje se poruka
				System.out.println("Nema istorije najma za uneseni period,probajte opet.");
			}
			else{
				this.getNSGOKartica().setIstorijaIznajmljivanja(listaNajmova);
				//zapis listaNajmova u korisnici.csv fajl!
				//kreira se lista stringova u kojoj su svi elementi iz liste najmova,samo odvojeni -,da kasnije,u prijavi,jer se splituje po , ne bi doslo do problema
				String istorijaNajmova = "";
				for(int i=0;i<listaNajmova.size();i++) {
					if(i==0) {
						istorijaNajmova += "[" + listaNajmova.get(i) + "-";
					}
					else if(i==(listaNajmova.size()-1)) {
						istorijaNajmova += listaNajmova.get(i) + "]";
					}
					else{
						istorijaNajmova += listaNajmova.get(i) + "-";
					}
				}
				//citanje dosadasnje liste i dodavanje u toj listi najmova za iznajmljivaca
				ArrayList<String> korisnici = new ArrayList<String>();
				try {
					FileInputStream fisKorisnici = new FileInputStream("data/korisnici.csv");
					String procitano = new String(fisKorisnici.readAllBytes(),"utf-8");
					String [] linije = procitano.split("\n");
					for(int i=0;i<linije.length;i++) {
						String [] pojedinacnaPolja = linije[i].split(",");
						String zaUpis = "";
						if(pojedinacnaPolja[1].strip().equals(this.getKorisnickoIme())){
							zaUpis = pojedinacnaPolja[0] + "," + pojedinacnaPolja[1] + "," + pojedinacnaPolja[2] + "," + pojedinacnaPolja[3] + "," + pojedinacnaPolja[4] + "," + pojedinacnaPolja[5] + "," + pojedinacnaPolja[6] + "," + pojedinacnaPolja[7] + "," + pojedinacnaPolja[8] + "," + istorijaNajmova;
						}
						else {
							zaUpis = linije[i];
						}
						korisnici.add(zaUpis);
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch(IOException e) {
					e.getMessage();
				}
				//zapis nove liste u fajl
				try {
					FileWriter fw = new FileWriter("data/korisnici.csv");
					for(int i=0;i<korisnici.size();i++) {
						fw.write(korisnici.get(i) + "\n");
						fw.flush();
					}
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		Platforma.opcijeIznajmljivaca(this);//poziva se da se program ne bi zavrsio vec da korisnik moze da bira jos neke opcije
		return listaNajmova;
	}
}
