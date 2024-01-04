package klase;

import java.time.LocalDate;
import java.util.ArrayList;

public class Kartica {
	
	private String identifikator;
	private Iznajmljivac iznajmljivac;
	private LocalDate datumOd;
	private LocalDate datumDo;
	private int raspolozivaSredstva;
	private Vozilo trenutnoAktivnoVozilo;//trenutno iznajmljeno vozilo(ako datum vracanja nije popunjen to vozilo je idalje iznajmljeno)
	private ArrayList<Najam> istorijaIznajmljivanja; 
	
	public Kartica() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Kartica(String identifikator, Iznajmljivac iznajmljivac, LocalDate datumOd, LocalDate datumDo,
			int raspolozivaSredstva, Vozilo trenutnoAktivnoVozilo, ArrayList<Najam> istorijaIznajmljivanja) {
		super();
		this.identifikator = identifikator;
		this.iznajmljivac = iznajmljivac;
		this.datumOd = datumOd;
		this.datumDo = datumDo;
		this.raspolozivaSredstva = raspolozivaSredstva;
		this.trenutnoAktivnoVozilo = trenutnoAktivnoVozilo;
		this.istorijaIznajmljivanja = istorijaIznajmljivanja;
	}

	public String getIdentifikator() {
		return identifikator;
	}

	public void setIdentifikator(String identifikator) {
		this.identifikator = identifikator;
	}

	public Iznajmljivac getIznajmljivac() {
		return iznajmljivac;
	}

	public void setIznajmljivac(Iznajmljivac iznajmljivac) {
		this.iznajmljivac = iznajmljivac;
	}

	public LocalDate getDatumOd() {
		return datumOd;
	}

	public void setDatumOd(LocalDate datumOd) {
		this.datumOd = datumOd;
	}

	public LocalDate getDatumDo() {
		return datumDo;
	}

	public void setDatumDo(LocalDate datumDo) {
		this.datumDo = datumDo;
	}

	public int getRaspolozivaSredstva() {
		return raspolozivaSredstva;
	}

	public void setRaspolozivaSredstva(int raspolozivaSredstva) {
		this.raspolozivaSredstva = raspolozivaSredstva;
	}

	public Vozilo getTrenutnoAktivnoVozilo() {
		return trenutnoAktivnoVozilo;
	}

	public void setTrenutnoAktivnoVozilo(Vozilo trenutnoAktivnoVozilo) {
		this.trenutnoAktivnoVozilo = trenutnoAktivnoVozilo;
	}

	public ArrayList<Najam> getIstorijaIznajmljivanja() {
		return istorijaIznajmljivanja;
	}

	public void setIstorijaIznajmljivanja(ArrayList<Najam> istorijaIznajmljivanja) {
		this.istorijaIznajmljivanja = istorijaIznajmljivanja;
	}
	
	
}
