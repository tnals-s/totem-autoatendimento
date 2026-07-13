import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.ArrayList;

public class TotemRestaurante extends JFrame {

    // Gerenciador de telas (O truque do "Baralho de Cartas")
    private CardLayout cardLayout;
    private JPanel painelPrincipal;

    // Lógica do Carrinho: Uma lista simples para guardar os preços dos itens guardados
    private ArrayList<Double> carrinhoPrecos = new ArrayList<>();
    private JLabel lblTotalCarrinho;
    private JLabel lblResumoFinal;

    public TotemRestaurante() {
        // Configurações básicas da janela (JFrame)
        setTitle("Totem de Autoatendimento");
        setSize(500, 800); // Formato vertical que lembra um totem de verdade
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicializando o CardLayout
        cardLayout = new CardLayout();
        painelPrincipal = new JPanel(cardLayout);

        // Criando as 3 telas básicas do fluxo
        criarTela1Inicio();
        criarTela2Cardapio();
        criarTela3Pagamento();

        // Adiciona o painel principal na janela
        add(painelPrincipal);
    }

    // --- TELA 1: INÍCIO ---
    private void criarTela1Inicio() {
    	// 1. Criar um painel customizado para desenhar a imagem de fundo
        JPanel telaInicio = new JPanel() {
            private Image imagemFundo = new ImageIcon(getClass().getResource("/imagem/telaInicio.png")).getImage();
            
         // Precisamos desse método para o Swing desenhar a imagem na tela
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagemFundo != null) {
                    g.drawImage(imagemFundo, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        
        // 2. Os comandos de configuração ficam aqui FORA das chaves do JPanel
        telaInicio.setLayout(null);

        JButton btnIniciar = new JButton();

        // tornando botão invisível
        btnIniciar.setOpaque(false);          // Não preenche o fundo do botão
        btnIniciar.setContentAreaFilled(false); // Não desenha a área de conteúdo (o "corpo" do botão)
        btnIniciar.setBorderPainted(false);   // Não desenha a borda do botão
        btnIniciar.setFocusPainted(false);    // Não desenha o retângulo de foco (aquele pontilhado)
        
        btnIniciar.setBounds(100, 480, 300, 100); 

        // 4. Configurar a ação do botão (a mesma de antes)
        btnIniciar.addActionListener(e -> {
            cardLayout.show(painelPrincipal, "Cardapio");
        });

        // 5. Opcional: Mudar o cursor do mouse para a "mãozinha" ao passar sobre o botão
        btnIniciar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        telaInicio.add(btnIniciar);
        painelPrincipal.add(telaInicio, "Inicio");
    }

    // --- TELA 2: CARDÁPIO ---
    private void criarTela2Cardapio() {
    	JPanel telaCardapio = new JPanel() {
            private Image imagemFundoCardapio = new ImageIcon(getClass().getResource("/imagem/telaCardapio.png")).getImage();
            
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagemFundoCardapio != null) {
                    g.drawImage(imagemFundoCardapio, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        
        // usar o BorderLayout para dividir o layout em norte, sul, centro, leste e oeste
        telaCardapio.setLayout(new BorderLayout());

        // Topo da tela, painel transparente para não ter nada por cima e "empurrar" os itens
        JPanel espacoTopo = new JPanel();
        espacoTopo.setOpaque(false);
        espacoTopo.setPreferredSize(new Dimension(450,225));
        telaCardapio.add(espacoTopo, BorderLayout.NORTH);

        // Centro: Lista de itens usando GridLayout (linhas, colunas)
        JPanel painelItens = new JPanel(new GridLayout(3, 1, 10, 15));
        painelItens.setOpaque(false); // deixar transparente para ver o fundo bege
        painelItens.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Criando botões simulando os itens do cardápio
        JButton btnHamburguer = new JButton("Combo OAK - R$ 35,00");
        JButton btnBatata = new JButton("Batata Frita - R$ 12,00");
        JButton btnRefrigerante = new JButton("Refrigerante - R$ 8,00");
        
        // colocando fonte personalizada nos botões
        Font fonteBotoes = new Font("Arial", Font.BOLD, 16);
        btnHamburguer.setFont(fonteBotoes);
        btnBatata.setFont(fonteBotoes);
        btnRefrigerante.setFont(fonteBotoes);

        // configurando as ações de adicionar ao carrinho
        btnHamburguer.addActionListener(e -> adicionarAoCarrinho(35.00));
        btnBatata.addActionListener(e -> adicionarAoCarrinho(12.00));
        btnRefrigerante.addActionListener(e -> adicionarAoCarrinho(8.00));

        painelItens.add(btnHamburguer);
        painelItens.add(btnBatata);
        painelItens.add(btnRefrigerante);
        
        telaCardapio.add(painelItens, BorderLayout.CENTER);

        // Rodapé: Mostra o total atual e o botão de avançar
        JPanel painelRodape = new JPanel(new BorderLayout());
        painelRodape.setBackground(new Color(245,242,235)); // tom bege ao fundo
        painelRodape.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        lblTotalCarrinho = new JLabel("Total: R$ 0,00  ");
        lblTotalCarrinho.setFont(new Font("Arial", Font.BOLD, 18));
        lblTotalCarrinho.setForeground(new Color(64, 35, 15)); // marrom escuro

        JButton btnAvancar = new JButton("Finalizar Pedido");
        btnAvancar.setFont(new Font("Arial", Font.BOLD, 16));
        btnAvancar.setBackground(new Color(230, 57, 70)); // Vermelho
        btnAvancar.setForeground(Color.WHITE);
        btnAvancar.setFocusPainted(false);
        
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 16));
        btnVoltar.setBackground(new Color(230, 57, 70)); // Vermelho
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setFocusPainted(false);
        
        btnAvancar.addActionListener(e -> {
            lblResumoFinal.setText("R$ " + calcularTotal());
            cardLayout.show(painelPrincipal, "Pagamento");
        });
        
        btnVoltar.addActionListener(e -> {
        	carrinhoPrecos.clear();
        	lblTotalCarrinho.setText("Total: R$ 0,00");
            cardLayout.show(painelPrincipal, "Inicio");
        });

        painelRodape.add(lblTotalCarrinho, BorderLayout.WEST);
        painelRodape.add(btnAvancar, BorderLayout.EAST);
        painelRodape.add(btnVoltar, BorderLayout.CENTER);
        telaCardapio.add(painelRodape, BorderLayout.SOUTH);

        painelPrincipal.add(telaCardapio, "Cardapio");
    }

    // --- TELA 3: PAGAMENTO ---
    private void criarTela3Pagamento() {
    	JPanel telaPagar = new JPanel() {
            private Image imagemFundoPagar = new ImageIcon(getClass().getResource("/imagem/telaPagar.png")).getImage();
            
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagemFundoPagar != null) {
                    g.drawImage(imagemFundoPagar, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };        
        
        telaPagar.setLayout(new BorderLayout());
        
        JPanel espacoTopo2 = new JPanel();
        espacoTopo2.setOpaque(false);
        espacoTopo2.setPreferredSize(new Dimension(450,225));
        telaPagar.add(espacoTopo2, BorderLayout.NORTH);
        
        JPanel painelFormulario = new JPanel(new GridBagLayout());
        painelFormulario.setOpaque(false);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 10, 12, 10);
        gbc.gridx = 0;

        lblResumoFinal = new JLabel("R$ 0,00");
        lblResumoFinal.setFont(new Font("Arial", Font.BOLD, 35));
        lblResumoFinal.setForeground(new Color(94, 35, 15));
        gbc.gridy = 0;
        painelFormulario.add(lblResumoFinal, gbc);
        
        // espaço invisível para pular o texto da imagem, empurra a caixa de texto para baixo
        gbc.gridy = 1;
        painelFormulario.add(Box.createVerticalStrut(120), gbc);

        JTextField txtNome = new JTextField(15);
        txtNome.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridy = 2;
        painelFormulario.add(txtNome, gbc);
        
        // um pequeno espaço para descer até o botão da imagem
        gbc.gridy = 3;
        painelFormulario.add(Box.createVerticalStrut(15), gbc);

        JButton btnPagar = new JButton();
        btnPagar.setOpaque(false);
        btnPagar.setContentAreaFilled(false);
        btnPagar.setBorderPainted(false);
        btnPagar.setFocusPainted(false);
        btnPagar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPagar.setPreferredSize(new Dimension(220, 50));
        
        gbc.gridy = 4;
        painelFormulario.add(btnPagar, gbc);

        btnPagar.addActionListener(e -> {
            String cliente = txtNome.getText().trim();
            if (cliente.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, digite seu nome antes de pagar.");
            } else {
                JOptionPane.showMessageDialog(this, "Obrigado, " + cliente + "!\nSeu pedido foi enviado para a cozinha.");
                // Reseta o carrinho e volta para a tela inicial
                carrinhoPrecos.clear();
                lblTotalCarrinho.setText("Total: R$ 0,00  ");
                txtNome.setText("");
                cardLayout.show(painelPrincipal, "Inicio");
            }
        });
        
        telaPagar.add(painelFormulario, BorderLayout.CENTER);

        painelPrincipal.add(telaPagar, "Pagamento");
    }

    // --- MÉTODOS AUXILIARES DE LÓGICA ---
    private void adicionarAoCarrinho(double preco) {
        carrinhoPrecos.add(preco);
        lblTotalCarrinho.setText("Total: R$ " + calcularTotal() + "  ");
    }

    private double calcularTotal() {
        double soma = 0;
        for (double preco : carrinhoPrecos) {
            soma += preco;
        }
        return soma;
    }

    public static void main(String[] args) {
        // Roda a interface gráfica na thread correta do Swing (Segurança de Threads)
        SwingUtilities.invokeLater(() -> {
            new TotemRestaurante().setVisible(true);
        });
    }
}