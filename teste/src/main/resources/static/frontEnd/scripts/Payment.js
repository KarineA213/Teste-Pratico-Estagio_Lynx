

async function processarPagamento(orderId, method) {
    const totalCents = cart.reduce((acc, item) => acc + (item.priceCents * item.quantity), 0);
    const paymentPayload = {
        method: method,
        amountCents: totalCents,
        orderId: orderId
    };

    try {
        const response = await fetch(`${API_URL}/orders/${orderId}/payments`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(paymentPayload)
        });

        if (response.ok) {
            alert(` Pedido #${orderId} finalizado.`);


            //limpar o carrinho
            cart = [];
            localStorage.removeItem('lynx_cart');
            updateCartUI();
            toggleCart(false); // faz o fechamento a barra lateral
            loadOrders(); // Aatualiza os pedidos
        } else {
            alert("Pedido criado, mas o pagamento falhou no servidor.");
        }
    } catch (err) {
        console.error("Erro no pagamento:", err);
    }
}