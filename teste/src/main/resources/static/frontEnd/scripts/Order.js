
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
    if (!ordersContainer) {
        console.warn("Aviso: Elemento lista de pedidos não encontrada.");
        return;
    }

    try {
        const response = await fetch(`${API_URL}/orders`);

        if (!response.ok) throw new Error("Erro ao se conectar a API de pedidos");

        const data = await response.json();

        //precisei alterar pra dar suporte a lista e pageable pq n tava aceitando pageable
        const orders = data.content || data;

        //precisa limpar o container pra n duplicar
        ordersContainer.innerHTML = "";

        // if (!orders || orders.length === 0) {
        //     ordersContainer.innerHTML = "<p style='text-align:center; padding:20px;'>Nenhum pedido encontrado no histórico.</p>";
        //     return;
        // }

        //do maior pro menor
        const sortedOrders = [...orders].sort((a, b) => b.id - a.id);


        ordersContainer.innerHTML = sortedOrders.map(order => {


            const rawItems = order.items || order.orderItems || [];

            let itemsHTML = "";

            if (rawItems.length > 0) {
                itemsHTML = rawItems.map(item => {

                    //tenta pegar o nome de qualquer jeito
                    const nome = item.productName || item.name || (item.product ? item.product.name : "Produto");
                    return `
                        <div style="display: flex; justify-content: space-between; padding: 5px 0; border-bottom: 1px dashed #000; font-size: 0.9em;">
                            <span> <strong>${nome}</strong></span>
                            <span>Qtd: ${item.quantity}</span>
                        </div>`;
                }).join('');
            } else {
                itemsHTML = `<div style="color: #666; font-style: italic;">Sem itens detalhados neste pedido.</div>`;
            }

            // --- TEMPLATE DO CARD ---
            return `
                <div class="order-card-final" style="background: #ffff00; border: 3px solid #000; margin-bottom: 25px; padding: 20px; box-shadow: 6px 6px 0px #000; color: #000; font-family: 'Courier New', Courier, monospace;">
                    <div style="display: flex; justify-content: space-between; align-items: center; border-bottom: 2px solid #000; padding-bottom: 10px; margin-bottom: 10px;">
                        <h3 style="margin: 0;">#PEDIDO ${order.id}</h3>
                        <span style="background: #000; color: #fff; padding: 3px 10px; font-weight: bold; font-size: 0.8em; text-transform: uppercase;">
                            ${order.status || 'NEW'}
                        </span>
                    </div>
                    
                    <p style="margin: 5px 0;"><strong> Data:</strong> ${new Date(order.createdAt || Date.now()).toLocaleString('pt-PT')}</p>
                    
                    <div style="margin: 15px 0; background: rgba(0,0,0,0.03); padding: 10px; border: 1px solid #000;">
                        <h4 style="margin: 0 0 10px 0; font-size: 1em; text-transform: uppercase; border-bottom: 1px solid #000;">Itens do Pedido:</h4>
                        ${itemsHTML}
                    </div>

                    <div style="text-align: right; font-size: 1.3em; border-top: 2px solid #000; padding-top: 10px; margin-top: 10px;">
                        <strong>TOTAL: R$ ${(order.totalAmount || 0).toFixed(2)}</strong>
                    </div>
                </div>
            `;
        }).join('');


    } catch (err) {
        console.error("Erro ao carregar pedidos:", err);
        // ordersContainer.innerHTML = `<p style="color:red; text-align:center;">Erro ao carregar pedidos. Verifique o console.</p>`;
    }
}