const addTaskButton = document.getElementById("addTaskButton");
const userList = document.getElementById("taskList");

function salvarDados() {
    const description = document.getElementById("description").value.trim();
    const idUser = document.getElementById("idUser").value.trim();

    if (!description || !idUser) {
        alert("Preencha todos os campos!");
        return;
    }

    fetch(`http://3.88.187.94:8080/api/user/findById?id=${idUser}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        },
    })
        .then(response => {
            if (!response.ok) throw new Error("Erro ao Buscar usuário");
            return response.json();
        })
        .then(savedUser => {
            const dataTask = {
                description: description,
                status: "OPEN",
                users: [savedUser]
            };

            return fetch("http://3.88.187.94:8080/api/card/save", {
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
            taskItem.href = `taskDetails.html?id=${savedTask.id}`;
            taskItem.textContent = `${savedTask.description}`;

            taskList.appendChild(taskItem);

            document.getElementById("description").value = "";
            document.getElementById("idUser").value = "";

            alert("Task adicionada com sucesso!");
        })
        .catch(error => {
            console.error("Erro:", error.message);
            alert("Ocorreu um erro ao salvar Task. Tente novamente.");
        });
}

function carregarTasks() {
    fetch("http://3.88.187.94:8080/api/card/findAll")
        .then(response => response.json())
        .then(tasks => {
            taskList.innerHTML = "";

            tasks.forEach(task => {
            const taskItem = document.createElement("a");
            taskItem.className = "task-item";
            taskItem.href =  `taskDetails.html?id=${task.id}`;
            taskItem.textContent = `${task.description}`;

            taskList.appendChild(taskItem);
            });
        })
        .catch(error => {
            console.error("Erro ao carregar as tasks:", error);
            alert("Erro ao carregar a lista de tasks.");
        });
}


carregarTasks();

addTaskButton.addEventListener("click", salvarDados);