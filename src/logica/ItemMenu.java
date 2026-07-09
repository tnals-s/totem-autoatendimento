package logica;

public class ItemMenu {
	private int codigo;
	private String nome;
	private double preco;
	
	// Construtor para inicializar o objeto
	public ItemMenu(int codigo, String nome, double preco) {
	this.codigo = codigo;
	this.nome = nome;
	this.preco = preco;
	}
	
	// Getters (Acesso seguro aos atributos trancados com private)
	public int getCodigo() {
		return codigo;
	}
	
	public String getNome() {
		return nome;
	}
		 
	public double getPreco() {
		return preco;
	}

	// Método para decidir o status com base na nota corte (Exemplo de lógica)
	//public String getStatus() {
	//if (this.preco >= 6.0) {
	//return "APROVADO";
	//} else
	//	 return "REPROVADO";}
}
