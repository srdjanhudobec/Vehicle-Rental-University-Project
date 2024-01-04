package klase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;

import enumi.Stanje;

public class Vlasnik extends Korisnik{
	
	private ArrayList<Vozilo> vozila;

	public Vlasnik() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Vlasnik(String korisnickoIme,String lozinka,ArrayList<Vozilo> vozila) {
		super(korisnickoIme,lozinka);
		this.vozila = vozila;
	}

	public ArrayList<Vozilo> getVozila() {
		return vozila;
	}

	public void setVozila(ArrayList<Vozilo> vozila) {
		this.vozila = vozila;
	}
	
	public String toString() {
		return "Vlasnik [korisnickoIme = " + korisnickoIme + ", vozila = " + vozila;
	}
	
	public void stavljanjeVozilaVanUpotrebe() {
		ArrayList<Vozilo> vozilaVl = this.getVozila();
		ArrayList<String> uklonjeniId = new ArrayList<String>();//u ovu listu se dodaju id obrisanih vozila da bi se mogla obrisati u korisnicima.csv
		for(int i=this.getVozila().size() - 1;i >= 0;i--) {//ide unazad da ne bi doslo do problema pri indeksiranju
			if(this.getVozila().get(i).getStanje().equals(Stanje.NEUPOTREBLJIVO))
			{
				uklonjeniId.add(this.getVozila().get(i).getIdentifikator());
				this.getVozila().remove(i);
			}
		}
		if(uklonjeniId.size()<= 0) {
			System.out.println("Nema vozila koja su neupotrebljiva,samim tim nema vozila koja trebaju da se uklone sa NSGO platforme.");
			 Platforma.opcijeVlasnika(this);//ako nema vozila koja trebaju da se izbace sa platforme,korisniku se opet nude opcije
		}
		else {
			//brisanje svega sto se nalazi u fajlu
			File vozilaCSV = new File("data/vozila.csv");
			if(vozilaCSV.length()>0) {
				try {
					FileWriter fwBrisanje = new FileWriter("data/vozila.csv");
					String upisPraznog = "";
					fwBrisanje.write(upisPraznog);
					fwBrisanje.flush();
					fwBrisanje.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
//			zapis u fajl bez neupotrebljivog vozila
			for(int i=0;i<this.getVozila().size();i++) {
				String zaUpis = "";
				try {
					vozilaCSV = new File("data/vozila.csv");
					FileWriter fw = new FileWriter("data/vozila.csv",true);
					if(vozilaCSV.length() > 0) {//ako vec postoje elementi onda se dodaje \n na pocetak da bi imao novi red
						if(this.getVozila().get(i).getClass().getName().strip().equals("klase.Bicikl")){
							Bicikl b1 = (Bicikl) this.getVozila().get(i);
							zaUpis = "\n" + "Bicikl," +  b1.getIdentifikator() + "," + b1.getVlasnik().getKorisnickoIme() +"," +  b1.getCenaPoSatu() +"," +  b1.getVelicinaTockova() +"," +  b1.getNosivost() +"," +  b1.getStanje() +"," +  b1.isZauzeto() +"," +  b1.getBrojBrzina() +"," +  b1.getVisina(); 
						}
						else if(this.getVozila().get(i).getClass().getName().strip().equals("klase.Trotinet"))
						{
							Trotinet t1 = (Trotinet) this.getVozila().get(i);
							zaUpis = "\n" + "Trotinet," +  t1.getIdentifikator() + "," + t1.getVlasnik().getKorisnickoIme() +"," +  t1.getCenaPoSatu() +"," +  t1.getVelicinaTockova() +"," +  t1.getNosivost() +"," +  t1.getStanje() +"," +  t1.isZauzeto() +"," +  t1.getMaksimalnaBrzina() +"," +  t1.getTrajanjeBaterije(); 
						}
					}else {//identicna stvar kao i za gornje grananje samo sto ovde ne ide \n jer ako je fajl prazan onda nam ne treba prazan red na pocetku
						if(this.getVozila().get(i).getClass().getName().strip().equals("klase.Bicikl")){
							Bicikl b1 = (Bicikl) this.getVozila().get(i);
							zaUpis = "Bicikl," +  b1.getIdentifikator() + "," + b1.getVlasnik().getKorisnickoIme() +"," +  b1.getCenaPoSatu() +"," +  b1.getVelicinaTockova() +"," +  b1.getNosivost() +"," +  b1.getStanje() +"," +  b1.isZauzeto() +"," +  b1.getBrojBrzina() +"," +  b1.getVisina(); 
						}
						else if(this.getVozila().get(i).getClass().getName().strip().equals("klase.Trotinet"))
						{
							Trotinet t1 = (Trotinet) this.getVozila().get(i);
							zaUpis = "Trotinet," +  t1.getIdentifikator() + "," + t1.getVlasnik().getKorisnickoIme() +"," +  t1.getCenaPoSatu() +"," +  t1.getVelicinaTockova() +"," +  t1.getNosivost() +"," +  t1.getStanje() +"," +  t1.isZauzeto() +"," +  t1.getMaksimalnaBrzina() +"," +  t1.getTrajanjeBaterije(); 
						}
					}
					fw.write(zaUpis);
					fw.flush();
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//promena u fajlu korisnici.csv gde se sa metodom replace id od izbacenog vozila replace-uje sa "" - praznim stringom
			//citanje iz fajla i menjanje podatka u listi
			ArrayList<String> korisnici = new ArrayList<String>();
			try {
				FileInputStream fis = new FileInputStream("data/korisnici.csv");
				String procitano = new String(fis.readAllBytes(),"utf-8");
				String [] linije = procitano.split("\n");
				for(int i=0;i<linije.length;i++) {
					String zaUpis = "";
					String [] pojedinacnaPolja = linije[i].split(",");
					if(pojedinacnaPolja[1].strip().equals(this.getKorisnickoIme())) {
						String izmenjeniString = "";
						for(int j=0;j<uklonjeniId.size();j++) {
							if(j==0) {//ako je prva iteracija onda se koristi pojedinacna polja
								izmenjeniString = pojedinacnaPolja[3].replace("-" + uklonjeniId.get(j), "");

							}else{
								izmenjeniString = izmenjeniString.replace("-" + uklonjeniId.get(j), "");
							}//u suprotnom se menja izmenjeni string
						}
						zaUpis = pojedinacnaPolja[0] + "," + pojedinacnaPolja[1] + "," + pojedinacnaPolja[2] + "," + izmenjeniString;
					}
					else if(pojedinacnaPolja[0].strip().equals("class klase.Iznajmljivac")){//menja se i kod iznajmljivaca se brise iz istorije iznajmljivanja tj kartice njegove
						String izmenjeniString = "";
						for(int j=0;j<uklonjeniId.size();j++) {
							if(pojedinacnaPolja[9].strip().contains(uklonjeniId.get(j))) {
								izmenjeniString = pojedinacnaPolja[9].replace("-" + uklonjeniId.get(j) + ",", "");
							}else {
								continue;
							}
						}
						if(!(izmenjeniString.equals(""))) {//ako je bilo izmene
							zaUpis = pojedinacnaPolja[0] + "," + pojedinacnaPolja[1] + "," + pojedinacnaPolja[2] + "," + pojedinacnaPolja[3]  + "," + pojedinacnaPolja[4]  + "," + pojedinacnaPolja[5]  + "," + pojedinacnaPolja[6]  + "," + pojedinacnaPolja[7]  + "," + pojedinacnaPolja[8]  + "," + izmenjeniString; 

						}else {//ako nije
							zaUpis = pojedinacnaPolja[0] + "," + pojedinacnaPolja[1] + "," + pojedinacnaPolja[2] + "," + pojedinacnaPolja[3]  + "," + pojedinacnaPolja[4]  + "," + pojedinacnaPolja[5]  + "," + pojedinacnaPolja[6]  + "," + pojedinacnaPolja[7]  + "," + pojedinacnaPolja[8]  + "," + pojedinacnaPolja[9]; 
						}
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
			//zapisivanje nove liste u fajl korisnici.csv
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
			//ako postoji u najmovi.csv fajlu da je nekad iznajmljivano to vozilo,brise se iz najmova
			//dosadasnja lista najmova i iz nje se menja ako postoji id od tog vozila taj red najma se brise iz liste
			ArrayList<String> najmovi = new ArrayList<String>();
			try {
				FileInputStream fis = new FileInputStream("data/najmovi.csv");
				String procitano = new String(fis.readAllBytes(),"utf-8");
				String [] linije = procitano.split("\n");
				for(int i=0;i<linije.length;i++) {
					String zaUpis = "";
					boolean preskaci = false;
					String [] pojedinacnaPolja = linije[i].split(",");
					for(int j=0;j<uklonjeniId.size();j++) {//ako najam sadrzi id on se ne dodaje u listu najmova
						if(pojedinacnaPolja[4].equals(uklonjeniId.get(j))) {
							preskaci = true;
						}
						else {
							zaUpis = linije[i];
						}
					}
					if(preskaci == false) {
						najmovi.add(zaUpis);
					}
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(IOException e) {
				e.getMessage();
			}
			//zapis te nove liste najma u fajl
			try {
				FileWriter fw = new FileWriter("data/najmovi.csv");
				for(int i=0;i<najmovi.size();i++) {
					fw.write(najmovi.get(i) + "\n");
					fw.flush();
				}
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Izbrisana su sva neupotrebljiva vozila od ulogovanog vlasnika.");
		}
		Platforma.opcijeVlasnika(this);//da se program ne bi ovde zavrsio,korisniku se opet nude opcije
	}
	
	static public Vlasnik konvertujUVlasnika(String s) {
		Vlasnik v1 = new Vlasnik();
		ArrayList<Vozilo> vozilaVlasnika = new ArrayList<Vozilo>();
		Bicikl b1 = null;
		Trotinet t1 = null;
		String bajsIliTrot;
		try {
			FileInputStream fis = new FileInputStream("data/korisnici.csv");
			String procitano = new String(fis.readAllBytes(),"utf-8");
			String [] linije = procitano.split("\n");
			for(int i=0;i<linije.length;i++) {
				String [] pojedinacnaPolja = linije[i].split(",");
				if(pojedinacnaPolja[0].equals("class klase.Vlasnik") && s.strip().equals(pojedinacnaPolja[1])) {
					v1.setKorisnickoIme(pojedinacnaPolja[1]);
					v1.setLozinka(pojedinacnaPolja[2]);
					//za vlasnika trebam iscitati sva vozila koja imaju korisnicko ime jednako parametru koji je dat
					//citam vozila,ako se poklapa prvi deo pre zareza sa parametrom metode,to vozilo dodajemo u listu koja se kasnije setuje kao lista vozila vlasnika
					FileInputStream fis2 = new FileInputStream("data/vozila.csv");
					String procitanaVozila = new String(fis2.readAllBytes(),"utf-8");
					String [] linijeVozila = procitanaVozila.split("\n");
					for(int j=0;j<linijeVozila.length;j++) {
						bajsIliTrot = "";
						String [] poljaVozila = linijeVozila[j].split(",");
						if(poljaVozila[2].equals(s) && poljaVozila[0].equals("Bicikl")) {
							b1 = new Bicikl();
							b1.setIdentifikator(poljaVozila[1]);
							b1.setVlasnik(null);
							b1.setCenaPoSatu(Integer.parseInt(poljaVozila[3]));
							b1.setVelicinaTockova(Integer.parseInt(poljaVozila[4]));
							b1.setNosivost(Integer.parseInt(poljaVozila[5]));
							Stanje stanje = null;
							String stringStanjaBajsa = poljaVozila[6].strip();
							switch(stringStanjaBajsa) {
								case "BEZ_OSTECENJA":
									stanje = Stanje.BEZ_OSTECENJA;
									break;
								case "MALO_OSTECENJE":
									stanje = Stanje.MALO_OSTECENJE;
									break;
								case "VELIKO_OSTECENJE":
									stanje = Stanje.VELIKO_OSTECENJE;
									break;
								case "NEUPOTREBLJIVO":
									stanje = Stanje.NEUPOTREBLJIVO;
									break;
							}
							b1.setStanje(stanje);
							b1.setZauzeto(Boolean.parseBoolean(poljaVozila[7]));
							b1.setBrojBrzina(Integer.parseInt(poljaVozila[8]));
							String visinaVozila = poljaVozila[9].strip();
							b1.setVisina(Integer.parseInt(visinaVozila));
							bajsIliTrot = "bajs";
						}
						else if(poljaVozila[2].equals(s) && poljaVozila[0].equals("Trotinet")) {
							t1 = new Trotinet();
							t1.setIdentifikator(poljaVozila[1]);
							t1.setVlasnik(null);
							t1.setCenaPoSatu(Integer.parseInt(poljaVozila[3]));
							t1.setVelicinaTockova(Integer.parseInt(poljaVozila[4]));
							t1.setNosivost(Integer.parseInt(poljaVozila[5]));
							Stanje stanje = null;
							String stringStanjaTrotineta = poljaVozila[6];
							switch(stringStanjaTrotineta) {
								case "BEZ_OSTECENJA":
									stanje = Stanje.BEZ_OSTECENJA;
									break;
								case "MALO_OSTECENJE":
									stanje = Stanje.MALO_OSTECENJE;
									break;
								case "VELIKO_OSTECENJE":
									stanje = Stanje.VELIKO_OSTECENJE;
									break;
								case "NEUPOTREBLJIVO":
									stanje = Stanje.NEUPOTREBLJIVO;
									break;
							}
							t1.setStanje(stanje);
							t1.setZauzeto(Boolean.parseBoolean(poljaVozila[7]));
							t1.setMaksimalnaBrzina(Integer.parseInt(poljaVozila[8]));
							String trajanjeBaterije = poljaVozila[9].strip();
							t1.setTrajanjeBaterije(Integer.parseInt(trajanjeBaterije));
							bajsIliTrot = "trot";
						}
						if(bajsIliTrot.equals("bajs")) {
							vozilaVlasnika.add(b1);
						}
						else if(bajsIliTrot.equals("trot")) {
							vozilaVlasnika.add(t1);
						}
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		v1.setVozila(vozilaVlasnika);
		for(int i=0;i<v1.getVozila().size();i++) {
			v1.getVozila().get(i).setVlasnik(v1);
		}
		return v1;
	}
	
	public Vlasnik kreirajInstancuVlasnika() {
		Vlasnik v1 = null;
		ArrayList<Vozilo> vozilaVlasnika = new ArrayList<Vozilo>();
		//ucitavanje svega iz csv fajla
		FileInputStream fisVozilo;
		String bajsIliTrot;
		Bicikl b1 = null;
		Trotinet t1 = null;
		try {
			fisVozilo = new FileInputStream("data/vozila.csv");
			String procitanaVozila = new String(fisVozilo.readAllBytes(),"utf-8");
			String [] linijeVozila = procitanaVozila.split("\n");
			for(int j=0;j<linijeVozila.length;j++) {
				bajsIliTrot = "";
				String [] pojedinacnaPoljaVozila = linijeVozila[j].split(",");//ako se poklapa korisnicko ime sa korisnickim imenom vlasnika u vozilima,ako je bicikl da se kreira bicikl ako je trotinet da se kreira trotinet
				if(pojedinacnaPoljaVozila[2].equals(Platforma.korisnickoIme.strip().toString())) {
					if(pojedinacnaPoljaVozila[0].strip().equals("Bicikl")) {
						b1 = new Bicikl();
						b1.setIdentifikator(pojedinacnaPoljaVozila[1]);
						b1.setVlasnik(konvertujUVlasnika(pojedinacnaPoljaVozila[2]));
						b1.setCenaPoSatu(Integer.parseInt(pojedinacnaPoljaVozila[3]));
						b1.setVelicinaTockova(Integer.parseInt(pojedinacnaPoljaVozila[4]));
						b1.setNosivost(Integer.parseInt(pojedinacnaPoljaVozila[5]));
						Stanje stanje = null;
						String stringStanjaBajsa = pojedinacnaPoljaVozila[6].strip();
						switch(stringStanjaBajsa) {
							case "BEZ_OSTECENJA":
								stanje = Stanje.BEZ_OSTECENJA;
								break;
							case "MALO_OSTECENJE":
								stanje = Stanje.MALO_OSTECENJE;
								break;
							case "VELIKO_OSTECENJE":
								stanje = Stanje.VELIKO_OSTECENJE;
								break;
							case "NEUPOTREBLJIVO":
								stanje = Stanje.NEUPOTREBLJIVO;
								break;
						}
						b1.setStanje(stanje);
						b1.setZauzeto(Boolean.parseBoolean(pojedinacnaPoljaVozila[7]));
						b1.setBrojBrzina(Integer.parseInt(pojedinacnaPoljaVozila[8]));
						String visinaVozila = pojedinacnaPoljaVozila[9].strip();
						b1.setVisina(Integer.parseInt(visinaVozila));
						bajsIliTrot = "bajs";
					}
					else if(pojedinacnaPoljaVozila[0].strip().equals("Trotinet")) {
						t1 = new Trotinet();
						t1.setIdentifikator(pojedinacnaPoljaVozila[1]);
						t1.setVlasnik(konvertujUVlasnika(pojedinacnaPoljaVozila[2]));
						t1.setCenaPoSatu(Integer.parseInt(pojedinacnaPoljaVozila[3]));
						t1.setVelicinaTockova(Integer.parseInt(pojedinacnaPoljaVozila[4]));
						t1.setNosivost(Integer.parseInt(pojedinacnaPoljaVozila[5]));
						Stanje stanje = null;
						String stringStanjaTrotineta = pojedinacnaPoljaVozila[6].strip();
						switch(stringStanjaTrotineta) {
							case "BEZ_OSTECENJA":
								stanje = Stanje.BEZ_OSTECENJA;
								break;
							case "MALO_OSTECENJE":
								stanje = Stanje.MALO_OSTECENJE;
								break;
							case "VELIKO_OSTECENJE":
								stanje = Stanje.VELIKO_OSTECENJE;
								break;
							case "NEUPOTREBLJIVO":
								stanje = Stanje.NEUPOTREBLJIVO;
								break;
						}
						t1.setStanje(stanje);
						t1.setZauzeto(Boolean.parseBoolean(pojedinacnaPoljaVozila[7]));
						t1.setMaksimalnaBrzina(Integer.parseInt(pojedinacnaPoljaVozila[8]));
						String trajanjeBaterije = pojedinacnaPoljaVozila[9].strip();
						t1.setTrajanjeBaterije(Integer.parseInt(trajanjeBaterije));
						bajsIliTrot = "trot";
					}
					if(bajsIliTrot.equals("bajs")) {
						vozilaVlasnika.add(b1);
					}
					else if(bajsIliTrot.equals("trot")) {
						vozilaVlasnika.add(t1);
					}
					//ide na sledece vozilo
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IOException e) {
			e.getMessage();
		}
		v1 = new Vlasnik(Platforma.korisnickoIme,Platforma.lozinka,vozilaVlasnika);
		return v1;
		}
}

