package dtoFacades;

import dtos.CocktailDTO;
import entities.Cocktail;
import errorhandling.EntityNotFoundException;
import facades.CocktailFacade;
import facades.IFacade;
import utils.EMF_Creator;
import utils.Utility;
import java.io.IOException;
import java.util.List;

public class CocktailDTOFacade implements IFacade<CocktailDTO> {
    private static IFacade<CocktailDTO> instance;
    private static IFacade<Cocktail> cocktailFacade;


    public CocktailDTOFacade() {
    }

    public static IFacade<CocktailDTO> getFacade() {
        if (instance == null) {
            cocktailFacade = CocktailFacade.getFacade(EMF_Creator.createEntityManagerFactory());
            instance = new CocktailDTOFacade();
        }
        return instance;
    }

    //TODO Remove/Change this before use
    public static String getExampleData() throws IOException {

        String data1 = Utility.fetchData("https://api.punkapi.com/v2/beers/1");
        String data2 = Utility.fetchData("https://icanhazdadjoke.com");

        return data1 + "\n" + data2;
    }

    @Override
    public CocktailDTO create(CocktailDTO cocktailDTO) {
        Cocktail c = cocktailDTO.getEntity();
        c = cocktailFacade.create(c);
        return new CocktailDTO(c);
    }

    @Override
    public CocktailDTO getById(int id) throws EntityNotFoundException {
        return new CocktailDTO(cocktailFacade.getById(id));
    }

    @Override
    public List<CocktailDTO> getAll() {
        return CocktailDTO.toList(cocktailFacade.getAll());
    }

    @Override
    public CocktailDTO update(CocktailDTO cocktailDTO) throws EntityNotFoundException {
        Cocktail cocktail = new Cocktail(cocktailDTO.getIngredient1(), cocktailDTO.getIngredient2(), cocktailDTO.getIngredient3());
        cocktail.setId(cocktailDTO.getId());
        Cocktail c = cocktailFacade.update(cocktail);
        return new CocktailDTO(c);
    }

    @Override
    public CocktailDTO delete(int id) throws EntityNotFoundException {
        return new CocktailDTO(cocktailFacade.delete(id));
    }

    @Override
    public CocktailDTO addRelation(int id1, int id2) throws EntityNotFoundException {
        return null;
    }

    @Override
    public CocktailDTO removeRelation(int id1, int id2) throws EntityNotFoundException {
        return null;
    }
}