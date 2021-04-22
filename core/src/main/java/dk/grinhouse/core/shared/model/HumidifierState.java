package dk.grinhouse.core.shared.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "humidifierState", schema = "stage")
public class HumidifierState
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "humidifierId")
	private int humidifierId;

	@Column(name = "isHumidifierOn")
	private boolean isHumidifierOn;

	@Column(name = "isDehumidifierOn")
	private boolean isDehumidifierOn;

	@Column(name = "stateDateTime")
	private Timestamp stateDateTime;

	@Column(name = "logs")
	private int greenhouseId;

	public int getGreenhouseId()
	{
		return greenhouseId;
	}

	public Timestamp getStateDateTime()
	{
		return stateDateTime;
	}

	public int getHumidifierId()
	{
		return humidifierId;
	}

	public void setGreenhouseId(int greenhouseId)
	{
		this.greenhouseId = greenhouseId;
	}

	public void setStateDateTime(Timestamp stateDateTime)
	{
		this.stateDateTime = stateDateTime;
	}

	public void setDehumidifierOn(boolean dehumidifierOn)
	{
		isDehumidifierOn = dehumidifierOn;
	}

	public void setHumidifierId(int humidifierId)
	{
		this.humidifierId = humidifierId;
	}

	public void setHumidifierOn(boolean humidifierOn)
	{
		isHumidifierOn = humidifierOn;
	}
}
