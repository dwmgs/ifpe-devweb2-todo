const commentForm = document.getElementById("commentForm");
const commentList = document.getElementById("commentList");

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

document.getElementById("commentForm").addEventListener("submit", function (e) {
    e.preventDefault();

    const userId = document.getElementById("userId").value.trim();
    const commentText = document.getElementById("commentInput").value.trim();

    if (!userId || !commentText) {
        alert("Preencha todos os campos!");
        return;
    }

    fetch(`http://34.201.166.146:8080/api/user/findById?id=${userId}`, {
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
        return fetch(`http://34.201.166.146:8080/api/comment/addComment?cardId=${taskId}&userId=${user.id}`, {
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