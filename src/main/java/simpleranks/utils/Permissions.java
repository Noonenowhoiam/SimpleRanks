package simpleranks.utils;

public enum Permissions {

    SETUP_RANK_CREATE("simpleranks.rank.create"), SETUP_RANK_DELETE("simpleranks.rank.delete"), SETUP_RANK_MODIFY("simpleranks.rank.modify"),
    SETUP_RANK_INFO("simpleranks.rank.info"), SETUP_RANK_LIST("simpleranks.rank.list"),
    GET_RANK("simpleranks.rank.get"),
    SET_RANK("simpleranks.rank.set"),
    CONFIG("simpleranks.config");

    Permissions(String perm) {
        this.perm = perm;
    }
    private final String perm;
    public String perm() { return perm; }
}
