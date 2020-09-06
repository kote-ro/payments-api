package com.example.paymentapp;

import com.example.paymentapp.model.Payment;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

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
	public void testGetPaymentById() {
		Payment payment = restTemplate.getForObject(getRootUrl() + "/payments/1", Payment.class);
		log.info(payment.getRouteNumber());
		Assert.assertNotNull(payment);
	}

	@Test
	public void testCreatePayment() {
		Payment payment = new Payment();
		payment.setDepartureDate(LocalDate.of(2020, 11, 21));
		payment.setRouteNumber(232);

		ResponseEntity<Payment> postResponse = restTemplate.postForEntity(getRootUrl() + "/payments", payment, Payment.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
	}
}
