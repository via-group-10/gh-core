package dk.grinhouse.persistence.shared.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "HumidifierState")
public class HumidifierState
{
	@Id
	private int humidifierId;

	private boolean isHumidifierOn;

	private boolean isDehumidifierOn;

	private LocalDate stateDateTime;

	
}
