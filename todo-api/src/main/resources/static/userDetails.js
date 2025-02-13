document.addEventListener("DOMContentLoaded", function() {
    const userId = new URLSearchParams(window.location.search).get('id');
    const userIdElement = document.getElementById("userId");
    const nameElement = document.getElementById("name");
    const roleElement = document.getElementById("role");
    const emailElement = document.getElementById("email");
    const cpfElement = document.getElementById("cpf");

    const editButton = document.getElementById("editUser");
    const saveButton = document.getElementById("saveUser");

    if (!userId) {
        alert("ID do usuário não fornecido!");
        return;
    }

    fetch(`http://localhost:8080/api/user/findById?id=${userId}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        },
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Erro ao buscar usuário");
        }
        return response.json();
    })
    .then(user => {
        userIdElement.textContent = user.id;
        nameElement.textContent = user.name;
        roleElement.textContent = user.role;
        emailElement.textContent = user.email;
        cpfElement.textContent = user.cpf;
    })
    .catch(error => {
        console.error("Erro:", error);
        alert("Não foi possível carregar os dados do usuário.");
    });

    document.getElementById("editUser").addEventListener("click", function() {
        nameElement.innerHTML = `<input type="text" id="editName" value="${nameElement.textContent}">`;
        roleElement.innerHTML = `<input type="text" id="editRole" value="${roleElement.textContent}">`;
        emailElement.innerHTML = `<input type="text" id="editEmail" value="${emailElement.textContent}">`;
        cpfElement.innerHTML = `<input type="text" id="editCpf" value="${cpfElement.textContent}">`;

        editButton.style.display = "none";
        saveButton.style.display = "inline-block";
    });

    document.getElementById("saveUser").addEventListener("click", function() {
        const updateUser = {
            id: userId,
            name: document.getElementById("editName").value.trim(),
            role: document.getElementById("editRole").value.trim(),
            email: document.getElementById("editEmail").value.trim(),
            cpf: document.getElementById("editCpf").value.trim()
        };

        fetch("http://localhost:8080/api/user/update", {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(updateUser)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Erro ao atualizar usuário");
            }
            alert("Usuário atualizado com sucesso!");
            window.location.href = "user.html";
        })
        .catch(error => {
            console.error("Erro:", error);
            alert("Não foi possível atualizar o usuário.");
        });
    });

    document.getElementById("deleteUser").addEventListener("click", function() {
        if (confirm("Tem certeza que deseja excluir este usuário?")) {
            fetch(`http://localhost:8080/api/user/delete?id=${userId}`, {
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json"
                },
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Erro ao excluir usuário");
                }
                alert("Usuário excluído com sucesso!");
                window.location.href = "user.html";
            })
            .catch(error => {
                console.error("Erro:", error);
                alert("Não foi possível excluir o usuário.");
            });
        }
    });
});
