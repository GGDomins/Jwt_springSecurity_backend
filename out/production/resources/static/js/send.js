function submitData(event) {

    const name = document.getElementById('name').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const passwordCheck = document.getElementById('password-check').value;

    //console.log('check');

    const data = {
        name: name,
        email: email,
        password: password
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
    // .catch(error => {
    //     console.error('fetch operation problem:', error);
    // });

    event.preventDefault();
}

function validateEmail(event) {
    const email = document.getElementById('email').value;
    const message = document.getElementById('email-message');
    const password = document.getElementById('password');
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    let isValid = regex.test(email);

    if(!isValid) {
        message.textContent = "올바른 이메일 형식이 아닙니다.";
        message.style.color = "#ff5c5c";
    } else {
        message.textContent = "올바른 이메일 형식입니다.";
        message.style.color = "#008f0a";
    }

    event.preventDefault();
}

function checkPassword(event) {
    const password = document.getElementById('password').value;
    const passwordCheck = document.getElementById('password-check').value;
    const message = document.getElementById('pwd-message');

    let isEqual = password == passwordCheck ? true : false;

    if(!isEqual) {
        message.textContent = "비밀번호가 일치하지 않습니다.";
        message.style.color = "#ff5c5c";
    } else {
        message.textContent = "비밀번호가 일치합니다.";
        message.style.color = "#008f0a";
    }

    event.preventDefault();
}

function viewPassword() {
    const btn = document.getElementById('show-pass');

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

document.getElementById('email').addEventListener('keyup', validateEmail);
document.getElementById('password-check').addEventListener('keyup', checkPassword);

viewPassword();