
window.addToCart = function(productId) {
    const product = allProducts.find(p => p.id === productId);
    if (!product) return;

    const existing = cart.find(item => item.id === productId);
    if (existing) {
        existing.quantity++;
    } else {
        cart.push({ ...product, quantity: 1 });
    }

    saveCartAndRefresh();
    toggleCart(true);
};

window.removeFromCart = function(id) {
    cart = cart.filter(item => item.id !== id);
    saveCartAndRefresh();
};

function saveCartAndRefresh() {
    localStorage.setItem('lynx_cart', JSON.stringify(cart));
    updateCartUI();
}


//att carrinho
function updateCartUI() {
    const list = document.getElementById('cart-items-list');
    const totalDisplay = document.getElementById('total-price-display');
    if (!list) return;

    if (cart.length === 0) {
        list.innerHTML = '<p class="empty-cart">Seu carrinho está vazio.</p>';
        totalDisplay.textContent = "Total: R$ 0,00";
        return;
    }

    let totalCents = 0;
    list.innerHTML = cart.map(item => {
        totalCents += item.priceCents * item.quantity;
        return `

            <div class="cart-item" style="padding: 10px; border-bottom: 1px solid #eee; display: flex; justify-content: space-between; align-items: center;">
                <div>
                    <p><strong>${item.name}</strong></p>
<!--                    pra converter de centavos pra real -->
                    <small>${item.quantity}x R$ ${(item.priceCents / 100).toFixed(2)}</small>
                </div>
                <button onclick="removeFromCart(${item.id})" style="color:red; background:none; border:none; cursor:pointer;"><i class="fa-solid fa-trash"></i></button>
            </div>
        `;

    }).join('');

    // tbm converter pra real
    totalDisplay.textContent = `Total: R$ ${(totalCents / 100).toFixed(2)}`;
}


function toggleCart(isOpen) {
    const sidebar = document.getElementById("cart-sidebar");
    const overlay = document.getElementById("cart-overlay");
    if (isOpen) {
        sidebar?.classList.add("active");
        overlay?.classList.add("active");
    } else {
        sidebar?.classList.remove("active");
        overlay?.classList.remove("active");
    }
}
