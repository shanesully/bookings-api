package com.studiomanager.bookingapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.studiomanager.bookingapi.domain.Booking;
import com.studiomanager.bookingapi.domain.FitnessClass;
import com.studiomanager.bookingapi.services.impl.FitnessClassServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookingApiApplicationTests {

	static final String BASE_URI = "http://localhost:";
	static final String BOOKINGS_ENDPOINT = "/bookings";
	static final String SAMPLE_CLASS = "{\"1\":{\"id\":1,\"className\":\"Pilates\",\"startDate\":\"2019-04-04\",\"endDate\":\"2019-04-05\",\"capacity\":9}}";
	static final String SAMPLE_CLASS_DECREMENTED = "{\"1\":{\"id\":1,\"className\":\"Pilates\",\"startDate\":\"2019-04-04\",\"endDate\":\"2019-04-05\",\"capacity\":8}}";

	static ObjectMapper mapper;

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	FitnessClassServiceImpl fitnessClassService;

	static Booking newBooking;

	@Before
	public void setup() {
		Mockito.when(fitnessClassService.getFitnessClasses()).thenReturn(SAMPLE_CLASS);
		returnClassWithCapacityForBooking(1);

		Mockito.when(fitnessClassService.decrementFitnessClassCapacityById(1)).thenReturn(new FitnessClass());

		mapper = new ObjectMapper();
		setObjectMapperUseStringsForDates();
	}

	@Test
	public void testBookingsEndpointGet() {
		ResponseEntity<String> response = this.restTemplate
				.getForEntity(BASE_URI + port + BOOKINGS_ENDPOINT, String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void bookingsEndpointShouldReturnDefaultResult() {
		assertThat(getBookings()).contains("bookingDate");
	}

	@Test
	public void testBookingsEndpointPost() throws Exception {
		newBooking = new Booking();

		newBooking.setId(2);
		newBooking.setClassId(1);
		newBooking.setMemberName("Luke O'Sullivan");
		newBooking.setBookingDate(LocalDate.parse("2019-04-01"));

		String requestJson = mapper.writeValueAsString(newBooking);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Object> request = new HttpEntity<>(requestJson, headers);

		ResponseEntity<String> response = createBooking(request);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getBody()).isEqualTo("Booking Successful");
		assertThat(getBookings()).contains("Luke O'Sullivan");
	}

	@Test
	public void testBookingsEndpointDelete() {
		assertThat(getBookings()).contains("bookingDate");

		String requestJson = "";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Object> request = new HttpEntity<>(requestJson, headers);

		deleteBooking(1, request);

		assertThat(getBookings()).doesNotContain("bookingDate");
	}

	@Test
	public void contextLoads() {
	}

	private void setObjectMapperUseStringsForDates() {
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	}

	private void returnClassWithCapacityForBooking(int id) {
		FitnessClass fitnessClass = new FitnessClass();
		fitnessClass.setCapacity(9);

		Mockito.when(fitnessClassService.getFitnessClassById(id)).thenReturn(fitnessClass);
	}

	private void returnClassWithoutCapacityForBooking(int id) {
		FitnessClass fitnessClass = new FitnessClass();
		fitnessClass.setCapacity(0);

		Mockito.when(fitnessClassService.getFitnessClassById(id)).thenReturn(fitnessClass);
	}

	private ResponseEntity<String> createBooking(HttpEntity<Object> request) {
		return this.restTemplate
				.exchange(BASE_URI + port + BOOKINGS_ENDPOINT, HttpMethod.POST, request, String.class);
	}

	private String getBookings() {
		return this.restTemplate.getForObject(BASE_URI + port + BOOKINGS_ENDPOINT,
				String.class);
	}

	private void deleteBooking(int id, HttpEntity request) {
		this.restTemplate
				.exchange(BASE_URI + port + BOOKINGS_ENDPOINT +"/" + id, HttpMethod.DELETE, request, String.class);
	}
}
