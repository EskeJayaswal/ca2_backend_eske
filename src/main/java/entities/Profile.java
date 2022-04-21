package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "profile")
@NamedQuery(name = "Profile.deleteAllRows", query = "DELETE from Profile")
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @OneToOne(mappedBy = "profile")
    private User user;

    @JoinTable(
            name = "profile_cocktail",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "cocktail_id"))

    @ManyToMany
    private List<Cocktail> cocktailList;

    public Profile(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        cocktailList = new ArrayList<>();
    }

    public Profile() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        user.setProfile(this);
    }

    public List<Cocktail> getCocktailList() {
        return cocktailList;
    }

    public void addCocktail(Cocktail cocktail) {
        this.cocktailList.add(cocktail);
        if(!cocktail.getProfileList().contains(this)){
            cocktail.addProfile(this);
        }

    }

    public void removeCocktail(Cocktail cocktail) {
        this.cocktailList.remove(cocktail);
        if(!cocktail.getProfileList().contains(this))
            cocktail.getProfileList().remove(this);
    }



    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", user=" + user +
                ", cocktailList=" + cocktailList +
                '}';
    }
}