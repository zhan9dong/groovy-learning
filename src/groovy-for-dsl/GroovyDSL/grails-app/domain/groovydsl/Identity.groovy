package groovydsl

class Identity {
    String email
    String password

    static belongsTo = [CustomerHasIdentity]
}
