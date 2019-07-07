package geneticAlgorithm;

public class Entity implements Comparable<Entity> {

    private int posX;
    private double functionValue;

    public Entity(int x) {
        this.posX = x;
    }

    public Entity(int x, double v) {
        this.posX = x;
        this.functionValue = v;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public double getFunctionValue() {
        return functionValue;
    }

    public void setFunctionValue(double functionValue) {
        this.functionValue = functionValue;
    }

    @Override
    public int compareTo(Entity arg0) {
        if (this.functionValue < arg0.functionValue) {
            return -1;
        } else if (this.functionValue > arg0.functionValue) {
            return 1;
        }
        return 0;
    }
}
