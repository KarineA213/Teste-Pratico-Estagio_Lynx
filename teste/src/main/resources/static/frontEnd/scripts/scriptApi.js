
//config api
const API_URL = "http://localhost:8080";
let allProducts = [];
let cart = JSON.parse(localStorage.getItem('lynx_cart')) || [];

// INICIALIZAÇÃO ÚNICA
document.addEventListener("DOMContentLoaded", () => {
    loadInitialData();
    setupUIEvents();
    updateCartUI();
});


//carregar os dados do seeding
async function loadInitialData() {
    try {
        await Promise.all([loadProducts(), loadCustomers(), loadOrders()]);
    } catch (error) {
        console.error("Erro no carregamento inicial:", error);
    }
}




//UI basicos pedidos
function setupUIEvents() {


    // sidebar
    document.getElementById("cart-icon")?.addEventListener("click", () => toggleCart(true));
    document.getElementById("close-cart")?.addEventListener("click", () => toggleCart(false));
    document.getElementById("cart-overlay")?.addEventListener("click", () => toggleCart(false));

    // botao de limpar carrinho
    document.getElementById("clear-cart")?.addEventListener("click", () => {
        cart = [];
        saveCartAndRefresh();
    });

    //botao de fechar o pedido
    const checkoutBtn = document.querySelector('.checkout-button');
    if (checkoutBtn) {
        checkoutBtn.onclick = finalizarPedido;
    }

    // menu hamburguer
    const hamburguer = document.querySelector(".hamburguer");
    const navList = document.querySelector(".nav-list");
    hamburguer?.addEventListener("click", () => {
        navList?.classList.toggle("active");
        hamburguer?.classList.toggle("active");
    });
}