package ohtu.verkkokauppa;

import org.junit.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class KauppaTest {
    @Test
    public void ostoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaan() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);
        
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);

        Varasto varasto = mock(Varasto.class);
        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10); 
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.saldo(2)).thenReturn(1); 
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "kalapuikko", 3));

        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);     
        
        verify(viite, times(0)).uusi();         

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka1", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu oikeilla parametreilla
        verify(viite, times(1)).uusi();
        verify(pankki).tilisiirto(eq("pekka1"), anyInt(), eq("12345"), anyString(), eq(5));   
        

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(1);
        k.tilimaksu("pekka2", "12346");

        verify(viite, times(2)).uusi();
        verify(pankki).tilisiirto(eq("pekka2"), anyInt(), eq("12346"), anyString(), eq(10));

        
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(2);
        k.tilimaksu("pekka3", "12347");

        verify(viite, times(3)).uusi();
        verify(pankki).tilisiirto(eq("pekka3"), anyInt(), eq("12347"), anyString(), eq(8));
        
        when(varasto.saldo(2)).thenReturn(0); 
        
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(2);
        k.tilimaksu("pekka4", "12348");

        verify(pankki).tilisiirto(eq("pekka4"), anyInt(), eq("12348"), anyString(), eq(5));
        verify(viite, times(4)).uusi();
    }

    @Test
    public void metodinAloitaAsiointiKutsuminenNollaaEdellisenOstoksenTiedot() {
        Pankki pankki = mock(Pankki.class);
        
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(42);

        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10); 
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.saldo(2)).thenReturn(1); 
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "kalapuikko", 3));

        Kauppa k = new Kauppa(varasto, pankki, viite);    

        k.aloitaAsiointi();
        k.lisaaKoriin(1);

        k.aloitaAsiointi();
        k.lisaaKoriin(2);
        k.tilimaksu("pekka4", "12348");

        verify(pankki).tilisiirto(eq("pekka4"), anyInt(), eq("12348"), anyString(), eq(3));

    }

    @Test
    public void koristaPoistamisenJalkeenTuotteenHintaOnVahennetty() {
        Pankki pankki = mock(Pankki.class);
        
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(42);

        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10); 
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.saldo(2)).thenReturn(1); 
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "kalapuikko", 3));

        Kauppa k = new Kauppa(varasto, pankki, viite);    

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(2);
        k.poistaKorista(1);
        k.tilimaksu("pekka4", "12348");

        verify(pankki).tilisiirto(eq("pekka4"), anyInt(), eq("12348"), anyString(), eq(3));
    }
}