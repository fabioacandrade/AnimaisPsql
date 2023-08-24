package com.ti2cc;

import java.sql.*;

public class DAO {
    private Connection conexao;

    public DAO() {
        conexao = null;
    }

    public boolean conectar() {
        String driverName = "org.postgresql.Driver";                    
		String serverName = "localhost";
		String mydatabase = "teste";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase;
		String username = "ti2cc";
		String password = "ti@cc";
		boolean status = false;

		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Conexão efetuada com o postgres!");
		} catch (ClassNotFoundException e) { 
			System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
		}

		return status;
        
    }

    public boolean close() {
        boolean status = false;
		
		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
    }

    public boolean inserirAnimal(Animais animal) {
        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("INSERT INTO animais (codigo, tipo, nome, raça) "
                    + "VALUES (" + animal.getCodigo() + ", '" + animal.getTipo() + "', '"
                    + animal.getNome() + "', '" + animal.getRaca() + "');");
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public boolean atualizarAnimal(Animais animal) {
        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            String sql = "UPDATE animais SET tipo = '" + animal.getTipo() + "', nome = '"
                    + animal.getNome() + "', raça = '" + animal.getRaca() + "'"
                    + " WHERE codigo = " + animal.getCodigo();
            st.executeUpdate(sql);
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public boolean excluirAnimal(int codigo) {
        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("DELETE FROM animais WHERE codigo = " + codigo);
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public Animais[] getAnimais() {
        Animais[] animais = null;

        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM animais");
            if (rs.next()) {
                rs.last();
                animais = new Animais[rs.getRow()];
                rs.beforeFirst();

                for (int i = 0; rs.next(); i++) {
                    animais[i] = new Animais(rs.getInt("codigo"), rs.getString("tipo"),
                            rs.getString("nome"), rs.getString("raça"));
                }
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return animais;
    }

}