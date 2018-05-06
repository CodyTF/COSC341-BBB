package edu.iup.cosc341.bbb;

/**
 * 
 * @author David T. Smith
 *
 * String constants used in BBB application
 * 
 */
public final class Constants {
    /**
     * The session scope attribute name under which the Customer object
     * for the currently logged in customer is stored.
     */
    public static final String CUSTOMER_KEY = "customer";
    
    /**
     * The session scope attribute name under which the Administrator object
     * for the currently logged in administrator is stored.
     */
    public static final String ADMINISTRATOR_KEY = "administrator";
    
    /**
     * The session scope attribute name under which the in progress Book Order object
     * is stored.
     */
    public static final String CART_KEY = "cart";
    
    /**
     * The session scope attribute name under which the last query results is stored.
     */
    public static final String BOOK_QUERY_KEY = "bookQuery";

    /**
     * Temporary session scope attribute name under which the forward action of a cancel
     * button is stored.
     */
	public static final String CANCEL_KEY = "cancel";

    /**
     * Temporary session scope attribute name under which the forward action of a pending
     * action is stored.
     */
	public static final String PENDING_KEY = "pending";

    /**
     * Page scope attribute name under which the welcome title string
     * is stored.
     */
	public static final String WELCOME_KEY = "welcome";

    /**
     * Page scope attribute name under which a book is stored
     * is stored.
     */
	public static final String BOOK_KEY = "book";

    /**
     * Page scope attribute name under which results of a report query
     * is stored.
     */
	public static final String RESULTS_KEY = "results";
}
