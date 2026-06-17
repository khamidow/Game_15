package uz.mobiler.gitagame15;

public class ModelData {
    private final int img;
    private final String type;
    private final String time;
    private final String move;

    public ModelData(int img, String type, String time, String move) {
        this.img = img;
        this.type = type;
        this.time = time;
        this.move = move;
    }

    public int getImg() {
        return img;
    }

    public String getType() {
        return type;
    }

    public String getTime() {
        return time;
    }

    public String getMove() {
        return move;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModelData modelData = (ModelData) o;

        if (img != modelData.img) return false;
        if (!type.equals(modelData.type)) return false;
        if (!time.equals(modelData.time)) return false;
        return move.equals(modelData.move);
    }

    @Override
    public int hashCode() {
        int result = img;
        result = 31 * result + type.hashCode();
        result = 31 * result + time.hashCode();
        result = 31 * result + move.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ModelData{" +
                "img=" + img +
                ", type='" + type + '\'' +
                ", time='" + time + '\'' +
                ", move='" + move + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int img;
        private String type;
        private String time;
        private String move;

        public Builder img(int img) {
            this.img = img;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder time(String time) {
            this.time = time;
            return this;
        }

        public Builder move(String move) {
            this.move = move;
            return this;
        }

        public ModelData build() {
            return new ModelData(img, type, time, move);
        }
    }
}
