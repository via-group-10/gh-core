package dk.grinhouse.models;

import javax.persistence.*;

@Entity
@Table(name = "MeasurementType", schema = "dbo")
public class MeasurementType
{
	@Id
	@Enumerated(EnumType.STRING)
	@Column(name = "title", columnDefinition = "nvarchar(100)")
	private MeasurementTypeEnum title;

	public MeasurementTypeEnum getTitle()
	{
		return title;
	}

	public void setTitle(MeasurementTypeEnum title)
	{
		this.title = title;
	}
}
