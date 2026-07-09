package visual;

import javax.swing.*;
import modelo.ItemMenu;
import modelo.Pedido;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaTotem extends JFrame {
    // Gerenciador do pedido atual
    private Pedido pedidoAtual;
    
    // Componentes da Interface Gráfica (GUI)
    private JTextArea txtCarrinho;
    private JLabel lblTotal;
    private JButton btnHamburguer, btnBatata, btnRefri;
    private JButton btnFinalizar, btnCancelar;

    public TelaTotem() {
        // Inicializa o motor do pedido
        this.pedidoAtual = new Pedido();
        
        // Configurações básicas da Janela (JFrame)
        setTitle("Totem de Autoatendimento - Fast Food");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a tela
        setLayout(new BorderLayout(10, 10)); // Organiza em Norte, Sul, Leste, Oeste e Centro

        // 1. TÍTULO DA TELA (Norte)
        JLabel lblTitulo = new JLabel("Bem-vindo! Faça seu pedido abaixo", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblTitulo, BorderLayout.NORTH);

        // 2. PAINEL DO CARDÁPIO (Oeste/Esquerda)
        JPanel painelCardapio = new JPanel();
        painelCardapio.setLayout(new GridLayout(3, 1, 5, 5)); // 3 linhas, 1 coluna
        painelCardapio.setBorder(BorderFactory.createTitledBorder("Cardápio"));

        btnHamburguer = new JButton("Hambúrguer - R$ 25,00");
        btnBatata = new JButton("Batata Frita - R$ 12,00");
        btnRefri = new JButton("Refrigerante - R$ 8,00");

        painelCardapio.add(btnHamburguer);
        painelCardapio.add(btnBatata);
        painelCardapio.add(btnRefri);
        add(painelCardapio, BorderLayout.WEST);

        // 3. PAINEL DO CARRINHO (Centro)
        JPanel painelCarrinho = new JPanel();
        painelCarrinho.setLayout(new BorderLayout(5, 5));
        painelCarrinho.setBorder(BorderFactory.createTitledBorder("Seu Pedido"));

        txtCarrinho = new JTextArea();
        txtCarrinho.setEditable(false); // Usuário não pode digitar no carrinho
        JScrollPane scrollCarrinho = new JScrollPane(txtCarrinho); // Barra de rolagem se a lista crescer
        
        lblTotal = new JLabel("Total: R$ 0,00", SwingConstants.RIGHT);
        lblTotal.setFont(new Font("Arial", Font.BOLD, 14));

        painelCarrinho.add(scrollCarrinho, BorderLayout.CENTER);
        painelCarrinho.add(lblTotal, BorderLayout.SOUTH);
        add(painelCarrinho, BorderLayout.CENTER);

        // 4. PAINEL DE AÇÕES (Sul/Baixo)
        JPanel painelAcoes = new JPanel();
        painelAcoes.setLayout(new FlowLayout(FlowLayout.RIGHT));

        btnCancelar = new JButton("Cancelar Pedido");
        btnFinalizar = new JButton("Finalizar e Pagar");
        
        painelAcoes.add(btnCancelar);
        painelAcoes.add(btnFinalizar);
        add(painelAcoes, BorderLayout.SOUTH);

        // Configurar as ações dos botões (Eventos)
        configurarEventos();
    }

    private void configurarEventos() {
        // Ação do botão Hambúrguer
        btnHamburguer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ItemMenu hamburguer = new ItemMenu(1, "Hambúrguer", 25.00);
                pedidoAtual.adicionarItem(hamburguer);
                atualizarInterface();
            }
        });

        // Ação do botão Batata
        btnBatata.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ItemMenu batata = new ItemMenu(2, "Batata Frita", 12.00);
                pedidoAtual.adicionarItem(batata);
                atualizarInterface();
            }
        });

        // Ação do botão Refrigerante
        btnRefri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ItemMenu refri = new ItemMenu(3, "Refrigerante", 8.00);
                pedidoAtual.adicionarItem(refri);
                atualizarInterface();
            }
        });

        // Botão Cancelar
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pedidoAtual.limparPedido();
                atualizarInterface();
                JOptionPane.showMessageDialog(TelaTotem.this, "Pedido cancelado com sucesso!");
            }
        });

        // Botão Finalizar (Aqui entra a Condicional Obrigatória do Professor!)
        btnFinalizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // ESTRUTURA CONDICIONAL OBRIGATÓRIA: Validar se o carrinho está vazio
                if (pedidoAtual.getItensSelecionados().isEmpty()) {
                    JOptionPane.showMessageDialog(TelaTotem.this, 
                            "Erro: Não é possível finalizar um pedido vazio!", 
                            "Aviso", 
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    // Simulação simples de sucesso
                    JOptionPane.showMessageDialog(TelaTotem.this, 
                            "Pedido finalizado!\nValor Total: R$ " + String.format("%.2f", pedidoAtual.getValorTotal()) + "\nObrigado pela preferência!");
                    pedidoAtual.limparPedido();
                    atualizarInterface();
                }
            }
        });
    }

    // ESTRUTURA DE REPETIÇÃO OBRIGATÓRIA: Varre a lista para atualizar o texto da tela
    private void atualizarInterface() {
        txtCarrinho.setText(""); // Limpa o texto antigo
        
        // Laço foreach rodando a coleção dinâmica (ArrayList)
        for (ItemMenu item : pedidoAtual.getItensSelecionados()) {
            txtCarrinho.append(item.getNome() + " - R$ " + String.format("%.2f", item.getPreco()) + "\n");
        }
        
        // Atualiza o JLabel do valor total
        lblTotal.setText("Total: R$ " + String.format("%.2f", pedidoAtual.getValorTotal()));
    }

    // Método main para rodar o projeto
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TelaTotem().setVisible(true);
            }
        });
    }
}