package facades;

import entities.Cocktail;
import entities.Profile;
import errorhandling.EntityNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class ProfileFacade implements IFacade<Profile>{

    private static ProfileFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private ProfileFacade() {}

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static IFacade<Profile> getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ProfileFacade();

        }
        return instance;
    }

    @Override
    public Profile create(Profile profile) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(profile);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return profile;
    }

    @Override
    public Profile getById(int id) throws EntityNotFoundException {
        EntityManager em = getEntityManager();
        Profile profile = em.find(Profile.class, id);
        if (profile == null)
            throw new EntityNotFoundException("The Profile entity with ID: "+id+" Was not found");
        return profile;
    }

    @Override
    public List<Profile> getAll() {
        EntityManager em = getEntityManager();
        TypedQuery<Profile> query = em.createQuery("SELECT p FROM Profile p", Profile.class);
        return query.getResultList();
    }

    @Override
    public Profile update(Profile profile) throws EntityNotFoundException {
        EntityManager em = getEntityManager();
        Profile p = em.find(Profile.class,profile.getId());
        if(p == null){
            throw new EntityNotFoundException("Profile with ID: " + profile.getId() + " not found");
        }
        p.setFirstName(profile.getFirstName());
        p.setLastName(profile.getLastName());
        p.setEmail(profile.getEmail());

        em.getTransaction().begin();
        Profile updated = em.merge(profile);
        em.getTransaction().commit();
        return updated;
    }

    @Override
    public Profile delete(int id) throws EntityNotFoundException {
        EntityManager em = getEntityManager();
        Profile p = em.find(Profile.class, id);
        if (p == null)
            throw new EntityNotFoundException("Could not remove Profile with id: "+id);
        em.getTransaction().begin();
        em.remove(p);
        em.getTransaction().commit();
        return p;
    }

    @Override
    public Profile addRelation(int id1, int id2) throws EntityNotFoundException {
        EntityManager em = emf.createEntityManager();
        try{
            Profile profile = em.find(Profile.class,id1);
            if(profile == null){
                throw new EntityNotFoundException("profile with ID: " + id1  + " not found");
            }
            Cocktail cocktail = em.find(Cocktail.class,id2);
            if(cocktail == null){
                throw new EntityNotFoundException("Cocktail with ID: " + id2  + " not found");
            }
            profile.addCocktail(cocktail);
            em.getTransaction().begin();
            Profile updated = em.merge(profile);
            em.getTransaction().commit();
            return updated;
        } finally {
            em.close();
        }
    }

    @Override
    public Profile removeRelation(int id1, int id2) throws EntityNotFoundException {
        EntityManager em = emf.createEntityManager();
        try{
            Profile profile = em.find(Profile.class, id1);
            if(profile == null){
                throw new EntityNotFoundException("Profile with ID: " + id1 + " not found");
            }
            Cocktail cocktail = em.find(Cocktail.class, id2);
            if(cocktail == null){
                throw new EntityNotFoundException("Cocktail with ID: " + id2 + " not found");
            }
            profile.removeCocktail(cocktail);

            em.getTransaction().begin();
            Profile updated = em.merge(profile);
            em.getTransaction().commit();
            return updated;
        } finally {
            em.close();
        }
    }
}

