package com.ti2cc;

public class Principal {
	
	public static void main(String[] args) {
		
		DAO dao = new DAO();
		
		dao.conectar();

		
		// Inserir um elemento na tabela
		Animais animal = new Animais(11, "cachorro", "Rex", "Labrador");
		if (dao.inserirAnimal(animal)) {
			System.out.println("Inserção com sucesso -> " + animal.toString());
		}
		
		// Mostrar animais
		System.out.println("==== Mostrar animais === ");
		Animais[] animais = dao.getAnimais();
		for (int i = 0; i < animais.length; i++) {
			System.out.println(animais[i].toString());
		}

		// Atualizar animal
		animal.setNome("Novo Rex");
		dao.atualizarAnimal(animal);

		// Mostrar animais
		System.out.println("==== Mostrar animais === ");
		animais = dao.getAnimais();
		for (int i = 0; i < animais.length; i++) {
			System.out.println(animais[i].toString());
		}
		
		// Excluir animal
		dao.excluirAnimal(animal.getCodigo());
		
		// Mostrar animais
		animais = dao.getAnimais();
		System.out.println("==== Mostrar animais === ");		
		for (int i = 0; i < animais.length; i++) {
			System.out.println(animais[i].toString());
		}
		
		dao.close();
	}
}
