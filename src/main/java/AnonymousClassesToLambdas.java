

/**
 * Demonstrates registering an instance method as an action listener with a method reference.
 * The source file uses Java's compact source-file form, so it has no explicit class declaration.
 */
/**
 * Registers {@link #save(ActionEvent)} as the button's action handler.
 */
void main() {
    Button button = new Button();
    button.addActionListener(this::save);
}

/**
 * Handles an action sent by a button.
 *
 * @param event the action event to handle
 */
void save(ActionEvent event) {}

/**
 * Describes an action and the object that produced it.
 *
 * @param source the object that generated the action
 */
record ActionEvent(Object source) {}

/**
 * A minimal button model that accepts an action listener.
 */
record Button() {
    /**
     * Registers a listener to receive this button's action events.
     *
     * @param listener the listener to register
     */
    void addActionListener(ActionListener listener) {}
}

/**
 * Receives action events from a button.
 */
interface ActionListener {
    /**
     * Invoked when an action occurs.
     *
     * @param event the event describing the action
     */
    void actionPerformed(ActionEvent event);
}