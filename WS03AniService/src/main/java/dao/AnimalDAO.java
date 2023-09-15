package dao;

import model.Animal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AnimalDAO extends DAO {
    public AnimalDAO() {
        super();
        conectar();
    }

    public void finalize() {
        close();
    }

    public boolean insert(Animal animal) {
        boolean status = false;
        try {
            String sql = "INSERT INTO animal (nome, especie, idade) "
                       + "VALUES (?, ?, ?);";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, animal.getNome());
            st.setString(2, animal.getEspecie());
            st.setInt(3, animal.getIdade());
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public Animal get(int id) {
        Animal animal = null;

        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM animal WHERE id=" + id;
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                animal = new Animal(rs.getInt("id"), rs.getString("nome"), rs.getString("especie"),
                        rs.getInt("idade"));
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return animal;
    }

    public List<Animal> getAll() {
        return getAll("");
    }

    public List<Animal> getOrderByID() {
        return getAll("id");
    }

    public List<Animal> getOrderByNome() {
        return getAll("nome");
    }

    public List<Animal> getOrderByEspecie() {
        return getAll("especie");
    }

    private List<Animal> getAll(String orderBy) {
        List<Animal> animais = new ArrayList<Animal>();

        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM animal" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Animal a = new Animal(rs.getInt("id"), rs.getString("nome"), rs.getString("especie"),
                        rs.getInt("idade"));
                animais.add(a);
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return animais;
    }

    public boolean update(Animal animal) {
        boolean status = false;
        try {
            String sql = "UPDATE animal SET nome = ?, especie = ?, idade = ? WHERE id = ?";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, animal.getNome());
            st.setString(2, animal.getEspecie());
            st.setInt(3, animal.getIdade());
            st.setInt(4, animal.getId());
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public boolean delete(int id) {
        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("DELETE FROM animal WHERE id = " + id);
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }
}
