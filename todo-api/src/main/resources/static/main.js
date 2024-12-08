const taskForm = document.getElementById("taskForm");
const addTaskButton = document.getElementById("addTaskButton");
const taskList = document.getElementById("taskList");

function salvarDados() {
    const name = document.getElementById("name").value.trim();
    const role = document.getElementById("role").value.trim();
    const description = document.getElementById("taskName").value.trim();

    if (!name || !role || !description) {
        alert("Preencha todos os campos!");
        return;
    }

    const dataUser = { name, role };

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
            const dataTask = {
                description: description,
                status: "BACKLOG",
                users: [savedUser]
            };

            return fetch("http://localhost:8080/api/card/save", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(dataTask)
            });
        })
        .then(response => {
            if (!response.ok) throw new Error("Erro ao salvar o card");
            return response.json();
        })
        .then(savedTask => {
            const taskItem = document.createElement("a");
            taskItem.className = "task-item";
            taskItem.href = "#";
            taskItem.textContent = `${savedTask.description}`;

            taskList.appendChild(taskItem);

            document.getElementById("name").value = "";
            document.getElementById("role").value = "";
            document.getElementById("taskName").value = "";

            alert("Usuário e tarefa adicionados com sucesso!");
        })
        .catch(error => {
            console.error("Erro:", error.message);
            alert("Ocorreu um erro ao salvar os dados. Tente novamente.");
        });
}

addTaskButton.addEventListener("click", salvarDados);
