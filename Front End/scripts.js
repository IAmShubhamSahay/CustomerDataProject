document.addEventListener('DOMContentLoaded', function() {
    // backend API endpoints
    const loginEndpoint = 'http://localhost:8080/customers/login';
    const customerEndpoint = 'http://localhost:8080/customers';

    // DOM elements
    const loginForm = document.getElementById('loginForm');
    const customerPage = document.getElementById('customerPage');
    const addCustomerButton = document.getElementById('addCustomerButton');
    const customerFormModal = document.getElementById('customerFormModal');
    const closeButton = document.getElementsByClassName('close')[0];

    // Login form submission
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

    // Add customer button click event
    addCustomerButton.addEventListener('click', function() {
        customerFormModal.style.display = 'block';
    });

    // Close the modal when the close button is clicked
    closeButton.addEventListener('click', function() {
        customerFormModal.style.display = 'none';
    });

    // Close the modal when user clicks anywhere outside the modal
    window.addEventListener('click', function(event) {
        if (event.target == customerFormModal) {
            customerFormModal.style.display = 'none';
        }
    });

    // AJAX/fetch requests to interact with backend endpoints
    function fetchData(url) {
        // Implement logic to fetch data from the backend using AJAX/fetch
    }

    function postData(url, data) {
        // Implement logic to post data to the backend using AJAX/fetch
    }

    // Fetch initial customer data
    fetchData(customerEndpoint);
});