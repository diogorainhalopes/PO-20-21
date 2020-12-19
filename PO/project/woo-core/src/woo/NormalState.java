package woo;


public class NormalState extends Client.ClientState {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009192006L;
    /**
     * @param state
     */
    NormalState(Client client) {
        client.super();
    }
    /** Promotes the client to a SelectionState */
    @Override
    public void promote(){
        setState(new SelectionState(getClient()));
    }

    /** Lowest State possible. Can't be further demoted. */
    @Override
    public void demote() {}

    /**
     * @see Client.ClientState#status()
     */
    @Override
    public String status() {
        return "NORMAL";
    }
}






