package ohtu.intjoukkosovellus;

public class IntJoukko {

    public final static int KAPASITEETTI = 5, // aloitustalukon koko
                            OLETUSKASVATUS = 5;  // luotava uusi taulukko on 
    // näin paljon isompi kuin vanha
    private int kasvatuskoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] lukujono;      // Joukon luvut säilytetään taulukon alkupäässä. 
    private int alkioidenLkm;    // Tyhjässä joukossa alkioiden_määrä on nolla. 

    public IntJoukko() {
        this(KAPASITEETTI);
    }

    public IntJoukko(int kapasiteetti) {
        this(kapasiteetti, OLETUSKASVATUS);
    }
    
    
    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        if (!validoiKapasiteettiJaKasvatuskoko(kapasiteetti, kasvatuskoko)) {
            return;
        }
        alustaLukujono(kapasiteetti);
        this.kasvatuskoko = kasvatuskoko;
    }

    private void alustaLukujono(int kapasiteetti) {
        lukujono = new int[kapasiteetti];
        for (int i = 0; i < lukujono.length; i++) {
            lukujono[i] = 0;
        }
        alkioidenLkm = 0;
    }

    private boolean validoiKapasiteettiJaKasvatuskoko(int kapasiteetti, int kasvatuskoko) {
        return kapasiteetti >= 0 && kasvatuskoko >= 0;
    }

    public boolean lisaa(int luku) {
        if (!kuuluu(luku)) {
            lukujono[alkioidenLkm++] = luku;
            if (alkioidenLkm == lukujono.length) {
                kasvata();
            }
            return true;
        }
        return false;
    }

    public void kasvata() {
        int[] taulukkoOld = lukujono;
        lukujono = new int[alkioidenLkm + kasvatuskoko];
        kopioiTaulukko(taulukkoOld, lukujono);
    }

    public boolean kuuluu(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == lukujono[i]) {
                return true;
            }
        }
        return false;
    }

    public boolean poista(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == lukujono[i]) {
                siirraAlkuaKohti(i);
                return true;
            }
        }
        return false;
    }

    private void siirraAlkuaKohti(int kohta) {
        for (int j = kohta; j < alkioidenLkm - 1; j++) {
            int apu = lukujono[j];
            lukujono[j] = lukujono[j + 1];
            lukujono[j + 1] = apu;
        }
        alkioidenLkm--;
    }

    private void kopioiTaulukko(int[] vanha, int[] uusi) {
        for (int i = 0; i < vanha.length; i++) {
            uusi[i] = vanha[i];
        }

    }

    public int mahtavuus() {
        return alkioidenLkm;
    }


    @Override
    public String toString() {
        String tuotos = "{";
        for (int i = 0; i < alkioidenLkm - 1; i++) {
            tuotos += lukujono[i] + ", ";
        }
        if (alkioidenLkm > 0) {
            tuotos += lukujono[alkioidenLkm - 1];
        }
        return tuotos + "}";
    }

    public int[] toIntArray() {
        int[] taulu = new int[alkioidenLkm];
        for (int i = 0; i < taulu.length; i++) {
            taulu[i] = lukujono[i];
        }
        return taulu;
    }
   

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko x = new IntJoukko();
        lisaaKaikki(x, a.toIntArray());
        lisaaKaikki(x, b.toIntArray());
        return x;
    }

    public static void lisaaKaikki(IntJoukko mihin, int[] mista) {
        for (int i = 0; i < mista.length; i++) {
            mihin.lisaa(mista[i]);
        }
    }

    public static void poistaKaikki(IntJoukko mista, int[] mitka) {
        for (int i = 0; i < mitka.length; i++) {
            mista.poista(mitka[i]);
        }
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko leikkaus = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            if (b.kuuluu(aTaulu[i])) {
                leikkaus.lisaa(aTaulu[i]);
            }
        }
        return leikkaus;
    }
    
    public static IntJoukko erotus(IntJoukko a, IntJoukko b) {
        IntJoukko z = new IntJoukko();
        lisaaKaikki(z, a.toIntArray());
        poistaKaikki(z, b.toIntArray());
        return z;
    }
        
}