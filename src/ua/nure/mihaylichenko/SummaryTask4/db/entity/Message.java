package ua.nure.mihaylichenko.SummaryTask4.db.entity;


/**
 * Messages entity
 * @author A.Mihaylichenko
 *
 */
public class Message extends Entity {

	private static final long serialVersionUID = 2491174052748545212L;
	
	private int id;
	private int clientId;
	private String message;
	private String subject;
	

	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "Message [id=" + id + ", clientId=" + clientId + ", message="
				+ message + ", subject=" + subject + "]";
	}
	

}
