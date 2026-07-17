import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.ArrayList;

public class TotemRestaurante extends JFrame {

    // gerenciador de telas ("Baralho de Cartas")
    private CardLayout cardLayout;
    private JPanel painelPrincipal;

    // carrinho: lista simples para guardar os preços dos itens guardados
    private ArrayList<Double> carrinhoPrecos = new ArrayList<>();
    
    // lista para guardar o nome de cada item clicado
    private ArrayList<String> carrinhoNomes = new ArrayList<>();
    
    private JLabel lblTotalCarrinho;
    private JLabel lblResumoFinal;

    public TotemRestaurante() {
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
        criarTela2Cardapio();
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

        // topo da tela, painel transparente para não ter nada por cima e "empurrar" os itens
        JPanel espacoTopo = new JPanel();
        espacoTopo.setOpaque(false);
        espacoTopo.setPreferredSize(new Dimension(450,225));
        telaCardapio.add(espacoTopo, BorderLayout.NORTH);

        // centro: lista de itens usando GridLayout (linhas, colunas)
        JPanel painelItens = new JPanel(new GridLayout(3, 1, 10, 15));
        painelItens.setOpaque(false); // deixar transparente para ver o fundo bege
        painelItens.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

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
        btnHamburguer.addActionListener(e -> adicionarAoCarrinho("Combo OAK", 35.00));
        btnBatata.addActionListener(e -> adicionarAoCarrinho("Batata Frita", 12.00));
        btnRefrigerante.addActionListener(e -> adicionarAoCarrinho("Refrigerante", 8.00));

        painelItens.add(btnHamburguer);
        painelItens.add(btnBatata);
        painelItens.add(btnRefrigerante);
        
        telaCardapio.add(painelItens, BorderLayout.CENTER);

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
        	carrinhoNomes.clear();
        	lblTotalCarrinho.setText("Total: R$ 0,00");
            cardLayout.show(painelPrincipal, "Inicio"); // voltando para a tela anterior e "resetando o carrinho"
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
            double totalCompra = calcularTotal();
            double limiteControle = 100.00;
            
            if (cliente.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, digite seu nome antes de pagar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                String mensagemFinal;
                double valorFinalCobrado = totalCompra; // O cliente sempre paga o valor exato gasto

                // calcula a quantidade de cada item
                int qntdHamburguer = 0;
                int qntdBatata = 0;
                int qntdRefri = 0;
                
                for (String nome : carrinhoNomes) {
                	if (nome.equals("Combo OAK")) qntdHamburguer++;
                	else if (nome.equals("Batata Frita")) qntdBatata++;
                	else if (nome.equals("Refrigerante")) qntdRefri++;
                }
                                
                StringBuilder resumoItens = new StringBuilder();
                if (qntdHamburguer > 0) resumoItens.append(String.format("- %dx Combo OAK (R$ %.2f)\n", qntdHamburguer, qntdHamburguer * 35.00));
                if (qntdBatata > 0) resumoItens.append(String.format("- %dx Batata Frita (R$ %.2f)\n", qntdBatata, qntdBatata * 12.00));
                if (qntdRefri > 0) resumoItens.append(String.format("- %dx Refrigerante (R$ %.2f)\n", qntdRefri, qntdRefri * 8.00));
                                
                // CONDICIONAL: Verifica se o consumo ultrapassou a marca de R$ 50,00
                if (totalCompra > limiteControle) {
                    double valorQuePassou = totalCompra - limiteControle; // Ex: 60 - 50 = 10
                  
                    mensagemFinal = String.format(
                        "Obrigado(a), %s!\n\n" +
                        "RESUMO DO SEU PEDIDO: \n" +
                        "%s\n" +
                        "Seu pedido ultrapassou o limite de controle (R$ %.2f):\n" +
                        "- Valor Base: R$ %.2f\n" +
                        "- Valor que passou: R$ %.2f\n\n" +
                        "-> VALOR TOTAL A PAGAR: R$ %.2f",
                        cliente, resumoItens.toString(), limiteControle, limiteControle, valorQuePassou, valorFinalCobrado
                    );
                } else {
                    // Se ficou abaixo ou igual a 50, mostra apenas o total normal
                    mensagemFinal = String.format(
                        "Obrigado(a), %s!\n\n" +
                        "RESUMO DO SEU PEDIDO: \n" +
                        "%s\n" +
                        "Seu pedido ficou dentro do limite de controle.\n\n" +
                        "-> VALOR TOTAL A PAGAR: R$ %.2f",
                        cliente, resumoItens.toString(), valorFinalCobrado
                    );
                }
                // Exibe o JOptionPane com o detalhamento
                JOptionPane.showMessageDialog(this, mensagemFinal, "Pedido Finalizado", JOptionPane.INFORMATION_MESSAGE);                // Reseta o carrinho e volta para a tela inicial
                
                carrinhoPrecos.clear();
                carrinhoNomes.clear();
                lblTotalCarrinho.setText("Total: R$ 0,00  ");
                txtNome.setText("");
                cardLayout.show(painelPrincipal, "Inicio");
            }
        });
        
        telaPagar.add(painelFormulario, BorderLayout.CENTER);

        painelPrincipal.add(telaPagar, "Pagamento");
    }

    // --- MÉTODOS AUXILIARES ---
    private void adicionarAoCarrinho(String nome, double preco) {
        carrinhoPrecos.add(preco);
        carrinhoNomes.add(nome);
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
    	// força o Java 2D a renderizar tudo em escala 1:1 (100%), ignorando a escala do Windows
        System.setProperty("sun.java2d.uiScale", "1.0");
    	
    	// roda a interface gráfica na thread correta do Swing (segurança de threads)
        SwingUtilities.invokeLater(() -> {
            new TotemRestaurante().setVisible(true);
        });
    }
}