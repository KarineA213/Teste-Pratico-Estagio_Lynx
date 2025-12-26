
async function pegarDados() {
    try {
        const response = await fetch("http://localhost:8080/products");
        const data = await response.json();
        console.log(data); // Veja a estrutura no console
    } catch (error) {
        console.error("Erro:", error);
    }
}

pegarDados();

fetch("http://localhost:8080/products")
    .then(res => res.json())
    .then(data => {
        // 'data.content' é o array que vem do teu ProductsControllers
        const produtos = data.content; 

        // O front "mapeia" os dados para HTML
        let htmlGerado = "";
        produtos.forEach(p => {
            htmlGerado += `
                <div class="product-card">
                    <h3>${p.name}</h3>
                    <p>Preço: R$ ${p.priceCents / 100}</p>
                </div>
            `;
        });

        // O front coloca o conteúdo na tela
        document.getElementById("products").innerHTML = htmlGerado;
    })
    .catch(err => {
        // O front trata o erro (ex: servidor desligado)
        alert("Não foi possível carregar os produtos!");
    });