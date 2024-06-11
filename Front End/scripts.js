document.addEventListener('DOMContentLoaded', function() {
    
    const loginEndpoint = 'http://localhost:8080/customers/login';
    const customerEndpoint = 'http://localhost:8080/customers';


    const loginForm = document.getElementById('loginForm');
    const customerPage = document.getElementById('customerPage');
    const addCustomerButton = document.getElementById('addCustomerButton');
    const customerFormModal = document.getElementById('customerFormModal');
    const closeButton = document.getElementsByClassName('close')[0];


    loginForm.addEventListener('submit', async function(event) {
        event.preventDefault();

        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        try {
            const response = await fetch(loginEndpoint, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ username, password })
            });

            if (response.ok) {
                window.location.href = 'home.html';
            } else {
                const errorText = await response.text();
                console.error('Login failed:', errorText);
                alert('Login failed. Please check your credentials.');
            }
        } catch (error) {
            console.error('An error occurred:', error);
            alert('An error occurred. Please try again later.');
        }
    });


    addCustomerButton.addEventListener('click', function() {
        customerFormModal.style.display = 'block';
    });


    closeButton.addEventListener('click', function() {
        customerFormModal.style.display = 'none';
    });


    window.addEventListener('click', function(event) {
        if (event.target == customerFormModal) {
            customerFormModal.style.display = 'none';
        }
    });


    function fetchData(url) {

    }

    function postData(url, data) {

    }


    fetchData(customerEndpoint);
});