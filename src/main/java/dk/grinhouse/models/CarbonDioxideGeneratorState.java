package dk.grinhouse.models;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "CarbonDioxideGeneratorState", schema = "dbo")
public class CarbonDioxideGeneratorState
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "carbonDioxideGeneratorId")
	private int carbonDioxideGeneratorStateId;

	@Column(name = "isCarbonDioxideGeneratorOn")
	private boolean isCarbonDioxideGeneratorOn;

	@Column(name = "stateDateTime")
	private Timestamp stateDateTime;

	@Column(name = "logs")
	private int greenhouseId;

	public Timestamp getStateDateTime()
	{
		return stateDateTime;
	}

	public int getGreenhouseId()
	{
		return greenhouseId;
	}

	public int getCarbonDioxideGeneratorId()
	{
		return carbonDioxideGeneratorStateId;
	}

	public void setStateDateTime(Timestamp stateDateTime)
	{
		this.stateDateTime = stateDateTime;
	}

	public void setGreenhouseId(int greenhouseId)
	{
		this.greenhouseId = greenhouseId;
	}

	public void setCarbonDioxideGeneratorId(int carbonDioxideGeneratorId)
	{
		this.carbonDioxideGeneratorStateId = carbonDioxideGeneratorId;
	}

	public void setCarbonDioxideGeneratorOn(boolean carbonDioxideGeneratorOn)
	{
		isCarbonDioxideGeneratorOn = carbonDioxideGeneratorOn;
	}
}
