package thyme.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "course")
public class Course {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@JsonProperty("id")
    private Integer id;
	
	@Column(name = "name")
	@JsonProperty("name")
    private String name;
	
	@Column(name = "number_of_weeks")
	@JsonProperty("numberOfWeeks")
    private Integer numberOfWeeks;
	
	@Column(name = "description")
	@JsonProperty("description")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumberOfWeeks() {
        return numberOfWeeks;
    }

    public void setNumberOfWeeks(Integer numberOfWeeks) {
        this.numberOfWeeks = numberOfWeeks;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((numberOfWeeks == null) ? 0 : numberOfWeeks.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (numberOfWeeks == null) {
			if (other.numberOfWeeks != null)
				return false;
		} else if (!numberOfWeeks.equals(other.numberOfWeeks))
			return false;
		return true;
	}

	@Override
    public String toString() {
        return "Course [id = " + id + ", name=" + name + ", Number of weeks= " + numberOfWeeks + ", Description= " + description + "]";
    }
}
