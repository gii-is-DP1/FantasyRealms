package es.us.dp1.l2_05_24_25.fantasy_realms.dto;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import es.us.dp1.l2_05_24_25.fantasy_realms.dto.ModDTO;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.Mod;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.ModType;

public class ModDTOTests {
    private Mod mod;
    private ModDTO modDTO;

    @BeforeEach
    public void setUp() {
        mod = Mockito.mock(Mod.class);
        Mockito.when(mod.getId()).thenReturn(1);
        Mockito.when(mod.getDescription()).thenReturn("Test Description");
        Mockito.when(mod.getPrimaryValue()).thenReturn(10);
        Mockito.when(mod.getSecondaryValue()).thenReturn(20);
        Mockito.when(mod.getModType()).thenReturn(ModType.PENALTY);

        modDTO = new ModDTO(mod);
    }

    @Test
    public void testModDTOCreation() {
        assertEquals(mod.getId(), modDTO.getId());
        assertEquals(mod.getDescription(), modDTO.getDescription());
        assertEquals(mod.getPrimaryValue(), modDTO.getPrimaryValue());
        assertEquals(mod.getSecondaryValue(), modDTO.getSecondaryValue());
        assertEquals(mod.getModType(), modDTO.getModType());
    }

    @Test
    public void testModDTOSettersAndGetters() {
        modDTO.setDescription("New Description");
        assertEquals("New Description", modDTO.getDescription());

        modDTO.setPrimaryValue(30);
        assertEquals(30, modDTO.getPrimaryValue());

        modDTO.setSecondaryValue(40);
        assertEquals(40, modDTO.getSecondaryValue());

        modDTO.setModType(ModType.BONUS);
        assertEquals(ModType.BONUS, modDTO.getModType());
    }

    @Test
    public void testModDTOConstructorWithNullValues() {
        Mod nullMod = Mockito.mock(Mod.class);
        Mockito.when(nullMod.getId()).thenReturn(2);
        Mockito.when(nullMod.getDescription()).thenReturn(null);
        Mockito.when(nullMod.getPrimaryValue()).thenReturn(null);
        Mockito.when(nullMod.getSecondaryValue()).thenReturn(null);
        Mockito.when(nullMod.getModType()).thenReturn(null);

        ModDTO nullModDTO = new ModDTO(nullMod);

        assertEquals(nullMod.getId(), nullModDTO.getId());
        assertNull(nullModDTO.getDescription());
        assertNull(nullModDTO.getPrimaryValue());
        assertNull(nullModDTO.getSecondaryValue());
        assertNull(nullModDTO.getModType());
    }
}
