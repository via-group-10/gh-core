package dk.grinhouse.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

@Entity
@Table(name = "ThresholdProfile", schema = "dbo")
public class ThresholdProfile
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "thresholdProfileId")
	private int thresholdProfileId;

	@Column(name = "profileName", columnDefinition = "nvarchar(100)")
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

	public ThresholdProfile()
	{
	}

	public ThresholdProfile(int thresholdProfileId, String profileName, boolean active,
		float minimumTemperature, float maximumTemperature, float minimumHumidity,
		float maximumHumidity, float minimumCarbonDioxide, float maximumCarbonDioxide,
		int greenhouseId)
	{
		this.thresholdProfileId = thresholdProfileId;
		this.profileName = profileName;
		this.active = active;
		this.minimumTemperature = minimumTemperature;
		this.maximumTemperature = maximumTemperature;
		this.minimumHumidity = minimumHumidity;
		this.maximumHumidity = maximumHumidity;
		this.minimumCarbonDioxide = minimumCarbonDioxide;
		this.maximumCarbonDioxide = maximumCarbonDioxide;
		this.greenhouseId = greenhouseId;
	}

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

	public float getMinimumTemperature()
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

	public boolean isActive()
	{
		return active;
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

	@JsonIgnore
	public byte[] getThresholdsInBytes()
	{
		byte[] data = new byte[12];
		data[0] = (byte)(((int) minimumTemperature) >> 8);
		data[1] = (byte)(((int) minimumTemperature));
		data[2] = (byte)(((int) maximumTemperature) >> 8);
		data[3] = (byte)(((int) maximumTemperature));
		data[4] = (byte)(((int) minimumHumidity) >> 8);
		data[5] = (byte)(((int) minimumHumidity));
		data[6] = (byte)(((int) maximumHumidity) >> 8);
		data[7] = (byte)(((int) maximumHumidity));
		data[8] = (byte)(((int) minimumCarbonDioxide) >> 8);
		data[9] = (byte)(((int) minimumCarbonDioxide));
		data[10] = (byte)(((int) maximumCarbonDioxide) >> 8);
		data[11] = (byte)(((int) maximumCarbonDioxide));
		return data;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		ThresholdProfile that = (ThresholdProfile) o;

		if (thresholdProfileId != that.thresholdProfileId)
			return false;
		if (active != that.active)
			return false;
		if (Float.compare(that.minimumTemperature, minimumTemperature) != 0)
			return false;
		if (Float.compare(that.maximumTemperature, maximumTemperature) != 0)
			return false;
		if (Float.compare(that.minimumHumidity, minimumHumidity) != 0)
			return false;
		if (Float.compare(that.maximumHumidity, maximumHumidity) != 0)
			return false;
		if (Float.compare(that.minimumCarbonDioxide, minimumCarbonDioxide) != 0)
			return false;
		if (Float.compare(that.maximumCarbonDioxide, maximumCarbonDioxide) != 0)
			return false;
		if (greenhouseId != that.greenhouseId)
			return false;
		return profileName != null ?
			profileName.equals(that.profileName) :
			that.profileName == null;
	}

	@Override
	public int hashCode()
	{
		int result = thresholdProfileId;
		result = 31 * result + (profileName != null ? profileName.hashCode() : 0);
		result = 31 * result + (active ? 1 : 0);
		result = 31 * result + (minimumTemperature != +0.0f ?
			Float.floatToIntBits(minimumTemperature) :
			0);
		result = 31 * result + (maximumTemperature != +0.0f ?
			Float.floatToIntBits(maximumTemperature) :
			0);
		result =
			31 * result + (minimumHumidity != +0.0f ? Float.floatToIntBits(minimumHumidity) : 0);
		result =
			31 * result + (maximumHumidity != +0.0f ? Float.floatToIntBits(maximumHumidity) : 0);
		result = 31 * result + (minimumCarbonDioxide != +0.0f ?
			Float.floatToIntBits(minimumCarbonDioxide) :
			0);
		result = 31 * result + (maximumCarbonDioxide != +0.0f ?
			Float.floatToIntBits(maximumCarbonDioxide) :
			0);
		result = 31 * result + greenhouseId;
		return result;
	}
}
