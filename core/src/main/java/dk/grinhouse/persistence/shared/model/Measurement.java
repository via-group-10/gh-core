package dk.grinhouse.persistence.shared.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "measurement")
public class Measurement
{
	@Id
	private long measurementId;

	private float measurementValue;

	private LocalDate measurementDateTime;

	@Column(name = "belongsTo")
	private int greenhouseId;

	@Column(name = "isOfType")
	@Enumerated(EnumType.STRING)
	private MeasurementTypeEnum measurementTypeEnum;
}
