package visual;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.ArrayList;

public class TelaTotem extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7274124888833498399L;
	// gerenciador de telas ("Baralho de Cartas")
    private CardLayout cardLayout;
    private JPanel painelPrincipal;

    // carrinho: lista simples para guardar os preços dos itens guardados
    private ArrayList<Double> carrinhoPrecos = new ArrayList<>();
    private JLabel lblTotalCarrinho;
    private JLabel lblResumoFinal;

    public TelaTotem() {
        // configurações básicas da janela (JFrame)
        setTitle("Totem de Autoatendimento");
        setSize(500, 800); // formato vertical, como em um totem real
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // inicializando o CardLayout
        cardLayout = new CardLayout();
        painelPrincipal = new JPanel(cardLayout);

        // criando 3 telas básicas do fluxo
        criarTela1Inicio();
        criarTela2Comida();
        criarTela3Pagamento();

        // adiciona o painel principal na janela
        add(painelPrincipal);
    }

    // --- TELA 1: INÍCIO ---
    private void criarTela1Inicio() {
    	// 1. painel customizado para desenhar a imagem de fundo
        JPanel telaInicio = new JPanel() {
            private Image imagemFundo = new ImageIcon(getClass().getResource("/imagem/telaInicio.png")).getImage();
            
         // método para o Swing desenhar a imagem na tela
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagemFundo != null) {
                    g.drawImage(imagemFundo, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
       
        telaInicio.setLayout(null);

        JButton btnIniciar = new JButton();

        // tornando botão invisível
        btnIniciar.setOpaque(false);          // não preenche o fundo do botão
        btnIniciar.setContentAreaFilled(false); // não desenha a área de conteúdo (o "corpo" do botão)
        btnIniciar.setBorderPainted(false);   // não desenha a borda do botão
        btnIniciar.setFocusPainted(false);    // não desenha o retângulo de foco (aquele pontilhado)
        
        btnIniciar.setBounds(100, 480, 300, 100); 

        // 4. configurar a ação do botão
        btnIniciar.addActionListener(e -> {
            cardLayout.show(painelPrincipal, "Cardapio");
        });

        // 5. mudar o cursor do mouse para uma "mãozinha" ao passar no botão
        btnIniciar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        telaInicio.add(btnIniciar);
        painelPrincipal.add(telaInicio, "Inicio");
    }

    // --- TELA 2: CARDÁPIO ---
    private void criarTela2Comida() {
    	JPanel telaComida = new JPanel() {
            private Image imagemFundoComida = new ImageIcon(getClass().getResource("/imagem/fundoNovo.png")).getImage();
            
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagemFundoComida != null) {
                    g.drawImage(imagemFundoComida, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        // usar o layout null
        telaComida.setLayout(null);

        
     // Menu lateral
        
        JButton btnComidas = new JButton("COMIDAS");
        JButton btnBebidas = new JButton("BEBIDAS");

        btnComidas.setFont(new Font("Arial", Font.BOLD, 16));
        btnBebidas.setFont(new Font("Arial", Font.BOLD, 16));
        
        // criando botões com os itens do cardápio
        JButton btnHamburguer = new JButton("Combo OAK - R$ 35,00");
        JButton btnBatata = new JButton("Batata Frita - R$ 12,00");
        JButton btnRefrigerante = new JButton("Refrigerante - R$ 8,00");
        
        // colocando fonte personalizada nos botões
        Font fonteBotoes = new Font("Arial", Font.BOLD, 16);
        btnHamburguer.setFont(fonteBotoes);
        btnBatata.setFont(fonteBotoes);
        btnRefrigerante.setFont(fonteBotoes);

        // configurando os botões com as ações de adicionar ao carrinho
        btnHamburguer.addActionListener(e -> adicionarAoCarrinho(35.00));
        btnBatata.addActionListener(e -> adicionarAoCarrinho(12.00));
        btnRefrigerante.addActionListener(e -> adicionarAoCarrinho(8.00));
 
        btnComidas.setBounds(20, 245, 130, 40);
        btnBebidas.setBounds(20, 310, 130, 40);

        btnHamburguer.setBounds(170, 245, 250, 40);
        btnBatata.setBounds(170, 295, 250, 40);
        btnRefrigerante.setBounds(170, 350, 250, 40);
        
       
        
        telaComida.add(btnComidas);
        telaComida.add(btnBebidas);

        telaComida.add(btnHamburguer);
        telaComida.add(btnBatata);
        telaComida.add(btnRefrigerante);
        
        // rodapé: mostra o total atual e o botão de avançar
        JPanel painelRodape = new JPanel(new BorderLayout());
        painelRodape.setBackground(new Color(245,242,235)); // tom bege ao fundo
        painelRodape.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        lblTotalCarrinho = new JLabel("Total: R$ 0,00  ");
        lblTotalCarrinho.setFont(new Font("Arial", Font.BOLD, 18));
        lblTotalCarrinho.setForeground(new Color(64, 35, 15)); // marrom escuro

        JButton btnAvancar = new JButton("Finalizar Pedido");
        btnAvancar.setFont(new Font("Arial", Font.BOLD, 16));
        btnAvancar.setBackground(new Color(230, 57, 70)); // vermelho
        btnAvancar.setForeground(Color.WHITE);
        btnAvancar.setFocusPainted(false);
        
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 16));
        btnVoltar.setBackground(new Color(230, 57, 70)); // vermelho
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setFocusPainted(false);
        
        btnAvancar.addActionListener(e -> {
            lblResumoFinal.setText("R$ " + calcularTotal());
            cardLayout.show(painelPrincipal, "Pagamento"); // passando para a próxima tela...
        });
        
        btnVoltar.addActionListener(e -> {
        	carrinhoPrecos.clear();
        	lblTotalCarrinho.setText("Total: R$ 0,00");
            cardLayout.show(painelPrincipal, "Inicio"); // voltando para a tela anterior e "resetando o carrinho"
        });

        painelRodape.add(lblTotalCarrinho, BorderLayout.WEST);
        painelRodape.add(btnAvancar, BorderLayout.EAST);
        painelRodape.add(btnVoltar, BorderLayout.CENTER);
        telaComida.add(painelRodape, BorderLayout.SOUTH);

        painelPrincipal.add(telaComida, "Cardapio");
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
        
        // espaço invisível para pular o texto da imagem; empurra a caixa de texto para baixo
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

    // --- MÉTODOS AUXILIARES ---
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
        // roda a interface gráfica na thread correta do Swing (segurança de threads)
        SwingUtilities.invokeLater(() -> {
            new TelaTotem().setVisible(true);
        });
    }
}