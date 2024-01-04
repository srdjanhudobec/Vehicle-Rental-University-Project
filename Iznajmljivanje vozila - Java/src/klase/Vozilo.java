package klase;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import enumi.Stanje;
import interfejsi.Iznajmljivo;
import interfejsi.Odrzavanje;
public abstract class Vozilo implements Iznajmljivo,Odrzavanje{
	
	protected String identifikator;
	protected Vlasnik vlasnik;
	protected int cenaPoSatu;
	protected int velicinaTockova;
	protected int nosivost;
	protected Stanje stanje;
	protected boolean zauzeto;
	
	public Vozilo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Vozilo(String identifikator, Vlasnik vlasnik, int cenaPoSatu, int velicinaTockova, int nosivost,
			Stanje stanje, boolean zauzeto) {
		super();
		this.identifikator = identifikator;
		this.vlasnik = vlasnik;
		this.cenaPoSatu = cenaPoSatu;
		this.velicinaTockova = velicinaTockova;
		this.nosivost = nosivost;
		this.stanje = stanje;
		this.zauzeto = zauzeto;
	}

	public String getIdentifikator() {
		return identifikator;
	}

	public void setIdentifikator(String identifikator) {
		this.identifikator = identifikator;
	}

	public Vlasnik getVlasnik() {
		return vlasnik;
	}

	public void setVlasnik(Vlasnik vlasnik) {
		this.vlasnik = vlasnik;
	}

	public int getCenaPoSatu() {
		return cenaPoSatu;
	}

	public void setCenaPoSatu(int cenaPoSatu) {
		this.cenaPoSatu = cenaPoSatu;
	}

	public int getVelicinaTockova() {
		return velicinaTockova;
	}

	public void setVelicinaTockova(int velicinaTockova) {
		this.velicinaTockova = velicinaTockova;
	}

	public int getNosivost() {
		return nosivost;
	}

	public void setNosivost(int nosivost) {
		this.nosivost = nosivost;
	}

	public Stanje getStanje() {
		return stanje;
	}

	public void setStanje(Stanje stanje) {
		this.stanje = stanje;
	}

	public boolean isZauzeto() {
		return zauzeto;
	}

	public void setZauzeto(boolean zauzeto) {
		this.zauzeto = zauzeto;
	}
	
	public void popravi(Vozilo izabranoVoz) {
		izabranoVoz.setStanje(Stanje.BEZ_OSTECENJA);
		//promena stanja u fajlu vozila.csv
		//citanje vozila do sada,promena i smestanje u listu
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
	}
	
	public Stanje proveriStanje()//metoda iz interfejsa koja vraca stanje vozila
	{
		return this.getStanje();
	}
	
	public void vrati(Vozilo v1) {
		//vratiti status zauzetosti vozila na slobodan kao i promena toga u fajlu
		v1.setZauzeto(false);
		//promena u fajlu
		//citanje svih vozila
		ArrayList<String> svaVozila = new ArrayList<String>();
		try {
			FileInputStream fis = new FileInputStream("data/vozila.csv");
			String procitano = new String(fis.readAllBytes(),"utf-8");
			String [] linije = procitano.split("\n");
			for(int i=0;i<linije.length;i++) {
				String zaUpis = "";
				String [] pojedinacnaPolja = linije[i].split(",");
				if(pojedinacnaPolja[1].strip().equals(v1.getIdentifikator().strip())) {
					zaUpis = pojedinacnaPolja[0] + "," + pojedinacnaPolja[1] + "," + pojedinacnaPolja[2] + "," + pojedinacnaPolja[3] + "," + pojedinacnaPolja[4] + "," + pojedinacnaPolja[5] + "," + pojedinacnaPolja[6] + "," + "false" + "," + pojedinacnaPolja[8] + "," + pojedinacnaPolja[9];
				}
				else {
					zaUpis = linije[i];
				}
				svaVozila.add(zaUpis);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IOException e) {
			e.getMessage();
		}
		//upisivanje novokreirane liste vozila sa promenjenjenom zauzetosti
		try {
			FileWriter fw = new FileWriter("data/vozila.csv");
			for(int i=0;i<svaVozila.size();i++) {
				fw.write(svaVozila.get(i) + "\n");
				fw.flush();
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void unajmi(Vozilo izabranoVozilo) {
		//treba da promeni status zauzetosti na true i to i u fajlu da se promeni!
		String zaUpis = "";
		//ostalo je samo zauzeto vozilo staviti na true i promeniti to i u vozilima
		izabranoVozilo.setZauzeto(true);
		//napraviti izmenjenu listu koja je procitala prethodne podatke i promenila zauzetost na true
		ArrayList<String> vozilaUpis = new ArrayList<String>();
		FileInputStream fis;
		try {
			fis = new FileInputStream("data/vozila.csv");
			String procitano = new String(fis.readAllBytes(),"utf-8");
			String [] linije = procitano.split("\n");
			for(int i=0;i<linije.length;i++) {
				zaUpis = "";
				String [] pojedinacnaPolja = linije[i].split(",");
				if(pojedinacnaPolja[1].strip().equals(izabranoVozilo.getIdentifikator().strip())) {
					zaUpis = pojedinacnaPolja[0] + "," + pojedinacnaPolja[1] + "," + pojedinacnaPolja[2] + "," + pojedinacnaPolja[3] + "," + pojedinacnaPolja[4] + "," + pojedinacnaPolja[5] + "," + pojedinacnaPolja[6] + "," + "true" + "," + pojedinacnaPolja[8] + "," + pojedinacnaPolja[9];
				}
				else {
					zaUpis = pojedinacnaPolja[0] + "," + pojedinacnaPolja[1] + "," + pojedinacnaPolja[2] + "," + pojedinacnaPolja[3] + "," + pojedinacnaPolja[4] + "," + pojedinacnaPolja[5] + "," + pojedinacnaPolja[6] + "," + pojedinacnaPolja[7] + "," + pojedinacnaPolja[8] + "," + pojedinacnaPolja[9];
				}
				vozilaUpis.add(zaUpis);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IOException e) {
			e.getMessage();
		}
		//zapis izmenjene liste u fajl
		try {
			FileWriter fwVoz = new FileWriter("data/vozila.csv");
			for(int k=0;k<vozilaUpis.size();k++) {
				fwVoz.write(vozilaUpis.get(k) + "\n");
				fwVoz.flush();
			}
			fwVoz.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static public ArrayList<Vozilo> nevracenaVozila(Iznajmljivac iznajmljivac){
		ArrayList<Vozilo> nevracena = new ArrayList<Vozilo>();
		ArrayList<Najam> sviNajmovi = Najam.sviNajmovi(iznajmljivac);
		//sad od svih najmova naci one koji nisu vraceni i oni koji se poklapaju sa iznajmljivacevim korisnickim imenom!
		for(int i=0;i<sviNajmovi.size();i++) {
			if(sviNajmovi.get(i).getKraj()==null && sviNajmovi.get(i).getIznajmljivac().getKorisnickoIme().equals(iznajmljivac.getKorisnickoIme()))
			{
				nevracena.add(sviNajmovi.get(i).getIznajmljenoVozilo());
			}
		}
		//vraca sva vozila iznajmljivaca koja nisu vracena
		return nevracena;
	}
	
	static public ArrayList<Vozilo> pretragaPoZauzetosti() {
		ArrayList<Vozilo> slobodnaVozila = new ArrayList<Vozilo>();
		try {
			FileInputStream fis = new FileInputStream("data/vozila.csv");//prolazak kroz fajl vozila.csv
			String procitano = new String(fis.readAllBytes(),"utf-8");
			String [] linije = procitano.split("\n");
			Bicikl b1 = null;
			Trotinet t1 = null;
			for(int i=0;i<linije.length;i++) {
				String [] pojedinacnaPoljaVozila = linije[i].split(",");
				String bajsIliTrot = "";
				if(pojedinacnaPoljaVozila[7].equals("false")) {//ako vozilo nije zauzeto,kreira se instanca vozila i dodaje se u listu slobodnih vozila
					if(pojedinacnaPoljaVozila[0].strip().equals("Bicikl")) {
						b1 = new Bicikl();
						b1.setIdentifikator(pojedinacnaPoljaVozila[1]);
						b1.setVlasnik(Vlasnik.konvertujUVlasnika(pojedinacnaPoljaVozila[2]));
						b1.getVlasnik();
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
					}else if(pojedinacnaPoljaVozila[0].strip().equals("Trotinet")) {
						t1 = new Trotinet();
						t1.setIdentifikator(pojedinacnaPoljaVozila[1]);
						t1.setVlasnik(Vlasnik.konvertujUVlasnika(pojedinacnaPoljaVozila[2]));
						t1.setCenaPoSatu(Integer.parseInt(pojedinacnaPoljaVozila[3]));
						t1.setVelicinaTockova(Integer.parseInt(pojedinacnaPoljaVozila[4]));
						t1.setNosivost(Integer.parseInt(pojedinacnaPoljaVozila[5]));
						Stanje stanje = null;
						String stringStanjaTrotineta = pojedinacnaPoljaVozila[6].strip();;
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
						slobodnaVozila.add(b1);
					}
					else if(bajsIliTrot.equals("trot")) {
						slobodnaVozila.add(t1);
					}
					//ide na sledece vozilo
					//dodaje se bajs ili trotinet
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IOException e) {
			e.getMessage();
		}
		return slobodnaVozila;
	}
	
	static public ArrayList<Vozilo> svaVozila(){
		ArrayList<Vozilo> svaVozila = new ArrayList<Vozilo>();//iscitava sva vozila iz fajla i kreira njihove instance
		Bicikl b1 = null;
		Trotinet t1 = null;
		try {
			FileInputStream fis = new FileInputStream("data/vozila.csv");
			String procitano = new String(fis.readAllBytes(),"utf-8");
			String [] linije = procitano.split("\n");
			for(int i=0;i<linije.length;i++) {
				String bajsIliTrot = "";
				String [] pojedinacnaPoljaVozila = linije[i].split(",");
				if(pojedinacnaPoljaVozila[0].strip().equals("Bicikl")) {
					b1 = new Bicikl();
					b1.setIdentifikator(pojedinacnaPoljaVozila[1]);
					b1.setVlasnik(Vlasnik.konvertujUVlasnika(pojedinacnaPoljaVozila[2]));
					b1.getVlasnik();
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
					t1.setVlasnik(Vlasnik.konvertujUVlasnika(pojedinacnaPoljaVozila[2]));
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
					svaVozila.add(b1);
				}
				else if(bajsIliTrot.equals("trot")) {
					svaVozila.add(t1);
				}
				//ide na sledece vozilo
				//dodaje se bajs ili trotinet
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IOException e) {
			e.getMessage();
		}
		return svaVozila;
	}
}
