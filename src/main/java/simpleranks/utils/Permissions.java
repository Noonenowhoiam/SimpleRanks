package simpleranks.utils;

public enum Permissions {

    SETUP_RANK_CREATE("simpleranks.rank.create"), SETUP_RANK_DELETE("simpleranks.rank.delete"), SETUP_RANK_MODIFY("simpleranks.rank.modify"),
    SETUP_RANK_INFO("simpleranks.rank.info"), SETUP_RANK_LIST("simpleranks.rank.list"),
    SETUP_GROUP_LIST(""), SETUP_GROUP_CREATE(""), SETUP_GROUP_MODIFY(""),  SETUP_GROUP_INFO(""), SETUP_GROUP_DELETE(""), //TODO
    GET_RANK("simpleranks.playerrank.get"),
    SET_RANK("simpleranks.playerrank.set"), SET_RANK_ALL("simpleranks.playerrank.set.all"),
    CONFIG("simpleranks.config");

    Permissions(String perm) {
        this.perm = perm;
    }
    private final String perm;
    public String perm() { return perm; }
}
