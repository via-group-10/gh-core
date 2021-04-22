package dk.grinhouse.core.shared.model;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "measurement", schema = "stage")
public class Measurement
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "measurementId")
	private int measurementId;

	@Column(name = "measuredValue")
	private float measuredValue;

	@Column(name = "measurementDateTime")
	private Timestamp measurementDateTime;

	@Column(name = "belongsTo")
	private int greenhouseId;

	@Column(name = "isOfType", columnDefinition = "nvarchar")
	@Enumerated(EnumType.STRING)
	private MeasurementTypeEnum measurementTypeEnum;

	public Measurement(float measuredValue, Timestamp measurementDateTime, int greenhouseId, MeasurementTypeEnum measurementTypeEnum)
	{
		this.measuredValue = measuredValue;
		this.measurementDateTime = measurementDateTime;
		this.greenhouseId = greenhouseId;
		this.measurementTypeEnum = measurementTypeEnum;
	}

	public Measurement()
	{

	}

	public int getGreenhouseId()
	{
		return greenhouseId;
	}

	public float getMeasurementValue()
	{
		return measuredValue;
	}

	public Timestamp getMeasurementDateTime()
	{
		return measurementDateTime;
	}

	public int getMeasurementId()
	{
		return measurementId;
	}

	public MeasurementTypeEnum getMeasurementTypeEnum()
	{
		return measurementTypeEnum;
	}

	public void setGreenhouseId(int greenhouseId)
	{
		this.greenhouseId = greenhouseId;
	}

	public void setMeasurementDateTime(Timestamp measurementDateTime)
	{
		this.measurementDateTime = measurementDateTime;
	}

	public void setMeasurementId(int measurementId)
	{
		this.measurementId = measurementId;
	}

	public void setMeasurementTypeEnum(MeasurementTypeEnum measurementTypeEnum)
	{
		this.measurementTypeEnum = measurementTypeEnum;
	}

	public void setMeasurementValue(float measurementValue)
	{
		this.measuredValue = measurementValue;
	}
}
