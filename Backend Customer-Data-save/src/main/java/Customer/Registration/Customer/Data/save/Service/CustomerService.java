package Customer.Registration.Customer.Data.save.Service;

import java.util.Optional;

import Customer.Registration.Customer.Data.save.Configuration.CustomRestTemplate;
import Customer.Registration.Customer.Data.save.Models.Customer;
import Customer.Registration.Customer.Data.save.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerrepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public CustomerService(CustomRestTemplate customRestTemplate) {
        this.restTemplate = customRestTemplate.createRestTemplate();
    }

    public void saveCustomer(Customer customer) {
        customerrepository.save(customer);
    }

    public void updateCustomer(Long id, Customer updatedCustomer) {
        Optional<Customer> existingCustomerOptional = customerrepository.findById(id);
        if (existingCustomerOptional.isPresent()) {
            Customer existingCustomer = existingCustomerOptional.get();
            existingCustomer.setFirstName(updatedCustomer.getFirstName());
            existingCustomer.setLastName(updatedCustomer.getLastName());
            existingCustomer.setStreet(updatedCustomer.getStreet());
            existingCustomer.setAddress(updatedCustomer.getAddress());
            existingCustomer.setCity(updatedCustomer.getCity());
            existingCustomer.setState(updatedCustomer.getState());
            existingCustomer.setPhone(updatedCustomer.getPhone());
            existingCustomer.setEmail(updatedCustomer.getEmail());
            customerrepository.save(existingCustomer);
        } else {
            throw new RuntimeException("Customer with ID " + id + " not found");
        }
    }

    public List<Customer> getCustomerByfirstn(String first_name) {
        return customerrepository.findByfirstName(first_name);
    }

    public List<Customer> getCustomerBylastn(String last_name) {
        return customerrepository.findBylastName(last_name);
    }

    public List<Customer> getCustomerBystreet(String street) {
        return customerrepository.findBystreet(street);
    }

    public List<Customer> getCustomerByaddress(String address) {
        return customerrepository.findByaddress(address);
    }

    public List<Customer> getCustomerBycity(String city) {
        return customerrepository.findBycity(city);
    }

    public List<Customer> getCustomerBystate(String state) {
        return customerrepository.findBystate(state);
    }

    public List<Customer> getCustomerByemail(String email) {
        return customerrepository.findByemail(email);
    }

    public List<Customer> getCustomerByPhone(String phone) {
        return customerrepository.findByphone(phone);
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerrepository.findById(id);
    }

    public void deleteACustomerById(Long id) {
        customerrepository.deleteById(id);
    }

//    public void fetchAndSaveRemoteCustomers() {
//        String authToken = authenticateUser();
//        List<Customer> remoteCustomers = getRemoteCustomerList(authToken);
//        remoteCustomers.forEach(this::saveOrUpdateCustomer);
//    }
//
//    private String authenticateUser() {
//        Map<String, String> loginRequest = new HashMap<>();
//        loginRequest.put("login_id", "test@sunbasedata.com");
//        loginRequest.put("password", "Test@123");
//
//        // Make POST request to authentication API
//        ResponseEntity<Map> response = restTemplate.postForEntity(
//                "https://qa.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp",
//                new HttpEntity<>(loginRequest),
//                Map.class
//        );
//
//        // Extract and return token from the response
//        if (response.getStatusCode() == HttpStatus.OK) {
//            Map<String, String> responseBody = response.getBody();
//            return responseBody.get("access_token");
//        } else {
//            throw new RuntimeException("Authentication failed");
//        }
//    }
//
//    private List<Customer> getRemoteCustomerList(String authToken) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(authToken);
//
//        // Make GET request to get customer list
//        ResponseEntity<Customer[]> response = restTemplate.exchange(
//                "https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list",
//                HttpMethod.GET,
//                new HttpEntity<>(headers),
//                Customer[].class
//        );
//
//        // Convert response body to List<Customer>
//        return Arrays.asList(Objects.requireNonNull(response.getBody()));
//    }

    private void saveOrUpdateCustomer(Customer remoteCustomer) {
        List<Customer> existingCustomer = customerrepository.findByemail(remoteCustomer.getEmail());
        if (!existingCustomer.isEmpty()) {
            Customer localCustomer = existingCustomer.get(0);
            localCustomer.setFirstName(remoteCustomer.getFirstName());
            localCustomer.setLastName(remoteCustomer.getLastName());
            localCustomer.setStreet(remoteCustomer.getStreet());
            localCustomer.setAddress(remoteCustomer.getAddress());
            localCustomer.setCity(remoteCustomer.getCity());
            localCustomer.setState(remoteCustomer.getState());
            localCustomer.setPhone(remoteCustomer.getPhone());
            localCustomer.setEmail(remoteCustomer.getEmail());
            customerrepository.save(localCustomer);

        } else {
            customerrepository.save(remoteCustomer);
        }
    }
}