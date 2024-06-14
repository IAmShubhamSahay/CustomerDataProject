package Customer.Registration.Customer.Data.save.Service;

import Customer.Registration.Customer.Data.save.Models.Customer;
import Customer.Registration.Customer.Data.save.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class ExternalApiCall {
    private final String tokenUrl = "https://qa.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";
    private final String customerListApi = "https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list";

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    public ExternalApiCall(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // fetching token and data from sunbase url
    public Object[] getTokenFromApi() {

        // Setting the login and password
        String requestBody = "{ \"login_id\": \"test@sunbasedata.com\", \"password\": \"Test@123\" }";

        // Calling the function to recieve token
        String token = Apicall(tokenUrl, requestBody);
        System.out.println(token);
        // storing the token
        String acessToken = token.substring(19, token.length() - 3);

        // fetching data from sunbase db by getCustomer function
        List<Object> customers = getCustomers(acessToken, customerListApi);

//        saveCustomers(customers);

        //Return the received data as an array
        Object[] customerReceived = customers.toArray();
        return customerReceived;
    }

    public String Apicall(String apiUrl, String requestBody) {

        //RestTemplate and httpHeader helps to consume the external api
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        //setting content type of header
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                String.class);

        String responseBody = responseEntity.getBody();
        return responseBody;
    }

    public List<Object> getCustomers(String token, String apiurl) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Object[]> responseEntity = restTemplate.exchange(
                apiurl,
                HttpMethod.GET,
                requestEntity,
                Object[].class);

        Object[] responseBody = responseEntity.getBody();
        saveCustomers(List.of(responseBody));
        return List.of(responseBody);
    }


//    private void saveOrUpdateCustomer(Customer remoteCustomer) {
//        List<Customer> existingCustomer = customerrepository.findByemail(remoteCustomer.getEmail());
//        if (!existingCustomer.isEmpty()) {
//            Customer localCustomer = existingCustomer.get(0);
//            localCustomer.setFirstn(remoteCustomer.getFirstn());
//            localCustomer.setLastn(remoteCustomer.getLastn());
//            localCustomer.setStreet(remoteCustomer.getStreet());
//            localCustomer.setAddress(remoteCustomer.getAddress());
//            localCustomer.setCity(remoteCustomer.getCity());
//            localCustomer.setState(remoteCustomer.getState());
//            localCustomer.setPhone(remoteCustomer.getPhone());
//            localCustomer.setEmail(remoteCustomer.getEmail());
//            customerrepository.save(localCustomer);
//
//        } else {
//            customerrepository.save(remoteCustomer);
//        }
//    }
//    private void saveCustomers(List<Object> customers){
//        for (Object customerObj : customers){
//            Customer customer = createCustomerFromObject(customerObj);
//            String excustomer = customer.getEmail();
//            if (!(customerRepository.findByemail(excustomer)==null)){
//                customerRepository.save(customer);
//            }
//            Customer mycustomer = customerRepository.findByemail(customer.getEmail())
//            for (Customer customer1 : customerRepository.findByemail(customer.getEmail())){
//                continue;
//            }
//            customerRepository.save(customer);


//            if (customer.getEmail() == null || customer.getEmail().isEmpty()) {
//                System.out.println("Skipping customer with null or empty email.");
//                continue; // Skip saving this customer
//            }
//
//            List<Customer> existingCustomers = customerRepository.findByemail(customer.getEmail());
//            if (existingCustomers.isEmpty()) {
//                customerRepository.save(customer);
//                System.out.println("Saved customer with email " + customer.getEmail());
//            } else {
//                Customer existingCustomer = existingCustomers.get(0); // Assuming you handle only the first found
//                System.out.println("Customer with email " + customer.getEmail() + " already exists.");
//                // Optionally, you can update existingCustomer if needed
//                // existingCustomer.updateFrom(customer);
//                // customerRepository.save(existingCustomer);
//            }
//        }
//
//    }



//    private Customer createCustomerFromObject(Object customerObj){
//        Customer customer = new Customer();
//        return customer;
//    }


    private void saveCustomers(List<Object> customers) {
        for (Object customerObj : customers) {
            Customer customer = createCustomerFromObject(customerObj);
            String email = customer.getEmail();
            if (email != null && !email.isEmpty()) {
                List<Customer> existingCustomer = customerRepository.findByemail(email);
                if (existingCustomer.isEmpty()) {
                    customerRepository.save(customer);
                } else {
                    // Optionally, update the existing customer
                    if(!existingCustomer.isEmpty()){
                        Customer existingCustomers = existingCustomer.get(0);
                        updateExistingCustomer(existingCustomers, customer);
                        customerRepository.save(existingCustomers);
                    }

                }
            }
        }
    }

    private Customer createCustomerFromObject(Object customerObj) {
        Map<String, Object> customerMap = (Map<String, Object>) customerObj;
        Customer customer = new Customer();
        customer.setUuid((String) customerMap.get("uuid"));
        customer.setFirstName((String) customerMap.get("first_name"));
        customer.setLastName((String) customerMap.get("last_name"));
        customer.setStreet((String) customerMap.get("street"));
        customer.setAddress((String) customerMap.get("address"));
        customer.setCity((String) customerMap.get("city"));
        customer.setState((String) customerMap.get("state"));
        customer.setEmail((String) customerMap.get("email"));
        customer.setPhone((String) customerMap.get("phone"));
        return customer;
    }

    private void updateExistingCustomer(Customer existingCustomer, Customer newCustomer) {
        existingCustomer.setFirstName(newCustomer.getFirstName());
        existingCustomer.setLastName(newCustomer.getLastName());
        existingCustomer.setStreet(newCustomer.getStreet());
        existingCustomer.setAddress(newCustomer.getAddress());
        existingCustomer.setCity(newCustomer.getCity());
        existingCustomer.setState(newCustomer.getState());
        existingCustomer.setPhone(newCustomer.getPhone());
        existingCustomer.setEmail(newCustomer.getEmail());
    }





}
