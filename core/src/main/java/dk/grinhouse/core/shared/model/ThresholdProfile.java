package dk.grinhouse.core.shared.model;

import javax.persistence.*;

@Entity
@Table(name = "thresholdProfile", schema = "stage")
public class ThresholdProfile
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "thresholdProfileId")
	private int thresholdProfileId;

	@Column(name = "profileName", columnDefinition = "nvarchar")
	private String profileName;

	@Column(name = "active")
	private boolean active;

	@Column(name = "minimumTemperature")
	private float minimumTemperature;

	@Column(name = "maximumTemperature")
	private float maximumTemperature;

	@Column(name = "minimumHumidity")
	private float minimumHumidity;

	@Column(name = "maximumHumidity")
	private float maximumHumidity;

	@Column(name = "minimumCarbonDioxide")
	private float minimumCarbonDioxide;

	@Column(name = "maximumCarbonDioxide")
	private float maximumCarbonDioxide;

	@Column(name = "storedIn")
	private int greenhouseId;

	public int getGreenhouseId()
	{
		return greenhouseId;
	}

	public float getMaximumCarbonDioxide()
	{
		return maximumCarbonDioxide;
	}

	public float getMaximumHumidity()
	{
		return maximumHumidity;
	}

	public float getMaximumTemperature()
	{
		return maximumTemperature;
	}

	public float getMinimumCarbonDioxide()
	{
		return minimumCarbonDioxide;
	}

	public float getMinimumHumidity()
	{
		return minimumHumidity;
	}

	public float getMinumumTemperature()
	{
		return minimumTemperature;
	}

	public int getThresholdProfileId()
	{
		return thresholdProfileId;
	}

	public String getProfileName()
	{
		return profileName;
	}

	public void setGreenhouseId(int greenhouseId)
	{
		this.greenhouseId = greenhouseId;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}

	public void setMaximumCarbonDioxide(float maximumCarbonDioxide)
	{
		this.maximumCarbonDioxide = maximumCarbonDioxide;
	}

	public void setMaximumHumidity(float maximumHumidity)
	{
		this.maximumHumidity = maximumHumidity;
	}

	public void setMaximumTemperature(float maximumTemperature)
	{
		this.maximumTemperature = maximumTemperature;
	}

	public void setMinimumCarbonDioxide(float minimumCarbonDioxide)
	{
		this.minimumCarbonDioxide = minimumCarbonDioxide;
	}

	public void setMinimumHumidity(float minimumHumidity)
	{
		this.minimumHumidity = minimumHumidity;
	}

	public void setMinimumTemperature(float minimumTemperature)
	{
		this.minimumTemperature = minimumTemperature;
	}

	public void setProfileName(String profileName)
	{
		this.profileName = profileName;
	}

	public void setThresholdProfileId(int thresholdProfileId)
	{
		this.thresholdProfileId = thresholdProfileId;
	}
}
