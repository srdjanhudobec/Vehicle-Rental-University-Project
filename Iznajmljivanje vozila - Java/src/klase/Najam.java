package klase;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import enumi.DodatnaOprema;
import enumi.Stanje;
public class Najam {

	private String identifikator;
	private LocalDateTime pocetak;
	private LocalDateTime kraj;
	private Iznajmljivac iznajmljivac;
	private Vozilo iznajmljenoVozilo;
	private ArrayList<DodatnaOprema> dodatnaOprema;
	private boolean uradjenServis;
	
	public Najam() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Najam(String identifikator, LocalDateTime pocetak, LocalDateTime kraj, Iznajmljivac iznajmljivac,
			Vozilo iznajmljenoVozilo, ArrayList<DodatnaOprema> dodatnaOprema, boolean uradjenServis) {
		super();
		this.identifikator = identifikator;
		this.pocetak = pocetak;
		this.kraj = kraj;
		this.iznajmljivac = iznajmljivac;
		this.iznajmljenoVozilo = iznajmljenoVozilo;
		this.dodatnaOprema = dodatnaOprema;
		this.uradjenServis = uradjenServis;
	}

	public String getIdentifikator() {
		return identifikator;
	}

	public void setIdentifikator(String identifikator) {
		this.identifikator = identifikator;
	}

	public LocalDateTime getPocetak() {
		return pocetak;
	}

	public void setPocetak(LocalDateTime pocetak) {
		this.pocetak = pocetak;
	}

	public LocalDateTime getKraj() {
		return kraj;
	}

	public void setKraj(LocalDateTime kraj) {
		this.kraj = kraj;
	}

	public Iznajmljivac getIznajmljivac() {
		return iznajmljivac;
	}

	public void setIznajmljivac(Iznajmljivac iznajmljivac) {
		this.iznajmljivac = iznajmljivac;
	}

	public Vozilo getIznajmljenoVozilo() {
		return iznajmljenoVozilo;
	}

	public void setIznajmljenoVozilo(Vozilo iznajmljenoVozilo) {
		this.iznajmljenoVozilo = iznajmljenoVozilo;
	}

	public ArrayList<DodatnaOprema> getDodatnaOprema() {
		return dodatnaOprema;
	}

	public void setDodatnaOprema(ArrayList<DodatnaOprema> dodatnaOprema) {
		this.dodatnaOprema = dodatnaOprema;
	}

	public boolean isUradjenServis() {
		return uradjenServis;
	}

	public void setUradjenServis(boolean uradjenServis) {
		this.uradjenServis = uradjenServis;
	}
	
	public String toString() {
		return this.getIdentifikator(); 
	}
	
	static public ArrayList<DodatnaOprema> citajDodatnuOpremu(String pojedinacnaPolja){
		ArrayList<DodatnaOprema> dodatna = new ArrayList<DodatnaOprema>();//ovo za length ispod red je tako zato sto se dodaje crta na kraju i onda ako posle te crte ne ide novo vozilo,taj zadnji podatak nam ne treba
		if(pojedinacnaPolja.split("-").length > 1) {//ako ima vise dodatne opreme uzima se duzina -1 da se ne bi uzeo zadnji el
			for(int j=0;j<pojedinacnaPolja.split("-").length - 1;j++) {
				String [] dodatneOpreme = pojedinacnaPolja.split("-");
				switch(dodatneOpreme[j].strip()) {
					case "KACIGA":
						dodatna.add(DodatnaOprema.KACIGA);
						break;
					case "REFLEKTUJUCI_PRSLUK":
						dodatna.add(DodatnaOprema.REFLEKTUJUCI_PRSLUK);
						break;
					case "SVETLO_PREDNJE":
						dodatna.add(DodatnaOprema.SVETLO_PREDNJE);
						break;
					case "SVETLO_ZADNJE":
						dodatna.add(DodatnaOprema.SVETLO_ZADNJE);
						break;
					case "POMOCNI_TOCKICI":
						dodatna.add(DodatnaOprema.POMOCNI_TOCKICI);
						break;
					case "KORPA":
						dodatna.add(DodatnaOprema.KORPA);
						break;
					case "DECIJE_SEDISTE":
						dodatna.add(DodatnaOprema.DECIJE_SEDISTE);
						break;
				}
			}
		}else {//ako ima samo jedna dodatna oprema onda se uzima element na nultom mestu
			for(int j=0;j<pojedinacnaPolja.split("-").length;j++) {
				String [] dodatneOpreme = pojedinacnaPolja.split("-");
				switch(dodatneOpreme[j].strip()) {
					case "KACIGA":
						dodatna.add(DodatnaOprema.KACIGA);
						break;
					case "REFLEKTUJUCI_PRSLUK":
						dodatna.add(DodatnaOprema.REFLEKTUJUCI_PRSLUK);
						break;
					case "SVETLO_PREDNJE":
						dodatna.add(DodatnaOprema.SVETLO_PREDNJE);
						break;
					case "SVETLO_ZADNJE":
						dodatna.add(DodatnaOprema.SVETLO_ZADNJE);
						break;
					case "POMOCNI_TOCKICI":
						dodatna.add(DodatnaOprema.POMOCNI_TOCKICI);
						break;
					case "KORPA":
						dodatna.add(DodatnaOprema.KORPA);
						break;
					case "DECIJE_SEDISTE":
						dodatna.add(DodatnaOprema.DECIJE_SEDISTE);
						break;
				}
			}
		}
		return dodatna;
	}
	
	static public Iznajmljivac konvertujUIznajmljivaca(String korisnickoIme) {
		Iznajmljivac iz1 = new Iznajmljivac();//uzima korisnicko ime iznajmljivaca
		Kartica k1 = new Kartica();
		try {
			FileInputStream fis = new FileInputStream("data/korisnici.csv");
			String procitano = new String(fis.readAllBytes(),"utf-8");
			String [] linije = procitano.split("\n");
			for(int i=0;i<linije.length;i++) {
				String [] pojedinacnaPolja = linije[i].split(",");//ako je procitani iznajmljivac i poklapa se korisnicko ime
				if(pojedinacnaPolja[0].strip().equals("class klase.Iznajmljivac") && pojedinacnaPolja[1].strip().equals(korisnickoIme)) {
					//kreiranje instance Iznajmljivaca na osnovu procitanih podataka
					iz1.setKorisnickoIme(korisnickoIme);
					iz1.setLozinka(pojedinacnaPolja[2]);
					k1.setIdentifikator(pojedinacnaPolja[3]);
					k1.setIznajmljivac(null);
					DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					k1.setDatumOd(LocalDate.parse(pojedinacnaPolja[5],format));
					k1.setDatumDo(LocalDate.parse(pojedinacnaPolja[6],format));
					k1.setRaspolozivaSredstva(Integer.parseInt(pojedinacnaPolja[7]));
					if(pojedinacnaPolja[8].strip().equals("null")) {
						k1.setTrenutnoAktivnoVozilo(null);
					}else {
						ArrayList<Vozilo> svaVozila = Vozilo.svaVozila();
						for(int j=0;j<svaVozila.size();j++) {
							if(svaVozila.get(j).getIdentifikator().equals(pojedinacnaPolja[8].strip())){
								k1.setTrenutnoAktivnoVozilo(svaVozila.get(j));
							}
						}
					}
					if(pojedinacnaPolja[9].strip().equals("null")) {
						k1.setIstorijaIznajmljivanja(null);
					}else {
						ArrayList<Najam> istorijaNajmaKartice = new ArrayList<Najam>();//ovde ce ici najmovi koji se nalaze u istoriji najmova!
						ArrayList<Najam> sviNajmovi = new ArrayList<Najam>();
						//vadjenje svih identifikatora procitanih iz najma
						ArrayList<String> identifikatoriProcitani = new ArrayList<String>();
						String [] id = pojedinacnaPolja[9].strip().split("-");
						for(int j=0;j<pojedinacnaPolja[9].strip().split("-").length;j++) {
							identifikatoriProcitani.add(id[j]);
						}
						//prolazak kroz karticu,ako se tamo nalazi id najma koji se poklapa sa listom svih identifikatora u najmovima taj podatak se dodaje
						for(int j=0;j<sviNajmovi.size();j++) {
							for(int k=0;k<identifikatoriProcitani.size();k++) {
								if(sviNajmovi.get(j).getIdentifikator().equals(identifikatoriProcitani.get(k))) {
									istorijaNajmaKartice.add(sviNajmovi.get(j));
								}
							}
						}
						k1.setIstorijaIznajmljivanja(istorijaNajmaKartice);
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IOException e) {
			e.getMessage();
		}
		iz1.setNSGOKartica(k1);
		iz1.getNSGOKartica().setIznajmljivac(iz1);
		return iz1;
	}
	
	static public ArrayList<Najam> sviNajmovi(){//ovde se pretrazuju svi najmovi bez obzira na njegovog iznajmljivaca
		ArrayList<Najam> sviNajmovi = new ArrayList<Najam>();
		Najam n1 = null;
		try {
			FileInputStream fis = new FileInputStream("data/najmovi.csv");
			String procitano = new String(fis.readAllBytes(),"utf-8");
			String [] linije = procitano.split("\n");
			for(int i=0;i<linije.length;i++) {
				String [] pojedinacnaPolja = linije[i].split(",");
				//na osnovu procitanih podataka se kreira instanca najma
				n1 = new Najam();
				n1.setIdentifikator(pojedinacnaPolja[0]);
				DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
				n1.setPocetak(LocalDateTime.parse(pojedinacnaPolja[1],format));
				if(pojedinacnaPolja[2].strip().equals("null")) {
					n1.setKraj(null);
				}else {
					n1.setKraj(LocalDateTime.parse(pojedinacnaPolja[2],format));
				}
				//metoda iznad koriscena upravo ovde za konverziju u iznajmljivaca
				n1.setIznajmljivac(konvertujUIznajmljivaca(pojedinacnaPolja[3]));
				//pretraga vozila na osnovu sifre da bi se smestilo ovde!
				ArrayList<Vozilo> svaVozila = Vozilo.svaVozila();
				Vozilo iznajmljeno = null;
				for(int k=0;k<svaVozila.size();k++) {
					if(svaVozila.get(k).getIdentifikator().strip().equals(pojedinacnaPolja[4].strip())) {
						iznajmljeno = svaVozila.get(k);
					}
				}
				n1.setIznajmljenoVozilo(iznajmljeno);
				//kreiranje liste dodatne opreme
				ArrayList<DodatnaOprema> dodatna = new ArrayList<DodatnaOprema>();//ovo za length ispod red je tako zato sto se dodaje crta na kraju i onda ako posle te crte ne ide novo vozilo,taj zadnji podatak nam ne treba
				if(pojedinacnaPolja[5].split("-").length > 1) {//ako ima vise dodatne opreme uzima se duzina -1 da se ne bi uzeo zadnji el
					for(int j=0;j<pojedinacnaPolja[5].split("-").length - 1;j++) {
						String [] dodatneOpreme = pojedinacnaPolja[5].split("-");
						switch(dodatneOpreme[j].strip()) {
							case "KACIGA":
								dodatna.add(DodatnaOprema.KACIGA);
								break;
							case "REFLEKTUJUCI_PRSLUK":
								dodatna.add(DodatnaOprema.REFLEKTUJUCI_PRSLUK);
								break;
							case "SVETLO_PREDNJE":
								dodatna.add(DodatnaOprema.SVETLO_PREDNJE);
								break;
							case "SVETLO_ZADNJE":
								dodatna.add(DodatnaOprema.SVETLO_ZADNJE);
								break;
							case "POMOCNI_TOCKICI":
								dodatna.add(DodatnaOprema.POMOCNI_TOCKICI);
								break;
							case "KORPA":
								dodatna.add(DodatnaOprema.KORPA);
								break;
							case "DECIJE_SEDISTE":
								dodatna.add(DodatnaOprema.DECIJE_SEDISTE);
								break;
						}
					}
				}else {//ako ima samo jedna dodatna oprema onda se uzima element na nultom mestu
					for(int j=0;j<pojedinacnaPolja[5].split("-").length;j++) {
						String [] dodatneOpreme = pojedinacnaPolja[5].split("-");
						switch(dodatneOpreme[j].strip()) {
							case "KACIGA":
								dodatna.add(DodatnaOprema.KACIGA);
								break;
							case "REFLEKTUJUCI_PRSLUK":
								dodatna.add(DodatnaOprema.REFLEKTUJUCI_PRSLUK);
								break;
							case "SVETLO_PREDNJE":
								dodatna.add(DodatnaOprema.SVETLO_PREDNJE);
								break;
							case "SVETLO_ZADNJE":
								dodatna.add(DodatnaOprema.SVETLO_ZADNJE);
								break;
							case "POMOCNI_TOCKICI":
								dodatna.add(DodatnaOprema.POMOCNI_TOCKICI);
								break;
							case "KORPA":
								dodatna.add(DodatnaOprema.KORPA);
								break;
							case "DECIJE_SEDISTE":
								dodatna.add(DodatnaOprema.DECIJE_SEDISTE);
								break;
						}
					}
				}
				
				
				n1.setDodatnaOprema(dodatna);
				n1.setUradjenServis(Boolean.parseBoolean(pojedinacnaPolja[6].strip()));
				sviNajmovi.add(n1);//dodavanje kreirane instance u sve najmove
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IOException e) {
			e.getMessage();
		}
		return sviNajmovi;//vraca listu svih najmova
	}
	
	static public ArrayList<Najam> sviNajmovi(Iznajmljivac iznajmljivac){//iznajmljivac se dodaje da se traze samo njegova vozila
		ArrayList<Najam> sviNajmovi = new ArrayList<Najam>();//metoda je ista kao od svih najmova samo sto se ovde koriste samo vozila od iznajmljivaca koji je prosledjen kao argument metode
		Najam n1 = null;
		try {
			FileInputStream fis = new FileInputStream("data/najmovi.csv");
			String procitano = new String(fis.readAllBytes(),"utf-8");
			String [] linije = procitano.split("\n");
			for(int i=0;i<linije.length;i++) {
				String [] pojedinacnaPolja = linije[i].split(",");
				if(pojedinacnaPolja[3].strip().equals(iznajmljivac.getKorisnickoIme())) {//ako je taj najam od ulogovanog iznajmljivaca
					n1 = new Najam();
					n1.setIdentifikator(pojedinacnaPolja[0]);
					DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
					n1.setPocetak(LocalDateTime.parse(pojedinacnaPolja[1],format));
					if(pojedinacnaPolja[2].strip().equals("null")) {
						n1.setKraj(null);
					}else {
						n1.setKraj(LocalDateTime.parse(pojedinacnaPolja[2],format));
					}
					n1.setIznajmljivac(iznajmljivac);
					//pretraga vozila na osnovu sifre da bi se smestilo ovde!
					ArrayList<Vozilo> svaVozila = Vozilo.svaVozila();
					Vozilo iznajmljeno = null;
					for(int k=0;k<svaVozila.size();k++) {
						if(svaVozila.get(k).getIdentifikator().strip().equals(pojedinacnaPolja[4].strip())) {
							iznajmljeno = svaVozila.get(k);
						}
					}
					n1.setIznajmljenoVozilo(iznajmljeno);
					//kreiranje liste dodatne opreme
					ArrayList<DodatnaOprema> dodatna = new ArrayList<DodatnaOprema>();
					if(pojedinacnaPolja[5].split("-").length > 1) {//ako ima vise dodatne opreme uzima se duzina -1 da se ne bi uzeo zadnji el
						for(int j=0;j<pojedinacnaPolja[5].split("-").length - 1;j++) {
							String [] dodatneOpreme = pojedinacnaPolja[5].split("-");
							switch(dodatneOpreme[j].strip()) {
								case "KACIGA":
									dodatna.add(DodatnaOprema.KACIGA);
									break;
								case "REFLEKTUJUCI_PRSLUK":
									dodatna.add(DodatnaOprema.REFLEKTUJUCI_PRSLUK);
									break;
								case "SVETLO_PREDNJE":
									dodatna.add(DodatnaOprema.SVETLO_PREDNJE);
									break;
								case "SVETLO_ZADNJE":
									dodatna.add(DodatnaOprema.SVETLO_ZADNJE);
									break;
								case "POMOCNI_TOCKICI":
									dodatna.add(DodatnaOprema.POMOCNI_TOCKICI);
									break;
								case "KORPA":
									dodatna.add(DodatnaOprema.KORPA);
									break;
								case "DECIJE_SEDISTE":
									dodatna.add(DodatnaOprema.DECIJE_SEDISTE);
									break;
							}
						}
					}else {//ako ima samo jedna dodatna oprema onda se uzima element na nultom mestu
						for(int j=0;j<pojedinacnaPolja[5].split("-").length;j++) {
							String [] dodatneOpreme = pojedinacnaPolja[5].split("-");
							switch(dodatneOpreme[j].strip()) {
								case "KACIGA":
									dodatna.add(DodatnaOprema.KACIGA);
									break;
								case "REFLEKTUJUCI_PRSLUK":
									dodatna.add(DodatnaOprema.REFLEKTUJUCI_PRSLUK);
									break;
								case "SVETLO_PREDNJE":
									dodatna.add(DodatnaOprema.SVETLO_PREDNJE);
									break;
								case "SVETLO_ZADNJE":
									dodatna.add(DodatnaOprema.SVETLO_ZADNJE);
									break;
								case "POMOCNI_TOCKICI":
									dodatna.add(DodatnaOprema.POMOCNI_TOCKICI);
									break;
								case "KORPA":
									dodatna.add(DodatnaOprema.KORPA);
									break;
								case "DECIJE_SEDISTE":
									dodatna.add(DodatnaOprema.DECIJE_SEDISTE);
									break;
							}
						}
					}
					
					
					n1.setDodatnaOprema(dodatna);
					n1.setUradjenServis(Boolean.parseBoolean(pojedinacnaPolja[6].strip()));
					sviNajmovi.add(n1);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IOException e) {
			e.getMessage();
		}
		return sviNajmovi;
	}
}
