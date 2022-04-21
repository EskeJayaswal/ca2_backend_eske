package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;


@Entity
@Table(name = "cocktail")
@NamedQuery(name = "Cocktail.deleteAllRows", query = "DELETE from Cocktail")
public class Cocktail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // TODO, delete this class, or rename to an Entity class that makes sense for what you are about to do
    // Delete EVERYTHING below if you decide to use this class, it's dummy data used for the initial demo
    @Column(name = "ingredient1")
    private String ingredient1;

    @Column(name = "ingredient2")
    private String ingredient2;

    @Column(name = "ingredient3")
    private String ingredient3;

    @ManyToMany(mappedBy="cocktailList")
    private List<Profile> profileList;
    
    public Cocktail() {
    }

    public Cocktail(String ingredient1, String ingredient2, String ingredient3) {
        this.ingredient1 = ingredient1;
        this.ingredient2 = ingredient2;
        this.ingredient3 = ingredient3;
        this.profileList = new ArrayList<>();
    }

    public void addProfile(Profile profile) {
        this.profileList.add(profile);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIngredient1() {
        return ingredient1;
    }

    public void setIngredient1(String ingredient1) {
        this.ingredient1 = ingredient1;
    }

    public String getIngredient2() {
        return ingredient2;
    }

    public void setIngredient2(String ingredient2) {
        this.ingredient2 = ingredient2;
    }

    public String getIngredient3() {
        return ingredient3;
    }

    public void setIngredient3(String ingredient3) {
        this.ingredient3 = ingredient3;
    }

    public List<Profile> getProfileList() {
        return profileList;
    }

    public void setProfileList(List<Profile> profileList) {
        this.profileList = profileList;
    }

    @Override
    public String toString() {
        return "Cocktail{" +
                "id=" + id +
                ", ingredient1='" + ingredient1 + '\'' +
                ", ingredient2='" + ingredient2 + '\'' +
                ", ingredient3='" + ingredient3 + '\'' +
                ", profileList=" + profileList +
                '}';
    }
}
