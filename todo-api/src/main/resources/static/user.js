const addTaskButton = document.getElementById("addUserButton");
const userList = document.getElementById("userList");

function salvarDados() {
    const name = document.getElementById("name").value.trim();
    const role = document.getElementById("role").value.trim();
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("cpf").value.trim();

    if (!name || !role || !email || !password) {
        alert("Preencha todos os campos!");
        return;
    }

    const dataUser = { name, role , email, password};
    console.log(dataUser);

    fetch("http://localhost:8080/api/user/save", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(dataUser)
    })
        .then(response => {
            if (!response.ok) throw new Error("Erro ao salvar o usuário");
            return response.json();
        })
        .then(savedUser => {
            const userItem = document.createElement("a");
            userItem.className = "user-item";
            userItem.href = `userDetails.html?id=${savedUser.id}`;
            userItem.textContent = `${savedUser.name}`;

            userList.appendChild(userItem);

            document.getElementById("name").value = "";
            document.getElementById("role").value = "";
            document.getElementById("email").value = "";
            document.getElementById("cpf").value = "";

            alert("Usuário adicionado com sucesso!");
        })
        .catch(error => {
            console.error("Erro:", error.message);
            alert("Ocorreu um erro ao salvar Usuário. Tente novamente.");
        });
}

    function carregarUsuarios() {
        fetch("http://localhost:8080/api/user/findAll")
            .then(response => response.json())
            .then(users => {
                userList.innerHTML = "";

                users.forEach(user => {
                    const userItem = document.createElement("a");
                    userItem.className = "user-item";
                    userItem.href = `userDetails.html?id=${user.id}`;
                    userItem.textContent = `${user.name}`;

                    userList.appendChild(userItem);
                });
            })
            .catch(error => {
                console.error("Erro ao carregar os usuários:", error);
                alert("Erro ao carregar a lista de usuários.");
            });
    }


carregarUsuarios();

addTaskButton.addEventListener("click", salvarDados);
