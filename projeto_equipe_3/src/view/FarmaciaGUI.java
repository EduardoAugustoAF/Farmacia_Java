package view;

import exception.EstoqueInsuficienteException;
import exception.ProdutoVencidoException;
import exception.ReceitaInvalidaException;
import model.Cliente;
import model.Estoque;
import model.Funcionario;
import model.Medicamento;
import model.Produto;
import model.ProdutoGenerico;
import model.Venda;
import service.FarmaciaService;
import util.FormatadorUtil;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FarmaciaGUI extends JFrame {
    private Estoque estoque;
    private FarmaciaService service;
    private List<Cliente> clientes;
    private List<Funcionario> funcionarios;
    private List<Venda> vendasFinalizadas;
    private Venda vendaAtual;
    private int contadorVenda;

    private DefaultTableModel estoqueTableModel;
    private JTable tabelaEstoque;
    private DefaultTableModel carrinhoTableModel;
    private JTable tabelaCarrinho;
    private DefaultComboBoxModel<Produto> produtoComboModel;
    private JComboBox<Produto> comboProdutos;

    private JTextField filtroEstoqueField;
    private JTextField idField;
    private JTextField nomeProdutoField;
    private JTextField precoField;
    private JTextField quantidadeField;
    private JComboBox<String> tipoProdutoCombo;
    private JTextField principioField;
    private JTextField dosagemField;
    private JCheckBox receitaObrigatoriaCheck;
    private JTextField validadeField;
    private JTextField categoriaField;
    private JTextField fabricanteField;
    private JTextField descontoField;

    private JTextField clienteNomeField;
    private JTextField clienteCpfField;
    private JTextField clienteTelefoneField;
    private JTextField funcionarioNomeField;
    private JTextField funcionarioMatriculaField;
    private JTextField funcionarioCargoField;
    private JSpinner quantidadeVendaSpinner;
    private JCheckBox possuiReceitaCheck;
    private JTextArea cupomArea;
    private JTextArea relatorioArea;

    private JLabel totalEstoqueLabel;
    private JLabel totalProdutosLabel;
    private JLabel totalVendaLabel;
    private JLabel itensVendaLabel;

    public FarmaciaGUI() {
        this.estoque = new Estoque();
        this.service = new FarmaciaService();
        this.clientes = new ArrayList<>();
        this.funcionarios = new ArrayList<>();
        this.vendasFinalizadas = new ArrayList<>();
        this.contadorVenda = 1001;

        carregarDadosIniciais();
        configurarJanela();
        montarLayout();
        iniciarNovaVenda();
        atualizarTela();
    }

    private void configurarJanela() {
        setTitle("Sistema de Gestão de Farmácia - Equipe 3");
        setSize(1120, 720);
        setMinimumSize(new Dimension(980, 650));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void montarLayout() {
        setLayout(new BorderLayout());
        add(criarCabecalho(), BorderLayout.NORTH);

        JTabbedPane abas = new JTabbedPane();
        abas.setFont(new Font("Segoe UI", Font.BOLD, 14));
        abas.addTab("Dashboard", criarDashboardPanel());
        abas.addTab("Estoque", criarEstoquePanel());
        abas.addTab("Vendas", criarVendasPanel());
        abas.addTab("Relatórios", criarRelatoriosPanel());

        add(abas, BorderLayout.CENTER);
    }

    private JPanel criarCabecalho() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(new Color(20, 103, 92));
        painel.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));

        JLabel titulo = new JLabel("Sistema de Gestão de Farmácia");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));

        JLabel subtitulo = new JLabel("Controle de estoque, vendas, clientes e relatórios em Java Swing");
        subtitulo.setForeground(new Color(225, 245, 241));
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        painel.add(titulo, BorderLayout.NORTH);
        painel.add(subtitulo, BorderLayout.SOUTH);
        return painel;
    }

    private JPanel criarDashboardPanel() {
        JPanel painel = new JPanel(new BorderLayout(16, 16));
        painel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        JPanel cards = new JPanel(new GridLayout(1, 4, 16, 16));
        totalProdutosLabel = criarValorCardLabel();
        totalEstoqueLabel = criarValorCardLabel();
        itensVendaLabel = criarValorCardLabel();
        totalVendaLabel = criarValorCardLabel();

        cards.add(criarCard("Produtos cadastrados", totalProdutosLabel, "Itens diferentes no estoque"));
        cards.add(criarCard("Unidades em estoque", totalEstoqueLabel, "Soma das quantidades"));
        cards.add(criarCard("Itens na venda atual", itensVendaLabel, "Produtos adicionados ao carrinho"));
        cards.add(criarCard("Total da venda", totalVendaLabel, "Valor parcial do atendimento"));

        JTextArea resumo = criarAreaTexto(false);
        resumo.setText(
            "Resumo da interface:\n\n" +
            "• A aba Estoque permite cadastrar medicamentos e produtos genéricos.\n" +
            "• A aba Vendas usa as classes Cliente, Funcionário, Venda e FarmaciaService.\n" +
            "• As exceções do projeto aparecem em tela: estoque insuficiente, produto vencido e receita inválida.\n" +
            "• A aba Relatórios reúne o estado atual do estoque, vendas finalizadas e relatórios dos objetos.\n\n" +
            "Essa tela foi criada em Swing para manter o projeto 100% Java, sem dependências externas."
        );

        painel.add(cards, BorderLayout.NORTH);
        painel.add(new JScrollPane(resumo), BorderLayout.CENTER);
        return painel;
    }

    private JLabel criarValorCardLabel() {
        JLabel label = new JLabel("0");
        label.setFont(new Font("Segoe UI", Font.BOLD, 28));
        label.setForeground(new Color(20, 103, 92));
        return label;
    }

    private JPanel criarCard(String titulo, JLabel valor, String descricao) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(210, 225, 222)),
            BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));
        card.setBackground(Color.WHITE);

        JLabel tituloLabel = new JLabel(titulo);
        tituloLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        tituloLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        valor.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel descLabel = new JLabel(descricao);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(Color.DARK_GRAY);
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(tituloLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(valor);
        card.add(Box.createVerticalStrut(8));
        card.add(descLabel);
        return card;
    }

    private JPanel criarEstoquePanel() {
        JPanel painel = new JPanel(new BorderLayout(14, 14));
        painel.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));

        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBorder(BorderFactory.createTitledBorder("Cadastro de Produto"));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;

        idField = new JTextField();
        nomeProdutoField = new JTextField();
        precoField = new JTextField();
        quantidadeField = new JTextField();
        tipoProdutoCombo = new JComboBox<>(new String[]{"Medicamento", "Produto Genérico"});
        principioField = new JTextField();
        dosagemField = new JTextField();
        receitaObrigatoriaCheck = new JCheckBox("Exige receita médica");
        validadeField = new JTextField("30/12/2026");
        categoriaField = new JTextField();
        fabricanteField = new JTextField();
        descontoField = new JTextField("0");

        adicionarCampo(formulario, c, 0, 0, "ID", idField);
        adicionarCampo(formulario, c, 1, 0, "Nome", nomeProdutoField);
        adicionarCampo(formulario, c, 2, 0, "Preço", precoField);
        adicionarCampo(formulario, c, 3, 0, "Qtd.", quantidadeField);
        adicionarCampo(formulario, c, 0, 2, "Tipo", tipoProdutoCombo);
        adicionarCampo(formulario, c, 1, 2, "Princípio ativo", principioField);
        adicionarCampo(formulario, c, 2, 2, "Dosagem", dosagemField);
        adicionarCampo(formulario, c, 3, 2, "Validade", validadeField);
        adicionarCampo(formulario, c, 0, 4, "Categoria", categoriaField);
        adicionarCampo(formulario, c, 1, 4, "Fabricante", fabricanteField);
        adicionarCampo(formulario, c, 2, 4, "Desconto %", descontoField);

        c.gridx = 3;
        c.gridy = 5;
        c.gridwidth = 1;
        formulario.add(receitaObrigatoriaCheck, c);

        JPanel botoes = new JPanel(new GridLayout(1, 3, 8, 8));
        JButton cadastrarButton = new JButton("Cadastrar produto");
        JButton limparButton = new JButton("Limpar campos");
        JButton removerButton = new JButton("Remover selecionado");
        botoes.add(cadastrarButton);
        botoes.add(limparButton);
        botoes.add(removerButton);

        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 4;
        formulario.add(botoes, c);

        estoqueTableModel = new DefaultTableModel(new String[]{"ID", "Nome", "Tipo", "Preço", "Qtd.", "Validade/Detalhe", "Receita"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaEstoque = new JTable(estoqueTableModel);
        tabelaEstoque.setRowHeight(24);
        tabelaEstoque.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        filtroEstoqueField = new JTextField();
        filtroEstoqueField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { atualizarTabelaEstoque(); }
            public void removeUpdate(DocumentEvent e) { atualizarTabelaEstoque(); }
            public void changedUpdate(DocumentEvent e) { atualizarTabelaEstoque(); }
        });

        JPanel buscaPanel = new JPanel(new BorderLayout(8, 8));
        buscaPanel.setBorder(BorderFactory.createTitledBorder("Produtos cadastrados"));
        buscaPanel.add(new JLabel("Buscar por ID ou nome: "), BorderLayout.WEST);
        buscaPanel.add(filtroEstoqueField, BorderLayout.CENTER);

        JPanel centro = new JPanel(new BorderLayout(8, 8));
        centro.add(buscaPanel, BorderLayout.NORTH);
        centro.add(new JScrollPane(tabelaEstoque), BorderLayout.CENTER);

        painel.add(formulario, BorderLayout.NORTH);
        painel.add(centro, BorderLayout.CENTER);

        cadastrarButton.addActionListener(e -> cadastrarProduto());
        limparButton.addActionListener(e -> limparFormularioProduto());
        removerButton.addActionListener(e -> removerProdutoSelecionado());
        tipoProdutoCombo.addActionListener(e -> ajustarCamposPorTipo());
        ajustarCamposPorTipo();

        return painel;
    }

    private void adicionarCampo(JPanel painel, GridBagConstraints c, int x, int y, String label, Component campo) {
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = 1;
        painel.add(new JLabel(label), c);
        c.gridy = y + 1;
        painel.add(campo, c);
    }

    private JPanel criarVendasPanel() {
        JPanel painel = new JPanel(new BorderLayout(12, 12));
        painel.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));

        JPanel dados = new JPanel(new GridLayout(2, 1, 8, 8));
        dados.add(criarDadosClientePanel());
        dados.add(criarDadosFuncionarioPanel());

        produtoComboModel = new DefaultComboBoxModel<>();
        comboProdutos = new JComboBox<>(produtoComboModel);
        comboProdutos.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel label = new JLabel(value == null ? "Selecione um produto" : value.getId() + " - " + value.getNome() + " | " + FormatadorUtil.formatarMoeda(value.calcularPreco()) + " | Qtd: " + value.getQuantidade());
            label.setOpaque(true);
            label.setBackground(isSelected ? new Color(205, 232, 227) : Color.WHITE);
            label.setBorder(BorderFactory.createEmptyBorder(4, 6, 4, 6));
            return label;
        });

        quantidadeVendaSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
        possuiReceitaCheck = new JCheckBox("Cliente apresentou receita válida");

        JButton adicionarItemButton = new JButton("Adicionar ao carrinho");
        JButton finalizarVendaButton = new JButton("Finalizar venda");
        JButton novaVendaButton = new JButton("Nova venda");

        JPanel vendaForm = new JPanel(new GridBagLayout());
        vendaForm.setBorder(BorderFactory.createTitledBorder("Atendimento de venda"));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;

        c.gridx = 0;
        c.gridy = 0;
        vendaForm.add(new JLabel("Produto"), c);
        c.gridy = 1;
        c.gridwidth = 3;
        vendaForm.add(comboProdutos, c);

        c.gridx = 3;
        c.gridy = 0;
        c.gridwidth = 1;
        vendaForm.add(new JLabel("Qtd."), c);
        c.gridy = 1;
        vendaForm.add(quantidadeVendaSpinner, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        vendaForm.add(possuiReceitaCheck, c);

        JPanel botoes = new JPanel(new GridLayout(1, 3, 8, 8));
        botoes.add(adicionarItemButton);
        botoes.add(finalizarVendaButton);
        botoes.add(novaVendaButton);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 4;
        vendaForm.add(botoes, c);

        carrinhoTableModel = new DefaultTableModel(new String[]{"Produto", "Qtd.", "Unitário", "Subtotal"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaCarrinho = new JTable(carrinhoTableModel);
        tabelaCarrinho.setRowHeight(24);

        cupomArea = criarAreaTexto(false);
        cupomArea.setText("O cupom fiscal aparecerá aqui após a finalização da venda.");

        JPanel direita = new JPanel(new GridLayout(2, 1, 8, 8));
        direita.add(new JScrollPane(tabelaCarrinho));
        direita.add(new JScrollPane(cupomArea));

        JPanel topo = new JPanel(new BorderLayout(8, 8));
        topo.add(dados, BorderLayout.NORTH);
        topo.add(vendaForm, BorderLayout.CENTER);

        painel.add(topo, BorderLayout.NORTH);
        painel.add(direita, BorderLayout.CENTER);

        adicionarItemButton.addActionListener(e -> adicionarItemVenda());
        finalizarVendaButton.addActionListener(e -> finalizarVenda());
        novaVendaButton.addActionListener(e -> iniciarNovaVenda());

        return painel;
    }

    private JPanel criarDadosClientePanel() {
        JPanel painel = new JPanel(new GridLayout(2, 3, 8, 4));
        painel.setBorder(BorderFactory.createTitledBorder("Cliente"));
        clienteNomeField = new JTextField("Gaby");
        clienteCpfField = new JTextField("555.666.777-88");
        clienteTelefoneField = new JTextField("7777-6666");
        painel.add(new JLabel("Nome"));
        painel.add(new JLabel("CPF"));
        painel.add(new JLabel("Telefone"));
        painel.add(clienteNomeField);
        painel.add(clienteCpfField);
        painel.add(clienteTelefoneField);
        return painel;
    }

    private JPanel criarDadosFuncionarioPanel() {
        JPanel painel = new JPanel(new GridLayout(2, 3, 8, 4));
        painel.setBorder(BorderFactory.createTitledBorder("Funcionário"));
        funcionarioNomeField = new JTextField("Augusto");
        funcionarioMatriculaField = new JTextField("MAT-001");
        funcionarioCargoField = new JTextField("Farmacêutico");
        painel.add(new JLabel("Nome"));
        painel.add(new JLabel("Matrícula"));
        painel.add(new JLabel("Cargo"));
        painel.add(funcionarioNomeField);
        painel.add(funcionarioMatriculaField);
        painel.add(funcionarioCargoField);
        return painel;
    }

    private JPanel criarRelatoriosPanel() {
        JPanel painel = new JPanel(new BorderLayout(8, 8));
        painel.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
        relatorioArea = criarAreaTexto(false);

        JButton atualizarButton = new JButton("Atualizar relatório geral");
        JButton limparButton = new JButton("Limpar área");
        JPanel botoes = new JPanel(new GridLayout(1, 2, 8, 8));
        botoes.add(atualizarButton);
        botoes.add(limparButton);

        painel.add(botoes, BorderLayout.NORTH);
        painel.add(new JScrollPane(relatorioArea), BorderLayout.CENTER);

        atualizarButton.addActionListener(e -> atualizarRelatorioGeral());
        limparButton.addActionListener(e -> relatorioArea.setText(""));
        return painel;
    }

    private JTextArea criarAreaTexto(boolean editavel) {
        JTextArea area = new JTextArea();
        area.setEditable(editavel);
        area.setFont(new Font("Consolas", Font.PLAIN, 13));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        return area;
    }

    private void carregarDadosIniciais() {
        long umDia = 86400000L;
        Date dataValida = new Date(System.currentTimeMillis() + (30 * umDia));
        Date dataVencida = new Date(System.currentTimeMillis() - umDia);

        estoque.cadastrarProduto(new Medicamento("MED01", "Dorflex", 15.50, 50, "Dipirona", "1g", false, dataValida));
        estoque.cadastrarProduto(new Medicamento("MED02", "Amoxicilina", 45.00, 20, "Amoxicilina", "500mg", true, dataValida));
        estoque.cadastrarProduto(new Medicamento("MED03", "Aspirina", 10.00, 10, "Ácido Acetilsalicílico", "500mg", false, dataVencida));
        estoque.cadastrarProduto(new ProdutoGenerico("GEN01", "Shampoo Clear", 25.00, 15, "Higiene", "Unilever", 0.10));
        estoque.cadastrarProduto(new ProdutoGenerico("GEN02", "Protetor Solar", 39.90, 12, "Dermocosmético", "Needs", 0.05));
    }

    private void iniciarNovaVenda() {
        Cliente cliente = montarClientePelosCampos();
        Funcionario funcionario = montarFuncionarioPelosCampos();
        vendaAtual = new Venda("VND-" + contadorVenda, cliente, funcionario);
        contadorVenda++;

        if (carrinhoTableModel != null) {
            carrinhoTableModel.setRowCount(0);
        }
        if (cupomArea != null) {
            cupomArea.setText("Venda iniciada: " + vendaAtual.getId() + "\nAdicione produtos ao carrinho.");
        }
        atualizarTela();
    }

    private Cliente montarClientePelosCampos() {
        String nome = obterTexto(clienteNomeField, "Cliente não informado");
        String cpf = obterTexto(clienteCpfField, "CPF não informado");
        String telefone = obterTexto(clienteTelefoneField, "Telefone não informado");
        Date dataNascimento = new Date(System.currentTimeMillis() - (8000L * 86400000L));
        return new Cliente(nome, cpf, telefone, dataNascimento);
    }

    private Funcionario montarFuncionarioPelosCampos() {
        String nome = obterTexto(funcionarioNomeField, "Funcionário não informado");
        String matricula = obterTexto(funcionarioMatriculaField, "MAT-000");
        String cargo = obterTexto(funcionarioCargoField, "Atendente");
        return new Funcionario(nome, "000.000.000-00", "Não informado", matricula, cargo, 0.0);
    }

    private String obterTexto(JTextField campo, String padrao) {
        if (campo == null || campo.getText().trim().isEmpty()) {
            return padrao;
        }
        return campo.getText().trim();
    }

    private void cadastrarProduto() {
        try {
            String id = idField.getText().trim();
            String nome = nomeProdutoField.getText().trim();
            double preco = parseDouble(precoField.getText());
            int quantidade = Integer.parseInt(quantidadeField.getText().trim());

            if (id.isEmpty() || nome.isEmpty()) {
                throw new IllegalArgumentException("Informe ID e nome do produto.");
            }
            if (preco <= 0 || quantidade < 0) {
                throw new IllegalArgumentException("Preço deve ser positivo e quantidade não pode ser negativa.");
            }
            if (estoque.buscar(id) != null) {
                throw new IllegalArgumentException("Já existe produto cadastrado com esse ID.");
            }

            Produto produto;
            if ("Medicamento".equals(tipoProdutoCombo.getSelectedItem())) {
                Date validade = parseData(validadeField.getText());
                produto = new Medicamento(
                    id,
                    nome,
                    preco,
                    quantidade,
                    principioField.getText().trim(),
                    dosagemField.getText().trim(),
                    receitaObrigatoriaCheck.isSelected(),
                    validade
                );
            } else {
                double desconto = parseDouble(descontoField.getText()) / 100.0;
                produto = new ProdutoGenerico(
                    id,
                    nome,
                    preco,
                    quantidade,
                    categoriaField.getText().trim(),
                    fabricanteField.getText().trim(),
                    desconto
                );
            }

            estoque.cadastrarProduto(produto);
            limparFormularioProduto();
            atualizarTela();
            JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso.", "Cadastro", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Verifique os campos numéricos de preço, quantidade e desconto.", "Erro de preenchimento", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Data inválida. Use o formato dd/MM/aaaa.", "Erro de data", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro de validação", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void removerProdutoSelecionado() {
        int linha = tabelaEstoque.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um produto na tabela.", "Remover", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String id = String.valueOf(tabelaEstoque.getValueAt(linha, 0));
        Produto produto = estoque.buscar(id);
        if (produto == null) {
            return;
        }

        int resposta = JOptionPane.showConfirmDialog(this, "Remover o produto " + produto.getNome() + "?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (resposta == JOptionPane.YES_OPTION) {
            estoque.getProdutos().remove(produto);
            atualizarTela();
        }
    }

    private void adicionarItemVenda() {
        try {
            Produto produto = (Produto) comboProdutos.getSelectedItem();
            if (produto == null) {
                JOptionPane.showMessageDialog(this, "Não há produto selecionado.", "Venda", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int quantidade = (Integer) quantidadeVendaSpinner.getValue();
            service.adicionarItemVenda(vendaAtual, produto, quantidade, possuiReceitaCheck.isSelected());
            atualizarCarrinho();
            atualizarTela();
            JOptionPane.showMessageDialog(this, "Item adicionado ao carrinho.", "Venda", JOptionPane.INFORMATION_MESSAGE);
        } catch (ReceitaInvalidaException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Receita obrigatória", JOptionPane.WARNING_MESSAGE);
        } catch (ProdutoVencidoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Produto vencido", JOptionPane.ERROR_MESSAGE);
        } catch (EstoqueInsuficienteException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Estoque insuficiente", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro inesperado: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void finalizarVenda() {
        if (vendaAtual.getItens().isEmpty()) {
            JOptionPane.showMessageDialog(this, "A venda ainda não possui itens.", "Finalizar venda", JOptionPane.WARNING_MESSAGE);
            return;
        }

        vendaAtual.setCliente(montarClientePelosCampos());
        vendaAtual.setFuncionario(montarFuncionarioPelosCampos());
        service.finalizarVenda(vendaAtual);
        vendasFinalizadas.add(vendaAtual);
        clientes.add(vendaAtual.getCliente());
        funcionarios.add(vendaAtual.getFuncionario());

        cupomArea.setText(vendaAtual.gerarRelatorio());
        atualizarRelatorioGeral();
        atualizarTela();
        JOptionPane.showMessageDialog(this, "Venda finalizada com sucesso.", "Venda", JOptionPane.INFORMATION_MESSAGE);
    }

    private void atualizarTela() {
        atualizarTabelaEstoque();
        atualizarComboProdutos();
        atualizarCarrinho();
        atualizarDashboard();
    }

    private void atualizarTabelaEstoque() {
        if (estoqueTableModel == null) {
            return;
        }
        String filtro = filtroEstoqueField == null ? "" : filtroEstoqueField.getText().trim().toLowerCase();
        estoqueTableModel.setRowCount(0);

        for (Produto produto : estoque.getProdutos()) {
            if (!filtro.isEmpty() && !produto.getId().toLowerCase().contains(filtro) && !produto.getNome().toLowerCase().contains(filtro)) {
                continue;
            }

            String tipo = produto instanceof Medicamento ? "Medicamento" : "Genérico";
            String detalhe = "-";
            String receita = "Não";
            if (produto instanceof Medicamento) {
                Medicamento med = (Medicamento) produto;
                detalhe = FormatadorUtil.formatarData(med.getDataValidade());
                receita = med.isNecessitaReceita() ? "Sim" : "Não";
            } else if (produto instanceof ProdutoGenerico) {
                detalhe = produto.getDescricao();
            }

            estoqueTableModel.addRow(new Object[]{
                produto.getId(),
                produto.getNome(),
                tipo,
                FormatadorUtil.formatarMoeda(produto.calcularPreco()),
                produto.getQuantidade(),
                detalhe,
                receita
            });
        }
    }

    private void atualizarComboProdutos() {
        if (produtoComboModel == null) {
            return;
        }
        Produto selecionado = (Produto) produtoComboModel.getSelectedItem();
        produtoComboModel.removeAllElements();
        for (Produto produto : estoque.getProdutos()) {
            produtoComboModel.addElement(produto);
        }
        if (selecionado != null && estoque.buscar(selecionado.getId()) != null) {
            produtoComboModel.setSelectedItem(selecionado);
        }
    }

    private void atualizarCarrinho() {
        if (carrinhoTableModel == null || vendaAtual == null) {
            return;
        }
        carrinhoTableModel.setRowCount(0);
        for (Map.Entry<Produto, Integer> item : vendaAtual.getItens().entrySet()) {
            Produto produto = item.getKey();
            int quantidade = item.getValue();
            carrinhoTableModel.addRow(new Object[]{
                produto.getNome(),
                quantidade,
                FormatadorUtil.formatarMoeda(produto.calcularPreco()),
                FormatadorUtil.formatarMoeda(produto.calcularPreco() * quantidade)
            });
        }
    }

    private void atualizarDashboard() {
        if (totalProdutosLabel == null) {
            return;
        }
        int totalProdutos = estoque.getProdutos().size();
        int unidades = 0;
        for (Produto produto : estoque.getProdutos()) {
            unidades += produto.getQuantidade();
        }
        int itensVenda = 0;
        double totalVenda = 0.0;
        if (vendaAtual != null) {
            for (Map.Entry<Produto, Integer> item : vendaAtual.getItens().entrySet()) {
                itensVenda += item.getValue();
            }
            totalVenda = vendaAtual.calcularTotal();
        }

        totalProdutosLabel.setText(String.valueOf(totalProdutos));
        totalEstoqueLabel.setText(String.valueOf(unidades));
        itensVendaLabel.setText(String.valueOf(itensVenda));
        totalVendaLabel.setText(FormatadorUtil.formatarMoeda(totalVenda));
    }

    private void atualizarRelatorioGeral() {
        if (relatorioArea == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("RELATÓRIO GERAL DO SISTEMA\n");
        sb.append("Gerado em: ").append(FormatadorUtil.formatarDataHora(new Date())).append("\n");
        sb.append("============================================================\n\n");

        sb.append("ESTOQUE ATUAL\n");
        sb.append("------------------------------------------------------------\n");
        for (Produto produto : estoque.getProdutos()) {
            sb.append(produto.getId()).append(" - ").append(produto.getDescricao())
              .append(" | Qtd: ").append(produto.getQuantidade())
              .append(" | Preço: ").append(FormatadorUtil.formatarMoeda(produto.calcularPreco()))
              .append("\n");
        }

        sb.append("\nVENDAS FINALIZADAS\n");
        sb.append("------------------------------------------------------------\n");
        if (vendasFinalizadas.isEmpty()) {
            sb.append("Nenhuma venda finalizada até o momento.\n");
        } else {
            for (Venda venda : vendasFinalizadas) {
                sb.append(venda.getId()).append(" | Cliente: ").append(venda.getCliente().getNome())
                  .append(" | Funcionário: ").append(venda.getFuncionario().getNome())
                  .append(" | Total: ").append(FormatadorUtil.formatarMoeda(venda.calcularTotal()))
                  .append("\n");
            }
        }

        adicionarSecaoClientesCadastrados(sb);
        adicionarSecaoFuncionariosCadastrados(sb);

        relatorioArea.setText(sb.toString());
    }

    private void adicionarSecaoClientesCadastrados(StringBuilder sb) {
        sb.append("\nCLIENTES CADASTRADOS\n");
        sb.append("------------------------------------------------------------\n");

        if (clientes.isEmpty()) {
            sb.append("Nenhum cliente cadastrado até o momento.\n");
            return;
        }

        List<String> cpfsListados = new ArrayList<>();
        int contador = 1;

        for (Cliente cliente : clientes) {
            String chave = normalizarChave(cliente.getCpf());
            if (chave.isEmpty()) {
                chave = normalizarChave(cliente.getNome() + cliente.getTelefone());
            }

            if (cpfsListados.contains(chave)) {
                continue;
            }

            cpfsListados.add(chave);
            sb.append(contador).append(". ")
              .append("Nome: ").append(cliente.getNome())
              .append(" | CPF: ").append(cliente.getCpf())
              .append(" | Telefone: ").append(cliente.getTelefone())
              .append(" | Pontos: ").append(cliente.getPontosFidelidade())
              .append("\n");
            contador++;
        }
    }

    private void adicionarSecaoFuncionariosCadastrados(StringBuilder sb) {
        sb.append("\nFUNCIONÁRIOS CADASTRADOS\n");
        sb.append("------------------------------------------------------------\n");

        if (funcionarios.isEmpty()) {
            sb.append("Nenhum funcionário cadastrado até o momento.\n");
            return;
        }

        List<String> matriculasListadas = new ArrayList<>();
        int contador = 1;

        for (Funcionario funcionario : funcionarios) {
            String chave = normalizarChave(funcionario.getMatricula());
            if (chave.isEmpty()) {
                chave = normalizarChave(funcionario.getCpf() + funcionario.getNome());
            }

            if (matriculasListadas.contains(chave)) {
                continue;
            }

            matriculasListadas.add(chave);
            sb.append(contador).append(". ")
              .append("Nome: ").append(funcionario.getNome())
              .append(" | Matrícula: ").append(funcionario.getMatricula())
              .append(" | Cargo: ").append(funcionario.getCargo())
              .append(" | Telefone: ").append(funcionario.getTelefone())
              .append("\n");
            contador++;
        }
    }

    private String normalizarChave(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.trim().toLowerCase().replaceAll("\\s+", "");
    }

    private void ajustarCamposPorTipo() {
        boolean medicamento = "Medicamento".equals(tipoProdutoCombo.getSelectedItem());
        principioField.setEnabled(medicamento);
        dosagemField.setEnabled(medicamento);
        receitaObrigatoriaCheck.setEnabled(medicamento);
        validadeField.setEnabled(medicamento);
        categoriaField.setEnabled(!medicamento);
        fabricanteField.setEnabled(!medicamento);
        descontoField.setEnabled(!medicamento);
    }

    private void limparFormularioProduto() {
        idField.setText("");
        nomeProdutoField.setText("");
        precoField.setText("");
        quantidadeField.setText("");
        principioField.setText("");
        dosagemField.setText("");
        receitaObrigatoriaCheck.setSelected(false);
        validadeField.setText("30/12/2026");
        categoriaField.setText("");
        fabricanteField.setText("");
        descontoField.setText("0");
    }

    private double parseDouble(String texto) {
        return Double.parseDouble(texto.trim().replace(",", "."));
    }

    private Date parseData(String texto) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        return sdf.parse(texto.trim());
    }

    public static void abrir() {
        SwingUtilities.invokeLater(() -> new FarmaciaGUI().setVisible(true));
    }
}
