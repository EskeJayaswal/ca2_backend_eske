package facades;

import dtos.CocktailDTO;
import entities.Cocktail;

import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

//import errorhandling.RenameMeNotFoundException;
import utils.EMF_Creator;
import utils.Utility;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class FacadeExample {

    private static FacadeExample instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private FacadeExample() {}

    public static FacadeExample getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new FacadeExample();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public String getExampleData() throws IOException {

        String data1 = Utility.fetchData("https://api.punkapi.com/v2/beers/1");
        String data2 = Utility.fetchData("https://icanhazdadjoke.com");

        return data1 + "\n" + data2;

    }

    public String getCocktailNames() throws IOException {

        String name = Utility.fetchData("https://www.thecocktaildb.com/api/json/v1/1/search.php?s=all");

        return name;
    }

    public CocktailDTO create(CocktailDTO c){
        Cocktail cocktail = new Cocktail(c.getIngredient1(), c.getIngredient2(), c.getIngredient3());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(cocktail);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new CocktailDTO(cocktail);
    }
    public CocktailDTO getById(int id) { //throws RenameMeNotFoundException {
        EntityManager em = emf.createEntityManager();
        Cocktail c = em.find(Cocktail.class, id);
        //if (c == null)
          //  throw new RenameMeNotFoundException("The RenameMe entity with ID: "+id+" Was not found");
        return new CocktailDTO(c);
    }
    
    //TODO Remove/Change this before use
    public long getCocktailCount(){
        EntityManager em = getEntityManager();
        try{
            long cocktailCount = (long)em.createQuery("SELECT COUNT(c) FROM Cocktail c").getSingleResult();
            return cocktailCount;
        }finally{  
            em.close();
        }
    }
    
    public List<CocktailDTO> getAll(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Cocktail> query = em.createQuery("SELECT c FROM Cocktail c", Cocktail.class);
        List<Cocktail> c = query.getResultList();
        return CocktailDTO.toList(c);
    }
}
