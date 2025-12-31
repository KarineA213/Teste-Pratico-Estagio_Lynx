async function loadProducts() {
    try {
        const response = await fetch(`${API_URL}/products`);
        const data = await response.json();
        allProducts = data.content || data;
        renderProducts(allProducts);
    } catch (err) {
        console.error("Erro ao carregar produtos:", err);
    }
}


//filtros de produtos


function filterAndRenderProducts() {
    const searchTerm = document.getElementById("search-input")?.value.toLowerCase() || "";
    const selectedCategory = document.getElementById("category-filter")?.value || "all";
    const onlyActive = document.getElementById("active-toggle")?.checked || false;

    const filtered = allProducts.filter(product => {
        const matchesSearch = product.name.toLowerCase().includes(searchTerm);
        const matchesCategory = selectedCategory === "all" || product.category === selectedCategory;
        const matchesStatus = !onlyActive || product.active === true;
        return matchesSearch && matchesCategory && matchesStatus;
    });

    renderProducts(filtered);
}

function renderProducts(list) {
    const container = document.querySelector(".grid-container");
    if (!container) return;

    if (list.length === 0) {
        container.innerHTML = "<p style='grid-column: 1/-1; text-align: center;'>Nenhum produto encontrado.</p>";
        return;
    }

    container.innerHTML = list.map(product => `
        <div class="product-card" style="background: #ffff00; border: 3px solid #000; padding: 15px; box-shadow: 5px 5px 0px #000;">
            <h3>${product.name}</h3>
            <p>Categoria: ${product.category}</p>
            <p>Preço: R$ ${(product.priceCents / 100).toFixed(2)}</p>
            <button class="add-button" onclick="addToCart(${product.id})" style="width: 100%; cursor: pointer;">Adicionar ao carrinho</button>
        </div>
    `).join('');
}


document.getElementById("search-button")?.addEventListener("click", filterAndRenderProducts);
document.getElementById("search-input")?.addEventListener("keypress", (e) => {
    if (e.key === 'Enter') filterAndRenderProducts();
});
document.getElementById("category-filter")?.addEventListener("change", filterAndRenderProducts);
document.getElementById("active-toggle")?.addEventListener("change", filterAndRenderProducts);
