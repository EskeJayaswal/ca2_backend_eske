package dtos;

import entities.Cocktail;
import java.util.List;
import java.util.stream.Collectors;


public class CocktailDTO {
    private int id;
    private String ingredient1;
    private String ingredient2;
    private String ingredient3;



    public CocktailDTO(Cocktail cocktail) {
        if(cocktail.getId() != 0)
            this.id = cocktail.getId();
        this.ingredient1 = cocktail.getIngredient1();
        this.ingredient2 = cocktail.getIngredient2();
        this.ingredient3 = cocktail.getIngredient3();
    }

    public Cocktail getEntity() {
        Cocktail c = new Cocktail(this.ingredient1, this.ingredient2, this.ingredient3);
        c.setId(this.id);
        return c;
    }
    public static List<CocktailDTO> toList(List<Cocktail> cocktails) {
        return cocktails.stream().map(CocktailDTO::new).collect(Collectors.toList());
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

    @Override
    public String toString() {
        return "CocktailDTO{" +
                "id=" + id +
                ", ingredient1='" + ingredient1 + '\'' +
                ", ingredient2='" + ingredient2 + '\'' +
                ", ingredient3='" + ingredient3 + '\'' +
                '}';
    }
}
