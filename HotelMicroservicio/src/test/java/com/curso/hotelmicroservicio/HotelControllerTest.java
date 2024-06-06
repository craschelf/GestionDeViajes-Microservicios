package com.curso.hotelmicroservicio;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.curso.enums.Categoria;
import com.curso.model.Hotel;
import com.curso.service.HotelService;

@SpringBootTest
@AutoConfigureMockMvc
class HotelControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private HotelService hotelService;

	@Test
	public void testTodosLosHoteles_Endpoint() throws Exception {
		// Arrange
		Hotel hotel1 = new Hotel();
		Hotel hotel2 = new Hotel();
		List<Hotel> hoteles = Arrays.asList(hotel1, hotel2);
		when(hotelService.todosLosHoteles()).thenReturn(hoteles);

		// Act y Assert
		mockMvc.perform(get("/hoteles")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].nombre").value(hotel1.getNombre()))
				.andExpect(jsonPath("$[1].nombre").value(hotel2.getNombre()));
	}

	@Test
	public void testBuscarHotelPorNombre_Endpoint() throws Exception {
		// Arrange
		Hotel hotel = new Hotel("hotel Raquel", Categoria.DOS, 200, true);
		when(hotelService.buscarHotelPorNombre("Hotel Raquel")).thenReturn(hotel);

		// Act & Assert
		mockMvc.perform(get("/hoteles/buscar").param("nombre", "Nombre del Hotel")).andExpect(status().isOk())
				.andExpect(jsonPath("$.nombre").value(hotel.getNombre()));
	}
}
