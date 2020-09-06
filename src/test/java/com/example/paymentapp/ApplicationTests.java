package com.example.paymentapp;

import com.example.paymentapp.model.Ticket;
import com.example.paymentapp.model.Status;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

	final static Logger log = Logger.getLogger(ApplicationTests.class.getName());

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void testGetTicketById() {
		Ticket ticket = restTemplate.getForObject(getRootUrl() + "/api/tickets/1", Ticket.class);
		log.info(ticket.getRouteNumber());
		Assert.assertNotNull(ticket);
	}

	@Test
	public void testGetAllTickets(){
		ResponseEntity<List<Ticket>> rateResponse =
				restTemplate.exchange(getRootUrl() + "/api/tickets",
						HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
		List<Ticket> tickets = rateResponse.getBody();
		tickets.forEach(payment -> {
			log.info(payment.toString());
		});
	}

	@Test
	public void testGetAllTicketsAfterCurrentDate(){
		ResponseEntity<List<Ticket>> rateResponse =
				restTemplate.exchange(getRootUrl() + "/api/tickets/afternow",
						HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
		List<Ticket> tickets = rateResponse.getBody();
		tickets.forEach(payment -> {
			log.info(payment.toString());
		});

	}

	@Test
	public void testCreateTicket() {
		Ticket ticket = new Ticket();
		ticket.setDepartureDate(LocalDate.of(2020, 11, 21));
		ticket.setRouteNumber(232);
		ticket.setStatus(Status.COMPLETED);
		ticket.setClientId(23L);
		ticket.setTicketId(33345L);

		ResponseEntity<Ticket> postResponse = restTemplate.postForEntity(getRootUrl() + "/tickets", ticket, Ticket.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
	}
}
