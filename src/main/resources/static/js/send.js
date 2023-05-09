function submitData(event) {

    const name = document.getElementById('name').value;
    const email = document.getElementById('email').value;

    console.log('hi');

    const data = {
        name: name,
        email: email
    };

    fetch('/address', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => {
    if (!response.ok) {
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

function viewPassword() {
    const btn = document.getElementById('showPass');

    let password = document.getElementById('password');
    btn.addEventListener('click', (event) => {
        if(password.type == 'text') {
            password.type = 'password';
        } else {
            password.type = 'text';
        }

        event.preventDefault();
    });
}

const form = document.getElementById('form');
form.addEventListener('submit', submitData);

viewPassword();