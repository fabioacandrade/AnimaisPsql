package app;

import static spark.Spark.*;
import service.AnimalService; // Updated to AnimalService

public class Aplicacao {
	
	private static AnimalService animalService = new AnimalService(); // Updated to AnimalService
	
    public static void main(String[] args) {
        port(6789);
        
        staticFiles.location("/public");
        
        post("/animal/insert", (request, response) -> animalService.insert(request, response)); // Updated to animalService
        get("/animal/:id", (request, response) -> animalService.get(request, response)); // Updated to animalService
        get("/animal/list/:orderby", (request, response) -> animalService.getAll(request, response)); // Updated to animalService
        get("/animal/update/:id", (request, response) -> animalService.getToUpdate(request, response)); // Updated to animalService
        post("/animal/update/:id", (request, response) -> animalService.update(request, response)); // Updated to animalService
        get("/animal/delete/:id", (request, response) -> animalService.delete(request, response)); // Updated to animalService
    }
}
