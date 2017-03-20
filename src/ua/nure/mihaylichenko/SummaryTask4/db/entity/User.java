package ua.nure.mihaylichenko.SummaryTask4.db.entity;

import java.sql.Date;

import ua.nure.mihaylichenko.SummaryTask4.db.enumeration.Language;
import ua.nure.mihaylichenko.SummaryTask4.db.enumeration.UserStatus;

/**
 * User entity
 * @author A.Mihaylichenko
 *
 */
public class User extends Entity {

	private static final long serialVersionUID = -8934817946545930786L;
	
	private int id;
	private String login;
	private String password;
	private String name;
	private String surName;
	private Date birthDay;
	private String mail;
	private Language language;
	private UserStatus userStatus;
	private int roleId;
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}
	
	public void setMail(String eMail) {
		this.mail = eMail;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", password=" + password + ", name=" + name + ", surName="
				+ surName + ", birthDay=" + birthDay + ", mail=" + mail + ", language=" + language + ", userStatus="
				+ userStatus + ", roleId=" + roleId + "]";
	}
	
}
