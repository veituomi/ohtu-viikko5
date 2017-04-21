
package ohtu.intjoukkosovellus;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class JoukkoOperaatiotTest {
    
    
    @Test
    public void testSomething() {
        IntJoukko eka = teeJoukko(1,2);
        IntJoukko toka = teeJoukko(3,4);
        
        IntJoukko tulos = IntJoukko.yhdiste(eka, toka);
        int[] vastauksenLuvut = tulos.toIntArray();
        Arrays.sort(vastauksenLuvut);
        
        int[] odotettu = {1,2,3,4};
        
        assertArrayEquals(odotettu, vastauksenLuvut);        
    } 

    private IntJoukko teeJoukko(int... luvut) {
        IntJoukko joukko = new IntJoukko();
        
        for (int luku : luvut) {
            joukko.lisaa(luku);
        }
        
        return joukko;
    }

    @Test
    public void yhdisteToimii() {
        IntJoukko joukko1 = teeJoukko(1, 2, 3, 4);
        IntJoukko joukko2 = teeJoukko(4, 5, 6, 7);
        IntJoukko yhdiste = teeJoukko(1, 2, 3, 4, 5, 6, 7);
        assertArrayEquals(IntJoukko.yhdiste(joukko1, joukko2).toIntArray(), yhdiste.toIntArray());
    }

    @Test
    public void leikkausToimii() {
        IntJoukko joukko1 = teeJoukko(1, 2, 3, 4, 5);
        IntJoukko joukko2 = teeJoukko(4, 5, 6, 7);
        IntJoukko leikkaus = teeJoukko(4, 5);
        assertArrayEquals(IntJoukko.leikkaus(joukko1, joukko2).toIntArray(), leikkaus.toIntArray());
    }

    @Test
    public void erotusToimii() {
        IntJoukko joukko1 = teeJoukko(1, 2, 3, 4, 5);
        IntJoukko joukko2 = teeJoukko(4, 5, 6, 7);
        IntJoukko leikkaus = teeJoukko(1, 2, 3);
        assertArrayEquals(IntJoukko.erotus(joukko1, joukko2).toIntArray(), leikkaus.toIntArray());
    }
}
