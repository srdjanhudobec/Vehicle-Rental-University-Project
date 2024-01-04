package dodatneKlase;

import java.security.SecureRandom;

public class kreiranjeIdentifikatora {

    public static String generisiID(int duzina)
    {
    	//velika,mala slova i brojevi od kojih se bira random karakter
        final String slovaIBrojevi = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        //random paket
        SecureRandom random = new SecureRandom();
        //pomocu StringBuildera se dodaju karakteri i kreira string
        StringBuilder sb = new StringBuilder();
        //pomocu for petlje koja se izvrsava onoliko puta koliko je korisnik zadao,dodaje se jedan po jedan random karakter iz stringa koji sadrzi velika,mala slova i brojeve
        for (int i = 0; i < duzina; i++)
        {
            int randomIndex = random.nextInt(slovaIBrojevi.length());//bira random broj koji se nalazi u opsegu duzine stringa
            sb.append(slovaIBrojevi.charAt(randomIndex));//karakter na random indeksu se dodaje u string
        }
 
        return sb.toString();//vraca se string sastavljen od random karaktera
    }
 
    public static void main(String[] args)
    {
    }
}
