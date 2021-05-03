package dk.grinhouse.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Greenhouse", schema = "dbo")
public class Greenhouse
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "greenhouseId")
	private int greenhouseId;

	@Column(name = "greenhouseName", columnDefinition = "nvarchar")
	private String greenhouseName;

	@Column(name = "loginName", columnDefinition = "nvarchar")
	private String loginName;

	@Column(name = "loginPassword", columnDefinition = "nvarchar")
	private String loginPassword;

	public Greenhouse()
	{
		super();
	}

	public Greenhouse(String greenhouseName, String loginName, String loginPassword)
	{
		this.greenhouseName = greenhouseName;
		this.loginName = loginName;
		this.loginPassword = loginPassword;
	}

	@OneToMany
	@JoinColumn(name = "belongsTo", referencedColumnName = "greenhouseId")
	private List<Measurement> measurements;

	@OneToMany
	@JoinColumn(name = "logs", referencedColumnName = "greenhouseId")
	private List<ACState> acStates;

	@OneToMany
	@JoinColumn(name = "logs", referencedColumnName = "greenhouseId")
	private List<HumidifierState> humidifierStates;

	@OneToMany
	@JoinColumn(name = "logs", referencedColumnName = "greenhouseId")
	private List<CarbonDioxideGeneratorState> carbonDioxideGeneratorStates;

	@OneToMany
	@JoinColumn(name = "storedIn", referencedColumnName = "greenhouseId")
	private List<ThresholdProfile> thresholdProfiles;

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
