package com.w2m.examen.integrations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.google.common.collect.Lists;
import com.w2m.examen.models.Heroe;
import com.w2m.examen.repositories.HeroeRepository;
import com.w2m.examen.security.JWTProvider;
import com.w2m.examen.exceptions.NotFoundException;

import static java.lang.String.format;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class HeroesTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private JWTProvider jwtProvider;
	
	@MockBean
	private HeroeRepository repository;
	
	@Test
	public void saveTestOk() throws Exception {
		when(repository.save(any())).thenReturn(getHeroeMock());
		mockMvc.perform(post("/heroes")
		  .header("Authorization", getTokenMock())
		  .contentType(MediaType.APPLICATION_JSON).content("{\"name\": \"Superman\"}"))
		  .andDo(print())
		  .andExpect(jsonPath("$.id").value(1l))
		  .andExpect(status().isCreated());
	}
	
	@Test
	public void updateTestOk() throws Exception {
		when(repository.findById(1L)).thenReturn(Optional.of(getHeroeMock()));
		when(repository.save(any())).thenReturn(getHeroeMockForUpdate());
		mockMvc.perform(put("/heroes")
		  .header("Authorization", getTokenMock())
		  .contentType(MediaType.APPLICATION_JSON).content("{\"id\":1,\"name\":\"Batman\"}"))
	      .andDo(print())
		  .andExpect(jsonPath("$.name").value("Batman"))
	      .andExpect(status().isOk());
	}
	
	@Test
	public void deleteTestOk() throws Exception {
		when(repository.findById(1L)).thenReturn(Optional.of(getHeroeMock()));
		mockMvc.perform(delete("/heroes/1")
		  .header("Authorization", getTokenMock()))
		  .andDo(print())
		  .andExpect(status().isOk());
	}
	
    @Test
    public void findAllTestOK() throws Exception {    	
       when(repository.findAll()).thenReturn(Lists.newArrayList(getHeroeMock()));
       mockMvc.perform(get("/heroes")
    	 .header("Authorization", getTokenMock()))
         .andDo(print())
         .andExpect(jsonPath("$.length()").value(1))
         .andExpect(status().isOk());
    }
    
    @Test
    public void findByIdTestOK() throws Exception {    	
       when(repository.findById(1L)).thenReturn(Optional.of(getHeroeMock()));
       mockMvc.perform(get("/heroes/1")
    	 .header("Authorization", getTokenMock()))
         .andDo(print())
         .andExpect(jsonPath("$.name").value("Superman"))
         .andExpect(status().isOk());
    }
    
    @Test
    public void findByIdTestNotFound() throws Exception {    	
       when(repository.findById(1L)).thenThrow(new NotFoundException("No se encontro el super heroe con id 1"));
       mockMvc.perform(get("/heroes/1")
    	 .header("Authorization", getTokenMock()))
         .andDo(print())
         .andExpect(jsonPath("$.message").value("No se encontro el super heroe con id 1"))
         .andExpect(status().isNotFound());
    }
    
    @Test
    public void findByNameTestOK() throws Exception {    	
       when(repository.findByNameContainingIgnoreCase(any())).thenReturn(Lists.newArrayList(getHeroeMock()));
       mockMvc.perform(get("/heroes/findByName?searchText=su")
    	 .header("Authorization", getTokenMock()))
         .andDo(print())
         .andExpect(jsonPath("$.length()").value(1))
         .andExpect(status().isOk());
    }
	
	private String getTokenMock() {
		String token = jwtProvider.createToken(new UsernamePasswordAuthenticationToken("test", "test", Arrays.asList()));
		return format("Bearer %s", token);
	}
	
    private Heroe getHeroeMock() {
    	Heroe heroe = new Heroe();
    	heroe.setId(1L);
    	heroe.setName("Superman");
    	return heroe;
    }
    
    private Heroe getHeroeMockForUpdate() {
    	Heroe heroe = new Heroe();
    	heroe.setId(1L);
    	heroe.setName("Batman");
    	return heroe;
    }
}
