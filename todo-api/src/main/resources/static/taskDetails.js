document.addEventListener("DOMContentLoaded", function () {
    const taskId = new URLSearchParams(window.location.search).get('id');
    const creatorName = document.getElementById("creatorName");
    const creatorRole = document.getElementById("creatorRole");
    const description = document.getElementById("description")
    const creationDate = document.getElementById("creationDate");
    const endDate = document.getElementById("endDate");
    const status = document.getElementById("status");

    const editButton = document.getElementById("editTask");
    const saveButton = document.getElementById("saveTask");

    const commentForm = document.getElementById("commentForm");
    const commentList = document.getElementById("commentList");

    if (!taskId) {
        alert("ID da tarefa não fornecido!");
        return;
    }

    fetch(`http://3.80.237.22:8080/api/card/findById?id=${taskId}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        },
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Erro ao buscar tarefa");
        }
        return response.json();
    })
    .then(task => {
        creatorName.textContent = task.users?.[0]?.name || "Sem criador";
        creatorRole.textContent = task.users?.[0]?.role || "Sem Função";
        description.textContent = task.description || "N/A"
        creationDate.textContent = task.creationDate || "N/A";
        endDate.textContent = task.endDate || "Não definida";
        status.textContent = task.status || "N/A";

        renderComments(task.comments || []);
    })
    .catch(error => {
        console.error("Erro:", error);
        alert("Não foi possível carregar os dados da tarefa.");
    });

    function renderComments(comments) {
        commentList.innerHTML = "";
        comments.forEach(comment => {
            const commentElement = document.createElement("div");
            commentElement.classList.add("comment-item");
            commentElement.innerHTML = `
                <p><strong>${comment.user?.name || "Desconhecido"} (${comment.user?.role || "N/A"}):</strong></p>
                <p>${comment.content || "Sem conteúdo"}</p>
            `;
            commentList.appendChild(commentElement);
        });
    }
    

    document.getElementById("editTask").addEventListener("click", function () {
        description.innerHTML = `<input type="text" id="editDescription" value="${description.textContent}">`;
        status.innerHTML = `
            <select id="editStatus">
                <option value="OPEN" ${status.textContent === "OPEN" ? "selected" : ""}>OPEN</option>
                <option value="DONE" ${status.textContent === "DONE" ? "selected" : ""}>DONE</option>
            </select>
        `;
    
        editButton.style.display = "none";
        saveButton.style.display = "inline-block";
    });
    

    document.getElementById("saveTask").addEventListener("click", function () {
        const updatedTask = {
            id: taskId, 
            description: document.getElementById("editDescription").value.trim(),
            status: document.getElementById("editStatus").value,
        };
    
        fetch("http://3.80.237.22:8080/api/card/update", {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(updatedTask),
        })
            .then(response => {
                if (!response.ok) throw new Error("Erro ao atualizar tarefa");
                alert("Tarefa atualizada com sucesso!");
                window.location.href = "task.html";
            })
            .catch(error => {
                console.error("Erro:", error);
                alert("Não foi possível atualizar a tarefa.");
            });
    });

    document.getElementById("deleteTask").addEventListener("click", function () {
        if (confirm("Tem certeza que deseja excluir esta tarefa?")) {
            fetch(`http://3.80.237.22:8080/api/card/delete?id=${taskId}`, {
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json",
                },
            })
                .then(response => {
                    if (!response.ok) throw new Error("Erro ao excluir tarefa");
                    alert("Tarefa excluída com sucesso!");
                    window.location.href = "task.html";
                })
                .catch(error => {
                    console.error("Erro:", error);
                    alert("Não foi possível excluir a tarefa.");
                });
        }
    });

    document.getElementById("commentForm").addEventListener("submit", function (e) {
        e.preventDefault();
    
        const userId = document.getElementById("userId").value.trim();
        const commentText = document.getElementById("commentInput").value.trim();
    
        if (!userId || !commentText) {
            alert("Preencha todos os campos!");
            return;
        }
    
        fetch(`http://3.80.237.22:8080/api/user/findById?id=${userId}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            },
        })
        .then(response => {
            if (!response.ok) throw new Error("Erro ao buscar usuário");
            return response.json();
        })
        .then(user => {
            return fetch(`http://3.80.237.22:8080/api/comment/addComment?cardId=${taskId}&userId=${user.id}`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(commentText), 
            });
        })
        .then(response => {
            if (!response.ok) throw new Error("Erro ao salvar comentário");
            return response.json();
        })
        .then(savedComment => {
            const commentElement = document.createElement("div");
            commentElement.classList.add("comment");
            commentElement.innerHTML = `
                <p><strong>${savedComment.user.name} (${savedComment.user.role}):</strong> ${savedComment.content}</p>
            `;
            document.getElementById("commentList").appendChild(commentElement);
    
            alert("Comentário adicionado com sucesso!");
            document.getElementById("commentForm").reset();
        })
        .catch(error => {
            console.error("Erro:", error);
            alert("Não foi possível adicionar o comentário.");
        });
    });
    
});
