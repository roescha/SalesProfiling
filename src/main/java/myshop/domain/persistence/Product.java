package myshop.domain.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product implements Comparable<Product> {
	@Id
	@Column(name = "line_num")
	private int lineNumber;

	@Column(name = "long_name", nullable = false)
	private String longname;

	@Column(name = "short_name", nullable = false)
	private String shortname;

	public Product() {
	}

	public Product(int lineNumber, String longname, String shortname) {
		this.lineNumber = lineNumber;
		this.longname = longname;
		this.shortname = shortname;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getLongname() {
		return longname;
	}

	public void setLongname(String longname) {
		this.longname = longname;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + lineNumber;
		result = prime * result + ((longname == null) ? 0 : longname.hashCode());
		result = prime * result + ((shortname == null) ? 0 : shortname.hashCode());
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
		Product other = (Product) obj;
		if (lineNumber != other.lineNumber)
			return false;
		if (longname == null) {
			if (other.longname != null)
				return false;
		} else if (!longname.equals(other.longname))
			return false;
		if (shortname == null) {
			if (other.shortname != null)
				return false;
		} else if (!shortname.equals(other.shortname))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Product [line_num=" + lineNumber + ", longName=" + longname + "]";
	}

	@Override
	public int compareTo(Product paramT) {
		return this.longname.compareTo(paramT.longname);
	}

}
