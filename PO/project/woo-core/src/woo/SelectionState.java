package woo;

public class SelectionState extends Client.ClientState {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009192006L;
    
    /**
     * @param state
     */
    SelectionState(Client client) {
        client.super();
    }
    /** Promotes the client to a EliteState */
    @Override
    public void promote(){
        setState(new EliteState(getClient()));
    }

    /** Demotes the client to a NormalState */
    @Override
    public void demote() {
        setState(new NormalState(getClient()));
    }

    /**
     * @see Client.ClientState#status()
     */
    @Override
    public String status() {
        return "SELECTION";
    }
}
