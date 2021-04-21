package dk.grinhouse.persistence.shared.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class MeasurementType
{
	@Id
	@Enumerated(EnumType.STRING)
	private MeasurementTypeEnum title;
}
