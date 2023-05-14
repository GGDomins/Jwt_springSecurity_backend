const form = document.getElementById('form');

const nameInput = document.getElementById('name');
const emailInput = document.getElementById('email');
const passwordInput = document.getElementById('password');
const passwordCheckInput = document.getElementById('password-check');

let emailMessage = document.getElementById('email-message');
let pwdMessage = document.getElementById('pwd-message');
let pwdCheckMessage = document.getElementById('pwdcheck-message');

let passwordValid = false;
let emailValid = false;

function submitData(event) {

    const name = nameInput.value;
    const email = emailInput.value;
    const password = passwordInput.value;
    const passwordCheck = passwordCheckInput.value;

    console.log([name, email, password, passwordCheck]);

    if (name && emailValid && passwordValid) {
        const data = {
            name: name,
            email: email,
            password: password
        };
    
        fetch('/api/signup', {
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
    } else {
        if (!name) {
            alert("이름을 입력해주세요.");
            document.getElementById('name').focus();
        } else if (name && !emailValid) {
            alert("이메일을 형식에 맞게 입력해주세요.");
            document.getElementById('email').focus();
        } else if (name && emailValid && !passwordValid) {
            alert("비밀번호를 다시 입력해주세요.");
            document.getElementById('password').focus();
        }
    }

    //console.log('check');

    event.preventDefault();
}

function validateEmail(event) {
    const email = emailInput.value;
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!email) {
        emailMessage.textContent = "";
        emailValid = false;
        return;
    }

    let isValid = regex.test(email);

    if(!isValid) {
        emailMessage.textContent = "올바른 이메일 형식이 아닙니다.";
        emailMessage.style.color = "#ff5c5c";
        emailValid = false;
    } else {
        emailMessage.textContent = "올바른 이메일 형식입니다.";
        emailMessage.style.color = "#008f0a";
        emailValid = true;
    }

    event.preventDefault();
}

function checkPassword(event) {
    const password = passwordInput.value;
    const passwordCheck = passwordCheckInput.value;

    if (!password && !passwordCheck) {
        pwdCheckMessage.textContent = "";
        passwordValid = false;
        return;
    }

    let isEqual = password == passwordCheck ? true : false;

    if(isEqual) {
        pwdCheckMessage.textContent = "비밀번호가 일치합니다.";
        pwdCheckMessage.style.color = "#008f0a";
        passwordValid = true;
    } else {
        pwdCheckMessage.textContent = "비밀번호가 일치하지 않습니다.";
        pwdCheckMessage.style.color = "#ff5c5c";
        passwordValid = false;
    }

    // if(!isEqual && ((!password && passwordCheck) || (password && !passwordCheck))) {
    //     message.textContent = "비밀번호가 일치하지 않습니다.";
    //     message.style.color = "#ff5c5c";
    //     passwordValid = false;
    // } else {
    //     message.textContent = "비밀번호가 일치합니다.";
    //     message.style.color = "#008f0a";
    //     passwordValid = true;
    // }
    
    event.preventDefault();
}

function viewPassword() {
    const btn = document.getElementById('show-pass');

    btn.addEventListener('click', (event) => {
        if(passwordInput.type == 'text') {
            passwordInput.type = 'password';
        } else {
            passwordInput.type = 'text';
        }

        event.preventDefault();
    });
}

function measureStrength(event) {
    const password = passwordInput.value;
    const passwordLength = password.length;
    const span = document.createElement('span');

    if(!password) {
        pwdMessage.textContent = "";
        return;
    }

    pwdMessage.textContent = `보안 수준: `;

    let score = 0;

    //check password length
    if (passwordLength < 8) {
        span.textContent = '위험';
        span.style.color = '#ff5c5c';

        pwdMessage.appendChild(span);
        
        return;
    } else if (passwordLength >= 8 && passwordLength < 12) {
        score += 1;
    } else {
        score += 2;
    }

    //check mix of characters
    if (password.match(/[a-z]/) && password.match(/[A-Z]/)) {
        score += 1;
    }

    if (password.match(/[\d+]/)) {
        score += 1;
    }

    if (password.match(/[!@#$%^&*?_~-]/)) {
        score += 1;
    }

    if (score < 2) {
        //strength = `<font color="#ff5c5c">위험</font>`;
        span.textContent = '위험';
        span.style.color = '#ff5c5c';
    } else if (score < 5) {
        //strength = `<font color="#c99402">중간</font>`;
        span.textContent = '중간';
        span.style.color = '#dba100';
    } else {
        //strength = `<font color="#008f0a">안전</font>`;
        span.textContent = '안전';
        span.style.color = '#008f0a';
    }

    pwdMessage.appendChild(span);

    event.preventDefault();
}


form.addEventListener('submit', submitData);
emailInput.addEventListener('keyup', validateEmail);
passwordCheckInput.addEventListener('keyup', checkPassword);
passwordInput.addEventListener('keyup', checkPassword);
passwordInput.addEventListener('keyup', measureStrength);

viewPassword();