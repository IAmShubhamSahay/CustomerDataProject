package Customer.Registration.Customer.Data.save.Controller;

import Customer.Registration.Customer.Data.save.Models.Customer;
import Customer.Registration.Customer.Data.save.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;
    @PostMapping("addcustomer")
    public void addCustomer(@RequestBody Customer customer){
        customerService.saveCustomer(customer);
    }

    @PutMapping("updatecustomer")
    public void updateCustomer(@RequestParam Long id,@RequestBody Customer customer){
        customerService.updateCustomer(id, customer);
    }

    @GetMapping("searchbyfname")
    public List<Customer> SearchByFirstName(@RequestParam String first_name){
        return customerService.getCustomerByfirstn(first_name);
    }

    @GetMapping("searchbylname")
    public List<Customer> SearchByLastName(@RequestParam String last_name){
        return customerService.getCustomerBylastn(last_name);
    }

    @GetMapping("searchbystreet")
    public List<Customer> SearchByStreet(@RequestParam String street){
        return customerService.getCustomerBystreet(street);
    }

    @GetMapping("searchbyaddress")
    public List<Customer> SearchByAddress(@RequestParam String address){
        return customerService.getCustomerByaddress(address);
    }

    @GetMapping("searchbycity")
    public List<Customer> SearchByCity(@RequestParam String city){
        return customerService.getCustomerBycity(city);
    }

    @GetMapping("searchbystate")
    public List<Customer> SearchByState(@RequestParam String state){
        return customerService.getCustomerBystate(state);
    }

    @GetMapping("searchbyemail")
    public List<Customer> SearchByEmail(@RequestParam String email){
        return customerService.getCustomerByemail(email);
    }

    @GetMapping("searchbyphone")
    public List<Customer> SearchByPhone(@RequestParam String phone){
        return customerService.getCustomerByPhone(phone);
    }

    @GetMapping("getsinglecustomer")
    public Optional<Customer> getSingleCustomerById(@RequestParam Long id){
        return customerService.getCustomerById(id);
    }

    @DeleteMapping("deletebyid")
    public void deleteCustomerById(@RequestParam Long id){
        customerService.deleteACustomerById(id);
    }

    @GetMapping("fetch-remote-customers")
    public void fetchAndSaveRemoteCustomers(){
        customerService.fetchAndSaveRemoteCustomers();
    }
}
