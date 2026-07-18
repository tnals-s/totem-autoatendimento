package modelo;

import java.util.ArrayList;

public class Pedido {
    // Atributos privados
    private ArrayList<ItemMenu> itensSelecionados;
    private double valorTotal;

    // Construtor: Inicializa a lista e o total zerado
    public Pedido() {
        this.itensSelecionados = new ArrayList<>();
        this.valorTotal = 0.0;
    }

    // Método para adicionar um item ao pedido
    public void adicionarItem(ItemMenu item) {
        this.itensSelecionados.add(item);
        this.valorTotal += item.getPreco(); // Acumula o valor do item no total
    }

    // Método para remover um item (caso o cliente desista)
    public void removerItem(ItemMenu item) {
        // Se o item realmente estiver na lista, removemos e subtraímos o valor
        if (this.itensSelecionados.remove(item)) {
            this.valorTotal -= item.getPreco();
        }
    }

    // Método para limpar o carrinho (usado ao cancelar ou finalizar)
    public void limparPedido() {
        this.itensSelecionados.clear();
        this.valorTotal = 0.0;
    }

    // --- GETTERS ---
    // (Note que não precisamos de 'setters' aqui, pois controlamos a lista e o total pelos métodos acima)

    public ArrayList<ItemMenu> getItensSelecionados() {
        return itensSelecionados;
    }

    public double getValorTotal() {
        return valorTotal;
    }
}