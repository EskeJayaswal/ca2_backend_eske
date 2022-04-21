package facades;

import entities.Cocktail;
import entities.Profile;
import errorhandling.EntityNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class CocktailFacade implements IFacade<Cocktail>{
    private static CocktailFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private CocktailFacade() {}

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static IFacade<Cocktail> getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CocktailFacade();
        }
        return instance;
    }


    @Override
    public Cocktail create(Cocktail cocktail) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(cocktail);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return cocktail;
    }

    @Override
    public Cocktail getById(int id) throws EntityNotFoundException {
        EntityManager em = getEntityManager();
        Cocktail cocktail = em.find(Cocktail.class, id);
        if (cocktail == null)
            throw new EntityNotFoundException("The RenameMe entity with ID: "+id+" Was not found");
        return cocktail;
    }

    @Override
    public List<Cocktail> getAll() {
        EntityManager em = getEntityManager();
        TypedQuery<Cocktail> query = em.createQuery("SELECT c FROM Cocktail c", Cocktail.class);
        return query.getResultList();
    }

    @Override
    public Cocktail update(Cocktail cocktail) throws EntityNotFoundException {
        EntityManager em = getEntityManager();
        Cocktail c = em.find(Cocktail.class,cocktail.getId());
        if(c == null){
            throw new EntityNotFoundException("Cocktail with ID: " + cocktail.getId() + " not found");
        }
        c.setIngredient1(cocktail.getIngredient1());
        c.setIngredient2(cocktail.getIngredient2());
        c.setIngredient3(cocktail.getIngredient3());

        em.getTransaction().begin();
        Cocktail updated = em.merge(cocktail);
        em.getTransaction().commit();
        return updated;
    }

    @Override
    public Cocktail delete(int id) throws EntityNotFoundException {
        EntityManager em = getEntityManager();
        Cocktail c = em.find(Cocktail.class, id);
        if (c == null)
            throw new EntityNotFoundException("Could not remove cocktail with id: "+id);
        em.getTransaction().begin();
        em.remove(c);
        em.getTransaction().commit();
        return c;
    }

    @Override
    public Cocktail addRelation(int id1, int id2) throws EntityNotFoundException {
        return null;
    }

    @Override
    public Cocktail removeRelation(int id1, int id2) throws EntityNotFoundException {
        return null;
    }
}
