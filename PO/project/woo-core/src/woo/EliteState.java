package woo;

public class EliteState extends Client.ClientState {
    
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009192006L;
    /**
     * @param state
     */
    EliteState(Client client) {
        client.super();
    }

    /** Highest State possible. Can't be further promoted. */
    @Override
    public void promote() {}

    /** Demotes the client to a SelectionState */
    @Override
    public void demote() {
        setState(new SelectionState(getClient()));
    }

    /**
     * @see Client.ClientState#status()
     */
    @Override
    public String status() {
        return "ELITE";
    }
}
