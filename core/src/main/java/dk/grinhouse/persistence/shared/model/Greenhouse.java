package dk.grinhouse.persistence.shared.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Greenhouse")
public class Greenhouse
{
	@Id
	private int greenhouseId;

	private String greenhouseName;

	private String loginName;

	private String loginPassword;

	public void setGreenhouseId(int greenhouseId)
	{
		this.greenhouseId = greenhouseId;
	}

	public void setLoginName(String loginName)
	{
		this.loginName = loginName;
	}

	public void setGreenhouseName(String greenhouseName)
	{
		this.greenhouseName = greenhouseName;
	}

	public void setLoginPassword(String loginPassword)
	{
		this.loginPassword = loginPassword;
	}

	public int getGreenhouseId()
	{
		return greenhouseId;
	}

	public String getGreenhouseName()
	{
		return greenhouseName;
	}

	public String getLoginName()
	{
		return loginName;
	}

	public String getLoginPassword()
	{
		return loginPassword;
	}

	@Override public String toString()
	{
		return "Greenhouse{" + "greenhouseId=" + greenhouseId + ", greenhouseName='" + greenhouseName + '\'' + ", loginName='"
				+ loginName + '\'' + ", loginPassword='" + loginPassword + '\'' + '}';
	}
}
