
// Arquivo: Order.js
// Arquivo: static/frontEnd/scripts/Order.js

async function finalizarPedido() {
    const customerId = document.getElementById('customer-select').value;
    const paymentMethod = document.getElementById('payment-method')?.value || 'PIX';
    const amountInput = document.getElementById('payment-amount'); // Campo opcional para valor parcial

    if (!customerId) return alert("Por favor, selecione um cliente!");
    if (cart.length === 0) return alert("O carrinho está vazio!");

    // Calcula o total do carrinho em centavos
    const totalCartCents = cart.reduce((acc, item) => acc + (item.priceCents * item.quantity), 0);

    // Define valor a pagar (se o input estiver vazio, paga o total)
    let amountToPayCents = (amountInput && amountInput.value > 0)
        ? Math.round(parseFloat(amountInput.value) * 100)
        : totalCartCents;

    const orderPayload = {
        customerId: parseInt(customerId),
        items: cart.map(item => ({ productId: item.id, quantity: item.quantity }))
    };

    try {
        // PASSO 1: Criar o Pedido [cite: 114]
        const orderRes = await fetch(`${API_URL}/orders`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(orderPayload)
        });

        if (!orderRes.ok) throw new Error("Falha ao criar pedido.");
        const orderCreated = await orderRes.json();

        // Exibe o número do pedido conforme exigido
        alert(`Pedido #${orderCreated.id} gerado com sucesso!`);

        // PASSO 2: Registrar o Pagamento
        const payRes = await fetch(`${API_URL}/orders/${orderCreated.id}/payments`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                method: paymentMethod,
                amountCents: amountToPayCents,
                orderId: orderCreated.id
            })
        });

        if (payRes.ok) {
            alert(`Pagamento de R$ ${(amountToPayCents/100).toFixed(2)} processado!`);

            // Limpeza e atualização da interface
            cart = [];
            localStorage.removeItem('lynx_cart');
            if (amountInput) amountInput.value = "";
            updateCartUI();
            toggleCart(false);
            loadOrders(); // Recarrega a lista para mostrar o novo status (PAID ou ON_WAIT)
        }

    } catch (err) {
        console.error("Erro no checkout:", err);
        alert("Erro ao finalizar: " + err.message);
    }
}

//carregar os pedidos
async function loadOrders() {
    const ordersContainer = document.getElementById('orders-list');
    if (!ordersContainer) return;

    try {
        const response = await fetch(`${API_URL}/orders`);
        if (!response.ok) throw new Error("Erro ao carregar histórico");

        const data = await response.json();
        const orders = data.content || data;

        if (!orders || orders.length === 0) {
            ordersContainer.innerHTML = "<p style='text-align:center;'>Nenhum pedido encontrado.</p>";
            return;
        }

        //do maior pro menor
        const sortedOrders = [...orders].sort((a, b) => b.id - a.id);

        ordersContainer.innerHTML = sortedOrders.map(order => `
            <div class="order-card-final" 
                 onclick="showOrderDetails(${order.id})" 
                 style="background: #ffff00; border: 3px solid #000; margin-bottom: 20px; padding: 15px; cursor: pointer; box-shadow: 4px 4px 0px #000;">
                <div class="order-card" style="display: flex; justify-content: space-between;">
                    <strong>#PEDIDO ${order.id}</strong>
                    <span class="span-orders" style="background: #000; color: #fff; padding: 2px 8px; font-size: 0.8em;">${order.status}</span>
                </div>
                <p class="p-orders-cards" style="margin: 10px 0 5px 0;">Data: ${new Date(order.createdAt).toLocaleString('pt-PT')}</p>
                <p><strong>Total: R$ ${(order.totalAmount || 0).toFixed(2)}</strong></p>
                <small style="color: #555;">(Clique para ver detalhes)</small>
                <div id="details-${order.id}" class="order-details-container" style="display:none; margin-top: 15px; border-top: 1px solid #000; padding-top: 10px;">
                    </div>
            </div>
        `).join('');

    } catch (err) {
        console.error("Erro ao carregar pedidos:", err);
    }
}

    //todo: precisa de um detalhe ao clicar, obrigatorio
    //detalhe ao clicar
async function showOrderDetails(orderId) {
    const detailsDiv = document.getElementById(`details-${orderId}`);

    // Se já estiver aberto, fecha
    if (detailsDiv.style.display === 'block') {
        detailsDiv.style.display = 'none';
        return;
    }

    try {
        const response = await fetch(`${API_URL}/orders/${orderId}`);
        if (!response.ok) throw new Error("Erro ao buscar detalhes");

        const order = await response.json();
        const items = order.items || [];

        let itemsHTML = "<h4>Itens do Pedido:</h4>";
        itemsHTML += items.map(item => `
            <div style="display: flex; justify-content: space-between; font-size: 0.9em; margin-bottom: 5px;">
                <span>${item.productName || 'Produto'} (x${item.quantity})</span>
                <span>R$ ${(item.unitPriceCents / 100).toFixed(2)}/un</span>
            </div>
        `).join('');

        detailsDiv.innerHTML = itemsHTML;
        detailsDiv.style.display = 'block';

    } catch (err) {
        console.error("Erro ao detalhar pedido:", err);
        alert("Não foi possível carregar os detalhes do pedido.");
    }
}