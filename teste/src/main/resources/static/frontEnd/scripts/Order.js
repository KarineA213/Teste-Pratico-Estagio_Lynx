
async function finalizarPedido() {
    const customerId = document.getElementById('customer-select').value;
    const paymentMethod = document.getElementById('payment-method')?.value || 'PIX';

    if (!customerId) return alert("Por favor, selecione um cliente!");
    if (cart.length === 0) return alert("O carrinho está vazio!");

    const orderPayload = {
        customerId: parseInt(customerId),
        items: cart.map(item => ({ productId: item.id, quantity: item.quantity }))
    };

    try {
        //tenta criar o pedido
        const response = await fetch(`${API_URL}/orders`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(orderPayload)
        });

        if (response.ok) {
            const orderCreated = await response.json();

            // PASSO 2: tenta receber os dados de pagamento
            const totalCents = cart.reduce((acc, item) => acc + (item.priceCents * item.quantity), 0);
            const payRes = await fetch(`${API_URL}/orders/${orderCreated.id}/payments`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ method: paymentMethod, amountCents: totalCents, orderId: orderCreated.id })
            });

            if (payRes.ok) {
                alert(`Pedido #${orderCreated.id} finalizado com sucesso!`);

                //limpar o carrinho
                cart = [];
                localStorage.removeItem('lynx_cart');
                updateCartUI();
                toggleCart(false);


            }
        }
    } catch (err) {
        console.error("Erro na finalização:", err);
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
                <div style="display: flex; justify-content: space-between;">
                    <strong>#PEDIDO ${order.id}</strong>
                    <span style="background: #000; color: #fff; padding: 2px 8px; font-size: 0.8em;">${order.status}</span>
                </div>
                <p style="margin: 10px 0 5px 0;">Data: ${new Date(order.createdAt).toLocaleString('pt-PT')}</p>
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