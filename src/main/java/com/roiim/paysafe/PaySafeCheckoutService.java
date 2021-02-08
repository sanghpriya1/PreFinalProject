package com.roiim.paysafe;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roiim.paysafe.CustomerRepository;
import com.roiim.paysafe.customer.Customer;
import com.roiim.paysafe.customer.CustomerDOB;
import com.roiim.paysafe.dto.CreateNewPaySafeCustomerRequestDTO;
import com.roiim.paysafe.dto.CreateNewPaySafeCustomerResponseDTO;
import com.roiim.paysafe.dto.MakePaymentRequestDTO;
import com.roiim.paysafe.dto.MakePaymentResponseDTO;
import com.roiim.paysafe.dto.SingleUseCustomerTokenRequestDTO;
import com.roiim.paysafe.dto.SingleUseCustomerTokenResponseDTO;


@Service
public class PaySafeCheckoutService {
	private Random rd;
	private RestTemplate restTemplate;
	private CustomerRepository customerRepository;
	
	
	public PaySafeCheckoutService() {
		
	}
	@Autowired
	public PaySafeCheckoutService(CustomerRepository customerRepository, RestTemplate restTemplate) {
		
		this.customerRepository = customerRepository;
		this.restTemplate = restTemplate;
		rd = new Random();
	}
	public SingleUseCustomerTokenResponseDTO creatSingleUserCustomerToken( String emailId ){
		
	        Customer customer = null;

	        customer = customerRepository.findByEmail( emailId );

	       
	        if( customer == null ){

	            
	            customer = createCustomer( emailId );

	            
	            if( customer == null ){

	                return null;
	            }
	        }
	     
	        String url = "https://api.test.paysafe.com/paymenthub/v1/customers/" + customer.getPaysafeId() + "/singleusecustomertokens" ;

	         HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

	        headers.setBasicAuth( "private-7751", "B-qa2-0-5f031cdd-0-302d0214496be84732a01f690268d3b8eb72e5b8ccf94e2202150085913117f2e1a8531505ee8ccfc8e98df3cf1748" );

	         SingleUseCustomerTokenRequestDTO singleUseCustomerTokenRequestDTO = new SingleUseCustomerTokenRequestDTO();
	        singleUseCustomerTokenRequestDTO.setMerchantRefNum( String.valueOf( rd.nextInt() ) );
	        singleUseCustomerTokenRequestDTO.setPaymentTypes( Arrays.asList( "CARD" ) );

	         ObjectMapper objectMapper = new ObjectMapper();
	        String jsonString = null;
	        try {

	            jsonString = objectMapper.writeValueAsString( singleUseCustomerTokenRequestDTO );
	        }
	        catch (JsonMappingException e) {
	            e.printStackTrace();
	        }
	        catch (JsonGenerationException e) {
	            e.printStackTrace();
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }

	        // build the request
	        HttpEntity< String > entity = new HttpEntity<>( jsonString, headers );

	        // send POST request
	        ResponseEntity< SingleUseCustomerTokenResponseDTO > responseEntity = restTemplate.postForEntity( url, entity, SingleUseCustomerTokenResponseDTO.class );

	        if( responseEntity.getStatusCode().equals( HttpStatus.CREATED ) ) {


	            SingleUseCustomerTokenResponseDTO singleUseCustomerTokenResponseDTO = responseEntity.getBody();
	            singleUseCustomerTokenResponseDTO.setMerchantRefNum(singleUseCustomerTokenRequestDTO.getMerchantRefNum());
	            System.out.println(responseEntity.getBody().toString());
	            
	            return singleUseCustomerTokenResponseDTO;
	        }

	        return null;

	        
			
	 } 
	private Customer createCustomer(String emailId) {
		
        String url = "https://api.test.paysafe.com/paymenthub/v1/customers";
        		

           HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

         headers.setBasicAuth( "private-7751", "B-qa2-0-5f031cdd-0-302d0214496be84732a01f690268d3b8eb72e5b8ccf94e2202150085913117f2e1a8531505ee8ccfc8e98df3cf1748" );

         CreateNewPaySafeCustomerRequestDTO createNewCustomerRequestDTO = new CreateNewPaySafeCustomerRequestDTO();
        createNewCustomerRequestDTO.setMerchantCustomerId( String.valueOf( rd.nextInt() ) );
        createNewCustomerRequestDTO.setLocale( "en_US" );
        createNewCustomerRequestDTO.setFirstName( "abc" );
        createNewCustomerRequestDTO.setMiddleName( "pqr" );
        createNewCustomerRequestDTO.setLastName( "xyz" );
        CustomerDOB dob = new CustomerDOB( 2, 3, 1998 );
        createNewCustomerRequestDTO.setDateOfBirth( dob );
        createNewCustomerRequestDTO.setEmail( emailId );
        createNewCustomerRequestDTO.setCellPhone( "9056482124" );
        createNewCustomerRequestDTO.setGender( "M" );
        createNewCustomerRequestDTO.setNationality( "Canadian" );
        createNewCustomerRequestDTO.setPhone( "777-444-8888" );
        createNewCustomerRequestDTO.setIp( "192.0.126.111" );

        // convert request object in to json object
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = null;
        try {

            jsonString = objectMapper.writeValueAsString(createNewCustomerRequestDTO);
        }
        catch (JsonMappingException e) {
            e.printStackTrace();
        }
        catch (JsonGenerationException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println( jsonString );

         HttpEntity< String > entity = new HttpEntity< String >( jsonString, headers );

         ResponseEntity< CreateNewPaySafeCustomerResponseDTO > responseEntity = restTemplate.postForEntity( url, entity, CreateNewPaySafeCustomerResponseDTO.class );

         if( responseEntity.getStatusCode() == HttpStatus.CREATED ){

             CreateNewPaySafeCustomerResponseDTO response = responseEntity.getBody();

              Customer newCustomer = new Customer();
            newCustomer.setPaysafeId( response.getId() );
            newCustomer.setEmail( response.getEmail() );

            customerRepository.save( newCustomer );
            return newCustomer;
        }
        else {

            System.out.println( "failed user creation" );

            Customer c = null;
            return c;
        }


	}
	public MakePaymentResponseDTO makePayment( MakePaymentRequestDTO makePaymentRequestDTO ){

        System.out.println( "In make payment" );

         String url = "https://api.test.paysafe.com/paymenthub/v1/payments";

         HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

          headers.setBasicAuth( "private-7751", "B-qa2-0-5f031cdd-0-302d0214496be84732a01f690268d3b8eb72e5b8ccf94e2202150085913117f2e1a8531505ee8ccfc8e98df3cf1748" );

        makePaymentRequestDTO.setCustomerIp( "10.10.12.64" );
        makePaymentRequestDTO.setMerchantRefNum( String.valueOf( rd.nextInt() ) );

         ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = null;
        try {

            jsonString = objectMapper.writeValueAsString( makePaymentRequestDTO );
        }
        catch (JsonMappingException e) {
            e.printStackTrace();
        }
        catch (JsonGenerationException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

         HttpEntity< String > entity = new HttpEntity<>( jsonString, headers );

        ResponseEntity<MakePaymentResponseDTO> responseEntity = restTemplate.postForEntity( url, entity, MakePaymentResponseDTO.class );

        if( responseEntity.getStatusCode() != HttpStatus.CREATED ){

            // throw an exception
        }

        return responseEntity.getBody();
    }

}


