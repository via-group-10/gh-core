package dk.grinhouse.models;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "acState", schema = "dbo")
public class ACState
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "acStateId")
	private int acStateId;

	@Column(name = "isHeaterOn")
	private boolean isHeaterOn;

	@Column(name = "isCoolerOn")
	private boolean isCoolerOn;

	@Column(name = "stateDateTime")
	private Timestamp stateDateTime;

	@Column(name = "logs")
	private int greenhouseId;

	public int getGreenhouseId()
	{
		return greenhouseId;
	}

	public int getAcStateId()
	{
		return acStateId;
	}

	public Timestamp getStateDateTime()
	{
		return stateDateTime;
	}

	public void setGreenhouseId(int greenhouseId)
	{
		this.greenhouseId = greenhouseId;
	}

	public void setAcStateId(int acStateId)
	{
		this.acStateId = acStateId;
	}

	public void setCoolerOn(boolean coolerOn)
	{
		isCoolerOn = coolerOn;
	}

	public void setHeaterOn(boolean heaterOn)
	{
		isHeaterOn = heaterOn;
	}

	public void setStateDateTime(Timestamp stateDateTime)
	{
		this.stateDateTime = stateDateTime;
	}
}

