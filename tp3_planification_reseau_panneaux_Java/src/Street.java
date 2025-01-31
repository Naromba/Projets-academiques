public class Street {
    private String name;
    private Site start;
    private Site end;
    private int cost;

    public Street(String name, Site start, Site end, int cost) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public Site getStart() {
        return start;
    }

    public Site getEnd() {
        return end;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "Street{name='" + name + "', start=" + start + ", end=" + end + ", cost=" + cost + "}";
    }
}
