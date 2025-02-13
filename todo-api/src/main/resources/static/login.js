document.addEventListener("DOMContentLoaded", function () {
    const loginBtn = document.getElementById("loginBtn");
    const emailInput = document.getElementById("email");
    const passwordInput = document.getElementById("password");

    loginBtn.addEventListener("click", function(){
        const emailValue = emailInput.value.trim();
        const passwordValue = passwordInput.value.trim();

        const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

        if(emailValue === "" || passwordValue === ""){
            alert("Por favor, preencha todos os campos.");
        }else if(!emailPattern.test(emailValue)){
            alert("Por favor, insira um e-mail válido.");
        }else if(passwordValue.length < 8){
            alert("A senha deve ter pelo menos 8 caracteres.");
        }else{
            window.location.href = "main.html";
        }
    });
});
