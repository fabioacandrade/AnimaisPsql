package service;

import java.util.Scanner;
import java.time.LocalDate;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import dao.AnimalDAO;
import model.Animal;
import spark.Request;
import spark.Response;


public class AnimalService {

    private AnimalDAO animalDAO = new AnimalDAO();
    private String form;
    private final int FORM_INSERT = 1;
    private final int FORM_DETAIL = 2;
    private final int FORM_UPDATE = 3;
    private final int FORM_ORDERBY_ID = 1;
    private final int FORM_ORDERBY_NOME = 2;
    private final int FORM_ORDERBY_ESPECIE = 3;
    
    
    public AnimalService() {
        makeForm();
    }

    
    public void makeForm() {
        makeForm(FORM_INSERT, new Animal(), FORM_ORDERBY_NOME);
    }

    
    public void makeForm(int orderBy) {
        makeForm(FORM_INSERT, new Animal(), orderBy);
    }

    
    public void makeForm(int tipo, Animal animal, int orderBy) {
        String nomeArquivo = "form.html";
        form = "";
        try{
            Scanner entrada = new Scanner(new File(nomeArquivo));
            while(entrada.hasNext()){
                form += (entrada.nextLine() + "\n");
            }
            entrada.close();
        }  catch (Exception e) { System.out.println(e.getMessage()); }
        
        String umAnimal = "";
        if(tipo != FORM_INSERT) {
            umAnimal += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
            umAnimal += "\t\t<tr>";
            umAnimal += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/animal/list/1\">Novo Animal</a></b></font></td>";
            umAnimal += "\t\t</tr>";
            umAnimal += "\t</table>";
            umAnimal += "\t<br>";            
        }
        
        if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
            String action = "/animal/";
            String name, nome, buttonLabel;
            if (tipo == FORM_INSERT){
                action += "insert";
                name = "Inserir Animal";
                nome = "Cachorro, Gato, ...";
                buttonLabel = "Inserir";
            } else {
                action += "update/" + animal.getID();
                name = "Atualizar Animal (ID " + animal.getID() + ")";
                nome = animal.getNome();
                buttonLabel = "Atualizar";
            }
            umAnimal += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
            umAnimal += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
            umAnimal += "\t\t<tr>";
            umAnimal += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
            umAnimal += "\t\t</tr>";
            umAnimal += "\t\t<tr>";
            umAnimal += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
            umAnimal += "\t\t</tr>";
            umAnimal += "\t\t<tr>";
            umAnimal += "\t\t\t<td>&nbsp;Nome: <input class=\"input--register\" type=\"text\" name=\"nome\" value=\""+ nome +"\"></td>";
            umAnimal += "\t\t\t<td>Idade: <input class=\"input--register\" type=\"text\" name=\"idade\" value=\""+ animal.getIdade() +"\"></td>";
            umAnimal += "\t\t\t<td>Espécie: <input class=\"input--register\" type=\"text\" name=\"especie\" value=\""+ animal.getEspecie() +"\"></td>";
            umAnimal += "\t\t</tr>";
            umAnimal += "\t\t<tr>";
            umAnimal += "\t\t\t<td>&nbsp;Data de Nascimento: <input class=\"input--register\" type=\"text\" name=\"dataNascimento\" value=\""+ animal.getDataNascimento().toString() + "\"></td>";
            umAnimal += "\t\t\t<td>Peso: <input class=\"input--register\" type=\"text\" name=\"peso\" value=\""+ animal.getPeso() + "\"></td>";
            umAnimal += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
            umAnimal += "\t\t</tr>";
            umAnimal += "\t</table>";
            umAnimal += "\t</form>";        
        } else if (tipo == FORM_DETAIL){
            umAnimal += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
            umAnimal += "\t\t<tr>";
            umAnimal += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Animal (ID " + animal.getID() + ")</b></font></td>";
            umAnimal += "\t\t</tr>";
            umAnimal += "\t\t<tr>";
            umAnimal += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
            umAnimal += "\t\t</tr>";
            umAnimal += "\t\t<tr>";
            umAnimal += "\t\t\t<td>&nbsp;Nome: "+ animal.getNome() +"</td>";
            umAnimal += "\t\t\t<td>Idade: "+ animal.getIdade() +"</td>";
            umAnimal += "\t\t\t<td>Espécie: "+ animal.getEspecie() +"</td>";
            umAnimal += "\t\t</tr>";
            umAnimal += "\t\t<tr>";
            umAnimal += "\t\t\t<td>&nbsp;Data de Nascimento: "+ animal.getDataNascimento().toString() + "</td>";
            umAnimal += "\t\t\t<td>Peso: "+ animal.getPeso() + "</td>";
            umAnimal += "\t\t\t<td>&nbsp;</td>";
            umAnimal += "\t\t</tr>";
            umAnimal += "\t</table>";        
        } else {
            System.out.println("ERRO! Tipo não identificado " + tipo);
        }
        form = form.replaceFirst("<UM-ANIMAL>", umAnimal);
        
        String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
        list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Animais</b></font></td></tr>\n" +
                "\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
                "\n<tr>\n" + 
                "\t<td><a href=\"/animal/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
                "\t<td><a href=\"/animal/list/" + FORM_ORDERBY_NOME + "\"><b>Nome</b></a></td>\n" +
                "\t<td><a href=\"/animal/list/" + FORM_ORDERBY_ESPECIE + "\"><b>Espécie</b></a></td>\n" +
                "\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
                "\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
                "\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
                "</tr>\n";
        
        List<Animal> animais;
        if (orderBy == FORM_ORDERBY_ID) {                    animais = animalDAO.getOrderByID();
        } else if (orderBy == FORM_ORDERBY_NOME) {            animais = animalDAO.getOrderByNome();
        } else if (orderBy == FORM_ORDERBY_ESPECIE) {        animais = animalDAO.getOrderByEspecie();
        } else {                                            animais = animalDAO.get();
        }

        int i = 0;
        String bgcolor = "";
        for (Animal a : animais) {
            bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
            list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
                      "\t<td>" + a.getID() + "</td>\n" +
                      "\t<td>" + a.getNome() + "</td>\n" +
                      "\t<td>" + a.getEspecie() + "</td>\n" +
                      "\t<td align=\"center\" valign=\"middle\"><a href=\"/animal/" + a.getID() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
                      "\t<td align=\"center\" valign=\"middle\"><a href=\"/animal/update/" + a.getID() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
                      "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteAnimal('" + a.getID() + "', '" + a.getNome() + "', '" + a.getEspecie() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
                      "</tr>\n";
        }
        list += "</table>";        
        form = form.replaceFirst("<LISTAR-ANIMAL>", list);                
    }
    
    
    public Object insert(Request request, Response response) {
        String nome = request.queryParams("nome");
        int idade = Integer.parseInt(request.queryParams("idade"));
        String especie = request.queryParams("especie");
        LocalDateTime dataNascimento = LocalDateTime.parse(request.queryParams("dataNascimento"));
        float peso = Float.parseFloat(request.queryParams("peso"));
        
        String resp = "";
        
        Animal animal = new Animal(-1, nome, idade, especie, dataNascimento, peso);
        
        if(animalDAO.insert(animal) == true) {
            resp = "Animal (" + nome + ") inserido!";
            response.status(201); // 201 Created
        } else {
            resp = "Animal (" + nome + ") não inserido!";
            response.status(404); // 404 Not found
        }
            
        makeForm();
        return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
    }

    
    public Object get(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));        
        Animal animal = (Animal) animalDAO.get(id);
        
        if (animal != null) {
            response.status(200); // success
            makeForm(FORM_DETAIL, animal, FORM_ORDERBY_NOME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Animal " + id + " não encontrado.";
            makeForm();
            form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

        return form;
    }

    
    public Object getToUpdate(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));        
        Animal animal = (Animal) animalDAO.get(id);
        
        if (animal != null) {
            response.status(200); // success
            makeForm(FORM_UPDATE, animal, FORM_ORDERBY_NOME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Animal " + id + " não encontrado.";
            makeForm();
            form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

        return form;
    }
    
    
    public Object getAll(Request request, Response response) {
        int orderBy = Integer.parseInt(request.params(":orderby"));
        makeForm(orderBy);
        response.header("Content-Type", "text/html");
        response.header("Content-Encoding", "UTF-8");
        return form;
    }           
    
    public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Animal animal = animalDAO.get(id);
        String resp = "";       

        if (animal != null) {
            animal.setNome(request.queryParams("nome"));
            animal.setIdade(Integer.parseInt(request.queryParams("idade")));
            animal.setEspecie(request.queryParams("especie"));
            animal.setDataNascimento(LocalDateTime.parse(request.queryParams("dataNascimento")));
            animal.setPeso(Float.parseFloat(request.queryParams("peso")));
            animalDAO.update(animal);
            response.status(200); // success
            resp = "Animal (ID " + animal.getID() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Animal (ID " + animal.getId() + ") não encontrado!";
        }
        makeForm();
        return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
    }

    
    public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Animal animal = animalDAO.get(id);
        String resp = "";       

        if (animal != null) {
            animalDAO.delete(id);
            response.status(200); // success
            resp = "Animal (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Animal (" + id + ") não encontrado!";
        }
        makeForm();
        return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
    }
}
