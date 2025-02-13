document.addEventListener("DOMContentLoaded", function(){
    const signUpBtn = document.getElementById("signUpBtn");
    const emailInput = document.getElementById("email");
    const passwordInput = document.getElementById("password");
    const confirmPasswordInput = document.getElementById("confirmPassword");

    signUpBtn.addEventListener("click", function(){
        const emailValue = emailInput.value.trim();
        const passwordValue = passwordInput.value.trim();
        const confirmPasswordValue = confirmPasswordInput.value.trim();

        const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

        if(emailValue === "" || passwordValue === "" || confirmPasswordValue === ""){
            alert("Por favor, preencha todos os campos.");
        }else if(!emailPattern.test(emailValue)){
            alert("Por favor, insira um e-mail válido.");
        }else if(passwordValue.length < 8 || confirmPasswordValue.length < 8){
            alert("A senha deve ter pelo menos 8 caracteres.");
        }else if(passwordValue != confirmPasswordValue){
            alert("As senhas devem ser iguais");
        }else{
            alert("Conta criada com sucesso!")
            window.location.href = "login.html";
        }
    });
});