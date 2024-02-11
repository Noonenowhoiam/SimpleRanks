package simpleranks.utils;

public enum Permissions {

    GET_RANK("simpleranks.rank.get"),
    SET_RANK("simpleranks.rank.set"),
    CONFIG("simpleranks.config");

    Permissions(String perm) {
        this.perm = perm;
    }
    private final String perm;
    public String perm() { return perm; }
}
