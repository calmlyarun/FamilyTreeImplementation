/**
 * 
 */
package family.tree.objects;

/**
 * @author AR266832
 *
 */
@SuppressWarnings("serial")
public class FamilyTreeException extends Exception {

	private String errorMsg;
	public FamilyTreeException(String msg) {
		setErrorMsg(msg);
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	
}
