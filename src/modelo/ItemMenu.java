package modelo;

public class ItemMenu {
	private int codigo;
	private String nome;
	private double preco;
	
	// Método Construtor para inicializar o objeto
	public ItemMenu(int codigo, String nome, double preco) {
		this.codigo = codigo;
		this.nome = nome;
		this.preco = preco;
	}
	
	// Getters e Setters (Permitem ler e alterar os dados com segurança)
	public int getCodigo() {
		return codigo;}
	
	public void setCodigo(int codigo) {
        this.codigo = codigo;}
	
	public String getNome() {
		return nome;}
	
	public void setNome(String nome) {
		this.nome = nome;}
		 
	public double getPreco() {
		return preco;}
	
	public void setPreco(double preco) {
		if (preco >= 0)
		this.preco = preco;
	}
	
}
