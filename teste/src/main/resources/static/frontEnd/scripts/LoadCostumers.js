
async function loadCustomers() {
    const select = document.getElementById('customer-select');
    if (!select) return;

    try {
        const response = await fetch(`${API_URL}/customers`);
        const data = await response.json();
        const customers = data.content || data;

        let optionsHTML = '<option value="">-- Selecione o Cliente --</option>';
        if (Array.isArray(customers)) {
            customers.forEach(c => {
                optionsHTML += `<option value="${c.id}">${c.name}</option>`;
            });
            select.innerHTML = optionsHTML;
        }
    } catch (err) {
        console.error("Erro ao carregar clientes:", err);
    }
}