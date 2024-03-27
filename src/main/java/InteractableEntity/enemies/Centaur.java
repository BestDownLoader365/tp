package InteractableEntity.enemies;

import InteractableEntity.Enemy;
import static filereader.filepath.EnemiesDesignFilePath.CENTAUR_PATH;

public class Centaur extends Enemy {

    public Centaur(int dmg, int def, int hp, int xCoordinate, int yCoordinate, int exp, int money) {
        super(dmg, def, hp, xCoordinate, yCoordinate, exp, money);
        this.name = "Centaur";
        this.filePath = CENTAUR_PATH;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
