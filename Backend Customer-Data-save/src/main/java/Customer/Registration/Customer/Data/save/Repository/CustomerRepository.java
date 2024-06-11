package Customer.Registration.Customer.Data.save.Repository;

import Customer.Registration.Customer.Data.save.Models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long > {


    List<Customer> findByfirstn(String first_name);
    List<Customer> findBylastn(String last_name);
    List<Customer> findBystreet(String street);
    List<Customer> findByaddress(String address);
    List<Customer> findBycity(String city);
    List<Customer> findBystate(String state);
    List<Customer> findByemail(String email);
    List<Customer> findByphone(String phone);

}
