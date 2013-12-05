package edu.rit.csh;

import org.apache.wicket.markup.html.panel.Panel;

/**
 * Panel containing a form that will search the owned
 * books in the database
 * @author scott
 *
 */
public class SearchOwnedBookPanel extends Panel {
	private static final long serialVersionUID = -7887042907526741946L;
	
	private final SearchOwnedBookForm form;
	
	public SearchOwnedBookPanel(String id){
		super(id);
		form = new SearchOwnedBookForm("searchOwnedBookForm");
		add(form);
	}

}
