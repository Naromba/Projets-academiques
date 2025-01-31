public class Site {
    private String name;
    private int id;

    public Site(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Site{name='" + name + "', id=" + id + "}";
    }
}
