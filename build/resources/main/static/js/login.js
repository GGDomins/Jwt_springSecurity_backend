const form = document.getElementById('form');
const emailInput = document.getElementById('email');
const passwordInput = document.getElementById('password');

function loginRequest(event) {
    const email = emailInput.value;
    const password = passwordInput.value;

    const data = {
        email: email,
        password: password
    };

    fetch('api/signup', {
        method: 'POST',
        headers: {
            'Content-type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if(!response.ok) {
                throw new Error('Network response error');
            }
            return response.json();
        })
        .then(data => {
            console.log(data);
        })
        .catch(error => {
            console.error('fetch operation problem:', error);
        });

    event.preventDefault();
}

form.addEventListener('submit', loginRequest);