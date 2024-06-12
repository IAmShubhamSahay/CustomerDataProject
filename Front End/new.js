<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

class Customer {
    constructor(id, firstName, lastName, street, address, city, state, email, phone) {
        this.id = id;
        this.first_name = firstName;
        this.last_name = lastName;
        this.street = street;
        this.address = address;
        this.city = city;
        this.state = state;
        this.email = email;
        this.phone = phone;
    }
}


const baseUrl = "http://localhost:8080/customer/";


function saveCustomer(customer) {
    $.ajax({
        url: baseUrl + "addcustomer",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(customer),
        success: function(response) {
            console.log("Customer saved successfully:", response);
        },
        error: function(xhr, status, error) {
            console.error("Error saving customer:", error);
        }
    });
}


function searchByFirstName(firstName) {
    $.ajax({
        url: baseUrl + "searchbyfname",
        type: "GET",
        data: {
            first_name: firstName
        },
        success: function(response) {
            console.log("Customers found by first name:", response);
        },
        error: function(xhr, status, error) {
            console.error("Error searching for customers by first name:", error);
        }
    });
}


function fetchAndSaveRemoteCustomers() {
    $.ajax({
        url: baseUrl + "fetch-remote-customers",
        type: "GET",
        success: function(response) {
            console.log("Remote customers fetched and saved successfully:", response);
        },
        error: function(xhr, status, error) {
            console.error("Error fetching and saving remote customers:", error);
        }
    });
}